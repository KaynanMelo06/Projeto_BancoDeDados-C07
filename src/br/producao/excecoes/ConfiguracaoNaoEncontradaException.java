package br.producao.excecoes;

public class ConfiguracaoNaoEncontradaException extends RuntimeException {
    public ConfiguracaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}