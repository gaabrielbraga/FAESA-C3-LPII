package repository;

import model.Quarto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class QuartoRepositorio {

    private static final String ARQUIVO = "quartos.csv";

    public static ArrayList<Quarto> carregar() {
        ArrayList<Quarto> quartos = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.isBlank()) {
                    quartos.add(Quarto.deCsv(linha));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return quartos;
    }

    public static void salvar(ArrayList<Quarto> quartos) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Quarto q : quartos) {
                escritor.write(q.paraCsv());
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar quartos: " + e.getMessage());
        }
    }
}
