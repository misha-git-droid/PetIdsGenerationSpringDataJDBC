package com.controller;

import com.entity.Person;
import com.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PersonController.class)
public class PersonControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private PersonRepository personRepository;

    @Test
    void personById_whenPersonExists_statusIsOk() throws Exception {
        Person person = new Person("Test");
        person.setId("any");
        when(personRepository.findById("any")).thenReturn(Optional.of(person));

        mvc.perform(get("/api/person/any"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Test"));
    }

    @Test
    void personById_whenPersonNotExists_statusNotFound() throws Exception {
        String personId = "any";
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        mvc.perform(get("/api/person/" + personId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not found person with id any"));
    }

    @Test
    void create_whenCreatePersonSuccess_statusIsOk() throws Exception {
        Person person = new Person("Test");
        when(personRepository.save(person)).thenReturn(person);
        String jsonPerson =
                """
                    {   
                        "firstName": "Test"
                    }
                """;

        mvc.perform(post("/api/person").contentType(MediaType.APPLICATION_JSON).content(jsonPerson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Test"));
    }

    @Test
    void update_whenPersonExists_statusIsOk() throws Exception {
        when(personRepository.existsById("any")).thenReturn(true);

        String jsonPerson =
                """
                    {
                        "personId": "any",
                        "firstName": "Test"
                    }
                """;

        mvc.perform(put("/api/person").contentType(MediaType.APPLICATION_JSON).content(jsonPerson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Test"));
    }

    @Test
    void update_whenPersonNotExists_statusNotFound() throws Exception {
        when(personRepository.existsById("any")).thenReturn(false);

        String jsonPerson =
                """
                    {
                        "personId": "any",
                        "firstName": "Test"
                    }
                """;

        mvc.perform(put("/api/person").contentType(MediaType.APPLICATION_JSON).content(jsonPerson))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_whenPersonExists_statusIsNoContent() throws Exception {
        String personId = "any";
        Person person = new Person("Test");
        person.setId(personId);
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        mvc.perform(delete("/api/person/" + personId))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_whenPersonNotExists_statusNotFound() throws Exception {
        String personId = "any";
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        mvc.perform(delete("/api/person/" + personId))
                .andExpect(status().isNotFound());
    }
}
