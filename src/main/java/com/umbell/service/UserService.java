package com.umbell.service;

import com.umbell.models.User;
import com.umbell.models.Account;
import com.umbell.repository.UserRepository;
import com.umbell.repository.UserRepositoryImpl;
import com.umbell.utils.HashUtil;
import java.util.List;
import java.util.regex.Pattern;

public class UserService {
    private final UserRepository userRepository;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MIN_NAME_LENGTH = 3;

    public UserService() {
        this.userRepository = new UserRepositoryImpl();
    }

    /**
     * Registra um novo usuário no sistema
     * @param name Nome do usuário
     * @param email Email do usuário
     * @param password Senha do usuário
     * @return O usuário registrado
     * @throws IllegalArgumentException se os dados forem inválidos
     * @throws RuntimeException se o email já estiver em uso
     */
    public User registerUser(String name, String email, String password) {
        // Validações
        if (name == null || name.trim().length() < MIN_NAME_LENGTH) {
            throw new IllegalArgumentException("Nome deve ter pelo menos " + MIN_NAME_LENGTH + " caracteres");
        }

        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email inválido");
        }

        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Senha deve ter pelo menos " + MIN_PASSWORD_LENGTH + " caracteres");
        }

        // Verifica se o email já está em uso
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já está em uso");
        }

        // Faz o hash da senha antes de criar o usuário
        String hashedPassword = HashUtil.hashPassword(password);

        // Cria e salva o usuário com a senha hasheada
        User user = new User(name.trim(), email.toLowerCase(), hashedPassword);
        return userRepository.save(user);
    }

    /**
     * Adiciona uma nova conta ao usuário
     * @param user O usuário que receberá a conta
     * @param account A conta a ser adicionada
     * @return O usuário atualizado com a nova conta
     */
    public User addAccount(User user, Account account) {
        if (user == null || account == null) {
            throw new IllegalArgumentException("Usuário e conta não podem ser nulos");
        }

        account.setUserEmail(user.getEmail());
        user.getAccounts().add(account);
        return userRepository.update(user);
    }

    /**
     * Remove uma conta do usuário
     * @param user O usuário que terá a conta removida
     * @param account A conta a ser removida
     * @return O usuário atualizado sem a conta
     */
    public User removeAccount(User user, Account account) {
        if (user == null || account == null) {
            throw new IllegalArgumentException("Usuário e conta não podem ser nulos");
        }

        if (!user.getEmail().equals(account.getUserEmail())) {
            throw new IllegalArgumentException("A conta não pertence ao usuário");
        }

        user.getAccounts().remove(account);
        return userRepository.update(user);
    }

    /**
     * Busca todas as contas de um usuário
     * @param user O usuário para buscar as contas
     * @return Lista de contas do usuário
     */
    public List<Account> getUserAccounts(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
        return userRepository.loadUserAccounts(user.getEmail());
    }

    /**
     * Carrega um usuário usando email e senha
     * @param email Email do usuário
     * @param password Senha do usuário
     * @return O usuário autenticado
     * @throws IllegalArgumentException se os dados forem inválidos
     * @throws RuntimeException se o usuário não for encontrado ou a senha estiver incorreta
     */
    public User loadUser(String email, String password) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email inválido");
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Senha não pode estar vazia");
        }

        // Busca o usuário pelo email
        User user = userRepository.findByEmail(email.toLowerCase())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se a senha está correta
        if (!HashUtil.checkPassword(password, user.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        return user;
    }
}