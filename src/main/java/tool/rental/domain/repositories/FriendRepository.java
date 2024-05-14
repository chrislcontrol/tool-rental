package tool.rental.domain.repositories;

import tool.rental.app.Settings;
import tool.rental.domain.entities.Friend;
import tool.rental.domain.infra.db.DataBase;
import tool.rental.utils.ToastError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

//    public List<Friend> listFriends() throws ToastError{
//        try(DataBase db = new DataBase()){
//            String query = """
//                        SELECT name FROM FRIEND ORDER BY name
//                        """;
//            PreparedStatement stm = db.connection.prepareStatement(query);
//
//            ResultSet result = db.executeQuery(stm);
//
//            List<Friend> friendList = new ArrayList<Friend>();
//
//            while (result.next()) {
//                friendList.add(new Friend());
//            }
//
//            return ;
//
//        } catch(SQLException e){
//            throw new ToastError(e.getMessage(), "Erro de banco de dados.");
//        }
//    }
}
