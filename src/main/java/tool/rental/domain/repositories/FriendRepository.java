package tool.rental.domain.repositories;

import tool.rental.app.Settings;
import tool.rental.domain.dao.FriendRentalSummary;
import tool.rental.domain.entities.Friend;
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

    public Friend getById(String friendId) throws ToastError {
        try (DataBase db = new DataBase()) {
            String query = """
                        SELECT
                            f.id,
                            f.name,
                            f.phone,
                            f.social_security,
                            f.user_id,
                            t.id as t__id,
                            t.brand as t__brand,
                            t.name as t__name,
                            t.cost as t__cost
                        
                        FROM FRIEND f
                        LEFT JOIN TOOL t on t.id = f.id
                        WHERE f.id = ? AND f.user_id = ?
                    """;

            PreparedStatement stm = db.connection.prepareStatement(query);
            User user = Settings.getUser();
            stm.setString(1, friendId);
            stm.setString(2, user.getId());

            ResultSet result = db.executeQuery(stm);
            if (!result.next()) {
                return null;
            }

            Friend friend = new Friend(
                    result.getString("id"),
                    result.getString("name"),
                    result.getString("phone"),
                    result.getString("social_security"),
                    user
            );

            return friend;

        } catch (SQLException e) {
            throw new ToastError(e.toString(), "Erro de banco de dados.");
        }
    }

    public List<FriendRentalSummary> findRentalSummary() throws ToastError {
        try (DataBase db = new DataBase()) {
            String query = """
                    SELECT
                    	f.name,
                    	f.social_security,
                    	count(r.id) as total_rental,
                    	SUM(CASE WHEN r.id is not null and r.devolution_timestamp IS NULL THEN 1 ELSE 0 END) as current_borrowed
                    FROM
                    	FRIEND f
                    	
                    LEFT JOIN RENTAL r
                    	on r.friend_id = f.id
                    	
                    WHERE f.USER_ID = ?
                                        
                    group by f.name, f.social_security
                    order by total_rental DESC
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

    public Friend createFriend(String name, String phone, String social_security) throws ToastError {
        try (DataBase db = new DataBase()) {
            String id = UUID.randomUUID().toString();


            PreparedStatement stm = db.connection.prepareStatement("INSERT INTO FRIEND VALUES(?, ?, ?, ?, ?)");
            stm.setString(1, id);
            stm.setString(2, name);
            stm.setString(3, phone);
            stm.setString(4, social_security);
            stm.setString(5, Settings.getUser().getId());

            db.executeUpdate(stm);
            return new Friend(id, name, phone, social_security, Settings.getUser());

        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
            throw new ToastError(
                    "Não foi possível cadastrar o amigo(a) devido a um erro com banco de dados",
                    "Erro de banco de dados"
            );
        }
    }

    public Friend deleteFriend(String friendId) throws ToastError {
        try (DataBase db = new DataBase()) {
            PreparedStatement stm = db.connection.prepareStatement("DELETE FROM FRIEND WHERE id = ?");
            stm.setString(1, friendId);

            db.executeUpdate(stm);
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
            throw new ToastError(
                    "Não foi possível deletar o amigo(a) devido a um erro com banco de dados",
                    "Erro de banco de dados"
            );
        }
        return null;
    }

    public boolean friendHasToolRented(String friendId) throws ToastError {
        try (DataBase db = new DataBase()) {
            PreparedStatement stm = db.connection.prepareStatement(
                    "SELECT * FROM RENTAL WHERE friend_id = ? AND devolution_timestamp IS NULL"
            );
            stm.setString(1, friendId);
            ResultSet rs = stm.executeQuery();
            return rs.next();

        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
            throw new ToastError(
                    "Não foi possível verificar se o usuário existe devido a um erro com o banco de dados.",
                    "Erro de banco de dados."
            );
        }
    }

    public boolean existsByNameAndSocial_Security(String name, String social_security) throws ToastError {
        try (DataBase db = new DataBase()) {
            PreparedStatement stm = db.connection.prepareStatement(
                    "SELECT id FROM FRIEND WHERE user_id = ? and name = ? and social_security = ?"
            );
            stm.setString(1, Settings.getUser().getId());
            stm.setString(2, name);
            stm.setString(3, social_security);
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

    public Friend updateFriend(String id, String name, String phone, String social_security, User user) throws ToastError {
        try (DataBase db = new DataBase()) {

            PreparedStatement stm = db.connection.prepareStatement(
                    """
                                        
                                            UPDATE FRIEND 

                            SET name = ?,
                                phone = ?,
                                social_security = ?

                            WHERE id = ?
                            """);
            stm.setString(1, name);
            stm.setString(2, phone);
            stm.setString(3, social_security);
            stm.setString(4, id);


            db.executeUpdate(stm);
            return new Friend(id, name, phone, social_security, user);

        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
            throw new ToastError(
                    "Não foi possível atualizar amigo devido a um erro com banco de dados",
                    "Erro de banco de dados"
            );
        }
    }
}
