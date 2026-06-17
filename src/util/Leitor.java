package util;

import java.util.Scanner;

public class Leitor {

    public static int lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine();
            try {
                return Integer.parseInt(texto.trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido! Digite um numero inteiro.");
            }
        }
    }

    public static double lerDouble(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine();
            try {
                return Double.parseDouble(texto.trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido! Digite um numero, ex: 150.0");
            }
        }
    }
}
