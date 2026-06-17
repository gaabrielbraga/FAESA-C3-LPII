package menu;

import model.Quarto;
import model.Reserva;
import repository.QuartoRepositorio;
import repository.ReservaRepositorio;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class RelatorioMenu {

    public void relatorioOcupacao() {
        ArrayList<Quarto> quartos = QuartoRepositorio.carregar();

        if (quartos.isEmpty()) {
            System.out.println("Nenhum quarto cadastrado ainda.");
            return;
        }

        int disponiveis = 0;
        int ocupados = 0;
        for (Quarto q : quartos) {
            if (q.disponivel()) {
                disponiveis++;
            } else {
                ocupados++;
            }
        }

        System.out.println("\n--- Relatorio de Ocupacao ---");
        System.out.println("Total de quartos: " + quartos.size());
        System.out.println("Disponiveis: " + disponiveis);
        System.out.println("Ocupados: " + ocupados);
    }

    public void relatorioReservas() {
        ArrayList<Reserva> reservas = ReservaRepositorio.carregar();

        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada ainda.");
            return;
        }

        int confirmadas = 0;
        int emAndamento = 0;
        int finalizadas = 0;
        int canceladas = 0;

        for (Reserva r : reservas) {
            switch (r.getStatus()) {
                case "confirmada" -> confirmadas++;
                case "em andamento" -> emAndamento++;
                case "finalizada" -> finalizadas++;
                case "cancelada" -> canceladas++;
            }
        }

        System.out.println("\n--- Relatorio de Reservas ---");
        System.out.println("Total de reservas: " + reservas.size());
        System.out.println("Confirmadas: " + confirmadas);
        System.out.println("Em andamento: " + emAndamento);
        System.out.println("Finalizadas: " + finalizadas);
        System.out.println("Canceladas: " + canceladas);
    }

    public void relatorioFinanceiro() {
        ArrayList<Reserva> reservas = ReservaRepositorio.carregar();
        ArrayList<Quarto> quartos = QuartoRepositorio.carregar();

        double faturamento = 0;
        int reservasFinalizadas = 0;

        for (Reserva r : reservas) {
            if (r.getStatus().equals("finalizada")) {
                Quarto quarto = buscarQuarto(quartos, r.getQuartoNumero());
                if (quarto != null) {
                    long noites = ChronoUnit.DAYS.between(r.getCheckIn(), r.getCheckOut());
                    faturamento += noites * quarto.getPreco();
                    reservasFinalizadas++;
                }
            }
        }

        System.out.println("\n--- Relatorio Financeiro ---");
        System.out.println("Reservas finalizadas: " + reservasFinalizadas);
        System.out.println("Faturamento total: R$ " + faturamento);
    }

    private Quarto buscarQuarto(ArrayList<Quarto> quartos, int numero) {
        for (Quarto q : quartos) {
            if (q.getNumero() == numero) {
                return q;
            }
        }
        return null;
    }
}
