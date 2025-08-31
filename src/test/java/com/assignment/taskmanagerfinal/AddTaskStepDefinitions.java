package com.assignment.taskmanagerfinal;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddTaskStepDefinitions {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<Task> response;

    @Given("the application is running")
    public void the_application_is_running() {
        // This step is intentionally empty because @SpringBootTest handles starting the app
    }

    @When("I send a POST request to {string} with the title {string}")
    public void i_send_a_post_request_to_with_the_title(String path, String title) {
        Task task = new Task(null, title, false);
        response = restTemplate.exchange(path, HttpMethod.POST, new HttpEntity<>(task), Task.class);
    }

    @When("I send a POST request to {string} with an empty title")
    public void i_send_a_post_request_to_with_an_empty_title(String path) {
        Task task = new Task(null, "", false);
        response = restTemplate.exchange(path, HttpMethod.POST, new HttpEntity<>(task), Task.class);
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) {
        assertEquals(statusCode, response.getStatusCode().value());
    }

    @Then("the response should contain a task with the title {string}")
    public void the_response_should_contain_a_task_with_the_title(String title) {
        Task responseBody = response.getBody();
        assertNotNull(responseBody);
        assertNotNull(responseBody.getId());
        assertEquals(title, responseBody.getTitle());
    }
}