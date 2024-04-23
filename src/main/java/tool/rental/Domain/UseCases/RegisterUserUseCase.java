package tool.rental.Domain.UseCases;

import tool.rental.Domain.Entities.User;
import tool.rental.Domain.Repositories.UserRepository;
import tool.rental.Utils.ToastError;

public class RegisterUserUseCase {
    private final UserRepository userRepository = new UserRepository();

    public User execute(String username, String password) throws ToastError {
        if (username == null || username.isEmpty()) {
            throw new ToastError("Usuário não pode ser nulo.", "Campo não pode ser nulo");
        }

        if (password == null || password.isEmpty()) {
            throw new ToastError("Senha não pode ser nula.", "Campo não pode ser nulo");
        }

        boolean exists = this.userRepository.existsByUsername(username);
        if (exists) {
            throw new ToastError("Usuário já existe com esse username.", "Usuário já cadastrado.");
        }

        return this.userRepository.createUser(username, password);
    }
}
