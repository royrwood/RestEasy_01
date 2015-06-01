package org.roy;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context ;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

//import javax.annotation.Resource;
import javax.inject.Inject;

import java.sql.Connection;
import java.sql.Timestamp;
import javax.sql.DataSource;

import javax.naming.InitialContext;
//import javax.naming.Context;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.SQLDialect;

import static org.roy.generated.Tables.*;
import org.roy.generated.tables.records.PeopleRecord;


// Deploy to remote/existing Tomcat
// http://wiki.aiwsolutions.net/2014/02/20/deploy-web-application-to-remote-tomcat-7-server/
// https://www.mulesoft.com/tcat/tomcat-mysql

//Start/Stop Postgresql on a different port so it doesn't conflict with the Dollarama-local one....

// pg_ctl -o "-p 12345" -D /usr/local/var/postgres -l /usr/local/var/postgres/server.log start
// psql -p 12345 -d roydb
//      \dt
//      \d people
//      SELECT * FROM people;
//      \q
// pg_ctl -D /usr/local/var/postgres stop -s -m fast

// create table people(id serial primary key, firstname varchar(255), lastname varchar(255), tstamp timestamp default current_timestamp);


// JOOQ includes tools to auto-gen classes that map to the database.  Details are on the JOOQ site.
// There is probably a Maven task to drive this, and a nice way to automatically drop the generated sources into place, but I'm doing it manually right now.
// Look in the /jooq directory of the project folder.  The people.xml file drives things.
// java -classpath jooq-3.6.1.jar:jooq-meta-3.6.1.jar:jooq-codegen-3.6.1.jar:postgresql-9.4-1201.jdbc4.jar:. org.jooq.util.GenerationTool people.xml



// Assumes that context path is "RestEasy_01" (set in Tomcat plugin section of pom.xml)
// Assumes that servlet mapping is "resteasy" (set in WEB-INF/web.xml)

@Path("/jooq")
public class JOOQService {
// Tomcat does not support Rsource injection out-of-the-box:  http://stackoverflow.com/questions/10814557/tomcat-resource-annotations-api-annotation-stops-working-in-tomcat-7
//    @Resource(name="java:comp/env/jdbc/PeopleDB")
//    public DataSource ds;

    @Inject
    SimplePOJO injectedPOJO;

    @Context
    Application myApplication;


    // curl -H "Accept: application/json" http://localhost:8080/RestEasy_01/resteasy/jooq/2 ; echo
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public SimplePOJO getPOJO(@PathParam("id") String id) {
        System.out.println("Received param id = " + id);

        SimplePOJO pojo = null;
        int intID = Integer.parseInt(id);

//        if (ds == null) {
//            System.out.println("Oops-- ds == null");
//        }

        if (injectedPOJO == null) {
            System.out.println("Oops-- injectedPOJO == null");
        }
        else {
            System.out.println("Cool-- injectedPOJO = " + injectedPOJO);
        }

        if (myApplication == null) {
            System.out.println("Oops-- myApplication == null");
        }
        else {
            System.out.println("Cool-- myApplication is not null");
            RestApplication app = (RestApplication) myApplication;
            app.doStuff();
        }


        try {
            System.out.println("Fetching JNDI/JDBC Datasource....");

            InitialContext initCtx = new InitialContext();
            DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/PeopleDB");

            System.out.println("Opening JDBC connection....");

            try (Connection connection = ds.getConnection()) {
                System.out.println("Connecting to JOOQ....");
                DSLContext db = DSL.using(connection, SQLDialect.POSTGRES_9_4);

                System.out.println("Fetching record via JOOQ....");
                PeopleRecord p = db.selectFrom(PEOPLE).where(PEOPLE.ID.equal(intID)).fetchOne();

                if (p == null) {
                    System.out.println("No matches found in database");
                }
                else {
                    System.out.println("PeopleRecord:" + p.getId() + ", " + p.getFirstname() + ", " + p.getLastname() + "," + p.getTstamp());

                    pojo = p.into(SimplePOJO.class);
                    System.out.println("Fetched pojo: " + pojo);
                }
            }
        }
        catch (Exception ex) {
            System.out.println("Caught Exception during processing: " + ex.toString() + ":" + ex.getMessage());
        }

        return pojo;
    }


    // curl -v -X POST -H "Content-Type: application/json" -d '{"firstName":"Dog","lastName":"Cow"}' http://localhost:8080/RestEasy_01/resteasy/jooq/2 ; echo

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postJSON(@PathParam("id") String id, SimplePOJO pojo) {
        System.out.println("Enter postJSON...");
        System.out.println("Received param id = " + id);

        int intID = Integer.parseInt(id);

        System.out.println("Input pojo: " + pojo);

        try {
            System.out.println("Fetching JNDI/JDBC Datasource....");

            javax.naming.Context initCtx = new InitialContext();
            DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/PeopleDB");

            System.out.println("Opening JDBC connection....");

            try (Connection connection = ds.getConnection()) {
                System.out.println("Connecting to JOOQ....");
                DSLContext db = DSL.using(connection, SQLDialect.POSTGRES_9_4);

                System.out.println("Fetching record via JOOQ....");
                PeopleRecord p = db.selectFrom(PEOPLE).where(PEOPLE.ID.equal(intID)).fetchOne();

                if (p == null) {
                    System.out.println("No matches found in database");
                } else {
                    System.out.println("Found PeopleRecord:" + p.getId() + ", " + p.getFirstname() + ", " + p.getLastname() + ", " + p.getTstamp());

                    Timestamp currentTimestamp = new Timestamp(new java.util.Date().getTime());

                    p.setFirstname(pojo.getFirstName());
                    p.setLastname(pojo.getLastName());
                    p.setTstamp(currentTimestamp);

                    System.out.println("Updating PeopleRecord....");

                    p.store();
                }
            }
        }
        catch (Exception ex) {
            System.out.println("Caught Exception during processing: " + ex.toString() + ":" + ex.getMessage());
        }

        System.out.println("Processing complete.");

        return Response.status(200).build();
    }


}
