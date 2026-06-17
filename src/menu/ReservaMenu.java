package menu;

import model.Funcionario;
import model.Hospede;
import model.Quarto;
import model.Reserva;
import model.Usuario;
import repository.FuncionarioRepositorio;
import repository.HospedeRepositorio;
import repository.QuartoRepositorio;
import repository.ReservaRepositorio;
import util.Leitor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ReservaMenu {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void criar(Scanner scanner, Usuario usuarioLogado) {
        int hospedeId = buscarHospedeInterativo(scanner);
        if (hospedeId == -1) {
            return;
        }

        ArrayList<Quarto> quartos = QuartoRepositorio.carregar();
        Quarto quarto = escolherQuartoDisponivel(scanner, quartos);
        if (quarto == null) {
            return;
        }
        int quartoNumero = quarto.getNumero();

        int funcionarioId = obterFuncionarioResponsavel(scanner, usuarioLogado);
        if (funcionarioId == -1) {
            return;
        }

        LocalDate checkIn = lerData(scanner, "Data de check-in (DD/MM/AAAA): ");
        LocalDate checkOut = lerData(scanner, "Data de check-out (DD/MM/AAAA): ");

        if (!checkOut.isAfter(checkIn)) {
            System.out.println("Data de check-out deve ser depois do check-in.");
            return;
        }

        ArrayList<Reserva> reservas = ReservaRepositorio.carregar();
        int novoId = reservas.size() + 1;
        Reserva reserva = new Reserva(novoId, hospedeId, quartoNumero, funcionarioId, checkIn, checkOut, "confirmada");
        reservas.add(reserva);
        ReservaRepositorio.salvar(reservas);

        quarto.setStatus("ocupado");
        QuartoRepositorio.salvar(quartos);

        System.out.println("Reserva criada com sucesso!");
    }

    public void listar() {
        ArrayList<Reserva> reservas = ReservaRepositorio.carregar();

        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada ainda.");
            return;
        }

        HashMap<Integer, String> nomesHospedes = carregarNomesHospedes();
        HashMap<Integer, String> nomesFuncionarios = carregarNomesFuncionarios();

        System.out.println("\n--- Lista de Reservas ---");
        for (Reserva r : reservas) {
            String nh = nomesHospedes.getOrDefault(r.getHospedeId(), "Hospede #" + r.getHospedeId());
            String nf = nomesFuncionarios.getOrDefault(r.getFuncionarioId(), "Funcionario #" + r.getFuncionarioId());
            System.out.println(r.toString(nh, nf));
        }
    }

    public void cancelar(Scanner scanner) {
        ArrayList<Reserva> reservas = ReservaRepositorio.carregar();

        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada ainda.");
            return;
        }

        HashMap<Integer, String> nomesHospedes = carregarNomesHospedes();
        HashMap<Integer, String> nomesFuncionarios = carregarNomesFuncionarios();

        System.out.println("\n--- Lista de Reservas ---");
        for (Reserva r : reservas) {
            String nh = nomesHospedes.getOrDefault(r.getHospedeId(), "Hospede #" + r.getHospedeId());
            String nf = nomesFuncionarios.getOrDefault(r.getFuncionarioId(), "Funcionario #" + r.getFuncionarioId());
            System.out.println(r.toString(nh, nf));
        }

        int id = Leitor.lerInteiro(scanner, "Digite o id da reserva a cancelar: ");

        Reserva encontrada = null;
        for (Reserva r : reservas) {
            if (r.getId() == id) {
                encontrada = r;
                break;
            }
        }

        if (encontrada == null) {
            System.out.println("Reserva nao encontrada.");
            return;
        }

        if (encontrada.getStatus().equalsIgnoreCase("finalizada") || encontrada.getStatus().equalsIgnoreCase("cancelada")) {
            System.out.println("Nao e possivel cancelar uma reserva com status '" + encontrada.getStatus() + "'.");
            return;
        }

        encontrada.cancelar();
        ReservaRepositorio.salvar(reservas);

        ArrayList<Quarto> quartos = QuartoRepositorio.carregar();
        for (Quarto q : quartos) {
            if (q.getNumero() == encontrada.getQuartoNumero()) {
                q.setStatus("disponivel");
                break;
            }
        }
        QuartoRepositorio.salvar(quartos);

        System.out.println("Reserva cancelada com sucesso!");
    }

    private int obterFuncionarioResponsavel(Scanner scanner, Usuario usuarioLogado) {
        if (usuarioLogado.getPerfil().equalsIgnoreCase("funcionario") && usuarioLogado.getFuncionarioId() > 0) {
            return usuarioLogado.getFuncionarioId();
        }

        int funcionarioId = Leitor.lerInteiro(scanner, "Id do funcionario responsavel: ");
        boolean existe = FuncionarioRepositorio.carregar().stream().anyMatch(f -> f.getId() == funcionarioId);
        if (!existe) {
            System.out.println("Funcionario nao encontrado. Cadastre o funcionario primeiro.");
            return -1;
        }
        return funcionarioId;
    }

    private int buscarHospedeInterativo(Scanner scanner) {
        System.out.print("Digite o nome ou CPF do hospede: ");
        String texto = scanner.nextLine().trim().toLowerCase();

        ArrayList<Hospede> encontrados = new ArrayList<>();
        for (Hospede h : HospedeRepositorio.carregar()) {
            if (h.getNome().toLowerCase().contains(texto) || h.getCpf().contains(texto)) {
                encontrados.add(h);
            }
        }

        if (encontrados.isEmpty()) {
            System.out.println("Nenhum hospede encontrado com esse nome ou CPF.");
            return -1;
        }

        System.out.println("\n--- Hospedes encontrados ---");
        for (Hospede h : encontrados) {
            System.out.println(h);
        }

        int id = Leitor.lerInteiro(scanner, "Digite o id do hospede desejado: ");
        for (Hospede h : encontrados) {
            if (h.getId() == id) {
                return id;
            }
        }

        System.out.println("Id informado nao esta na lista encontrada.");
        return -1;
    }

    private Quarto escolherQuartoDisponivel(Scanner scanner, ArrayList<Quarto> quartos) {
        ArrayList<Quarto> disponiveis = new ArrayList<>();
        for (Quarto q : quartos) {
            if (q.disponivel()) {
                disponiveis.add(q);
            }
        }

        if (disponiveis.isEmpty()) {
            System.out.println("Nenhum quarto disponivel no momento.");
            return null;
        }

        System.out.println("\n--- Quartos disponiveis ---");
        for (Quarto q : disponiveis) {
            System.out.println(q);
        }

        int numero = Leitor.lerInteiro(scanner, "Digite o numero do quarto desejado: ");
        for (Quarto q : disponiveis) {
            if (q.getNumero() == numero) {
                return q;
            }
        }

        System.out.println("Numero informado nao esta na lista de disponiveis.");
        return null;
    }

    private HashMap<Integer, String> carregarNomesHospedes() {
        HashMap<Integer, String> mapa = new HashMap<>();
        for (Hospede h : HospedeRepositorio.carregar()) {
            mapa.put(h.getId(), h.getNome());
        }
        return mapa;
    }

    private HashMap<Integer, String> carregarNomesFuncionarios() {
        HashMap<Integer, String> mapa = new HashMap<>();
        for (Funcionario f : FuncionarioRepositorio.carregar()) {
            mapa.put(f.getId(), f.getNome());
        }
        return mapa;
    }

    private LocalDate lerData(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine();
            try {
                return LocalDate.parse(texto, FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data invalida! Use o formato DD/MM/AAAA, ex: 20/06/2026.");
            }
        }
    }
}
