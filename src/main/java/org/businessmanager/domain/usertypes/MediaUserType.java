package org.businessmanager.domain.usertypes;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;
import org.springframework.http.MediaType;

public class MediaUserType implements UserType {

	@Override
	public Object assemble(Serializable arg0, Object arg1)
			throws HibernateException {
		return null;
	}

	@Override
	public Object deepCopy(Object arg0) throws HibernateException {
		if (arg0 == null)
			return null;
		else
			return MediaType.valueOf(arg0.toString());
	}

	@Override
	public Serializable disassemble(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object arg0, Object arg1) throws HibernateException {
		if (arg0 == null && arg1 == null) {
			return true;
		} else if (arg0 != null) {
			return arg0.equals(arg1);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode(Object arg0) throws HibernateException {
		if (arg0 == null) {
			return 0;
		} else
			return arg0.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object nullSafeGet(ResultSet arg0, String[] arg1,
			SessionImplementor arg2, Object arg3) throws HibernateException,
			SQLException {

		assert arg1.length == 1;
		String mediaType = StringType.INSTANCE.get(arg0, arg1[0], arg2)
				.toString();

		return MediaType.valueOf(mediaType);
	}

	@Override
	public void nullSafeSet(PreparedStatement arg0, Object arg1, int arg2,
			SessionImplementor arg3) throws HibernateException, SQLException {

		if (arg1 == null) {
			StringType.INSTANCE.set(arg0, null, arg2, arg3);
		} else {
			MediaType mediaType = (MediaType) arg1;
			StringType.INSTANCE.set(arg0, mediaType.toString(), arg2, arg3);
		}

	}

	@Override
	public Object replace(Object arg0, Object arg1, Object arg2)
			throws HibernateException {
		return arg0;
	}

	@Override
	public Class returnedClass() {
		return MediaType.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { StringType.INSTANCE.sqlType() };
	}

}
