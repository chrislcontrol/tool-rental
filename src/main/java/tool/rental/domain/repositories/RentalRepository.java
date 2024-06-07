package tool.rental.domain.repositories;

import tool.rental.app.Settings;
import tool.rental.domain.entities.Friend;
import tool.rental.domain.entities.Rental;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.entities.User;
import tool.rental.domain.infra.db.DataBase;
import tool.rental.utils.Toast;
import tool.rental.utils.ToastError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RentalRepository {
    public int countBorrowedByUser() throws ToastError {
        try (DataBase dataBase = new DataBase()) {
            String query = """
                            SELECT
                            	COUNT(r.id) as total_count
                            FROM RENTAL r
                            LEFT JOIN TOOL t on t.id = r.tool_id
                                                
                            WHERE t.user_id = ? AND r.devolution_timestamp is null
                    """;
            PreparedStatement stm = dataBase.connection.prepareStatement(query);
            stm.setString(1, Settings.getUser().getId());

            ResultSet result = dataBase.executeQuery(stm);

            if (!result.next()) {
                return 0;
            }

            return result.getInt("total_count");
        } catch (SQLException e) {
            throw new ToastError(e.getMessage(), "Erro de banco de dados.");
        }
    }

    public void updateDevolutionTimestamp(Rental rental, long devolutionTimestamp) throws ToastError {
        try (DataBase db = new DataBase()) {
            String query = """
                        UPDATE RENTAL SET devolution_timestamp = ? WHERE id = ?
                    """;
            PreparedStatement stm = db.connection.prepareStatement(query);
            stm.setLong(1, devolutionTimestamp);
            stm.setString(2, rental.getId());

            db.executeUpdate(stm);

        } catch (SQLException e) {
            throw new ToastError(e.toString(), "Erro de banco de dados");
        }
    }

    public void create(long rentalTimestamp, Friend friend, Tool tool) throws ToastError {
        try (DataBase db = new DataBase()) {
            String id = UUID.randomUUID().toString();

            PreparedStatement stm = db.connection.prepareStatement("INSERT INTO RENTAL VALUES(?, ?, null, ?, ?)");
            stm.setString(1, id);
            stm.setLong(2, rentalTimestamp);
            stm.setString(3, friend.getId());
            stm.setString(4, tool.getId());

            db.executeUpdate(stm);

        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
            throw new ToastError(
                    "Não foi possível registrar o empréstimo da ferramenta devido a um erro de banco de dados.",
                    "Erro de banco de dados");
        }
    }

    public List<Rental> listAll() throws ToastError {
        try (DataBase db = new DataBase()) {
            PreparedStatement stm = db.connection.prepareStatement(
                    """
                            SELECT
                                r.id as r__id,
                                r.rental_timestamp as r__rental_timestamp,
                                r.devolution_timestamp as r__devolution_timestamp,
                                f.id as f__id,
                                f.name as f__name,
                                f.phone as f__phone,
                                f.social_security as f__social_security,
                                t.id as t__id,
                                t.brand as t__brand,
                                t.name as t__name,
                                t.cost as t__cost
                                
                              FROM RENTAL r
                              
                              LEFT JOIN FRIEND f on
                                f.id = r.friend_id
                                
                              LEFT JOIN TOOL t on
                                t.id = r.tool_id
                              
                              WHERE
                                t.user_id = ?
                                
                              ORDER BY
                                r__rental_timestamp DESC
                                  
                            """
            );

            User user = Settings.getUser();
            stm.setString(1, user.getId());
            ResultSet result = db.executeQuery(stm);
            ArrayList<Rental> rentals = new ArrayList<>();

            while (result.next()) {
                Friend friend = new Friend(
                        result.getString("f__id"),
                        result.getString("f__name"),
                        result.getString("f__phone"),
                        result.getString("f__social_security"),
                        user
                );
                Tool tool = new Tool(
                        result.getString("t__id"),
                        result.getString("t__brand"),
                        result.getString("t__name"),
                        result.getDouble("t__cost"),
                        user
                );

                Rental rental = new Rental(
                        result.getString("r__id"),
                        result.getLong("r__rental_timestamp"),
                        result.getLong("r__devolution_timestamp"),
                        friend,
                        tool

                );
                rentals.add(rental);
            }

            rentals.trimToSize();

            return rentals;

        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
            throw new ToastError(
                    "Não foi possível registrar o empréstimo da ferramenta devido a um erro de banco de dados.",
                    "Erro de banco de dados");
        }
    }

}
