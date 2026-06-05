package br.producao.models;

import java.time.LocalDateTime;

public class ProdutoModel {
    private int idProduto;
    private String nomeModelo;
    private double custoProducao;
    private LocalDateTime dataFabricacao;

    // Crie o Construtor, Getters e Setters
    public ProdutoModel(int idProduto, String nomeModelo, double custoProducao, LocalDateTime dataFabricacao) {
        this.idProduto = idProduto;
        this.nomeModelo = nomeModelo;
        this.custoProducao = custoProducao;
        this.dataFabricacao = dataFabricacao;
    }

    // Getters e Setters omitidos por brevidade...


    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeModelo() {
        return nomeModelo;
    }

    public void setNomeModelo(String nomeModelo) {
        this.nomeModelo = nomeModelo;
    }

    public double getCustoProducao() {
        return custoProducao;
    }

    public void setCustoProducao(double custoProducao) {
        this.custoProducao = custoProducao;
    }

    public LocalDateTime getDataFabricacao() {
        return dataFabricacao;
    }

    public void setDataFabricacao(LocalDateTime dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
    }
}