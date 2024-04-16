package tool.rental.Domain.Repositories;


import tool.rental.Domain.Entities.User;
import tool.rental.Domain.Infra.DB.DataBase;
import tool.rental.Utils.ToastError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class UserRepository {
    public User findByUsernameAndPassword(String username, String encodedPassword) throws ToastError {
        try (DataBase db = new DataBase()) {
            PreparedStatement stm = db.connection.prepareStatement(
                    "SELECT * FROM USER WHERE username = ? AND password = ?"
            );
            stm.setString(1, username);
            stm.setString(2, encodedPassword);

            ResultSet result = stm.executeQuery();
            if (!result.next()) {
                return null;
            }

            return new User(
                    result.getString("id"),
                    result.getString("username"),
                    result.getString("password"),
                    true
            );

        } catch (SQLException e) {
            throw new ToastError("Erro ao consultar no banco de dados", "Erro de banco de dados.");
        }
    }

    public void setRememberMe(User user, boolean rememberMe) {

    }

    public void createUser(String username, String encodedPassword) throws ToastError {
        try (DataBase db = new DataBase()) {
            String id = UUID.randomUUID().toString();

            PreparedStatement stm = db.connection.prepareStatement("INSERT INTO USER VALUES(?, ?, ?)");
            stm.setString(1, id);
            stm.setString(2, username);
            stm.setString(3, encodedPassword);

            db.executeStatement(stm);

        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
            throw new ToastError(
                    "Não foi possível criar o usuário devido a um erro com o banco de dados.",
                    "Erro de banco de dados."
            );
        }
    }
}
