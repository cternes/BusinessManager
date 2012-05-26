package org.businessmanager.util;

import java.util.List;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Expression;

public class QueryUtil {

	public static <T> T getFirstResult(JPAQuery query, Expression<T> expr) {
		List<T> list = query.list(expr);
		if(list.size() >  0) {
			return list.get(0);
		}
		return null;
	}
}
