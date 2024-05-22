package tool.rental.domain.use_cases;

import tool.rental.domain.infra.db.DataBase;
import tool.rental.utils.ToastError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IsRentalOpenUseCase {

    public boolean isToolRented(String toolId ) throws ToastError {
        try (DataBase db = new DataBase()) {
            String query = """
                        SELECT
                            tool_id
                         FROM 
                            RENTAL
                         WHERE
                            tool_id = ? AND devolution_timestamp is null
                         ORDER BY 
                                tool_id DESC 
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
