package tool.rental.domain.repositories;


import tool.rental.domain.entities.User;
import tool.rental.domain.infra.db.DataBase;
import tool.rental.utils.ToastError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRepository {
    public User findByUsernameAndPassword(String username, String encodedPassword) throws ToastError {
        try (DataBase db = new DataBase()) {
            PreparedStatement stm = db.connection.prepareStatement(
                    "SELECT * FROM USER WHERE username = ? AND password = ?"
            );
            stm.setString(1, username);
            stm.setString(2, encodedPassword);

            ResultSet result = db.executeQuery(stm);
            if (!result.next()) {
                return null;
            }

            return new User(
                    result.getString("id"),
                    result.getString("username"),
                    true,
                    result.getBoolean("has_mock")
            );

        } catch (SQLException e) {
            throw new ToastError("Erro ao consultar no banco de dados", "Erro de banco de dados.");
        }
    }

    public User createUser(String username, String encodedPassword) throws ToastError {
        try (DataBase db = new DataBase()) {
            String id = UUID.randomUUID().toString();

            PreparedStatement stm = db.connection.prepareStatement("INSERT INTO USER VALUES(?, ?, ?, 0)");
            stm.setString(1, id);
            stm.setString(2, username);
            stm.setString(3, encodedPassword);

            db.executeUpdate(stm);

            return new User(id, username);

        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
            throw new ToastError(
                    "Não foi possível criar o usuário devido a um erro com o banco de dados.",
                    "Erro de banco de dados."
            );
        }
    }

    public boolean existsByUsername(String username) throws ToastError {
        try (DataBase db = new DataBase()) {
            PreparedStatement stm = db.connection.prepareStatement("SELECT id FROM USER WHERE username = ?");
            stm.setString(1, username);
            ResultSet result = db.executeQuery(stm);
            return result.next();

        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
            throw new ToastError(
                    "Não foi possível verificar se o usuário existe devido a um erro com o banco de dados.",
                    "Erro de banco de dados."
            );
        }
    }

    public void setMock(User user, boolean hasMock) throws ToastError {
        try (DataBase db = new DataBase()) {
            PreparedStatement stm = db.connection.prepareStatement("UPDATE USER SET has_mock = ? WHERE id = ?");
            stm.setBoolean(1, hasMock);
            stm.setString(2, user.getId());

            db.executeUpdate(stm);

            user.setHasMock(true);

        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
            throw new ToastError(
                    "Não foi possível verificar se o usuário existe devido a um erro com o banco de dados.",
                    "Erro de banco de dados."
            );
        }
    }
}
