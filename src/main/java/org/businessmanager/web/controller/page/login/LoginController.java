package org.businessmanager.web.controller.page.login;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.businessmanager.web.jsf.helper.ResourceBundleProducer;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;


/**
 * @author Christian Ternes
 *
 */
@Component("loginController")
@Scope("request")
public class LoginController implements PhaseListener {

	private static final long serialVersionUID = 6255054082630693853L;
	private final Log logger = LogFactory.getLog(getClass());
	 
	/**
	 *
	 * Redirects the login request directly to spring security check.
	 * Leave this method as it is to properly support spring security.
	 * 
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String doLogin() throws ServletException, IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("/j_spring_security_check");

		dispatcher.forward((ServletRequest) context.getRequest(),
				(ServletResponse) context.getResponse());

		FacesContext.getCurrentInstance().responseComplete();

		return null;
	}

	@Override
	public void afterPhase(PhaseEvent event) {
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 * 
	 * Do something before rendering phase.
	 */
	@Override
	public void beforePhase(PhaseEvent event) {
		logger.debug("Entering phase: renderResponse");
		Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(
				WebAttributes.AUTHENTICATION_EXCEPTION);
 
        if (e instanceof BadCredentialsException) {
        	logger.debug("Found exception in session map: "+e);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(
            		WebAttributes.AUTHENTICATION_EXCEPTION, null);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundleProducer.getString("login_failed"), ResourceBundleProducer.getString("login_failed")));
        }
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 * 
	 * In which phase you want to interfere?
	 */
	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
}
