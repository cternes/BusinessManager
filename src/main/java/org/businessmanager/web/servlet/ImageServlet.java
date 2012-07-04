/*******************************************************************************
 * Copyright 2012 Christian Ternes and Thorsten Volland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.businessmanager.web.servlet;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A servlet to workaround a strange PrimeFaces bug.
 * 
 * We have to store dynamically created images as byte[] in the session map with a random UUID. That random UUID is passed to the {@link ImageServlet} 
 * and will be retrieved from the session map and rendered as image.  
 * 
 */ 
public class ImageServlet extends HttpServlet {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 8406015696157225147L;
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get UUID from request.
		String imageKey = request.getParameter("key");

		// Check if UUID is supplied to the request.
		if (imageKey == null) {
			// Do your thing if the ID is not supplied to the request.
			// Throw an exception, or send 404, or show default/warning image, or just ignore it.
			response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			return;
		}

		byte[] imageContent = (byte[]) request.getSession().getAttribute(imageKey);
		request.getSession().removeAttribute(imageKey);

		// Init response.
		response.reset();
		response.setBufferSize(DEFAULT_BUFFER_SIZE);
		//response.setContentType("image/png");
		response.setHeader("Content-Length", String.valueOf(imageContent.length));
		//response.setHeader("Content-Disposition", "inline; filename=\"" + "chart_"+imageKey + "\"");
		BufferedOutputStream output = null ;

		try{
			output= new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
			output.write(imageContent);
		} catch (Exception e) {
			logger.error("An error occurred while streaming image. Error was: ", e);
		} finally{
			close(output);
		}
	}

	private void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				logger.error("An error occurred while closing resource. Error was: ", e);
			}
		}
	}
}
