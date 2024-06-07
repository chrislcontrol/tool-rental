package tool.rental.domain.repositories;


import tool.rental.domain.entities.User;
import tool.rental.domain.infra.db.DataBase;
import tool.rental.utils.ToastError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * A user repository class that provides methods to manage users.
 */
public class UserRepository {

    /**
     * Finds a user by username and password.
     *
     * @param username the username of the user
     * @param encodedPassword the encoded password of the user
     * @return the user, or null if not found
     * @throws ToastError if a database error occurs
     */
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

    /**
     * Creates a new user.
     *
     * @param username the username of the user
     * @param encodedPassword the encoded password of the user
     * @return the created user
     * @throws ToastError if a database error occurs
     */
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

    /**
     * Checks if a user exists by username.
     *
     * @param username the username of the user
     * @return true if the user exists, false otherwise
     * @throws ToastError if a database error occurs
     */
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

    /**
     * Sets the mock flag for a user.
     *
     * @param user the user to update
     * @param hasMock the new value of the mock flag
     * @throws ToastError if a database error occurs
     */
    public void setMock(User user, boolean hasMock) throws ToastError{
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
