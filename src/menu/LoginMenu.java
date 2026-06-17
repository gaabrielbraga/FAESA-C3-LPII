package menu;

import model.Usuario;
import repository.UsuarioRepositorio;

import java.util.ArrayList;
import java.util.Scanner;

public class LoginMenu {

    public boolean existeUsuario() {
        return !UsuarioRepositorio.carregar().isEmpty();
    }

    public void cadastrarPrimeiroAdmin(Scanner scanner) {
        System.out.println("\nNenhum usuario cadastrado ainda. Vamos criar o administrador inicial.");
        cadastrar(scanner, "admin");
    }

    public void cadastrar(Scanner scanner, String perfilFixo) {
        ArrayList<Usuario> usuarios = UsuarioRepositorio.carregar();

        System.out.print("Login: ");
        String login = scanner.nextLine();

        if (buscarPorLogin(usuarios, login) != null) {
            System.out.println("Esse login ja existe.");
            return;
        }

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        String perfil = perfilFixo;
        if (perfil == null) {
            System.out.print("Perfil (admin/funcionario): ");
            perfil = scanner.nextLine();
        }

        int novoId = usuarios.size() + 1;
        Usuario usuario = new Usuario(novoId, login, senha, perfil, 0);
        usuarios.add(usuario);
        UsuarioRepositorio.salvar(usuarios);

        System.out.println("Usuario cadastrado com sucesso!");
    }

    public void listar() {
        ArrayList<Usuario> usuarios = UsuarioRepositorio.carregar();

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado ainda.");
            return;
        }

        System.out.println("\n--- Lista de Usuarios ---");
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }

    public void editar(Scanner scanner) {
        ArrayList<Usuario> usuarios = UsuarioRepositorio.carregar();

        listar();
        int id = util.Leitor.lerInteiro(scanner, "Digite o id do usuario a editar: ");

        Usuario encontrado = buscarPorId(usuarios, id);
        if (encontrado == null) {
            System.out.println("Usuario nao encontrado.");
            return;
        }

        System.out.print("Nova senha (deixe em branco para manter a atual): ");
        String novaSenha = scanner.nextLine();

        System.out.print("Novo perfil admin/funcionario (deixe em branco para manter o atual): ");
        String novoPerfil = scanner.nextLine();

        String senhaFinal = novaSenha.isBlank() ? encontrado.getSenha() : novaSenha;
        String perfilFinal = novoPerfil.isBlank() ? encontrado.getPerfil() : novoPerfil;

        Usuario atualizado = new Usuario(encontrado.getId(), encontrado.getLogin(), senhaFinal, perfilFinal, encontrado.getFuncionarioId());
        usuarios.set(usuarios.indexOf(encontrado), atualizado);
        UsuarioRepositorio.salvar(usuarios);

        System.out.println("Usuario atualizado com sucesso!");
    }

    public void excluir(Scanner scanner, Usuario usuarioLogado) {
        ArrayList<Usuario> usuarios = UsuarioRepositorio.carregar();

        listar();
        int id = util.Leitor.lerInteiro(scanner, "Digite o id do usuario a excluir: ");

        if (id == usuarioLogado.getId()) {
            System.out.println("Voce nao pode excluir o usuario com o qual esta logado.");
            return;
        }

        Usuario encontrado = buscarPorId(usuarios, id);
        if (encontrado == null) {
            System.out.println("Usuario nao encontrado.");
            return;
        }

        usuarios.remove(encontrado);
        UsuarioRepositorio.salvar(usuarios);
        System.out.println("Usuario excluido com sucesso!");
    }

    private Usuario buscarPorId(ArrayList<Usuario> usuarios, int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public Usuario autenticar(Scanner scanner) {
        ArrayList<Usuario> usuarios = UsuarioRepositorio.carregar();

        String login = lerLoginComConfirmacao(scanner);

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = buscarPorLogin(usuarios, login);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario;
        }

        return null;
    }

    private String lerLoginComConfirmacao(Scanner scanner) {
        while (true) {
            System.out.print("Login: ");
            String login = scanner.nextLine();

            System.out.print("Confirma o login \"" + login + "\"? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("s")) {
                return login;
            }

            System.out.println("Vamos digitar o login novamente.");
        }
    }

    private Usuario buscarPorLogin(ArrayList<Usuario> usuarios, String login) {
        for (Usuario u : usuarios) {
            if (u.getLogin().equals(login)) {
                return u;
            }
        }
        return null;
    }
}
