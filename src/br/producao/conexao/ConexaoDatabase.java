package br.producao.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/SistemaProducao";
    private static final String USER = "root"; // Altere para seu usuário
    private static final String PASS = "sua_senha"; // Altere para sua senha

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }
}