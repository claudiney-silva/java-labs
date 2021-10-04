package br.com.effetivo.jcsq.bdd.stepdefs;

import br.com.effetivo.jcsq.service.CalculadoraService;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@CucumberContextConfiguration
@ExtendWith(SpringExtension.class)
@Import({CalculadoraService.class})
public class BDDCalculadoraSoma {

    private Scenario scenario;
    private Integer result;
    private Integer expected;

    @Autowired
    private CalculadoraService calculadoraService;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("eu quero somar dois números")
    public void given_eu_quero_somar_dois_numeros() {
        scenario.log(String.format("Request %1$s", "ok"));
    }

    @When("eu informo {int} e {int}")
    public void when_eu_informo_x_e_y(int x, int y) {
        scenario.log(String.format("Somar %s e %s", x, y));
        expected = x + y;
        result = calculadoraService.somar(x, y);
    }

    @Then("a soma é retornada")
    public void then_a_soma_e_retornada() {
        assertNotNull(expected);
        assertNotNull(result);
        assertEquals(expected, result);
    }
}
