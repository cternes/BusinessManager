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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.Predicate;
import org.businessmanager.collections.UnmodifiableList;

public class CollectionUtil {

	public static <T> List<T> typedUnmodifiableSubList(List<?> sourceList,
			final Class<T> clazz) {

		List<T> resultList = new ArrayList<T>();
		for (Iterator<?> it = sourceList.iterator(); it.hasNext();) {
			Object curObj = it.next();
			if (clazz.equals(curObj.getClass())) {
				resultList.add(clazz.cast(curObj));
			}
		}
		return new UnmodifiableList<T>(resultList);
	}

	public static <T> List<T> predicateUnmodifiableSubList(List<T> sourceList,
			Predicate predicate) {
		List<T> resultList = new ArrayList<T>();
		
		for (Iterator<T> it = sourceList.iterator(); it.hasNext();) {
			T curObj = it.next();
			
			if(predicate.evaluate(curObj)) {
				resultList.add(curObj);
			}
		}
		return new UnmodifiableList<T>(resultList);
	}
}
