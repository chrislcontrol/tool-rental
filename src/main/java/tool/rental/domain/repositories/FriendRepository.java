package tool.rental.domain.repositories;

import tool.rental.app.Settings;
import tool.rental.domain.entities.Friend;
import tool.rental.domain.entities.User;
import tool.rental.domain.infra.db.DataBase;
import tool.rental.utils.ToastError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendRepository {
    public int countByUser() throws ToastError {
        try (DataBase dataBase = new DataBase()) {
            String query = "SELECT COUNT(id) as total from FRIEND WHERE user_id = ?";
            PreparedStatement stm = dataBase.connection.prepareStatement(query);
            stm.setString(1, Settings.getUser().getId());

            ResultSet result = dataBase.executeQuery(stm);

            if (!result.next()) {
                return 0;
            }

            return result.getInt("total");


        } catch (SQLException e) {
            throw new ToastError(e.toString(), "Erro de banco de dados.");
        }

    }

    public ArrayList<Friend> listAll() throws ToastError {
        try(DataBase db = new DataBase()) {
            String query = """
                    SELECT
                        f.id as f__id,
                        f.name as f__name,
                        f.phone as f__phone,
                        f.social_security as f__social_security
                    FROM 
                        FRIEND f
                    WHERE
                        f.user_id = ?
                    ORDER BY 
                        f.name
                    """;

            PreparedStatement stm = db.connection.prepareStatement(query);
            User user = Settings.getUser();

            stm.setString(1, user.getId());

            ResultSet result = db.executeQuery(stm);

            ArrayList<Friend> friends = new ArrayList<Friend>();

            while (result.next()) {
                Friend friend = new Friend(
                        result.getString("f__id"),
                        result.getString("f__name"),
                        result.getString("f__phone"),
                        result.getString("f__social_security"),
                        user
                );

                friends.add(friend);
            }

            friends.trimToSize();

            return friends;

        } catch (SQLException e){
            throw new ToastError("Erro ao listar os amigos", "Erro de banco de dados.");
        }
    }
}
