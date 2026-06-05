package br.producao.dao;

import br.producao.conexao.ConexaoDatabase;
import br.producao.models.ProdutoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // 1. INSERT
    public void inserir(ProdutoModel p) {
        String sql = "INSERT INTO Produto (nome_modelo, custo_producao, data_fabricacao) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoDatabase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNomeModelo());
            stmt.setDouble(2, p.getCustoProducao());
            stmt.setTimestamp(3, Timestamp.valueOf(p.getDataFabricacao()));
            stmt.executeUpdate();
            System.out.println("Produto inserido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. SELECT (Todos)
    public void listarTodos() {
        String sql = "SELECT * FROM Produto";
        try (Connection conn = ConexaoDatabase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_produto") +
                        " | Modelo: " + rs.getString("nome_modelo") +
                        " | Custo: R$" + rs.getDouble("custo_producao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. BUSCA POR ATRIBUTO (Exigência do Requisito)
    public void buscarPorNome(String nomeBusca) {
        String sql = "SELECT * FROM Produto WHERE nome_modelo LIKE ?";
        try (Connection conn = ConexaoDatabase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeBusca + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Encontrado: " + rs.getString("nome_modelo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. UPDATE
    public void atualizarCusto(int id, double novoCusto) {
        String sql = "UPDATE Produto SET custo_producao = ? WHERE id_produto = ?";
        try (Connection conn = ConexaoDatabase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, novoCusto);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Custo atualizado!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. DELETE
    public void deletar(int id) {
        String sql = "DELETE FROM Produto WHERE id_produto = ?";
        try (Connection conn = ConexaoDatabase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Produto deletado!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}