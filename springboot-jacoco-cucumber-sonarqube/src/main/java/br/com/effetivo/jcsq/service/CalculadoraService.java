package br.com.effetivo.jcsq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CalculadoraService {

    public int somar(int x, int y) {
        return x + y;
    }

    public int subtrair(int x, int y) {
        return x - y;
    }

    public int multiplicar(int x, int y) {
        return x * y;
    }

    public int dividir(int x, int y) {
        return x/y;
    }

}
