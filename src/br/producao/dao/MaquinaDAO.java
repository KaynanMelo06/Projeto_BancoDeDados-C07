package br.producao.dao;

import br.producao.conexao.ConexaoDatabase;
import br.producao.models.MaquinaModel;

import java.sql.*;

public class MaquinaDAO {

    public void inserir(MaquinaModel m) {
        String sql = "INSERT INTO Maquina (codigo_identificacao, tempo_processo_segundos, tipo_etapa) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoDatabase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, m.getCodigoIdentificacao());
            stmt.setInt(2, m.getTempoProcessoSegundos());
            stmt.setString(3, m.getTipoEtapa());
            stmt.executeUpdate();
            System.out.println("Máquina inserida com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listarTodas() {
        String sql = "SELECT * FROM Maquina";
        try (Connection conn = ConexaoDatabase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_maquina") +
                        " | Código: " + rs.getString("codigo_identificacao") +
                        " | Etapa: " + rs.getString("tipo_etapa"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buscarPorEtapa(String etapaBusca) {
        String sql = "SELECT * FROM Maquina WHERE tipo_etapa LIKE ?";
        try (Connection conn = ConexaoDatabase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + etapaBusca + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Encontrado: " + rs.getString("codigo_identificacao") + " (" + rs.getString("tipo_etapa") + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarTempo(int id, int novoTempo) {
        String sql = "UPDATE Maquina SET tempo_processo_segundos = ? WHERE id_maquina = ?";
        try (Connection conn = ConexaoDatabase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, novoTempo);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Tempo de processo atualizado!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM Maquina WHERE id_maquina = ?";
        try (Connection conn = ConexaoDatabase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Máquina deletada!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}