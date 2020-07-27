package com.allot.nx.dao;


import com.allot.nx.entity.Person;
import com.allot.nx.service.AddSomeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Singleton
public class PersonDao {

    private Logger logger = LoggerFactory.getLogger(PersonDao.class);


    @Inject
    private EntityManager entityManager;

    public void persist(Person person) {
        entityManager.persist(person);
    }

    public void delete(Person person) {
        entityManager.remove(person);
    }

    public Person findById(Long id) {   // this is the "id" field from PanacheEntity
        return entityManager.find(Person.class, id);
    }

    public List<Person> findByPersonId(String pid) {
        return entityManager.createQuery("FROM Person WHERE personId = :personId", Person.class).setParameter("personId", pid).getResultList();
    }

    public List<Person> findAll() {
        return entityManager.createQuery("FROM Person", Person.class).getResultList();
    }

    public List<Person> findByName(String lastName) {
        return entityManager.createQuery("FROM Person WHERE familyName = :lastName", Person.class).setParameter("lastName", lastName).getResultList();
    }

    public List<Person> findBornAfter(LocalDate date) {
        logger.info("PersonDao.findBornAfter {}", date);
        return entityManager.createQuery("FROM Person WHERE birthDate > :date", Person.class).setParameter("date", date).getResultList();
    }
}
