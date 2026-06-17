package model;

import repository.QuartoRepositorio;

import java.util.ArrayList;

public class Hotel {
    private String nome;

    public Hotel(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
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
        return "Hotel: " + nome;
    }
}
