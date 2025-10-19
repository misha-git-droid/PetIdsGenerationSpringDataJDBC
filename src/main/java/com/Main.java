package com;

import com.entity.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;

import java.util.UUID;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public BeforeConvertCallback<Person> beforeConvertCallback() {
        return (person) -> {
            if (person.getId() == null) {
                person.setId(UUID.randomUUID().toString());
            }
            return person;
        };
    }
}