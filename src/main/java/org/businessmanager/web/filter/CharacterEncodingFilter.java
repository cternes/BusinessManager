package org.businessmanager.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * This filter makes sure that every request and every response will be character encoded with UTF-8.
 * <p>
 * To use it register the filter in <i>web.xml</i>:
 * <pre>
 * <code>
 	&lt;filter&gt;
        &lt;filter-name>Character Encoding Filter&lt;/filter-name&gt;
        &lt;filter-class>org.businessmanager.web.servlet.filter.CharacterEncodingFilter&lt;/filter-class&gt;
    &lt;/filter&gt;
    &lt;filter-mapping&gt;
        &lt;filter-name>Character Encoding Filter&lt;/filter-name&gt;
        &lt;servlet-name>Faces Servlet&lt;/servlet-name&gt;
    &lt;/filter-mapping&gt;
 * </code>
 * </pre>
 * 
 * @author Christian Ternes
 *
 */
public class CharacterEncodingFilter implements Filter {

	@Override
	public void init(FilterConfig theFilterConfig) throws ServletException {
		//empty
	}

	@Override
	public void doFilter(ServletRequest theRequest, ServletResponse theResponse, FilterChain theChain) throws IOException, ServletException {
		if(theRequest.getCharacterEncoding() == null) {
		    theRequest.setCharacterEncoding("UTF-8");
		}
		if(theResponse.getCharacterEncoding() == null) {
			theResponse.setCharacterEncoding("UTF-8");
		}
		theChain.doFilter(theRequest, theResponse);
	}

	@Override
	public void destroy() {
		//empty
	}

}
