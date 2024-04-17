package tool.rental.Domain.Repositories;

import tool.rental.Domain.Entities.Cache;
import tool.rental.Domain.Entities.User;
import tool.rental.Domain.Infra.DB.DataBase;
import tool.rental.Utils.ToastError;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CacheRepository {
    public void setUserAsCached(User user) throws ToastError {
        try (DataBase db = new DataBase()) {
                PreparedStatement stm = db.connection.prepareStatement(
                    "INSERT INTO main.CACHE VALUES (?, ?)"
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
                    new User(result.getString("logged_user_id"), result.getString("U.username"), true)
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
