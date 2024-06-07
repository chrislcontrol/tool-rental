package tool.rental.domain.repositories;

import tool.rental.domain.entities.Cache;
import tool.rental.domain.entities.User;
import tool.rental.domain.infra.db.DataBase;
import tool.rental.utils.ToastError;

import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * A cache repository class that provides methods to manage the cache of logged in users.
 */
public class CacheRepository {

    /**
     * Sets a user as cached in the database.
     *
     * @param user the user to be cached
     * @throws ToastError if a database error occurs
     */
    public void setUserAsCached(User user) throws ToastError {
        try (DataBase db = new DataBase()) {
            PreparedStatement stm = db.connection.prepareStatement(
                    "INSERT INTO main.CACHE VALUES (?,?)"
            );

            stm.setString(1, UUID.randomUUID().toString());
            stm.setString(2, user.getId());

            db.executeUpdate(stm);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível setar o usuário login automático",
                    "Erro de banco de dados.",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    /**
     * Clears the cache of logged in users from the database.
     *
     * @throws ToastError if a database error occurs
     */
    public void clearCache() throws ToastError {
        try (DataBase db = new DataBase()) {
            PreparedStatement stm = db.connection.prepareStatement("DELETE FROM CACHE");
            db.executeUpdate(stm);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new ToastError("Não foi possível limpar o cache de usuário logado",
                    "Problema de banco de dados.");
        }
    }

    /**
     * Retrieves the cached user from the database.
     *
     * @return the cached user, or null if no user is cached
     * @throws ToastError if a database error occurs
     */
    public Cache getCache() throws ToastError {
        try (DataBase db = new DataBase()) {
            PreparedStatement stm = db.connection.prepareStatement(
                    "SELECT *, U.username as \"U.username\" FROM CACHE JOIN USER U on U.id = CACHE.logged_user_id"
            );
            ResultSet result = db.executeQuery(stm);

            if (!result.next()) {
                return null;
            }

            return new Cache(
                    result.getString("id"),
                    new User(
                            result.getString("logged_user_id"),
                            result.getString("U.username"),
                            true,
                            result.getBoolean("has_mock")
                    )
            );


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível recuperar o último usuário logado.",
                    "Erro de banco de dados.",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return null;
        }
    }
}
