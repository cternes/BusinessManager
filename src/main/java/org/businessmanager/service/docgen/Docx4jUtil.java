package org.businessmanager.service.docgen;

import java.io.StringWriter;
import java.util.List;

import org.docx4j.TextUtils;
import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.wml.Text;
import org.jvnet.jaxb2_commons.ppp.Child;

public class Docx4jUtil {
	@SuppressWarnings("unchecked")
	public static <T> T getParentOfType(Child child, Class<T> parentClass) {
		Object parent = child.getParent();

		if (parent == null) {
			return null;
		}

		if (parent.getClass().equals(parentClass)) {
			return (T) parent;
		}

		if (parent instanceof Child) {
			return getParentOfType((Child) parent, parentClass);
		}
		return null;
	}
	
	public static void substituteText(Object object, String text) {
		List<Object> children = getChildren(object);

		if (children != null) {
			for (Object child : children) {
				child = XmlUtils.unwrap(child);

				if (child instanceof Text) {
					((Text) child).setValue(text);
				} else {
					substituteText(child, text);
				}
			}
		}
	}
	
	public static List<Object> getChildren(Object o) {
		return TraversalUtil.getChildrenImpl(o);
	}
	
	public static String extractText(Object obj) {
		StringWriter sw = new StringWriter();
		try {
			TextUtils.extractText(obj, sw);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sw.getBuffer().toString();
	}
}
