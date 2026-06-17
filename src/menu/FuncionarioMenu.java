package menu;

import model.Funcionario;
import model.Usuario;
import repository.FuncionarioRepositorio;
import repository.UsuarioRepositorio;
import util.Leitor;

import java.util.ArrayList;
import java.util.Scanner;

public class FuncionarioMenu {

    private final ArrayList<Funcionario> funcionarios;

    public FuncionarioMenu() {
        this.funcionarios = FuncionarioRepositorio.carregar();
    }

    public void cadastrar(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Cargo: ");
        String cargo = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        int novoId = funcionarios.size() + 1;
        Funcionario funcionario = new Funcionario(novoId, nome, cargo, telefone);
        funcionarios.add(funcionario);
        FuncionarioRepositorio.salvar(funcionarios);

        criarAcessoDoFuncionario(scanner, funcionario.getId());

        System.out.println("Funcionario cadastrado com sucesso!");
    }

    private void criarAcessoDoFuncionario(Scanner scanner, int funcionarioId) {
        ArrayList<Usuario> usuarios = UsuarioRepositorio.carregar();

        String login;
        while (true) {
            System.out.print("Login de acesso ao sistema: ");
            String tentativa = scanner.nextLine();

            boolean loginJaExiste = usuarios.stream().anyMatch(u -> u.getLogin().equals(tentativa));
            if (!loginJaExiste) {
                login = tentativa;
                break;
            }
            System.out.println("Esse login ja existe. Escolha outro.");
        }

        System.out.print("Senha de acesso ao sistema: ");
        String senha = scanner.nextLine();

        int novoIdUsuario = usuarios.size() + 1;
        Usuario usuario = new Usuario(novoIdUsuario, login, senha, "funcionario", funcionarioId);
        usuarios.add(usuario);
        UsuarioRepositorio.salvar(usuarios);

        System.out.println("Acesso ao sistema criado para o funcionario (login: " + login + ").");
    }

    public void listar() {
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionario cadastrado ainda.");
            return;
        }

        System.out.println("\n--- Lista de Funcionarios ---");
        for (Funcionario f : funcionarios) {
            System.out.println(f);
        }
    }

    public void editar(Scanner scanner) {
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionario cadastrado ainda.");
            return;
        }

        listar();
        int id = Leitor.lerInteiro(scanner, "Digite o id do funcionario a editar: ");

        Funcionario encontrado = null;
        for (Funcionario f : funcionarios) {
            if (f.getId() == id) {
                encontrado = f;
                break;
            }
        }

        if (encontrado == null) {
            System.out.println("Funcionario nao encontrado.");
            return;
        }

        System.out.print("Novo nome (deixe em branco para manter \"" + encontrado.getNome() + "\"): ");
        String nome = scanner.nextLine();

        System.out.print("Novo cargo (deixe em branco para manter \"" + encontrado.getCargo() + "\"): ");
        String cargo = scanner.nextLine();

        System.out.print("Novo telefone (deixe em branco para manter \"" + encontrado.getTelefone() + "\"): ");
        String telefone = scanner.nextLine();

        String nomeFinal = nome.isBlank() ? encontrado.getNome() : nome;
        String cargoFinal = cargo.isBlank() ? encontrado.getCargo() : cargo;
        String telefoneFinal = telefone.isBlank() ? encontrado.getTelefone() : telefone;

        Funcionario atualizado = new Funcionario(encontrado.getId(), nomeFinal, cargoFinal, telefoneFinal);
        funcionarios.set(funcionarios.indexOf(encontrado), atualizado);
        FuncionarioRepositorio.salvar(funcionarios);

        System.out.println("Funcionario atualizado com sucesso!");
    }

    public void excluir(Scanner scanner) {
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionario cadastrado ainda.");
            return;
        }

        listar();
        int id = Leitor.lerInteiro(scanner, "Digite o id do funcionario a excluir: ");

        Funcionario encontrado = null;
        for (Funcionario f : funcionarios) {
            if (f.getId() == id) {
                encontrado = f;
                break;
            }
        }

        if (encontrado == null) {
            System.out.println("Funcionario nao encontrado.");
            return;
        }

        funcionarios.remove(encontrado);
        FuncionarioRepositorio.salvar(funcionarios);

        int idExcluido = encontrado.getId();
        ArrayList<Usuario> usuarios = UsuarioRepositorio.carregar();
        usuarios.removeIf(u -> u.getFuncionarioId() == idExcluido);
        UsuarioRepositorio.salvar(usuarios);

        System.out.println("Funcionario e seu acesso ao sistema foram excluidos com sucesso!");
    }
}
