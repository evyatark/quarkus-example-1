package com.allot.nx.Controller;

import com.allot.nx.entity.Person;
import com.allot.nx.service.AddSomeData;
import com.allot.nx.service.PersonService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/person")
public class PersonResource {

    @Inject
    AddSomeData addSomeData;

    @Inject
    PersonService personService;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
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


    /**
     * This will work out of the box because there is a default
     * converter from POJO to JSON!
     * @return
     * http://localhost:8080/person/lastName/Marley
     */
    @GET
    @Path("/lastName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPersonsWithLastNameAsJson(@PathParam("name") String lastName) {
        return personService.getAllPersonsWithLastName(lastName);
    }

    @GET
    @Path("/dao/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPersonsFromDao() {
        return personService.getAll();
    }


    @GET
    @Path("/bornAfter/{year}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPersonsBornAfterYear(@PathParam("year") Integer year) {
        return personService.findBornAfter(year);
    }


    @GET
    @Path("/age/average")
    @Produces(MediaType.APPLICATION_JSON)
    public Double averageAge() {
        return personService.calculateAverageOfAgesOfPersonsBornAfter(1800);
    }

}