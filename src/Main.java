import br.producao.arquivos.LeitorConfiguracao;
import br.producao.dao.*;
import br.producao.maquinas.*;
import br.producao.simulacao.TarefaProducao;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE PRODUÇÃO PARALELA (VSSS) ===\n");

        // --- PARTE 1: SIMULAÇÃO COM THREADS ---
        Map<String, Integer> config = LeitorConfiguracao.ler("configuracao.txt");

        MaquinaCorte mCorte = new MaquinaCorte("Laser-01", config.getOrDefault("CORTE", 2));
        MaquinaMontagem mMontagem = new MaquinaMontagem("Montadora-X", config.getOrDefault("MONTAGEM", 3));
        MaquinaInspecao mInspecao = new MaquinaInspecao("Scanner-Q", config.getOrDefault("INSPECAO", 1));

        Maquina[] etapas = { mCorte, mMontagem, mInspecao };

        Thread t1 = new Thread(new TarefaProducao("Placa Base VSSS v1.0", etapas));
        Thread t2 = new Thread(new TarefaProducao("Motor DC 12V", etapas));

        t1.start();
        t2.start();

        // Espera as threads terminarem antes de abrir o menu do Banco de Dados
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // --- PARTE 2: MENU DO BANCO DE DADOS ---
        executarMenuBancoDeDados();
    }

    public static void executarMenuBancoDeDados() {
        Scanner scanner = new Scanner(System.in);
        ProdutoDAO produtoDAO = new ProdutoDAO();
        RelatoriosDAO relatoriosDAO = new RelatoriosDAO();
        int opcao = 0;

        while (opcao != 9) {
            System.out.println("\n=== MENU DO BANCO DE DADOS ===");
            System.out.println("1. Listar todos os Produtos");
            System.out.println("2. Buscar Produto por Nome");
            System.out.println("3. Atualizar Custo de um Produto");
            System.out.println("4. Deletar Produto");
            System.out.println("5. Ver Relatório de Inspeção (JOIN 1)");
            System.out.println("6. Ver Relatório de Manutenção (JOIN 2)");
            System.out.println("7. Ver Operadores por Máquina (JOIN 3)");
            System.out.println("9. Sair");
            System.out.print("Escolha uma opcao: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    produtoDAO.listarTodos();
                    break;
                case 2:
                    System.out.print("Digite parte do nome: ");
                    String nome = scanner.nextLine();
                    produtoDAO.buscarPorNome(nome);
                    break;
                case 3:
                    System.out.print("ID do Produto: ");
                    int id = scanner.nextInt();
                    System.out.print("Novo Custo: ");
                    double custo = scanner.nextDouble();
                    produtoDAO.atualizarCusto(id, custo);
                    break;
                case 4:
                    System.out.print("ID do Produto a deletar: ");
                    int idDeletar = scanner.nextInt();
                    produtoDAO.deletar(idDeletar);
                    break;
                case 5:
                    relatoriosDAO.relatorioProdutosInspecionados();
                    break;
                case 6:
                    relatoriosDAO.relatorioManutencaoMaquinas();
                    break;
                case 7:
                    relatoriosDAO.relatorioOperadoresMaquinas();
                    break;
                case 9:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }
}