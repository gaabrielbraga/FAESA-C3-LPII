package model;

import java.time.LocalDate;

public class Reserva {
    private int id;
    private int hospedeId;
    private int quartoNumero;
    private int funcionarioId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String status;

    public Reserva(int id, int hospedeId, int quartoNumero, int funcionarioId,
                    LocalDate checkIn, LocalDate checkOut, String status) {
        this.id = id;
        this.hospedeId = hospedeId;
        this.quartoNumero = quartoNumero;
        this.funcionarioId = funcionarioId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getHospedeId() {
        return hospedeId;
    }

    public int getQuartoNumero() {
        return quartoNumero;
    }

    public int getFuncionarioId() {
        return funcionarioId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void confirmar() {
        this.status = "confirmada";
    }

    public void cancelar() {
        this.status = "cancelada";
    }

    public String paraCsv() {
        return id + ";" + hospedeId + ";" + quartoNumero + ";" + funcionarioId + ";"
                + checkIn + ";" + checkOut + ";" + status;
    }

    public static Reserva deCsv(String linha) {
        String[] campos = linha.split(";");
        int id = Integer.parseInt(campos[0]);
        int hospedeId = Integer.parseInt(campos[1]);
        int quartoNumero = Integer.parseInt(campos[2]);
        int funcionarioId = Integer.parseInt(campos[3]);
        LocalDate checkIn = LocalDate.parse(campos[4]);
        LocalDate checkOut = LocalDate.parse(campos[5]);
        String status = campos[6];
        return new Reserva(id, hospedeId, quartoNumero, funcionarioId, checkIn, checkOut, status);
    }

    public String toString(String nomeHospede, String nomeFuncionario) {
        return "Id: " + id
                + " | Hospede: " + nomeHospede
                + " | Quarto: " + quartoNumero
                + " | Funcionario: " + nomeFuncionario
                + " | CheckIn: " + checkIn
                + " | CheckOut: " + checkOut
                + " | Status: " + status;
    }

    @Override
    public String toString() {
        return "Id: " + id + " | Hospede id: " + hospedeId + " | Quarto: " + quartoNumero
                + " | Funcionario id: " + funcionarioId + " | CheckIn: " + checkIn
                + " | CheckOut: " + checkOut + " | Status: " + status;
    }
}
