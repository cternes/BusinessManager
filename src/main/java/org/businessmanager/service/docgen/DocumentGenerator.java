package org.businessmanager.service.docgen;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.convert.out.pdf.viaXSLFO.PdfSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.model.structure.HeaderFooterPolicy;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;


public class DocumentGenerator {
	private static String INVOICE_POS_MARKER = "rgpos";

	private Map<String, Object> replacements;
	private File templateFile;

	public DocumentGenerator(Map<String, Object> replacements, File templateFile) {
		this.replacements = replacements;
		this.templateFile = templateFile;
	}

	// this function is not adapted completely yet - not ready for use!
	public void createDocument() {
		try {
			WordprocessingMLPackage template = WordprocessingMLPackage
					.load(templateFile);

			handleMainDocumentPart(template);
			handleHeaderFooter(template);

			// save result as docx
			template.save(new File("output.docx"));

			// save result as pdf
			template.setFontMapper(new IdentityPlusMapper());
			org.docx4j.convert.out.pdf.PdfConversion c = new org.docx4j.convert.out.pdf.viaXSLFO.Conversion(
					template);

			OutputStream os = new java.io.FileOutputStream("output.pdf");
			c.output(os, new PdfSettings());

			System.out.println("Done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Errors");
		}
	}

	private void handleMainDocumentPart(WordprocessingMLPackage template)
			throws JAXBException {
		MainDocumentPart mainDocumentPart = template.getMainDocumentPart();

		org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document) mainDocumentPart
				.getJaxbElement();
		Body body = wmlDocumentEl.getBody();
		new TraversalUtil(body, new WmlPackageFixer());

		handleInvoicePosTable(mainDocumentPart);
		substituteFields(mainDocumentPart, replacements);
	}

	private void handleHeaderFooter(WordprocessingMLPackage template) {
		List<SectionWrapper> sectionWrappers = template.getDocumentModel()
				.getSections();

		for (SectionWrapper sw : sectionWrappers) {
			HeaderFooterPolicy hfp = sw.getHeaderFooterPolicy();

			substituteFields(hfp.getFirstHeader(), replacements);
			substituteFields(hfp.getDefaultHeader(), replacements);
			substituteFields(hfp.getEvenHeader(), replacements);

			substituteFields(hfp.getFirstFooter(), replacements);
			substituteFields(hfp.getDefaultFooter(), replacements);
			substituteFields(hfp.getEvenFooter(), replacements);
		}
	}

	@SuppressWarnings("unchecked")
	private void handleInvoicePosTable(MainDocumentPart mainDocumentPart)
			throws JAXBException {
		final String XPATH_TO_BOOKMARK_NODES = "//w:bookmarkStart";

		Object ivposRpl = this.replacements.get("ivpos");
		if (!(ivposRpl instanceof List)) {
			return;
		}

		List<Object> ivpos = (List<Object>) ivposRpl;

		List<?> bookmarks = mainDocumentPart.getJAXBNodesViaXPath(
				XPATH_TO_BOOKMARK_NODES, true);

		for (Object obj : bookmarks) {
			CTBookmark bookmark = getBookmark((JAXBElement<?>) obj);

			if (bookmark != null
					&& INVOICE_POS_MARKER.equals(bookmark.getName())) {
				Tr templateRow = Docx4jUtil.getParentOfType(bookmark, Tr.class);
				Tbl table = Docx4jUtil.getParentOfType(templateRow, Tbl.class);
				int pos = table.getContent().indexOf(templateRow);

				for (Object posObj : ivpos) {
					if (!(posObj instanceof Map)) {
						continue;
					}

					Map<String, Object> posValues = (Map<String, Object>) posObj;

					Tr trMoved = XmlUtils.deepCopy(templateRow);
					table.getContent().add(pos, trMoved);
					substituteFields(trMoved, posValues);
					pos++;
				}

				table.getContent().remove(templateRow);

			}
		}
	}

	private CTBookmark getBookmark(JAXBElement<?> jaxbObj) {
		if (!(jaxbObj.getValue() instanceof CTBookmark)) {
			return null;
		}

		CTBookmark bookmark = (org.docx4j.wml.CTBookmark) jaxbObj.getValue();
		return bookmark;
	}

	private void substituteFields(Object object,
			Map<String, Object> replacements) {
		if (object == null) {
			return;
		}

		List<Object> children = Docx4jUtil.getChildren(object);

		if (children != null) {
			List<Object> childrenCopy = new ArrayList<Object>(children);
			for (int i = 0; i < childrenCopy.size(); i++) {
				Object child = XmlUtils.unwrap(childrenCopy.get(i));
				if (child instanceof Text) {
					new VariableSubstitution((Text) child, object, replacements)
							.execute();
				} else if (child instanceof P) {
					handleParagraphVariables((P) child, replacements);
				} else {
					substituteFields(child, replacements);
				}
			}
		}
	}

	private void handleParagraphVariables(P paragraph,
			Map<String, Object> replacements) {
		List<Object> content = paragraph.getContent();
		List<Object> newContent = new ArrayList<Object>();
		List<R> candidates = new ArrayList<R>();
		String paragraphText = "";

		boolean candidateActive = false;
		for (Object item : content) {
			String tmpTxt = paragraphText;
			if (!(item instanceof R)) {
				continue;
			}
			R run = (R) item;
			substituteFields(run, replacements);

			String runText = Docx4jUtil.extractText(run);
			tmpTxt += runText;

			boolean alreadyAdded = false;
			if (containsVariables(tmpTxt)) {
				candidates.add(run);
				List<R> newRun = combineRuns(candidates, tmpTxt);
				substituteFields(newRun.get(0), replacements);
				newContent.add(newRun.get(0));
				candidates.clear();
				candidateActive = false;

				if (newRun.size() > 1) {
					runText = Docx4jUtil.extractText(newRun.get(1));
					run = newRun.get(1);
					paragraphText = runText;
				} else {
					paragraphText = "";
					continue;
				}
			} else {
				paragraphText = tmpTxt;
			}

			if (runText != null
					&& (runText.contains("$") || runText.contains("{"))) {
				candidates.add(run);
				candidateActive = true;
			} else if (candidateActive) {
				candidates.add(run);
			} else if (!alreadyAdded) {
				newContent.add(run);
				paragraphText = "";
			}

		}
		newContent.addAll(candidates);
		paragraph.getContent().clear();
		paragraph.getContent().addAll(newContent);
	}

	private List<R> combineRuns(List<R> candidates, String text) {
		if (candidates == null || candidates.size() < 1) {
			return null;
		}

		if (candidates.size() < 2) {
			return candidates;
		}

		int indexOf = text.indexOf("}");

		String textIncludingVariable = text.substring(0, indexOf + 1);
		List<R> result = new ArrayList<R>();
		R r = candidates.get(0);
		Docx4jUtil.substituteText(r, textIncludingVariable);
		result.add(r);

		String textAfterVariable = text.substring(indexOf + 1);

		if (!"".equals(textAfterVariable)) {
			R r2 = candidates.get(candidates.size() - 1);
			Docx4jUtil.substituteText(r2, textAfterVariable);
			result.add(r2);
		}
		return result;
	}

	private boolean containsVariables(String text) {
		Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
		Matcher matcher = pattern.matcher(text);
		return matcher.find();
	}
}
