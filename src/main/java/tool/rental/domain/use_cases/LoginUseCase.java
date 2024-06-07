package tool.rental.domain.use_cases;

import tool.rental.domain.entities.User;
import tool.rental.domain.repositories.CacheRepository;
import tool.rental.domain.repositories.UserRepository;
import tool.rental.utils.ToastError;

/**
 * This class represents a use case for user login functionality.
 */
public class LoginUseCase {
    private final UserRepository userRepository = new UserRepository(); // Repository for users
    private final CacheRepository cacheRepository = new CacheRepository(); // Repository for caching

    /**
     * Executes the use case to authenticate a user.
     *
     * @param username   The username of the user.
     * @param password   The password of the user.
     * @param rememberMe Flag indicating whether to remember the user's session.
     * @return The authenticated User object.
     * @throws ToastError if the provided username or password is incorrect or if input validation fails.
     */
    public User execute(String username, String password, boolean rememberMe) throws ToastError {
        this.validateInputs(username, password); // Validate the provided username and password

        // Find the user by the provided username and password
        User user = this.userRepository.findByUsernameAndPassword(username, password);
        // If user is not found, throw an error
        if (user == null) {
            throw new ToastError("Usuário ou senha incorretos.", "Falha ao logar");
        }

        // If rememberMe is true, cache the user's session
        if (rememberMe) {
            this.cacheRepository.setUserAsCached(user);
        }

        return user; // Return the authenticated User object
    }

    /**
     * Validates the provided username and password.
     *
     * @param username The username to validate.
     * @param password The password to validate.
     * @throws ToastError if the username or password is null or empty.
     */
    private void validateInputs(String username, String password) throws ToastError {
        if (username == null || username.isEmpty()) {
            throw new ToastError("Usuário não pode ser nulo.", "Campo não pode ser nulo");
        }

        if (password == null || password.isEmpty()) {
            throw new ToastError("Senha não pode ser nula.", "Campo não pode ser nulo");
        }
    }
}


