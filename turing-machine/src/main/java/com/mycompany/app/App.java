package com.mycompany.app;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
	Scanner scan = new Scanner(System.in);	

	Gson gson = new Gson();

	List<String> states = new ArrayList<>();
	List<String> alphabet = new ArrayList<>();
	String initial_state = "";
	List<String> final_states = new ArrayList<>();
	Map<String, Object> transitions = new HashMap<>(); 
	System.out.println("Encriptador/Desencriptador de cifrado césar");
	System.out.println("\nEscoja una de las siguientes opciones:\n(1) Encriptar\n(2) Desencriptar");
	System.out.println("Opción: ");
	int option = scan.nextInt();
	scan.nextLine();


	if (option == 1) {
		try (FileReader reader = new FileReader("encrypt.json")) {
			Machine machine = gson.fromJson(reader, Machine.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
	} else {
		try (FileReader reader = new FileReader("/desencrypt.json")) {
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    }
}
