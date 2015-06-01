package org.roy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

// Assumes that context path is "RestEasy_01" (set in Tomcat plugin section of pom.xml)
// Assumes that servlet mapping is "resteasy" (set in WEB-INF/web.xml)

@Path("/HelloWorld")
public class HelloWorld {

    // curl http://localhost:8080/RestEasy_01/resteasy/HelloWorld/test01/moof ; echo
    @GET
    @Path("/test01/{param}")
    public Response test01(@PathParam("param") String msg) {

        System.out.println("HelloWorld:printMessage: msg = " + msg);

        String result = "Restful example : " + msg;

        return Response.status(200).entity(result).build();

    }

    // curl -H "Accept: application/json" http://localhost:8080/RestEasy_01/resteasy/HelloWorld/pojo/123 ; echo
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/pojo/{id}")
    public SimplePOJO getPOJO(@PathParam("id") String id) {
        System.out.println("Received param id = " + id);

        SimplePOJO pojo = null;
        int intID = Integer.parseInt(id);

        pojo = new SimplePOJO();
        pojo.setFirstName("Roy");
        pojo.setLastName("Wood");
        pojo.setId(intID);

        return pojo;
    }

}