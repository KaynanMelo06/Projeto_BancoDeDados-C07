package br.producao.dao;

import br.producao.conexao.ConexaoDatabase;
import java.sql.*;

public class RelatoriosDAO {

    public void relatorioProdutosInspecionados() {
        String sql = "SELECT p.nome_modelo, r.aprovado, r.observacoes_tecnicas " +
                "FROM Produto p INNER JOIN Relatorio_Inspecao r ON p.id_produto = r.id_produto";

        System.out.println("\n--- RELATÓRIO DE INSPEÇÃO DE PRODUTOS ---");
        executarSelectJoin(sql, "nome_modelo", "aprovado", "observacoes_tecnicas");
    }

    public void relatorioManutencaoMaquinas() {
        String sql = "SELECT m.codigo_identificacao, man.descricao_erro, man.custo_reparo " +
                "FROM Maquina m INNER JOIN Manutencao man ON m.id_maquina = man.id_maquina";

        System.out.println("\n--- RELATÓRIO DE MANUTENÇÃO DE MÁQUINAS ---");
        executarSelectJoin(sql, "codigo_identificacao", "descricao_erro", "custo_reparo");
    }

    public void relatorioOperadoresMaquinas() {
        String sql = "SELECT o.nome_completo, m.codigo_identificacao, m.tipo_etapa " +
                "FROM Operador o " +
                "INNER JOIN Operador_has_Maquina om ON o.id_operador = om.Operador_id_operador " +
                "INNER JOIN Maquina m ON om.Maquina_id_maquina = m.id_maquina " + // <-- ESPAÇO AQUI NO FINAL
                "ORDER BY o.nome_completo ASC";

        System.out.println("\n--- RELATÓRIO DE OPERADORES POR MÁQUINA ---");
        executarSelectJoin(sql, "nome_completo", "codigo_identificacao", "tipo_etapa");
    }

    private void executarSelectJoin(String sql, String col1, String col2, String col3) {
        try (Connection conn = ConexaoDatabase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println(rs.getString(col1) + " | " + rs.getString(col2) + " | " + rs.getString(col3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}