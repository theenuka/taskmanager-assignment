package com.assignment.taskmanagerfinal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskManagerUITest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private String baseUrl;

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.setBrowserVersion("stable");

        // Headless mode for CI/CD
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox"); // <-- ADD THIS LINE

        driver = new ChromeDriver(options);
        baseUrl = "http://localhost:" + port;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldDisplayTaskManagerTitleOnPageLoad() {
        driver.get(baseUrl + "/");
        WebElement heading = driver.findElement(By.tagName("h1"));
        assertEquals("Task Manager", heading.getText());
    }

    @Test
    void shouldAddTaskAndDisplayItInTheList() {
        driver.get(baseUrl + "/");
        String taskTitle = "Learn Selenium WebDriver";

        WebElement titleInput = driver.findElement(By.cssSelector("input[name='title']"));
        WebElement addButton = driver.findElement(By.cssSelector("input[type='submit']"));

        titleInput.sendKeys(taskTitle);
        addButton.click();

        WebElement taskList = driver.findElement(By.tagName("ul"));
        assertTrue(taskList.getText().contains(taskTitle), "The new task should be visible in the list.");
    }
}