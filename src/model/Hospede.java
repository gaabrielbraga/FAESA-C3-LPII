package model;

public class Hospede {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;

    public Hospede(int id, String nome, String cpf, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String paraCsv() {
        return id + ";" + nome + ";" + cpf + ";" + telefone;
    }

    public static Hospede deCsv(String linha) {
        String[] campos = linha.split(";");
        int id = Integer.parseInt(campos[0]);
        String nome = campos[1];
        String cpf = campos[2];
        String telefone = campos[3];
        return new Hospede(id, nome, cpf, telefone);
    }

    @Override
    public String toString() {
        return "Id: " + id + " | Nome: " + nome + " | CPF: " + cpf + " | Telefone: " + telefone;
    }
}
