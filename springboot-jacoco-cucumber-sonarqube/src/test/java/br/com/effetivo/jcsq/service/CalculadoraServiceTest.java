package br.com.effetivo.jcsq.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({CalculadoraService.class})
class CalculadoraServiceTest {

    @Autowired
    private CalculadoraService calculadoraService;

    @Test
    @DisplayName("Deverá somar")
    public void testSomar() {
        int result = calculadoraService.somar(10, 5);
        assertEquals(result, 15);
    }


}