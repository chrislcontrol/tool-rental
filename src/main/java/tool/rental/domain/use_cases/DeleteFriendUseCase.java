package tool.rental.domain.use_cases;

import tool.rental.domain.entities.Friend;
import tool.rental.domain.infra.db.DataBase;
import tool.rental.domain.repositories.FriendRepository;
import tool.rental.utils.ToastError;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteFriendUseCase {
    private FriendRepository friendRepository = new FriendRepository();

    public void execute(String friendId) throws ToastError {
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
    }}
