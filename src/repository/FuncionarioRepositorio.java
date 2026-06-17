package repository;

import model.Funcionario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FuncionarioRepositorio {

    private static final String ARQUIVO = "funcionarios.csv";

    public static ArrayList<Funcionario> carregar() {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.isBlank()) {
                    funcionarios.add(Funcionario.deCsv(linha));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return funcionarios;
    }

    public static void salvar(ArrayList<Funcionario> funcionarios) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Funcionario f : funcionarios) {
                escritor.write(f.paraCsv());
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar funcionarios: " + e.getMessage());
        }
    }
}
