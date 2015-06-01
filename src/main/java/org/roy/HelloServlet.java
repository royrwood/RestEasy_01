package org.roy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// Assumes that context path is "RestEasy_01" (set in Tomcat plugin section of pom.xml)
// Assumes that servlet mapping is "servlets/HelloServlet" (set in WEB-INF/web.xml)
// curl http://localhost:8080/RestEasy_01/servlets/HelloServlet ; echo

public class HelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println("Hello World.");
    }
}