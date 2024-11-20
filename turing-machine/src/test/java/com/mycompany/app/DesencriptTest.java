package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

class DesencriptTest {
    private Gson gson;
    private Machine machine;

    @BeforeEach
    void setup() throws IOException {
        gson = new Gson();
        try (FileReader reader = new FileReader("desencrypt.json")) {
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
        String input = "3#krod pxqgr";
        Caesar_Cipher cipher = new Caesar_Cipher(machine, input);

        assertEquals(14, cipher.getInputTape().size(),
                "La cinta de entrada debería contener los caracteres esperados más los delimitadores.");
        assertTrue(cipher.getInputTape().contains("k"), "La cinta de entrada debería incluir el carácter 'k'.");
        assertTrue(cipher.getAlphabetTape().contains("a"), "La cinta del alfabeto debería contener el carácter 'a'.");
    }

    @Test
    void testTransitions() {
        String input = "3#krod pxqgr";
        Caesar_Cipher cipher = new Caesar_Cipher(machine, input);

        Map<String, List<String>> delta = machine.getDelta();
        String result = cipher.derivation(machine.getQ0(), machine.getF().get(0), delta);

        assertNotNull(result, "El resultado del cifrado no debería ser nulo.");
        assertEquals("hola mundo", result,
                "El texto encriptado debería coincidir con el esperado para 'krod pxqgr' con k=3.");
    }

    @Test
    void testCompleteFlow() throws IOException {
        String input = "3#krod pxqgr";

        Caesar_Cipher cipher = new Caesar_Cipher(machine, input);
        String result = cipher.derivation(machine.getQ0(), machine.getF().get(0), machine.getDelta());

        assertNotNull(result, "El texto encriptado no debería ser nulo.");
        assertEquals("hola mundo", result,
                "El resultado del cifrado debería ser correcto para la clave proporcionada.");
    }
}
