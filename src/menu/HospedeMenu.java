package menu;

import model.Hospede;
import repository.HospedeRepositorio;
import util.Leitor;

import java.util.ArrayList;
import java.util.Scanner;

public class HospedeMenu {

    private final ArrayList<Hospede> hospedes;

    public HospedeMenu() {
        this.hospedes = HospedeRepositorio.carregar();
    }

    public void cadastrar(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        String cpf = lerCpf(scanner);

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        int novoId = hospedes.size() + 1;
        Hospede hospede = new Hospede(novoId, nome, cpf, telefone);
        hospedes.add(hospede);
        HospedeRepositorio.salvar(hospedes);

        System.out.println("Hospede cadastrado com sucesso!");
    }

    public void listar() {
        if (hospedes.isEmpty()) {
            System.out.println("Nenhum hospede cadastrado ainda.");
            return;
        }

        System.out.println("\n--- Lista de Hospedes ---");
        for (Hospede h : hospedes) {
            System.out.println(h);
        }
    }

    public void editar(Scanner scanner) {
        if (hospedes.isEmpty()) {
            System.out.println("Nenhum hospede cadastrado ainda.");
            return;
        }

        listar();
        int id = Leitor.lerInteiro(scanner, "Digite o id do hospede a editar: ");

        Hospede encontrado = null;
        for (Hospede h : hospedes) {
            if (h.getId() == id) {
                encontrado = h;
                break;
            }
        }

        if (encontrado == null) {
            System.out.println("Hospede nao encontrado.");
            return;
        }

        System.out.print("Novo nome (deixe em branco para manter \"" + encontrado.getNome() + "\"): ");
        String nome = scanner.nextLine();

        System.out.print("Novo telefone (deixe em branco para manter \"" + encontrado.getTelefone() + "\"): ");
        String telefone = scanner.nextLine();

        String nomeFinal = nome.isBlank() ? encontrado.getNome() : nome;
        String telefoneFinal = telefone.isBlank() ? encontrado.getTelefone() : telefone;

        Hospede atualizado = new Hospede(encontrado.getId(), nomeFinal, encontrado.getCpf(), telefoneFinal);
        hospedes.set(hospedes.indexOf(encontrado), atualizado);
        HospedeRepositorio.salvar(hospedes);

        System.out.println("Hospede atualizado com sucesso!");
    }

    public void excluir(Scanner scanner) {
        if (hospedes.isEmpty()) {
            System.out.println("Nenhum hospede cadastrado ainda.");
            return;
        }

        listar();
        int id = Leitor.lerInteiro(scanner, "Digite o id do hospede a excluir: ");

        Hospede encontrado = null;
        for (Hospede h : hospedes) {
            if (h.getId() == id) {
                encontrado = h;
                break;
            }
        }

        if (encontrado == null) {
            System.out.println("Hospede nao encontrado.");
            return;
        }

        hospedes.remove(encontrado);
        HospedeRepositorio.salvar(hospedes);
        System.out.println("Hospede excluido com sucesso!");
    }

    private String lerCpf(Scanner scanner) {
        String cpf;
        while (true) {
            System.out.print("CPF (somente numeros): ");
            cpf = scanner.nextLine();

            if (cpf.matches("\\d+")) {
                break;
            }

            System.out.println("CPF invalido! Digite apenas numeros, sem pontos ou tracos.");
        }
        return cpf;
    }
}
