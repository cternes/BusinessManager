package org.businessmanager.web.controller.page.contact;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.businessmanager.aop.annotation.ErrorHandled;
import org.businessmanager.domain.Address;
import org.businessmanager.domain.Address.AddressType;
import org.businessmanager.domain.Contact;
import org.businessmanager.domain.ContactItem;
import org.businessmanager.domain.Email;
import org.businessmanager.domain.Phone;
import org.businessmanager.service.ContactService;
import org.businessmanager.web.bean.ContactBean;
import org.businessmanager.web.bean.ContactItemBean;
import org.businessmanager.web.controller.AbstractPageController;
import org.businessmanager.web.controller.AddressManagementController;
import org.businessmanager.web.controller.state.AddressModel;
import org.businessmanager.web.jsf.helper.ResourceBundleProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("contactEditController")
@Scope("view")
public class ContactEditController extends AbstractPageController {

	@Autowired
	private ContactService contactService;
	
	@Autowired
	private AddressModel addressModel;
	
	@Autowired
	AddressManagementController addressController;

	private ContactBean bean = new ContactBean();
	private List<ContactItemBean> emailList = new ArrayList<ContactItemBean>();
	private ContactItemBean selectedEmail;
	private List<ContactItemBean> phoneList = new ArrayList<ContactItemBean>();
	private ContactItemBean selectedPhone;
	
	private List<String> availableScopes = new ArrayList<String>();
	private List<String> avaliableSalutations = new ArrayList<String>();
	
	@PostConstruct
	public void init() {
		List<AddressType> aAvailableAddressTypeList = new ArrayList<AddressType>();
		aAvailableAddressTypeList.add(AddressType.SCOPE_BILLING);
		aAvailableAddressTypeList.add(AddressType.SCOPE_SHIPPING);
		addressController.setAvailableAddressTypes(aAvailableAddressTypeList);
		
		emailList.add(new ContactItemBean(true));
		phoneList.add(new ContactItemBean(true));

		availableScopes.add(ResourceBundleProducer.getString("scope_private"));
		availableScopes.add(ResourceBundleProducer
				.getString("scope_commercial"));
		availableScopes.add(ResourceBundleProducer.getString("scope_misc"));

		avaliableSalutations.add(ResourceBundleProducer
				.getString("salutation_mr"));
		avaliableSalutations.add(ResourceBundleProducer
				.getString("salutation_mrs"));
	}

	@ErrorHandled
	public String saveContact() {
		if (validateInput()) {
			Contact contact = createContact();
			fillContact(contact);
			saveAddressList(contact);
			contactService.addContact(contact);

			addMessage(FacesMessage.SEVERITY_INFO,
					"editcontact_success_contact_saved");

			return navigationHelper.getContactmanagement();
		}

		return "#";
	}

	private void saveAddressList(Contact contact) {
		List<Address> assignedAddressList = addressController.getAssignedAddressList();
		
		for (Address address : assignedAddressList) {
			address.setContact(contact);
			contact.getAddresses().add(address);
		}
	}

	private void fillContact(Contact contact) {
		if (!StringUtils.isEmpty(bean.getCompany())) {
			contact.setCompany(bean.getCompany());
		}

		if (!StringUtils.isEmpty(bean.getJobTitle())) {
			contact.setJobTitle(bean.getJobTitle());
		}

		for (ContactItemBean contactItem : emailList) {
			Email email = new Email();
			addItemToContact(contact, contactItem, email);
		}

		for (ContactItemBean contactItem : phoneList) {
			Phone phone = new Phone();
			addItemToContact(contact, contactItem, phone);
		}
	}

	private void addItemToContact(Contact contact, ContactItemBean contactItem,
			ContactItem item) {

		// only add contact item if value is given
		if (contactItem == null || "".equals(contactItem.getValue())) {
			return;
		}

		item.setIsDefault(contactItem.getIsDefault());
		item.setScope(contactItem.getScope());
		item.setValue(contactItem.getValue());
		item.setContact(contact);
		contact.getContactItemList().add(item);
	}

	private boolean validateInput() {
		boolean isValid = true;

		if (bean.getFirstname() == null || bean.getFirstname().isEmpty()) {
			addMessage(FacesMessage.SEVERITY_WARN,
					"editcontact_warn_no_firstname");
			isValid = false;
		}

		if (bean.getLastname() == null || bean.getLastname().isEmpty()) {
			addMessage(FacesMessage.SEVERITY_WARN,
					"editcontact_warn_no_lastname");
			isValid = false;
		}

		for (ContactItemBean item : getEmailList()) {
			if (!item.getValue().isEmpty() && !EmailValidator.getInstance().isValid(item.getValue())) {
				addExtendedMessage(FacesMessage.SEVERITY_WARN,
						"editcontact_warn_invalid_mail", "(" + item.getValue()
								+ ")");
				isValid = false;
			}
		}

		return isValid;
	}

	public ContactBean getBean() {
		return bean;
	}

	private Contact createContact() {
		Contact contact = new Contact(bean.getFirstname(), bean.getLastname());
		contact.setSalutation(bean.getSalutation());
		contact.setTitle(bean.getTitle());
		return contact;
	}

	public List<ContactItemBean> getEmailList() {
		return emailList;
	}

	public void addEmail() {
		emailList.add(new ContactItemBean());
	}

	public void removeEmail() {
		if (selectedEmail != null) {
			emailList.remove(selectedEmail);

			if (selectedEmail.getIsDefault()) {
				ContactItemBean contactItemBean = emailList.get(0);
				if (contactItemBean != null) {
					contactItemBean.setIsDefault(true);
				}
			}
		}
	}

	public void setSelectedEmail(ContactItemBean selectedEmail) {
		this.selectedEmail = selectedEmail;
	}

	public ContactItemBean getSelectedEmail() {
		return selectedEmail;
	}

	public boolean getShowRemoveEmailButton() {
		if (emailList.size() > 1) {
			return true;
		}
		return false;
	}

	public List<String> getAvailableScopes() {
		return availableScopes;
	}

	public List<ContactItemBean> getPhoneList() {
		return phoneList;
	}

	public void addPhone() {
		phoneList.add(new ContactItemBean());
	}

	public void removePhone() {
		if (selectedPhone != null) {
			phoneList.remove(selectedPhone);
		}
	}

	public void setSelectedPhone(ContactItemBean selectedPhone) {
		this.selectedPhone = selectedPhone;
	}

	public ContactItemBean getSelectedPhone() {
		return selectedPhone;
	}

	public boolean getShowRemovePhoneButton() {
		if (phoneList.size() > 1) {
			return true;
		}
		return false;
	}

	public List<String> getAvailableSalutations() {
		return avaliableSalutations;
	}

	public AddressModel getAddressModel() {
		if (addressModel.getEntityList() == null) {
			addressModel.setEntityList(new ArrayList<Address>());
		}
		return addressModel;
	}
	
	public void addAddress() {
		addressModel.getEntityList().add(new Address());
	}
	
}
