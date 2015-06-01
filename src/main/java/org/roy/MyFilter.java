package org.roy;

import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletResponse;

// A sample servlet filter (installed via WEB-INF/web.xml)
// To see output, look at Tomcat logs (tail -f ~/Java/apache-tomcat-8.0.21/logs/catalina.out)

public class MyFilter implements javax.servlet.Filter {
    public FilterConfig filterConfig;

    public void doFilter(final ServletRequest request, final ServletResponse response, FilterChain chain) throws java.io.IOException, javax.servlet.ServletException {
        System.out.println("Entering MyFilter.doFilter...");

        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("X-Dogcow", "Moof");

        chain.doFilter(request, response);

        System.out.println("Exiting MyFilter.doFilter.");
    }

    public void init(final FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        // Nothing to do
    }
}