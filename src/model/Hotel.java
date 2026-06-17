package model;

import repository.QuartoRepositorio;

import java.util.ArrayList;

public class Hotel {
    private String nome;
    private String endereco;

    public Hotel(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void listarQuartos() {
        ArrayList<Quarto> quartos = QuartoRepositorio.carregar();

        if (quartos.isEmpty()) {
            System.out.println("Nenhum quarto cadastrado ainda.");
            return;
        }

        System.out.println("\n--- Quartos do " + nome + " ---");
        for (Quarto q : quartos) {
            System.out.println(q);
        }
    }

    @Override
    public String toString() {
        return "Hotel: " + nome + " | Endereco: " + endereco;
    }
}
