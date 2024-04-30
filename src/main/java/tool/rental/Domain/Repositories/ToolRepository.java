package tool.rental.Domain.Repositories;

import tool.rental.App.Settings;
import tool.rental.Domain.Entities.Tool;
import tool.rental.Domain.Infra.DB.DataBase;
import tool.rental.Utils.ToastError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ToolRepository {
    public ArrayList<Tool> listAll() throws ToastError {
        try (DataBase db = new DataBase()) {
            PreparedStatement stm = db.connection.prepareStatement(
                    "SELECT * FROM TOOL LEFT JOIN RENTAL r. on WHERE user_id = ? "
            );
            stm.setString(1, Settings.getUser().getId());

            ResultSet result = db.executeQuery(stm);

            ArrayList<Tool> tools = new ArrayList<Tool>();

            while (result.next()) {
                Tool tool = new Tool(
                        result.getString("id"),
                        result.getString("brand"),
                        result.getDouble("cost"),
                        result.getString("user_id")
                );
                tools.add(tool);
            }

            tools.trimToSize();

            return tools;

        } catch (SQLException e) {
            throw new ToastError("Falha ao listar as ferramentas.", "Erro de banco de dados.");
        }
    }
}
