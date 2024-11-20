package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

class EncriptTest {
    private Gson gson;
    private Machine machine;

    @BeforeEach
    void setup() throws IOException {
        gson = new Gson();
        try (FileReader reader = new FileReader("encrypt.json")) {
            machine = gson.fromJson(reader, Machine.class);
        }
    }

    @Test
    void testFileReading() {
        assertNotNull(machine, "La máquina debería cargarse correctamente desde el archivo JSON.");
        assertFalse(machine.getDelta().isEmpty(), "El mapa de transiciones (delta) no debería estar vacío.");
    }

    @Test
    void testTapeInitialization() {
        String input = "3#hola mundo";
        Caesar_Cipher cipher = new Caesar_Cipher(machine, input);

        assertEquals(14, cipher.getInputTape().size(),
                "La cinta de entrada debería contener los caracteres esperados más los delimitadores.");
        assertTrue(cipher.getInputTape().contains("h"), "La cinta de entrada debería incluir el carácter 'h'.");
        assertTrue(cipher.getAlphabetTape().contains("a"), "La cinta del alfabeto debería contener el carácter 'a'.");
    }

    @Test
    void testTransitions() {
        String input = "3#hola mundo";
        Caesar_Cipher cipher = new Caesar_Cipher(machine, input);

        Map<String, List<String>> delta = machine.getDelta();
        String result = cipher.derivation(machine.getQ0(), machine.getF().get(0), delta);

        assertNotNull(result, "El resultado del cifrado no debería ser nulo.");
        assertEquals("krod pxqgr", result,
                "El texto encriptado debería coincidir con el esperado para 'hola mundo' con k=3.");
    }

    @ParameterizedTest
    @CsvSource({
            "3#hola mundo, krod pxqgr",
            "5#java test, ofaf yjxy",
            "1#cipher, djqifs",
            "7#prueba, wyblih",
            "3 # ROMA NO FUE CONSTRUIDA EN UN DIA, URPD QR IXH FRQVWUXLGD HQ XQ GLD"
    })
    void testCompleteFlow(String input, String expected) {
        Caesar_Cipher cipher = new Caesar_Cipher(machine, input);
        String result = cipher.derivation(machine.getQ0(), machine.getF().get(0), machine.getDelta());

        assertNotNull(result, "El texto encriptado no debería ser nulo.");
        assertEquals(expected.toLowerCase(), result,
                "El resultado del cifrado debería ser correcto para la clave proporcionada.");
    }
}
