package tool.rental.Domain.UseCases;

import tool.rental.Domain.Entities.User;
import tool.rental.Domain.Repositories.UserRepository;
import tool.rental.Utils.ToastError;

public class RegisterUserUseCase {
    private final UserRepository userRepository = new UserRepository();

    public void execute(String username, String password, String confirmPassword) throws ToastError {
        if (username == null || username.isEmpty()) {
            throw new ToastError("Usuário não pode ser nulo.", "Campo não pode ser nulo");
        }

        if (password == null || password.isEmpty()) {
            throw new ToastError("Senha não pode ser nula.", "Campo não pode ser nulo");
        }

        if (confirmPassword == null || confirmPassword.isEmpty()) {
            throw new ToastError("Confirmar senha não pode ser nula.", "Campo não pode ser nulo");
        }

        if (!password.equals(confirmPassword)) {
            throw new ToastError("As senhas não são iguais", "Senhas diferentes");
        }

        boolean exists = this.userRepository.existsByUsername(username);
        if (exists) {
            throw new ToastError("Usuário já existe com esse username.", "Usuário já cadastrado.");
        }

        this.userRepository.createUser(username, password);
    }
}
