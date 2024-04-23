package tool.rental.Domain.UseCases;

import tool.rental.Domain.Entities.User;
import tool.rental.Domain.Repositories.CacheRepository;
import tool.rental.Domain.Repositories.UserRepository;
import tool.rental.Utils.ToastError;

public class LoginUseCase {
    private final UserRepository userRepository = new UserRepository();
    private final CacheRepository cacheRepository = new CacheRepository();

    public User execute(String username, String password, boolean rememberMe) throws ToastError {
        this.validateInputs(username, password);

        User user = this.userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new ToastError("Usuário ou senha incorretos.", "Falha ao logar");
        }

        if (rememberMe) {
            this.cacheRepository.setUserAsCached(user);
        }

        return user;
    }

    private void validateInputs(String username, String password) throws ToastError {
        if (username == null || username.isEmpty()) {
            throw new ToastError("Usuário não pode ser nulo.", "Campo não pode ser nulo");
        }

        if (password == null || password.isEmpty()) {
            throw new ToastError("Senha não pode ser nula.", "Campo não pode ser nulo");
        }
    }
}

