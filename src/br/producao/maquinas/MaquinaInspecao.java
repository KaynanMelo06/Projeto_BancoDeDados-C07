package br.producao.maquinas;

import br.producao.produtos.Produto;
import br.producao.excecoes.MaquinaQuebradaException;

public class MaquinaInspecao extends Maquina{

    public MaquinaInspecao(String id, int tempoProcesso) {
        super(id, tempoProcesso);
    }

    @Override
    public void processar(Produto p) throws MaquinaQuebradaException {
        System.out.println("[Inspecao] Inspecionar: " + p.getNome());

        try {
            // Simula o tempo de processo (conversão de segundos para milissegundos)
            Thread.sleep(tempoProcesso * 1000);

            // Simulação de Falha Mecânica (10% de chance)
            // Se falhar, lança a exceção que será capturada pela LinhaProducao
            if (Math.random() < 0.1) {
                throw new MaquinaQuebradaException("Erro encontrado na inspecao " + p.getNome() + "!");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("[Inspecao] Finalizado: " + p.getNome());
    }
}
