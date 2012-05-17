/*******************************************************************************
 * Copyright 2012 Christian Ternes and Thorsten Volland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
