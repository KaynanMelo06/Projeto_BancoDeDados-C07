package br.producao.simulacao;

import br.producao.maquinas.Maquina;

// 1. Implementar a interface Runnable
public class TarefaProducao implements Runnable {

    private String nomeProduto;
    private Maquina[] etapas;

    // 2. Construtor para receber os dados necessários
    public TarefaProducao(String nomeProduto, Maquina[] etapas) {
        this.nomeProduto = nomeProduto;
        this.etapas = etapas;
    }

    // 3. O metodo run() contém a lógica que vai rodar na Thread
    @Override
    public void run() {
        Simulador.iniciarProcesso(this.nomeProduto, this.etapas);
    }
}