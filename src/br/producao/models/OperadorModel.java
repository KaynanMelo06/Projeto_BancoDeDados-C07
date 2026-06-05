package br.producao.models;

public class OperadorModel {
    private int idOperador;
    private String nomeCompleto;
    private double salario;
    private boolean turnoNoturno;

    public OperadorModel(int idOperador, String nomeCompleto, double salario, boolean turnoNoturno) {
        this.idOperador = idOperador;
        this.nomeCompleto = nomeCompleto;
        this.salario = salario;
        this.turnoNoturno = turnoNoturno;
    }

    public int getIdOperador() { return idOperador; }
    public void setIdOperador(int idOperador) { this.idOperador = idOperador; }
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
    public boolean isTurnoNoturno() { return turnoNoturno; }
    public void setTurnoNoturno(boolean turnoNoturno) { this.turnoNoturno = turnoNoturno; }
}