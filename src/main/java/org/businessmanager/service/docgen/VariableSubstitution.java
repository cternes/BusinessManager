package org.businessmanager.service.docgen;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.Br;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Text;

public class VariableSubstitution {

	private Text text;
	private Object parent;
	private Map<String, Object> replacements;
	private Integer line = null;
	private RPr style = null;

	public VariableSubstitution(Text text, Object parent,
			Map<String, Object> replacements) {
		this.text = text;
		this.parent = parent;
		this.replacements = replacements;

		if (parent instanceof R) {
			this.style = ((R) parent).getRPr();
		}
	}

	public void execute() {
		String value = text.getValue();
		value = replaceTokens(value);

		String[] lines = value.split("\n");
		int index = 0;
		for (String line : lines) {
			if (index > 0) {
				if (parent instanceof R) {
					R run = (R) parent;
					List<Object> content = run.getContent();
					int textIndex = content.indexOf(text.getParent());
					text = XmlUtils.deepCopy(text);
					run.getContent().add(textIndex + index, text);
					Br createBr = Context.getWmlObjectFactory().createBr();
					run.getContent().add(textIndex + index, createBr);
				}
			}

			text.setSpace("preserve");
			text.setValue(line);
			index++;
		}

		if (style != null && parent instanceof R) {
			((R) parent).setRPr(style);
		}
	}

	private String replaceTokens(String text) {
		Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
		Matcher matcher = pattern.matcher(text);
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			String[] variableInfo = matcher.group(1).split("\\[");
			Object replacement = replacements.get(variableInfo[0]);
			if (replacement != null) {
				if (variableInfo.length > 1) {
					handleVariableOptions(variableInfo[1].replace("]", ""));
				}

				String replacementString = replacement.toString();
				if (line != null) {
					String[] split = replacementString.split("\n");

					if (line > split.length || line < 1) {
						replacementString = "";
					} else {
						replacementString = split[line - 1];
					}
				}
				matcher.appendReplacement(buffer, "");
				buffer.append(replacementString);
			}
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}

	private void handleVariableOptions(String optionsString) {
		String[] options = optionsString.split(",");

		for (String option : options) {
			String[] optionHolder = option.split("=");
			String key = optionHolder[0];
			String value = optionHolder[1];
			setOption(key, value);
		}
	}

	private void setOption(String key, String value) {
		if ("line".equals(key)) {
			line = Integer.valueOf(value);
		} else if ("i".equals(key)) {
			BooleanDefaultTrue italic =  toBooleanDefaultTrue(value);
			if(italic != null) {
				style.setI(italic);
			}
		} else if("b".equals(key)) {
			BooleanDefaultTrue bold =  toBooleanDefaultTrue(value);
			if(bold != null) {
				style.setB(bold);
			}
		}
	}

	private BooleanDefaultTrue toBooleanDefaultTrue(String value) {
		BooleanDefaultTrue x = new BooleanDefaultTrue();

		if ("0".equals(value)) {
			x.setVal(false);
		} else if ("1".equals(value)) {
			x.setVal(true);
		} else {
			return null;
		}
		return x;
	}
	
	
}
