package model;

public class Quarto {
    private int numero;
    private String tipo;
    private double preco;
    private String status;

    public Quarto(int numero, String tipo, double preco, String status) {
        this.numero = numero;
        this.tipo = tipo;
        this.preco = preco;
        this.status = status;
    }

    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPreco() {
        return preco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean disponivel() {
        return status.equalsIgnoreCase("disponivel");
    }

    public String paraCsv() {
        return numero + ";" + tipo + ";" + preco + ";" + status;
    }

    public static Quarto deCsv(String linha) {
        String[] campos = linha.split(";");
        int numero = Integer.parseInt(campos[0]);
        String tipo = campos[1];
        double preco = Double.parseDouble(campos[2]);
        String status = campos[3];
        return new Quarto(numero, tipo, preco, status);
    }

    @Override
    public String toString() {
        return "Numero: " + numero + " | Tipo: " + tipo + " | Preco: " + preco + " | Status: " + status;
    }
}
