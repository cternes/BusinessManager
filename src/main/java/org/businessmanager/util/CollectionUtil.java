package org.businessmanager.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.businessmanager.collections.UnmodifiableList;

public class CollectionUtil {

	public static <T> List<T> typedUnmodifiableSubList(List<?> sourceList,
			final Class<T> clazz) {

		List<T> resultList = new ArrayList<T>();
		for (Iterator<?> it = sourceList.iterator(); it.hasNext();) {
			Object curObj = it.next();
			if(clazz.equals(curObj.getClass())) {
				resultList.add(clazz.cast(curObj));
			}
		}
		return new UnmodifiableList<T>(resultList);
	}
}
