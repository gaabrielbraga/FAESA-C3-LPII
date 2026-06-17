package model;

public class Funcionario {
    private int id;
    private String nome;
    private String cargo;
    private String telefone;

    public Funcionario(int id, String nome, String cargo, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void checkIn(Reserva reserva) {
        reserva.setStatus("em andamento");
    }

    public void checkOut(Reserva reserva) {
        reserva.setStatus("finalizada");
    }

    public String paraCsv() {
        return id + ";" + nome + ";" + cargo + ";" + telefone;
    }

    public static Funcionario deCsv(String linha) {
        String[] campos = linha.split(";");
        int id = Integer.parseInt(campos[0]);
        String nome = campos[1];
        String cargo = campos[2];
        String telefone = campos[3];
        return new Funcionario(id, nome, cargo, telefone);
    }

    @Override
    public String toString() {
        return "Id: " + id + " | Nome: " + nome + " | Cargo: " + cargo + " | Telefone: " + telefone;
    }
}
