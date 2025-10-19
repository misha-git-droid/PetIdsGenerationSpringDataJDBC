package com.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
public class Person {
    @Id
    private String personId;
    private String firstName;

    public Person(String firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return personId;
    }
    public void setId(String personId) {
        this.personId = personId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) { this.firstName = firstName; }
}
