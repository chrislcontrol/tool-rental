package tool.rental.domain.use_cases;

import tool.rental.domain.repositories.UserRepository;
import tool.rental.utils.ToastError;

/**
 * This class represents a use case for registering a new user in the system.
 */
public class RegisterUserUseCase {
    private final UserRepository userRepository = new UserRepository(); // Repository for users

    /**
     * Executes the use case to register a new user in the system.
     *
     * @param username       The username of the new user.
     * @param password       The password of the new user.
     * @param confirmPassword The confirmation password of the new user.
     * @throws ToastError if an error occurs during the execution or if input validation fails.
     */
    public void execute(String username, String password, String confirmPassword) throws ToastError {
        // Validate inputs
        if (username == null || username.isEmpty()) {
            throw new ToastError("Usuário não pode ser nulo.", "Campo não pode ser nulo");
        }

        if (password == null || password.isEmpty()) {
            throw new ToastError("Senha não pode ser nula.", "Campo não pode ser nulo");
        }

        if (confirmPassword == null || confirmPassword.isEmpty()) {
            throw new ToastError("Confirmar senha não pode ser nula.", "Campo não pode ser nulo");
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            throw new ToastError("As senhas não são iguais", "Senhas diferentes");
        }

        // Check if user with the same username already exists
        boolean exists = this.userRepository.existsByUsername(username);
        if (exists) {
            throw new ToastError("Usuário já existe com esse username.", "Usuário já cadastrado.");
        }

        // Create the user in the repository
        this.userRepository.createUser(username, password);
    }
}

