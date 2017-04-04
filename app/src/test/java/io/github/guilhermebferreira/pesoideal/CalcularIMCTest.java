package io.github.guilhermebferreira.pesoideal;

import org.junit.Test;

import static io.github.guilhermebferreira.pesoideal.CalcularIMC.getImc;
import static org.junit.Assert.*;

/**
 * Created by guilherme on 04/04/17.
 */

public class CalcularIMCTest {

    @Test
    public void adulto_abaixo_peso_isCorrect() throws Exception {
        assertEquals("Abaixo do peso", CalcularIMC.adultoResultado((double) 18));
    }
    @Test
    public void adulto_abaixo_peso_erro() throws Exception {
        assertNotEquals("Abaixo do peso", CalcularIMC.adultoResultado((double) 20));
    }
    @Test
    public void adulto_peso_normal_isCorrect() throws Exception {
        assertEquals("Peso normal", CalcularIMC.adultoResultado((double) 22));
    }
    @Test
    public void adulto_peso_normal_erro() throws Exception {
        assertNotEquals("Abaixo do peso", CalcularIMC.adultoResultado((double) 26));
    }


    @Test
    public void getImc_correct() throws Exception {
        assertEquals(getImc((double) 80, 1.80), 24.69, 0.01);
    }
    @Test
    public void getImc_error() throws Exception {
        assertNotEquals(getImc((double) 20, 1.70), 25, 0.01);
    }



}
