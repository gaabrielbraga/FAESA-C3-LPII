package repository;

import model.Usuario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UsuarioRepositorio {

    private static final String ARQUIVO = "usuarios.csv";

    public static ArrayList<Usuario> carregar() {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (!linha.isBlank()) {
                    usuarios.add(Usuario.deCsv(linha));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return usuarios;
    }

    public static void salvar(ArrayList<Usuario> usuarios) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Usuario u : usuarios) {
                escritor.write(u.paraCsv());
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuarios: " + e.getMessage());
        }
    }
}
