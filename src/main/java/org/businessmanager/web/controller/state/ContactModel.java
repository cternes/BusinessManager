package org.businessmanager.web.controller.state;

import org.businessmanager.domain.Contact;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Christian Ternes
 *
 */
@Component
@Scope("conversation.access")
public class ContactModel extends AbstractModel<Contact> {

}
