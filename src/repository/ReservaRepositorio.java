package repository;

import model.Reserva;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ReservaRepositorio {

    private static final String ARQUIVO = "reservas.csv";

    public static ArrayList<Reserva> carregar() {
        ArrayList<Reserva> reservas = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.isBlank()) {
                    reservas.add(Reserva.deCsv(linha));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return reservas;
    }

    public static void salvar(ArrayList<Reserva> reservas) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Reserva r : reservas) {
                escritor.write(r.paraCsv());
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar reservas: " + e.getMessage());
        }
    }
}
