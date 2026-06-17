package menu;

import model.Quarto;
import repository.QuartoRepositorio;
import util.Leitor;

import java.util.ArrayList;
import java.util.Scanner;

public class QuartoMenu {

    public void cadastrar(Scanner scanner) {
        ArrayList<Quarto> quartos = QuartoRepositorio.carregar();

        int numero = Leitor.lerInteiro(scanner, "Numero do quarto: ");

        if (buscarPorNumero(numero) != null) {
            System.out.println("Ja existe um quarto com esse numero.");
            return;
        }

        System.out.print("Tipo (ex: solteiro, casal, suite): ");
        String tipo = scanner.nextLine();

        double preco = Leitor.lerDouble(scanner, "Preco da diaria: ");

        Quarto quarto = new Quarto(numero, tipo, preco, "disponivel");
        quartos.add(quarto);
        QuartoRepositorio.salvar(quartos);

        System.out.println("Quarto cadastrado com sucesso!");
    }

    public void listar() {
        ArrayList<Quarto> quartos = QuartoRepositorio.carregar();

        if (quartos.isEmpty()) {
            System.out.println("Nenhum quarto cadastrado ainda.");
            return;
        }

        System.out.println("\n--- Lista de Quartos ---");
        for (Quarto q : quartos) {
            System.out.println(q);
        }
    }

    public void editar(Scanner scanner) {
        ArrayList<Quarto> quartos = QuartoRepositorio.carregar();

        if (quartos.isEmpty()) {
            System.out.println("Nenhum quarto cadastrado ainda.");
            return;
        }

        System.out.println("\n--- Lista de Quartos ---");
        for (Quarto q : quartos) {
            System.out.println(q);
        }

        int numero = Leitor.lerInteiro(scanner, "Digite o numero do quarto a editar: ");

        Quarto encontrado = null;
        for (Quarto q : quartos) {
            if (q.getNumero() == numero) {
                encontrado = q;
                break;
            }
        }

        if (encontrado == null) {
            System.out.println("Quarto nao encontrado.");
            return;
        }

        System.out.print("Novo tipo (deixe em branco para manter \"" + encontrado.getTipo() + "\"): ");
        String tipo = scanner.nextLine();

        double preco = Leitor.lerDouble(scanner, "Novo preco (digite 0 para manter " + encontrado.getPreco() + "): ");

        System.out.print("Novo status disponivel/ocupado (deixe em branco para manter \"" + encontrado.getStatus() + "\"): ");
        String status = scanner.nextLine();

        String tipoFinal = tipo.isBlank() ? encontrado.getTipo() : tipo;
        double precoFinal = preco == 0 ? encontrado.getPreco() : preco;
        String statusFinal = status.isBlank() ? encontrado.getStatus() : status;

        Quarto atualizado = new Quarto(encontrado.getNumero(), tipoFinal, precoFinal, statusFinal);
        quartos.set(quartos.indexOf(encontrado), atualizado);
        QuartoRepositorio.salvar(quartos);

        System.out.println("Quarto atualizado com sucesso!");
    }

    public void excluir(Scanner scanner) {
        ArrayList<Quarto> quartos = QuartoRepositorio.carregar();

        if (quartos.isEmpty()) {
            System.out.println("Nenhum quarto cadastrado ainda.");
            return;
        }

        System.out.println("\n--- Lista de Quartos ---");
        for (Quarto q : quartos) {
            System.out.println(q);
        }

        int numero = Leitor.lerInteiro(scanner, "Digite o numero do quarto a excluir: ");

        Quarto encontrado = null;
        for (Quarto q : quartos) {
            if (q.getNumero() == numero) {
                encontrado = q;
                break;
            }
        }

        if (encontrado == null) {
            System.out.println("Quarto nao encontrado.");
            return;
        }

        quartos.remove(encontrado);
        QuartoRepositorio.salvar(quartos);
        System.out.println("Quarto excluido com sucesso!");
    }

    public Quarto buscarPorNumero(int numero) {
        for (Quarto q : QuartoRepositorio.carregar()) {
            if (q.getNumero() == numero) {
                return q;
            }
        }
        return null;
    }
}
