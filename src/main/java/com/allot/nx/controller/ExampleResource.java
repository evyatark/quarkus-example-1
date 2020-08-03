package com.allot.nx.controller;

import com.allot.nx.entity.Person;
import com.allot.nx.service.AddSomeData;
import com.allot.nx.service.PersonService;
import io.quarkus.runtime.Quarkus;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/ex")
public class ExampleResource {

    private Logger logger = LoggerFactory.getLogger(ExampleResource.class);

    @Inject
    AddSomeData addSomeData;

    @Inject
    PersonService personService;

    @ConfigProperty(defaultValue = "World!", name = "my.name")
    String name ;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello " + name + "\n";
    }

    /**
     * Shut down the application, but not the database service!
     * @return
     */
    @GET
    @Path("/shutdown")
    @Produces(MediaType.TEXT_PLAIN)
    public String shutMeDown() {
        logger.warn("shuting down the app in 5 seconds...");
        //System.exit(1);
        Quarkus.asyncExit(-1);
        return "app was shutdown\n";    // this will never happen! so the client HTTP call will not receive a response
    }


    @GET
    @Path("/init")
    @Produces(MediaType.TEXT_PLAIN)
    public String init() {
        addSomeData.doAddSomeDataToDatabase();
        return "DB initialized and populated";
    }

    @GET
    @Path("/all")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllPersons() {
        return personService.getAllPersons().toString();
    }

    /**
     * This will work out of the box because there is a default
     * converter from POJO to JSON!
     * @return
     */
    @GET
    @Path("/all/json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPersonsAsJson() {
        return personService.getAllPersons();
    }


    /**
     * This Won't work (out of the box) because there is no default
     * converter from POJO to XML
     * @return
     */
    @GET
    @Path("/all/xml")
    @Produces(MediaType.APPLICATION_XML)
    public List<Person> getAllPersonsAsXml() {
        return personService.getAllPersons();
    }

}