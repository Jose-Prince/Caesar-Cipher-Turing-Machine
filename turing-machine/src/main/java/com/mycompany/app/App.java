package com.mycompany.app;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
	Scanner scan = new Scanner(System.in);	

	Gson gson = new Gson();
	System.out.println("Encriptador/Desencriptador de cifrado césar");

	//Sets input
	System.err.print("\nIngrese el texto que quiere encriptar/desencriptar: ");
	String input = scan.nextLine();
	//Sets k
	System.out.print("Ingrese el valor k: ");
	int k = scan.nextInt();
	scan.nextLine();

	List<String> states = new ArrayList<>();
	List<String> alphabet = new ArrayList<>();
	String initial_state = "";
	List<String> final_states = new ArrayList<>();
	List<List<List<Object>>> transitions = new ArrayList<>(); 
	System.out.println("\nEscoja una de las siguientes opciones:\n(1) Encriptar\n(2) Desencriptar");
	System.out.print("Opción: ");
	int option = scan.nextInt();
	scan.nextLine();

	if (option == 1) {
		try (FileReader reader = new FileReader("encrypt.json")) {
			Machine machine = gson.fromJson(reader, Machine.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} else {
		try (FileReader reader = new FileReader("desencrypt.json")) {
			Machine machine = gson.fromJson(reader, Machine.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    }
}
