package br.producao.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/SistemaProducao";
    private static final String USER = "admin_producao"; // Usuário criado na Opção 2
    private static final String PASS = "123456";         // Senha definida na Opção 2

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }
}