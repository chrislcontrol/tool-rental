package tool.rental.domain.repositories;

import tool.rental.app.Settings;
import tool.rental.domain.entities.Rental;
import tool.rental.domain.infra.db.DataBase;
import tool.rental.utils.ToastError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public boolean isRentalOpen(String toolId ) throws ToastError{
        try (DataBase db = new DataBase()) {
            String query = """
                        SELECT
                         tool_id
                         FROM RENTAL
                         WHERE tool_id = ? AND devolution_timestamp is null
                         ORDER BY tool_id
                    """;
            PreparedStatement stm = db.connection.prepareStatement(query);
            stm.setString(1, toolId);
            ResultSet result = db.executeQuery(stm);
            return result.next();

        } catch (SQLException e) {
            throw new ToastError(e.toString(), "Erro de banco de dados");
        }
    }

}
