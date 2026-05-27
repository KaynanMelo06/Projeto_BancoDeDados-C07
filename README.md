# Sistema de Gestão de Linha de Produção (Manufatura de Robótica - VSSS)

Este repositório contém o desenvolvimento do banco de dados relacional para um **Sistema de Gestão de Linha de Produção Automated**, desenvolvido como parte do projeto prático da disciplina de Banco de Dados no **Inatel (Instituto Nacional de Telecomunicações)**.

O sistema simula o fluxo completo de fabricação de uma indústria automatizada voltada para componentes de robótica, especificamente para a categoria **VSSS (Very Small Size Soccer)**, integrando conceitos avançados de Engenharia de Produção, Computação e Automação Industrial.

---

## 📌 Visão Geral do Projeto

O objetivo principal deste banco de dados é gerenciar e registrar todo o fluxo de fabricação de produtos, o maquinário envolvido nas etapas do processo, os operadores escalados por turno e os laudos de controle de qualidade gerados ao fim da linha.

A arquitetura do sistema foi projetada para garantir:
* **Rastreabilidade total:** Acompanhamento de cada peça produzida desde a montagem até a inspeção final.
* **Controle de manutenções:** Registro do histórico de reparos e falhas mecânicas/elétricas de todo o maquinário industrial.
* **Auditoria de qualidade:** Controle rigoroso do status de aprovação de cada peça através de laudos técnicos detalhados.

---

## 🛠️ Estrutura do Banco de Dados (Modelagem Lógica)

O modelo implementado atende aos requisitos de integridade referencial, abrangendo as três cardinalidades clássicas de um modelo relacional:

1. **Produto:** Representa os itens fabricados (Ex: Placas base VSSS, motores com encoder, chassis, módulos de rádio).
2. **Relatorio_Inspecao (Relacionamento 1:1):** Cada produto gerado passa obrigatoriamente por uma única inspeção detalhada de controle de qualidade.
3. **Maquina:** Representa os equipamentos físicos da planta industrial (CNC, Pick and Place, Forno Reflow, Estações de Teste).
4. **Manutencao (Relacionamento 1:N):** Uma máquina pode sofrer múltiplas paradas ou quebras ao longo de sua vida útil, mas cada laudo técnico de manutenção pertence a uma única máquina.
5. **Operador:** Funcionários técnicos habilitados a operar o maquinário da fábrica.
6. **Operador_has_Maquina (Relacionamento N:M):** Tabela associativa que gerencia a escala de trabalho, permitindo que múltiplos operadores operem diferentes máquinas dependendo do revezamento de turnos e treinamentos técnicos.

---

## 🚀 Conteúdo da Segunda Entrega

O script SQL principal (`script_entrega_2_final.sql`) foi construído de forma limpa e otimizada (sem códigos poluídos gerados por ferramentas automáticas), contemplando os seguintes requisitos acadêmicos:

* **DDL Completo:** Criação automatizada de todas as tabelas com tipos de dados coesos (`INT`, `VARCHAR`, `DECIMAL`, `DATETIME`, `TINYINT`, `TEXT`).
* **Carga Inicial de Dados:** Inserção de no mínimo 5 registros realistas e integrados para cada entidade do sistema.
* **Controle de Acesso e Segurança (DCL):** * Criação de uma Role específica para operação (`role_operacao_fabrica`) com permissões restritas de `SELECT` e `INSERT`.
  * Criação de 2 usuários arbitrários (`usr_auditor` e `usr_tecnico`) associados por padrão a esta role.
* **Objetos Programáveis incorporados (DML Avançado):**
  * **View (`vw_rastreabilidade_produtos`):** Visão unificada criada para auditorias rápidas, cruzando os produtos com seus respectivos laudos de inspeção.
  * **Stored Procedure (`sp_registrar_falha`):** Procedimento automatizado para o registro ágil de novas manutenções preventivas ou corretivas na data corrente.
  * **Trigger (`trg_validar_custo_produto`):** Gatilho de validação que impede a inserção de produtos com custos de produção negativos, blindando as regras de negócio financeiras da fábrica.

---

## 💻 Como Executar o Script

1. Certifique-se de ter um servidor MySQL ativo instalado localmente ou em nuvem.
2. Abra sua ferramenta de gerência de preferência (como o **MySQL Workbench**).
3. Copie o código contido no arquivo `.sql` deste repositório e cole em uma aba de consulta.
4. Execute o script completo (clicando no ícone do raio). 
5. O script foi projetado de forma idempotente, ou seja, possui tratamentos de limpeza (`DROP DATABASE/ROLE/USER IF EXISTS`) para que possa ser reexecutado múltiplas vezes seguidas sem gerar conflitos ou erros de duplicação.

---

## 👥 Integrantes do Grupo

Projeto desenvolvido com orgulho pelos acadêmicos de Engenharia do **Inatel**:

* **Kaynan H Dias de Melo**
* **Nathan Arruola da Costa**
* **Fuad Murad**

---
Desenvolvido em Santa Rita do Sapucaí, MG - Polo do Vale da Eletrônica.
