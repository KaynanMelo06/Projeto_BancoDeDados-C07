package br.producao.simulacao;

import br.producao.maquinas.Maquina;
import br.producao.produtos.Produto;
import br.producao.excecoes.MaquinaQuebradaException;

public class LinhaProducao {
    private Maquina[] etapas;

    public LinhaProducao(Maquina[] etapas) {
        this.etapas = etapas;
    }

    public void iniciar(Produto p) {
        for (int i = 0; i < etapas.length; i++) {
            Maquina m = etapas[i];
            try {
                m.processar(p);
            } catch (MaquinaQuebradaException e) {
                System.out.println("Erro na linha: " + e.getMessage());
                p.setAprovado(false);
                break;
            }
        }
    }
}