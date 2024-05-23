package tool.rental.domain.use_cases;

import tool.rental.domain.entities.Friend;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.infra.db.DataBase;
import tool.rental.domain.repositories.RentalRepository;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RentalToolUseCase {
    private final RentalRepository rentalRepository = new RentalRepository();

    public void execute(Friend friend, Tool tool) throws ToastError {
        long currentStamp = System.currentTimeMillis();
        if (isAnyToolRentedByFriend(friend)){
            JOptionPane.showMessageDialog(
                    null,
                    "O(A) amigo(a) selecionado(a) já possui ferramenta(s) emprestada(s)."
            );
        }
        this.rentalRepository.updateRentalTimestamp(currentStamp, friend, tool);
    }

    private boolean isAnyToolRentedByFriend(Friend friend) throws ToastError {
        try (DataBase db = new DataBase()) {
            String query = """
                        SELECT 
                            friend_id
                        FROM
                            RENTAL
                        WHERE
                            friend_id = ? AND devolution_timestamp is null
                    """;
            PreparedStatement stm = db.connection.prepareStatement(query);
            stm.setString(1, friend.getId());
            ResultSet result = db.executeQuery(stm);

            return result.next();

        } catch (SQLException e) {
            throw  new ToastError(e.getMessage(), "Erro de banco de dados");
        }
    }
}
