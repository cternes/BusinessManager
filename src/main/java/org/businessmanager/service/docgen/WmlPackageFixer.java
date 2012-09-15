package org.businessmanager.service.docgen;

import java.util.List;

import org.docx4j.TraversalUtil;
import org.docx4j.TraversalUtil.CallbackImpl;
import org.docx4j.XmlUtils;
import org.jvnet.jaxb2_commons.ppp.Child;

public class WmlPackageFixer extends CallbackImpl {

    @Override
    public void walkJAXBElements(Object parent) {
            List<?> children = getChildren(parent);

            if (children != null) {
                    for (Object child: children) {
                            child = XmlUtils.unwrap(child);

                            // fix for JAXB Bug
                            ((Child) child).setParent(parent);

                            this.apply(child);

                            if (this.shouldTraverse(child)) {
                                    walkJAXBElements(child);
                            }
                    }
            }
    }

    @Override
    public List<Object> apply(Object element) {
            return null;
    }

    @Override
    public boolean shouldTraverse(Object o) {
            return true;
    }

    @Override
    public List<Object> getChildren(Object o) {
            return TraversalUtil.getChildrenImpl(o);
    }
}