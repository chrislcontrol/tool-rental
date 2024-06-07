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

/**
 * Repository class for handling operations related to friends.
 */
public class FriendRepository {

    /**
     * Counts the number of friends associated with the current user.
     *
     * @return The total count of friends.
     * @throws ToastError If an error occurs during database operation.
     */
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
            throw new ToastError(e.toString(), "Database Error");
        }
    }

    /**
     * Lists all friends associated with the current user.
     *
     * @return The list of friends.
     * @throws ToastError If an error occurs during database operation.
     */
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
            throw new ToastError("Error listing friends. " + e, "Database Error");
        }
    }

    /**
     * Gets a friend by their ID.
     *
     * @param friendId The ID of the friend to retrieve.
     * @return The friend object if found, null otherwise.
     * @throws ToastError If an error occurs during database operation.
     */
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
            throw new ToastError(e.toString(), "Database Error");
        }
    }

    /**
     * Generates a summary of friend rentals.
     *
     * @return The list of friend rental summaries.
     * @throws ToastError If an error occurs during database operation.
     */
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
            throw new ToastError(e.toString(), "Database Error");
        }
    }

    /**
     * Creates a new friend.
     *
     * @param name            The name of the friend.
     * @param phone           The phone number of the friend.
     * @param social_security The social security number of the friend.
     * @return The newly created friend object.
     * @throws ToastError If an error occurs during database operation.
     */
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
                    "Could not create friend due to a database error",
                    "Database Error"
            );
        }
    }

    /**
     * Deletes a friend.
     *
     * @param friendId The ID of the friend to delete.
     * @return Null since the friend is deleted.
     * @throws ToastError If an error occurs during database operation.
     */
    public Friend deleteFriend(String friendId) throws ToastError {
        try (DataBase db = new DataBase()) {
            PreparedStatement stm = db.connection.prepareStatement("DELETE FROM FRIEND WHERE id = ?");
            stm.setString(1, friendId);

            db.executeUpdate(stm);
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
            throw new ToastError(
                    "Could not delete friend due to a database error",
                    "Database Error"
            );
        }
        return null;
    }

    /**
     * Checks if a friend has any tools rented.
     *
     * @param friendId The ID of the friend.
     * @return True if the friend has tools rented, false otherwise.
     * @throws ToastError If an error occurs during database operation.
     */
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
                    "Could not check if friend has tools rented due to a database error",
                    "Database Error"
            );
        }
    }

    /**
     * Checks if a friend exists by name and social security.
     *
     * @param name            The name of the friend.
     * @param social_security The social security number of the friend.
     * @return True if the friend exists, false otherwise.
     * @throws ToastError If an error occurs during database operation.
     */
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
                    "Could not check if friend exists due to a database error",
                    "Database Error"
            );
        }
    }

    /**
     * Updates a friend's information.
     *
     * @param id              The ID of the friend to update.
     * @param name            The new name of the friend.
     * @param phone           The new phone number of the friend.
     * @param social_security The new social security number of the friend.
     * @param user            The user associated with the friend.
     * @return The updated friend object.
     * @throws ToastError If an error occurs during database operation.
     */
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
                    "Could not update friend due to a database error",
                    "Database Error"
            );
        }
    }
}