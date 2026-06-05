package br.producao.simulacao;

import br.producao.maquinas.Maquina;
import br.producao.produtos.Produto;

public class Simulador {

    // Metodo estático que encapsula a criação e execução de um produto
    public static void iniciarProcesso(String nomeProduto, Maquina[] etapas) {
        System.out.println(">> Nova thread iniciada para: " + nomeProduto);

        Produto p = new Produto(nomeProduto);
        LinhaProducao linha = new LinhaProducao(etapas);

        linha.iniciar(p);
        p.registrarEmArquivo();
    }
}