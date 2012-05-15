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

		if (obj != null && obj instanceof HasDefault && clazz.equals(obj.getClass())) {
			Boolean isDefault = ((HasDefault) obj).getIsDefault();
			if(isDefault == null) isDefault = false;
			
			if (predicate != null) {
				return isDefault
						&& predicate.evaluate(obj);
			} else {
				return isDefault;
			}
		}
		return false;
	}

}
