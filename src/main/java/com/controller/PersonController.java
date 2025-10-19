package com.controller;

import com.dto.RequestPersonDto;
import com.dto.RequestPersonDtoId;
import com.entity.Person;
import com.exception.PersonNotFoundException;
import com.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/{personId}")
    public ResponseEntity<Person> personById(@PathVariable("personId") String personId) {
        Optional<Person> optional = personRepository.findById(personId);

        if (optional.isEmpty()) {
            throw new PersonNotFoundException(personId);
        }

        return ResponseEntity.status(HttpStatus.OK).body(optional.get());
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody RequestPersonDto personData) {
        Person person = new Person(personData.getFirstName());
        personRepository.save(person);
        return ResponseEntity.status(HttpStatus.OK).body(person);
    }

    @PutMapping
    public ResponseEntity<Person> update(@RequestBody RequestPersonDtoId personData) {
        String personId = personData.getPersonId();

        if (!personRepository.existsById(personId)) {
            throw new PersonNotFoundException(personId);
        }

        Person person = new Person(personData.getFirstName());
        person.setId(personId);
        personRepository.save(person);
        return ResponseEntity.status(HttpStatus.OK).body(person);
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<Person> delete(@PathVariable("personId") String personId) {
        Optional<Person> optional = personRepository.findById(personId);

        if (optional.isEmpty()) {
            throw new PersonNotFoundException(personId);
        }

        personRepository.delete(optional.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(optional.get());
    }
}
