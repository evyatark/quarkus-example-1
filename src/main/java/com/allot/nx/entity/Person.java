package com.allot.nx.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class Person extends PanacheEntity {

    // key of entity Person
    @Column(length = 100, unique = true)
    public String personId;

    // fields of entity Person
    @Column(length = 60, unique = false)
    public String name;
    @Column(length = 20, unique = false)
    public String privateName;
    @Column(length = 30, unique = false)
    public String familyName;
    @Column(length = 50, unique = false)
    public LocalDate birthDate;
    @Column(length = 10, unique = false)
    public Boolean alive;


    public Person() {
    }

    public Person(String name, String privateName, String familyName, LocalDate birthDate, Boolean alive) {
        this.name = name;
        this.privateName = privateName;
        this.familyName = familyName;
        this.birthDate = birthDate;
        this.alive = alive;
    }

// simple queries on entity Person

    // return the first person found with that name
    public static Person findByName(String name){
        return find("name", name).firstResult();
    }

    public static List<Person> findAlive(){
        return list("alive", true);
    }

    public static List<Person> findDead(){
        return list("alive", false);
    }

    public static List<Person> findByBirthDate(LocalDate date){
        return list("birthDate", date);
    }

//    public static List<Person> findByBirthYear(int year){
//        return list("birthDate", date);
//    }


    @Override
    public String toString() {
        return "Person{" +
                "personId='" + personId + '\'' +
                ", name='" + name + '\'' +
                ", privateName='" + privateName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", birthDate=" + birthDate +
                ", alive=" + alive +
                ", id=" + id +
                '}';
    }
}
