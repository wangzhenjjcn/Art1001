package com.aladdin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class MyCORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpresponse = (HttpServletResponse) response;
        httpresponse.setHeader("Access-Control-Allow-Origin", "*");
        httpresponse.setHeader("Access-Control-Allow-Methods", "*,DELETE,PUT,POST,GET");
        httpresponse.setHeader("Access-Control-Max-Age", "3600");
        httpresponse.setHeader("Access-Control-Allow-Headers", "content-type,access_token");
        httpresponse.setHeader("Access-Control-Allow-Credentials","true");
        chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
