package br.producao.arquivos;

import br.producao.excecoes.ConfiguracaoNaoEncontradaException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeitorConfiguracao {

    public static Map<String, Integer> ler(String caminhoArquivo) {
        Map<String, Integer> map = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(caminhoArquivo));
            for (String s : lines) {
                String[] parts = s.split("=");
                if(parts.length == 2) {
                    map.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                }
            }
        } catch (Exception e) {
            throw new ConfiguracaoNaoEncontradaException("ERRO CRÍTICO: O arquivo '" + caminhoArquivo + "' não foi encontrado!");
        }
        return map;
    }
}