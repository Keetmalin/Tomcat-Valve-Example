package com.keetmalin.tomcat;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Keet Sugathadasa
 */
public class TomcatValve extends ValveBase {
    private static final Logger logger = Logger.getLogger(TomcatValve.class.getName());

    public void invoke(Request request, Response response) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = request.getRequest();

        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();

        logger.info("Receiving request");

        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            logger.log(Level.INFO, "Header --> {0} Value --> {1}", new Object[]{header, httpServletRequest.getHeader(header)});
        }

        getNext().invoke(request, response);
    }
}
