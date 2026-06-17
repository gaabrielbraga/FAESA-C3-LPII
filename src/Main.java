import menu.CheckInMenu;
import menu.FuncionarioMenu;
import menu.HospedeMenu;
import menu.LoginMenu;
import menu.QuartoMenu;
import menu.RelatorioMenu;
import menu.ReservaMenu;
import model.Hotel;
import model.Usuario;
import util.Leitor;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel("Pousada");

        HospedeMenu hospedeMenu = new HospedeMenu();
        QuartoMenu quartoMenu = new QuartoMenu();
        FuncionarioMenu funcionarioMenu = new FuncionarioMenu();
        ReservaMenu reservaMenu = new ReservaMenu();
        CheckInMenu checkInMenu = new CheckInMenu();
        RelatorioMenu relatorioMenu = new RelatorioMenu();
        LoginMenu loginMenu = new LoginMenu();

        boolean continuarExecutando = true;

        while (continuarExecutando) {
            Usuario usuarioLogado = fazerLogin(scanner, loginMenu);

            if (usuarioLogado == null) {
                continuarExecutando = false;
                break;
            }

            System.out.println("\nBem-vindo(a), " + usuarioLogado.getLogin() + "! (perfil: " + usuarioLogado.getPerfil() + ")");

            if (usuarioLogado.getPerfil().equalsIgnoreCase("admin")) {
                continuarExecutando = menuAdmin(scanner, hotel, hospedeMenu, quartoMenu, funcionarioMenu,
                        reservaMenu, checkInMenu, relatorioMenu, loginMenu, usuarioLogado);
            } else {
                continuarExecutando = menuFuncionario(scanner, hotel, hospedeMenu, quartoMenu, reservaMenu, checkInMenu, usuarioLogado);
            }
        }

        System.out.println("Sistema encerrado. Ate logo!");
        scanner.close();
    }

    private static Usuario fazerLogin(Scanner scanner, LoginMenu loginMenu) {
        if (!loginMenu.existeUsuario()) {
            loginMenu.cadastrarPrimeiroAdmin(scanner);
        }

        int tentativas = 0;
        while (tentativas < 3) {
            System.out.println("\n===== Login =====");
            Usuario usuario = loginMenu.autenticar(scanner);

            if (usuario != null) {
                return usuario;
            }

            tentativas++;
            System.out.println("Login ou senha invalidos. Tentativa " + tentativas + " de 3.");
        }

        System.out.println("Numero maximo de tentativas excedido. Encerrando o sistema.");
        return null;
    }

    private static boolean menuAdmin(Scanner scanner, Hotel hotel, HospedeMenu hospedeMenu, QuartoMenu quartoMenu,
                                      FuncionarioMenu funcionarioMenu, ReservaMenu reservaMenu,
                                      CheckInMenu checkInMenu, RelatorioMenu relatorioMenu, LoginMenu loginMenu,
                                      Usuario usuarioLogado) {
        int opcao;
        do {
            System.out.println("\n===== " + hotel.getNome() + " (ADMIN) =====");
            System.out.println("1 - Hospedes");
            System.out.println("2 - Quartos");
            System.out.println("3 - Funcionarios");
            System.out.println("4 - Reservas");
            System.out.println("5 - Check-in / Check-out");
            System.out.println("6 - Relatorios");
            System.out.println("7 - Usuarios do sistema");
            System.out.println("8 - Logout (trocar de conta)");
            System.out.println("0 - Sair do sistema");
            opcao = Leitor.lerInteiro(scanner, "Escolha uma opcao: ");

            switch (opcao) {
                case 1 -> menuHospedes(scanner, hospedeMenu);
                case 2 -> menuQuartos(scanner, quartoMenu);
                case 3 -> menuFuncionarios(scanner, funcionarioMenu);
                case 4 -> menuReservas(scanner, reservaMenu, usuarioLogado);
                case 5 -> menuCheckInCheckOut(scanner, checkInMenu, usuarioLogado);
                case 6 -> menuRelatorios(scanner, relatorioMenu);
                case 7 -> menuUsuarios(scanner, loginMenu, usuarioLogado);
                case 8 -> System.out.println("Saindo da conta...");
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 8 && opcao != 0);

        return opcao == 8;
    }

    private static boolean menuFuncionario(Scanner scanner, Hotel hotel, HospedeMenu hospedeMenu, QuartoMenu quartoMenu,
                                            ReservaMenu reservaMenu, CheckInMenu checkInMenu, Usuario usuarioLogado) {
        int opcao;
        do {
            System.out.println("\n===== " + hotel.getNome() + " (FUNCIONARIO) =====");
            System.out.println("1 - Hospedes");
            System.out.println("2 - Quartos");
            System.out.println("3 - Reservas");
            System.out.println("4 - Check-in / Check-out");
            System.out.println("5 - Logout (trocar de conta)");
            System.out.println("0 - Sair do sistema");
            opcao = Leitor.lerInteiro(scanner, "Escolha uma opcao: ");

            switch (opcao) {
                case 1 -> menuHospedes(scanner, hospedeMenu);
                case 2 -> menuQuartos(scanner, quartoMenu);
                case 3 -> menuReservas(scanner, reservaMenu, usuarioLogado);
                case 4 -> menuCheckInCheckOut(scanner, checkInMenu, usuarioLogado);
                case 5 -> System.out.println("Saindo da conta...");
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 5 && opcao != 0);

        return opcao == 5;
    }

    private static void menuHospedes(Scanner scanner, HospedeMenu hospedeMenu) {
        int opcao;
        do {
            System.out.println("\n--- Hospedes ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Editar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Voltar");
            opcao = Leitor.lerInteiro(scanner, "Escolha uma opcao: ");

            switch (opcao) {
                case 1 -> hospedeMenu.cadastrar(scanner);
                case 2 -> hospedeMenu.listar();
                case 3 -> hospedeMenu.editar(scanner);
                case 4 -> hospedeMenu.excluir(scanner);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }

    private static void menuQuartos(Scanner scanner, QuartoMenu quartoMenu) {
        int opcao;
        do {
            System.out.println("\n--- Quartos ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Editar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Voltar");
            opcao = Leitor.lerInteiro(scanner, "Escolha uma opcao: ");

            switch (opcao) {
                case 1 -> quartoMenu.cadastrar(scanner);
                case 2 -> quartoMenu.listar();
                case 3 -> quartoMenu.editar(scanner);
                case 4 -> quartoMenu.excluir(scanner);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }

    private static void menuFuncionarios(Scanner scanner, FuncionarioMenu funcionarioMenu) {
        int opcao;
        do {
            System.out.println("\n--- Funcionarios ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Editar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Voltar");
            opcao = Leitor.lerInteiro(scanner, "Escolha uma opcao: ");

            switch (opcao) {
                case 1 -> funcionarioMenu.cadastrar(scanner);
                case 2 -> funcionarioMenu.listar();
                case 3 -> funcionarioMenu.editar(scanner);
                case 4 -> funcionarioMenu.excluir(scanner);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }

    private static void menuReservas(Scanner scanner, ReservaMenu reservaMenu, Usuario usuarioLogado) {
        int opcao;
        do {
            System.out.println("\n--- Reservas ---");
            System.out.println("1 - Criar reserva");
            System.out.println("2 - Listar");
            System.out.println("3 - Cancelar");
            System.out.println("0 - Voltar");
            opcao = Leitor.lerInteiro(scanner, "Escolha uma opcao: ");

            switch (opcao) {
                case 1 -> reservaMenu.criar(scanner, usuarioLogado);
                case 2 -> reservaMenu.listar();
                case 3 -> reservaMenu.cancelar(scanner);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }

    private static void menuCheckInCheckOut(Scanner scanner, CheckInMenu checkInMenu, Usuario usuarioLogado) {
        int opcao;
        do {
            System.out.println("\n--- Check-in / Check-out ---");
            System.out.println("1 - Fazer check-in");
            System.out.println("2 - Fazer check-out");
            System.out.println("0 - Voltar");
            opcao = Leitor.lerInteiro(scanner, "Escolha uma opcao: ");

            switch (opcao) {
                case 1 -> checkInMenu.fazerCheckIn(scanner, usuarioLogado);
                case 2 -> checkInMenu.fazerCheckOut(scanner, usuarioLogado);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }

    private static void menuRelatorios(Scanner scanner, RelatorioMenu relatorioMenu) {
        int opcao;
        do {
            System.out.println("\n--- Relatorios ---");
            System.out.println("1 - Ocupacao de quartos");
            System.out.println("2 - Reservas por status");
            System.out.println("3 - Financeiro");
            System.out.println("0 - Voltar");
            opcao = Leitor.lerInteiro(scanner, "Escolha uma opcao: ");

            switch (opcao) {
                case 1 -> relatorioMenu.relatorioOcupacao();
                case 2 -> relatorioMenu.relatorioReservas();
                case 3 -> relatorioMenu.relatorioFinanceiro();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }

    private static void menuUsuarios(Scanner scanner, LoginMenu loginMenu, Usuario usuarioLogado) {
        int opcao;
        do {
            System.out.println("\n--- Usuarios do Sistema ---");
            System.out.println("1 - Cadastrar novo usuario");
            System.out.println("2 - Listar usuarios");
            System.out.println("3 - Editar usuario");
            System.out.println("4 - Excluir usuario");
            System.out.println("0 - Voltar");
            opcao = Leitor.lerInteiro(scanner, "Escolha uma opcao: ");

            switch (opcao) {
                case 1 -> loginMenu.cadastrar(scanner, null);
                case 2 -> loginMenu.listar();
                case 3 -> loginMenu.editar(scanner);
                case 4 -> loginMenu.excluir(scanner, usuarioLogado);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }
}
