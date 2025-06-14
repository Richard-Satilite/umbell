package com.umbell.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe utilitária responsável por inicializar o banco de dados SQLite.
 * Cria o arquivo do banco caso ele não exista e executa o script de schema para
 * configurar as tabelas e estruturas necessárias.
 * 
 * O caminho do banco é definido em {@code src/main/resources/database/umbell.db}
 * e o script SQL para o schema em {@code src/main/resources/database/umbell_schema.sql}.
 * 
 * @author Richard Satilite
 */
public class DBInitializer {

    /**
     * Caminho relativo para o arquivo do banco de dados SQLite.
     */
    private static final String DB_PATH = "src/main/resources/database/umbell.db";

    /**
     * URL de conexão JDBC para o banco SQLite, construída a partir do caminho.
     */
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;

    /**
     * Caminho relativo para o arquivo SQL que contém o script do schema do banco.
     */
    private static final String SCHEMA_PATH = "src/main/resources/database/umbell_schema.sql";

    /**
     * Inicializa o banco de dados, criando o arquivo se necessário e executando o schema.
     */
    public static void initialize() {
        createDatabaseIfNotExists();
        executeSchema();
    }

    /**
     * Cria o arquivo do banco de dados SQLite se ele ainda não existir.
     * Garante que os diretórios pais estejam criados.
     */
    private static void createDatabaseIfNotExists() {
        File dbFile = new File(DB_PATH);
        if (!dbFile.exists()) {
            try {
                dbFile.getParentFile().mkdirs();
                dbFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Lê o arquivo de schema SQL e executa os comandos no banco de dados para
     * criar as tabelas e estruturas necessárias.
     * Caso algum erro ocorra, imprime o stack trace.
     */
    private static void executeSchema() {
        try {
            String schema = Files.readString(Paths.get(SCHEMA_PATH));
            
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 Statement stmt = conn.createStatement()) {
                
                String[] statements = schema.split(";");
                
                for (String statement : statements) {
                    if (!statement.trim().isEmpty()) {
                        stmt.execute(statement);
                    }
                }
                
                System.out.println("Database schema executed successfully.");
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}