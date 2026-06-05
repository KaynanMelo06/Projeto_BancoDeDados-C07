# ⚙️ **Projeto: Simulador de Linha de Produção**
🧠 *Integra Engenharia de Produção e Computação usando Programação Orientada a Objetos (Java)*

---

## 🎯 **Objetivo do Projeto**
Desenvolver um simulador que representa uma **linha de produção automatizada**, composta por máquinas que processam produtos em sequência, registram resultados e geram relatórios de desempenho.

O projeto demonstra conceitos fundamentais de **Engenharia de Produção** (fluxo produtivo, gargalos, tempo de ciclo) e de **Computação** (POO, threads, exceções, arquivos, interfaces e pacotes).

---

## 🏭 **Conceito de Engenharia de Produção Aplicado**
- Fluxo produtivo e sequenciamento de operações.
- Simulação de falhas mecânicas e manutenção.
- Controle de qualidade e rejeição de peças.
- Registro e análise de desempenho (Logs).

---

## 💻 **Conceitos de Programação Utilizados**
✅ Classes e Objetos  
✅ Herança e Polimorfismo  
✅ Interfaces (`Registravel`, `Runnable`)  
✅ Pacotes  
✅ Modificadores de acesso e Encapsulamento  
✅ Construtores  
✅ Atributos e métodos `static`  
✅ Coleções e Arrays  
✅ **Threads
✅ **Java NIO (Path, Files)** para I/O moderno  
✅ Exceções personalizadas (`RuntimeException`)

---

## 🧩 **Estrutura de Pacotes**
```
br.producao.maquinas   -> Lógica das máquinas (Corte, Montagem, Inspeção)
br.producao.produtos   -> Definição do produto e gravação de arquivo
br.producao.simulacao  -> Controle do fluxo (LinhaProducao, Simulador, TarefaProducao)
br.producao.arquivos   -> Leitura de configurações externas
br.producao.excecoes   -> Erros personalizados do sistema
```

---
# 🧱 Principais Classes e Interfaces

## 🏗️ Classe Abstrata `Maquina`

Base para todas as máquinas da fábrica. Define o contrato processar().

```Java
public abstract class Maquina {
    protected String id;
    protected int tempoProcesso;
    // ...
    public abstract void processar(Produto p) throws maquinaQuebradaException;
}
```
## ⚙️ Subclasses de Máquina

    MaquinaCorte: Simula o corte e possui chance de falha mecânica (Lâmina partir).

    MaquinaMontagem: Realiza a montagem das peças.

    MaquinaInspecao: Verifica a qualidade e pode rejeitar o produto (10% de chance).

## 📦 Classe Produto

Implementa a lógica de status e gravação usando Java NIO.

```Java
public class Produto implements Registravel {
    // ...
    @Override
    public void registrarEmArquivo() {
        Path caminho = Paths.get("relatorio_producao.txt");
        // Usa Files.writeString com opção APPEND para criar log histórico
        Files.writeString(caminho, conteudo, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
```
## ⚡ Threads (Implementação Clássica)

O projeto utiliza a interface Runnable para definir tarefas independentes, permitindo a fabricação paralela de vários produtos.

    Classe TarefaProducao: Implementa Runnable e encapsula toda a lógica de fabricação de um único carro.

    No Main: Instanciamos objetos Thread passando as tarefas como parâmetro.


```Java
// Exemplo do código no Main
TarefaProducao tarefa = new TarefaProducao("Carro A", etapas);
Thread t1 = new Thread(tarefa);
t1.start(); // Inicia a produção paralela
```

## 🧠 Classes de Simulação

LinhaProducao

Gerencia a passagem do produto pelas etapas sequenciais (Corte -> Montagem -> Inspeção) e trata exceções de falha.

Simulador

Classe utilitária que orquestra o início do processo e garante o registro final do produto.

## 📂 Leitura de Configuração (Java NIO)

O sistema lê os tempos de processo de um arquivo externo configuracao.txt localizado na raiz do projeto. Isso permite ajustar a velocidade da fábrica sem recompilar o código.

```Java
// Exemplo de configuração
CORTE=5
MONTAGEM=3
INSPECAO=2
```
## ❗ Exceções Personalizadas

O sistema possui tratamento robusto de erros:

    maquinaQuebradaException: Lançada quando ocorre uma falha mecânica (ex: na MaquinaCorte).

    ConfiguracaoNaoEncontradaException: Prevista para erros críticos na leitura do arquivo de configuração.

## 📊 Exemplo de Saída (Console)

```

=== SISTEMA DE PRODUÇÃO PARALELA ===

>> Thread iniciada para: Carro Modelo A
>> Thread iniciada para: Carro Modelo B
[Corte] A cortar: Carro Modelo A
[Corte] A cortar: Carro Modelo B
[Corte] Finalizado: Carro Modelo A
[Montagem] A montar: Carro Modelo A
...
[Inspeção] APROVADO: Carro Modelo A
[Arquivo] Relatório gravado para: Carro Modelo A
```

## 📝 Formato do Relatório (Arquivo)

O arquivo relatorio_producao.txt é gerado automaticamente:
```

Produto ID: 1 | Nome: Carro Modelo A | Status: APROVADO
Produto ID: 2 | Nome: Carro Modelo B | Status: REJEITADO
Produto ID: 3 | Nome: Carro Modelo C | Status: APROVADO
```