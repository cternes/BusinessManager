package org.businessmanager.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.businessmanager.util.CollectionUtil;

/**
 * @author Christian Ternes
 * 
 */
@Entity
public class Contact extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String firstname;

	@Column
	private String lastname;

	@Column
	private String title;

	@Column
	private String jobTitle;

	@Column
	private String image;

	@Column
	private String company;

	@Column
	private Integer salutation;

	@OneToMany(cascade = CascadeType.ALL)
	private List<ContactItem> contactItems = new ArrayList<ContactItem>();

	Contact() {
	}

	public Contact(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getSalutation() {
		return salutation;
	}

	public void setSalutation(Integer salutation) {
		this.salutation = salutation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result
				+ ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result
				+ ((salutation == null) ? 0 : salutation.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (salutation == null) {
			if (other.salutation != null)
				return false;
		} else if (!salutation.equals(other.salutation))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public List<ContactItem> getContactItemList() {
		return contactItems;
	}

	public void setContactItemList(List<ContactItem> contactItems) {
		this.contactItems = contactItems;
	}

	/**
	 * Returns list of e-mail addresses of contact. List is read-only! Please
	 * use {@link #setContactItems(List)} to modify contact items.
	 * 
	 * @return list of e-mail addresses
	 */
	public List<Email> getEmailList() {
		return CollectionUtil.typedUnmodifiableSubList(getContactItemList(),
				Email.class);
	}

	/**
	 * Returns list of phone numbers of contact. List is read-only! Please use
	 * {@link #setContactItems(List)} to modify contact items.
	 * 
	 * @return list of phone numbers
	 */
	public List<Phone> getPhoneList() {
		return CollectionUtil.typedUnmodifiableSubList(getContactItemList(),
				Phone.class);
	}

	/**
	 * Returns list of fax numbers of contact. List is read-only! Please use
	 * {@link #setContactItems(List)} to modify contact items.
	 * 
	 * @return list of fax numbers
	 */
	public List<Fax> getFaxList() {
		return CollectionUtil.typedUnmodifiableSubList(getContactItemList(),
				Fax.class);
	}
	
	/**
	 * Returns list of fax numbers of contact. List is read-only! Please use
	 * {@link #setContactItems(List)} to modify contact items.
	 * 
	 * @return list of website urls
	 */
	public List<Website> getWebsiteList() {
		return CollectionUtil.typedUnmodifiableSubList(getContactItemList(),
				Website.class);
	}
}
