package com.allot.nx.service;

import com.allot.nx.dao.PersonDao;
import com.allot.nx.entity.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class PersonService {

    public List<Person> getAllPersons() {
        List<Person> result = Person.listAll();
        return result;
    }




    // using PersonDao

    @Inject
    PersonDao personDao;

    public List<Person> getAllPersonsWithLastName(String lastName) {
        List<Person> result = personDao.findByName(lastName);
        return result;
    }

    public List<Person> getAll() {
        List<Person> result = personDao.findAll();
        return result;
    }

    public List<Person> findBornAfter(int year) {
        //Date date = new Date(year - 1900, 11, 31);  // 11 is December
        LocalDate localDate = LocalDate.of(year, 12, 31);
        List<Person> result = personDao.findBornAfter(localDate);
        return result;
    }
}
