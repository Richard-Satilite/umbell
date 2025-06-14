package com.umbell.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilitário para geração e verificação de hashes MD5 de senhas.
 * Fornece métodos para gerar o hash MD5 de uma senha em texto plano
 * e para verificar se uma senha corresponde a um hash armazenado.
 * 
 * @author Richard Satilite
 */
public class HashUtil {
    
    /**
     * Gera um hash MD5 da senha fornecida.
     * 
     * @param password A senha em texto plano que será hasheada.
     * @return Uma string hexadecimal representando o hash MD5 da senha.
     * @throws RuntimeException caso o algoritmo MD5 não seja suportado pelo ambiente.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(password.getBytes());
            
            // Converter bytes para hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash da senha", e);
        }
    }

    /**
     * Verifica se uma senha em texto plano corresponde ao hash MD5 armazenado.
     * 
     * @param password A senha em texto plano para verificar.
     * @param hashedPassword O hash MD5 armazenado para comparação.
     * @return {@code true} se a senha gerada corresponder ao hash armazenado, {@code false} caso contrário.
     */
    public static boolean checkPassword(String password, String hashedPassword) {
        String hashedInput = hashPassword(password);
        return hashedInput.equals(hashedPassword);
    }
}