package com.neusoft.core.restful;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.neusoft.core.util.SpringMVCUtil;

/**
 * 类SpringMVCFilter.java的实现描述：配合SpringMVCUtil获得请求的request
 * 
 * @author Administrator 2017年5月8日 上午10:15:17
 */
public class SpringMVCFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                              ServletException {
        SpringMVCUtil.setRequest(request);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
