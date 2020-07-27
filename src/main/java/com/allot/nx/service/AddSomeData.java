package com.allot.nx.service;

import com.allot.nx.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AddSomeData {

    private Logger logger = LoggerFactory.getLogger(AddSomeData.class);

    @Transactional
    public void doAddSomeDataToDatabase() {
        logger.info("doAddSomeDataToDatabase started");

        // creating a person
        Person person = new Person();
        person.name = "Evyatar Kafkafi";
        person.familyName = "Kafkafi";
        person.privateName = "Evyatar";
        person.birthDate = LocalDate.of(1968, Month.AUGUST, 28);
        person.alive = true;
        person.personId = "023817190";

        // persist it
        person.persist();
        logger.info("doAddSomeDataToDatabase persisted one person");
// note that once persisted, you don't need to explicitly save your entity: all
// modifications are automatically persisted on transaction commit.

        // check if it's persistent
        if(person.isPersistent()){
            logger.info("person {} is persisted", person.name);
        }

        Person person2 = new Person("Bob Marley", "Robert Nesta", "Marley", LocalDate.of(1945, Month.FEBRUARY, 6), false);
        person2.persist();

        (new Person("Elvis Presley", "Elvis Aaron", "Presley", LocalDate.of(1935, Month.JANUARY, 8), false)).persist();

        // getting a list of all Person entities
        logger.info("doAddSomeDataToDatabase: before listAll");
        List<Person> allPersons = Person.listAll();
        logger.info("doAddSomeDataToDatabase: display all persons: {}", allPersons);

        // finding a specific person by ID
        Person person1 = Person.findById(1L);
        if (person != null) {
            logger.info("found {}", person1.name);
        }

        person1 = Person.findByName("Bob Marley");
        logger.info("found {}", person1.name);

// finding a specific person by ID via an Optional
        //Optional<Person> optional = Person.findByIdOptional(10L);
        //Person person2 = optional.orElseThrow(() -> new NotFoundException());

        // finding all living persons
        List<Person> livingPersons = Person.list("alive", true);

        // counting all persons
        long countAll = Person.count();
        logger.info("counted {} persons", countAll);

        // counting all living persons
        long countAlive = Person.count("alive", true);
        logger.info("counted {} persons alive", countAlive);

// delete all living persons
//        Person.delete("alive", true);

// delete all persons
//        Person.deleteAll();

// delete by id
//        boolean deleted = Person.deleteById(personId);

        person1 = Person.findByName("Elvis Presley");
        logger.info("found {}. He is {}.", person1.name, person1.alive?"alive":"dead");
        // set the name of all living persons to 'Mortal'
        Person.update("name = concat('Late ', name) where alive = ?1", false);
    }
}
