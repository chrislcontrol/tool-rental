package tool.rental.domain.repositories;

import tool.rental.app.Settings;
import tool.rental.domain.DAO.CountIdAndSumCostDAO;
import tool.rental.domain.entities.Friend;
import tool.rental.domain.entities.Rental;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.entities.User;
import tool.rental.domain.infra.db.DataBase;
import tool.rental.utils.ToastError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ToolRepository {

    private void setCurrentRentalToTool(Tool tool, ResultSet result) throws SQLException {
        String rentalId = result.getString("r__id");
        if (rentalId == null) {
            return;
        }

        Friend friend = new Friend(
                result.getString("f__id"),
                result.getString("f__name"),
                result.getString("f__phone"),
                result.getString("f__social_security"),
                Settings.getUser()
        );

        Rental currentRental = new Rental(
                result.getString("r__id"),
                result.getInt("r__rental_timestamp"),
                result.getInt("r__devolution_timestamp"),
                friend,
                tool
        );
        tool.setCurrentRental(currentRental);

    }

    public ArrayList<Tool> listAll() throws ToastError {
        try (DataBase db = new DataBase()) {
            PreparedStatement stm = db.connection.prepareStatement(
                    """
                            SELECT
                            	t.id as t__id,
                            	t.brand as t__brand,
                            	t.cost as t__cost,
                            	r.id as r__id,
                            	r.rental_timestamp as r__rental_timestamp,
                            	r.devolution_timestamp as r__devolution_timestamp,
                            	f.id as f__id,
                            	f.name as f__name,
                            	f.phone as f__phone,
                            	f.social_security as f__social_security
                            FROM
                            	TOOL t
                            LEFT JOIN RENTAL r
                            	on	r.tool_id = t.id
                            	and r.devolution_timestamp is null
                            	
                            LEFT JOIN FRIEND f
                            	on f.id = r.friend_id
                            WHERE
                            	t.user_id = ?
                            """
            );
            User user = Settings.getUser();

            stm.setString(1, user.getId());

            ResultSet result = db.executeQuery(stm);

            ArrayList<Tool> tools = new ArrayList<Tool>();

            while (result.next()) {
                Tool tool = new Tool(
                        result.getString("t__id"),
                        result.getString("t__brand"),
                        result.getDouble("t__cost"),
                        user
                );

                this.setCurrentRentalToTool(tool, result);
                tools.add(tool);
            }

            tools.trimToSize();

            return tools;

        } catch (SQLException e) {
            throw new ToastError("Falha ao listar as ferramentas. " + e, "Erro de banco de dados.");
        }
    }

    public CountIdAndSumCostDAO countAndSumCostByUser() throws ToastError {
        try (DataBase dataBase = new DataBase()) {
            String query = "SELECT COUNT(id) as total_count, SUM(cost) as total_cost from TOOL WHERE user_id = ?";
            PreparedStatement stm = dataBase.connection.prepareStatement(query);
            stm.setString(1, Settings.getUser().getId());

            ResultSet result = dataBase.executeQuery(stm);

            if (!result.next()) {
                return new CountIdAndSumCostDAO(0, 0.0);
            }

            return new CountIdAndSumCostDAO(
                    result.getInt("total_count"),
                    result.getDouble("total_cost")
            );

        } catch (SQLException e) {
            throw new ToastError(e.toString(), "Erro de banco de dados.");
        }
    }

    public Tool getById(String toolId) throws ToastError {
        try (DataBase db = new DataBase()) {
            String query = """
                        SELECT
                            t.id,
                            t.brand,
                            t.cost,
                            u.id as u__id,
                            u.username as u__username,
                            u.has_mock as u__has_mock,
                            r.id as r__id,
                            r.rental_timestamp as r__rental_timestamp,
                            r.devolution_timestamp as r__devolution_timestamp,
                            f.id as f__id,
                            f.name as f__name,
                            f.phone as f__phone,
                            f.social_security as f__social_security
                            
                        FROM TOOL t
                        LEFT JOIN USER u on t.user_id = u.id
                        LEFT JOIN RENTAL r on r.tool_id = t.id and r.devolution_timestamp is null
                        LEFT JOIN FRIEND f on f.id = r.friend_id
                        WHERE t.id = ?
                    """;

            PreparedStatement stm = db.connection.prepareStatement(query);
            stm.setString(1, toolId);

            ResultSet result = db.executeQuery(stm);
            if (!result.next()) {
                return null;
            }

            User user = new User(
                    result.getString("u__id"),
                    result.getString("u__username"),
                    false,
                    result.getBoolean("u__has_mock")
            );

            Tool tool = new Tool(
                    result.getString("id"),
                    result.getString("brand"),
                    result.getDouble("cost"),
                    user
            );

            this.setCurrentRentalToTool(tool, result);

            return tool;

        } catch (SQLException e) {
            throw new ToastError(e.toString(), "Erro de banco de dados.");
        }
    }
}