package org.roy;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import javax.persistence.Column;

import java.sql.Timestamp;

import javax.inject.Named;


@XmlRootElement
@Named
public class SimplePOJO {
    @Column(name = "id")
    int id;

    @Column(name = "firstname")
    String firstName;

    @Column(name = "lastname")
    String lastName;

    @Column(name = "tstamp")
    Timestamp tstamp;

//    public SimplePOJO() {
//        this.firstName = "Clarus";
//        this.lastName = "DogCow";
//    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTstamp() {
        return tstamp;
    }

    public void setTstamp(Timestamp tstamp) {
        this.tstamp = tstamp;
    }

    public String toString() {
        return "SimplePOJO(" + id + "," + firstName + "," + lastName + "," + tstamp + ")";
    }
}

