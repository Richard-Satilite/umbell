package com.umbell.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utilitário para gerenciamento da conexão com o banco de dados SQLite.
 * Define o caminho do banco e provê método para estabelecer conexão.
 * 
 * O banco utilizado está localizado em "src/main/resources/database/umbell.db".
 * 
 * @author Richard Satilite
 */
public class DatabaseUtil {

    /**
     * Caminho relativo do arquivo do banco de dados SQLite.
     */
    private static final String DB_PATH = "src/main/resources/database/umbell.db";

    /**
     * URL de conexão JDBC para o banco SQLite, construída a partir do caminho.
     */
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;

    /**
     * Estabelece e retorna uma conexão ativa com o banco de dados SQLite.
     * 
     * @return uma {@link Connection} ativa com o banco de dados
     * @throws SQLException caso ocorra algum erro ao conectar ao banco
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}