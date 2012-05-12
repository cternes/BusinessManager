package org.businessmanager.util;

import org.apache.commons.collections.Predicate;
import org.businessmanager.domain.HasDefault;

public class DefaultItemPredicate implements Predicate {

	private Class<?> clazz;
	private Predicate predicate = null;

	public DefaultItemPredicate(Class<? extends HasDefault> typeClass) {
		this(typeClass, null);
	}

	public DefaultItemPredicate(Class<? extends HasDefault> typeClass,
			Predicate predicate) {
		this.clazz = typeClass;
		this.predicate = predicate;
	}

	@Override
	public boolean evaluate(Object obj) {

		if (obj instanceof HasDefault && clazz.equals(obj.getClass())) {
			if (predicate != null) {
				return ((HasDefault) obj).getIsDefault()
						&& predicate.evaluate(obj);
			} else {
				return ((HasDefault) obj).getIsDefault();
			}
		}
		return false;
	}

}
