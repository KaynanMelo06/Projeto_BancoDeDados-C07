package br.producao.dao;

import br.producao.conexao.ConexaoDatabase;
import br.producao.models.OperadorModel;

import java.sql.*;

public class OperadorDAO {

    public void inserir(OperadorModel o) {
        String sql = "INSERT INTO Operador (nome_completo, salario, turno_noturno) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoDatabase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, o.getNomeCompleto());
            stmt.setDouble(2, o.getSalario());
            stmt.setInt(3, o.isTurnoNoturno() ? 1 : 0);
            stmt.executeUpdate();
            System.out.println("Operador inserido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listarTodos() {
        String sql = "SELECT * FROM Operador";
        try (Connection conn = ConexaoDatabase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_operador") +
                        " | Nome: " + rs.getString("nome_completo") +
                        " | Salário: R$" + rs.getDouble("salario"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buscarPorNome(String nomeBusca) {
        String sql = "SELECT * FROM Operador WHERE nome_completo LIKE ?";
        try (Connection conn = ConexaoDatabase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeBusca + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Encontrado: " + rs.getString("nome_completo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarSalario(int id, double novoSalario) {
        String sql = "UPDATE Operador SET salario = ? WHERE id_operador = ?";
        try (Connection conn = ConexaoDatabase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, novoSalario);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Salário atualizado!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM Operador WHERE id_operador = ?";
        try (Connection conn = ConexaoDatabase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Operador deletado!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}