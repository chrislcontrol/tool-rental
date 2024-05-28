package tool.rental.domain.repositories;

import tool.rental.app.Settings;
import tool.rental.domain.entities.Friend;
import tool.rental.domain.entities.Rental;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.infra.db.DataBase;
import tool.rental.utils.ToastError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

}
