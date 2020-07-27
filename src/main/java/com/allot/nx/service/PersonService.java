package com.allot.nx.service;

import com.allot.nx.dao.PersonDao;
import com.allot.nx.entity.Person;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

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



    @Counted
    @Timed(name = "timeCalculateAverageOfAgesOfPersonsBornAfter", description = "Times how long it takes to calculate Average Of Ages Of Persons Born After specified year", unit = MetricUnits.MILLISECONDS)
    public double calculateAverageOfAgesOfPersonsBornAfter(int year) {
        List<Person> persons = personDao.findBornAfter(LocalDate.of(year, 12, 31));
        int count = persons.size();
        int sum = 0 ;
        for (Person person : persons) {
            int age = age(person);
            sum = sum + age ;
        }
        double average = sum / count ;
        return average;
    }

    @Counted(name = "count_ComputeAge")
    public int age(Person person) {     // must be public in order to be counted!
        return (LocalDate.now().getYear() - person.birthDate.getYear());
    }

}
