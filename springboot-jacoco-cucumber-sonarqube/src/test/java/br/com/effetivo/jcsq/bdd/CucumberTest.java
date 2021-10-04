package br.com.effetivo.jcsq.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;

/**
 * To run cucumber test
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        plugin = {"pretty", "json:target/cucumber-reports/cucumber.json"},
        glue = {"br.com.effetivo.jcsq.bdd"}
)
public class CucumberTest {
}