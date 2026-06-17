package repository;

import model.Hospede;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HospedeRepositorio {

    private static final String ARQUIVO = "hospedes.csv";

    public static ArrayList<Hospede> carregar() {
        ArrayList<Hospede> hospedes = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.isBlank()) {
                    hospedes.add(Hospede.deCsv(linha));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return hospedes;
    }

    public static void salvar(ArrayList<Hospede> hospedes) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Hospede h : hospedes) {
                escritor.write(h.paraCsv());
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar hospedes: " + e.getMessage());
        }
    }
}
