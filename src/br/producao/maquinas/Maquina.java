package br.producao.maquinas;

import br.producao.produtos.Produto;
import br.producao.excecoes.MaquinaQuebradaException;

public abstract class Maquina {
    protected String id;
    protected int tempoProcesso;

    public Maquina(String id, int tempoProcesso) {
        this.id = id;
        this.tempoProcesso = tempoProcesso;
    }

    public abstract void processar(Produto p) throws MaquinaQuebradaException;
}