package br.producao.models;

public class MaquinaModel {
    private int idMaquina;
    private String codigoIdentificacao;
    private int tempoProcessoSegundos;
    private String tipoEtapa;

    public MaquinaModel(int idMaquina, String codigoIdentificacao, int tempoProcessoSegundos, String tipoEtapa) {
        this.idMaquina = idMaquina;
        this.codigoIdentificacao = codigoIdentificacao;
        this.tempoProcessoSegundos = tempoProcessoSegundos;
        this.tipoEtapa = tipoEtapa;
    }

    public int getIdMaquina() { return idMaquina; }
    public void setIdMaquina(int idMaquina) { this.idMaquina = idMaquina; }
    public String getCodigoIdentificacao() { return codigoIdentificacao; }
    public void setCodigoIdentificacao(String codigoIdentificacao) { this.codigoIdentificacao = codigoIdentificacao; }
    public int getTempoProcessoSegundos() { return tempoProcessoSegundos; }
    public void setTempoProcessoSegundos(int tempoProcessoSegundos) { this.tempoProcessoSegundos = tempoProcessoSegundos; }
    public String getTipoEtapa() { return tipoEtapa; }
    public void setTipoEtapa(String tipoEtapa) { this.tipoEtapa = tipoEtapa; }
}