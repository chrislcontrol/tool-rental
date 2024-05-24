package tool.rental.domain.repositories;

import tool.rental.app.Settings;
import tool.rental.domain.dao.FriendRentalSummary;
import tool.rental.domain.entities.Friend;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.entities.User;
import tool.rental.domain.infra.db.DataBase;
import tool.rental.utils.ToastError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        try (DataBase db = new DataBase()) {
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
            ArrayList<Friend> friends = new ArrayList<>();

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
        } catch (SQLException e) {
            throw new ToastError("Erro ao listar os amigos. " + e, "Erro de banco de dados.");
        }
    }

    public List<FriendRentalSummary> findRentalSummary() throws ToastError {
        try (DataBase db = new DataBase()) {
            String query = """
                    SELECT
                    	f.name,
                    	f.social_security,
                    	count(r.id) as total_rental,
                    	SUM(CASE WHEN r.devolution_timestamp IS NULL THEN 1 ELSE 0 END) as current_borrowed
                                        
                    FROM
                    	FRIEND f
                    	
                    LEFT JOIN RENTAL r
                    	on r.friend_id = f.id
                    	
                    WHERE f.USER_ID = ?
                    group by f.name, f.social_security
                                        
                    order by total_rental
                    """;

            PreparedStatement stm = db.connection.prepareStatement(query);
            stm.setString(1, Settings.getUser().getId());
            ResultSet result = db.executeQuery(stm);

            ArrayList<FriendRentalSummary> results = new ArrayList<>();

            while (result.next()) {
                FriendRentalSummary summary = new FriendRentalSummary(
                        result.getString("name"),
                        result.getString("social_security"),
                        result.getInt("total_rental"),
                        result.getInt("current_borrowed")
                );
                results.add(summary);
            }

            results.trimToSize();

            return results;

        } catch (SQLException e) {
            throw new ToastError(e.toString(), "Erro de banco de dados");
        }
    }

    public Friend updateFriend(String id, String name, int phone, int social_security) throws ToastError {
        try (DataBase db = new DataBase()) {


            PreparedStatement stm = db.connection.prepareStatement("INSERT INTO TOOL VALUES(?, ?, ?, ?)");
            stm.setString(1, id);
            stm.setString(2, name);
            stm.setInt(3, phone);
            stm.setInt(4, social_security);

            db.executeUpdate(stm);
            return new Tool(id, name, phone, social_security());

        }
    }
}