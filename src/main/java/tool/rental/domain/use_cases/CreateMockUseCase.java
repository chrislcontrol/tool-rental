package tool.rental.domain.use_cases;

import tool.rental.domain.entities.User;
import tool.rental.domain.infra.db.DataBase;
import tool.rental.domain.repositories.UserRepository;
import tool.rental.utils.ToastError;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class CreateMockUseCase {
    private final UserRepository userRepository = new UserRepository();

    public void execute(User user) throws ToastError {
        String[] queries = this.createQueries(user);
        try (DataBase db = new DataBase()) {

            for (String query : queries) {
                PreparedStatement stm = db.connection.prepareStatement(query);
                db.executeUpdate(stm);
            }

            this.userRepository.setMock(user, true);

        } catch (SQLException e) {
            throw new ToastError("Erro ao criar o mock", "Erro ao criar mock");
        }
    }

    private String[] createQueries(User user) {
        String[][] ids = new String[3][4];
        String userId = user.getId();

        for (String[] idArray : ids) {
            for (int i = 0; i < idArray.length; i++) {
                idArray[i] = UUID.randomUUID().toString();
            }
        }

        return new String[]{
                String.format(
                        """
                                -- Inserindo dados na tabela FRIEND
                                INSERT INTO FRIEND (id, name, phone, social_security, user_id) VALUES
                                  ('%s', 'João Silva', '1198765432', '12345678901', '%s'),
                                  ('%s', 'Maria Oliveira', '1199876543', '98765432102', '%s'),
                                  ('%s', 'Pedro Sousa', '1198765434', '11122233303', '%s'),
                                  ('%s', 'Ana Paula', '1199876545', '44455566604', '%s');
                                """,
                        ids[0][0], userId,
                        ids[0][1], userId,
                        ids[0][2], userId,
                        ids[0][3], userId
                ),
                String.format(
                        """
                                                
                                -- Inserindo dados na tabela TOOL
                                INSERT INTO TOOL (id, brand, name, cost, user_id) VALUES
                                  ('%s', 'DeWalt', 'Martelo', 100.0, '%s'),
                                  ('%s', 'Bosch', 'Furadeira', 50.0, '%s'),
                                  ('%s', 'Makita', 'Chave de boca', 200.0, '%s'),
                                  ('%s', 'Hitachi', 'Chave de fenda', 150.0, '%s');
                                 """,

                        ids[1][0], userId,
                        ids[1][1], userId,
                        ids[1][2], userId,
                        ids[1][3], userId),

                String.format(
                        """
                                -- Inserindo dados na tabela RENTAL
                                INSERT INTO RENTAL (id, rental_timestamp, devolution_timestamp, friend_id, tool_id) VALUES
                                  ('%s', 1717720124627, 1717720125627, '%s', '%s'),
                                  ('%s', 1717720124627, 1717720125627, '%s', '%s'),
                                  ('%s', 1717720124627, 1717720125627, '%s', '%s'),
                                  ('%s', 1717720124627, 1717720125627, '%s', '%s');
                                """,
                        ids[2][0], ids[0][0], ids[1][0],
                        ids[2][1], ids[0][1], ids[1][1],
                        ids[2][2], ids[0][2], ids[1][2],
                        ids[2][3], ids[0][3], ids[1][3]
                )
        };
    }
}
