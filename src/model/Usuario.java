package model;

public class Usuario {
    private int id;
    private String login;
    private String senha;
    private String perfil;
    private int funcionarioId;

    public Usuario(int id, String login, String senha, String perfil, int funcionarioId) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
        this.funcionarioId = funcionarioId;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public int getFuncionarioId() {
        return funcionarioId;
    }

    public String paraCsv() {
        return id + ";" + login + ";" + senha + ";" + perfil + ";" + funcionarioId;
    }

    public static Usuario deCsv(String linha) {
        String[] campos = linha.split(";");
        int id = Integer.parseInt(campos[0]);
        String login = campos[1];
        String senha = campos[2];
        String perfil = campos[3];
        int funcionarioId = campos.length > 4 ? Integer.parseInt(campos[4]) : 0;
        return new Usuario(id, login, senha, perfil, funcionarioId);
    }

    @Override
    public String toString() {
        return "Id: " + id + " | Login: " + login + " | Perfil: " + perfil;
    }
}
