package menu;

import model.Quarto;
import model.Reserva;
import model.Usuario;
import repository.QuartoRepositorio;
import repository.ReservaRepositorio;
import util.Leitor;

import java.util.ArrayList;
import java.util.Scanner;

public class CheckInMenu {

    public void fazerCheckIn(Scanner scanner, Usuario usuarioLogado) {
        ArrayList<Reserva> reservas = ReservaRepositorio.carregar();

        Reserva reserva = escolherReserva(scanner, reservas, "confirmada");
        if (reserva == null) {
            return;
        }

        reserva.setStatus("em andamento");
        ReservaRepositorio.salvar(reservas);

        System.out.println("Check-in realizado com sucesso! Reserva " + reserva.getId() + " agora esta em andamento.");
    }

    public void fazerCheckOut(Scanner scanner, Usuario usuarioLogado) {
        ArrayList<Reserva> reservas = ReservaRepositorio.carregar();

        Reserva reserva = escolherReserva(scanner, reservas, "em andamento");
        if (reserva == null) {
            return;
        }

        reserva.setStatus("finalizada");
        ReservaRepositorio.salvar(reservas);

        ArrayList<Quarto> quartos = QuartoRepositorio.carregar();
        for (Quarto q : quartos) {
            if (q.getNumero() == reserva.getQuartoNumero()) {
                q.setStatus("disponivel");
                break;
            }
        }
        QuartoRepositorio.salvar(quartos);

        System.out.println("Check-out realizado com sucesso! Quarto " + reserva.getQuartoNumero() + " esta disponivel novamente.");
    }

    private Reserva escolherReserva(Scanner scanner, ArrayList<Reserva> reservas, String statusEsperado) {
        ArrayList<Reserva> filtradas = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.getStatus().equalsIgnoreCase(statusEsperado)) {
                filtradas.add(r);
            }
        }

        if (filtradas.isEmpty()) {
            System.out.println("Nenhuma reserva com status '" + statusEsperado + "' encontrada.");
            return null;
        }

        System.out.println("\n--- Reservas com status '" + statusEsperado + "' ---");
        for (Reserva r : filtradas) {
            System.out.println(r);
        }

        int id = Leitor.lerInteiro(scanner, "Digite o id da reserva: ");
        for (Reserva r : reservas) {
            if (r.getId() == id && r.getStatus().equalsIgnoreCase(statusEsperado)) {
                return r;
            }
        }

        System.out.println("Reserva nao encontrada com esse id e status.");
        return null;
    }
}
