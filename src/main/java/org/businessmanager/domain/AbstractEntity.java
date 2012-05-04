package org.businessmanager.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

/**
 * This abstract class provides common properties for entities like mutationUser and mutationTime.
 * <p/>
 * All entity classes should extend this class. 
 * 
 * @author Christian Ternes
 *
 */
@MappedSuperclass
public abstract class AbstractEntity implements MutationTracked {

	@Column
	private String mutationUser;
	
	@Column
	private Calendar mutationTime;
	
	@Enumerated(EnumType.STRING)
	private MutationType mutationType;
	
	@Column
	private Calendar validFrom;
	
	@Column
	private Calendar validUntil;

	@Override
	public void setMutationUser(String mutationUser) {
		this.mutationUser = mutationUser;
	}

	@Override
	public String getMutationUser() {
		return mutationUser;
	}

	@Override
	public void setMutationTime(Calendar mutationTime) {
		this.mutationTime = mutationTime;
	}

	@Override
	public Calendar getMutationTime() {
		return mutationTime;
	}

	@Override
	public void setMutationType(MutationType mutationType) {
		this.mutationType = mutationType;
	}

	@Override
	public MutationType getMutationType() {
		return mutationType;
	}

	@Override
	public void setValidFrom(Calendar validFrom) {
		this.validFrom = validFrom;
	}

	@Override
	public Calendar getValidFrom() {
		return validFrom;
	}

	@Override
	public void setValidUntil(Calendar validUntil) {
		this.validUntil = validUntil;
	}

	@Override
	public Calendar getValidUntil() {
		return validUntil;
	}
	
}
