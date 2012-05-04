package org.businessmanager.error;

/**
 * Thrown if someone tries to add a {@link User} with a username/email which already exists in the system. 
 * 
 * @author Christian Ternes
 */
public class DuplicateUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateUserException(String message) {
		super(message);
	}
	
	public DuplicateUserException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
