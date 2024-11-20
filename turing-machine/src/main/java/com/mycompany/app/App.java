package com.mycompany.app;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import com.google.gson.Gson;

/**
 * 
 */
public class App {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		Gson gson = new Gson();
		System.out.println("Encriptador/Desencriptador de cifrado césar");

		// Sets input
		System.err.print("\nIngrese el texto que quiere encriptar/desencriptar");
		System.err.print("\n(Incluye el valor 'k' seguido por # antes del texto): ");
		String input = scan.nextLine();

		System.out.println("\nEscoja una de las siguientes opciones:\n(1) Encriptar\n(2) Desencriptar");
		System.out.print("Opción: ");
		int option = scan.nextInt();
		scan.nextLine();

		if (option == 1) {
			try (FileReader reader = new FileReader("encrypt.json")) {
				Machine machine = gson.fromJson(reader, Machine.class);
				Caesar_Cipher encript_machine = new Caesar_Cipher(machine, input);
				String result = encript_machine.derivation(machine.getQ0(), machine.getF().get(0), machine.getDelta());
				System.err.println("Resultado: " + result + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try (FileReader reader = new FileReader("desencrypt.json")) {
				Machine machine = gson.fromJson(reader, Machine.class);
				Caesar_Cipher encript_machine = new Caesar_Cipher(machine, input);
				String result = encript_machine.derivation(machine.getQ0(), machine.getF().get(0), machine.getDelta());
				System.err.println("Resultado: " + result + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
