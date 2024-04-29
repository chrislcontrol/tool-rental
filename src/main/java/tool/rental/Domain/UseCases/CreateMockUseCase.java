package tool.rental.Domain.UseCases;

import tool.rental.Domain.Entities.User;
import tool.rental.Domain.Infra.DB.DataBase;
import tool.rental.Domain.Repositories.UserRepository;
import tool.rental.Utils.ToastError;

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
        return new String[]{
                String.format(
                        """
                                -- Inserindo dados na tabela FRIEND
                                INSERT INTO FRIEND (id, name, phone, social_security, user_id) VALUES
                                  ('%s', 'João Silva', '1198765432', '123456789', '%s'),
                                  ('%s', 'Maria Oliveira', '1199876543', '987654321', '%s'),
                                  ('%s', 'Pedro Sousa', '1198765434', '111222333', '%s'),
                                  ('%s', 'Ana Paula', '1199876545', '444555666', '%s');
                                """,
                        UUID.randomUUID(), user.getId(),
                        UUID.randomUUID(), user.getId(),
                        UUID.randomUUID(), user.getId(),
                        UUID.randomUUID(), user.getId()
                ),
                String.format(
                        """
                                                
                                -- Inserindo dados na tabela TOOL
                                INSERT INTO TOOL (id, brand, cost, user_id) VALUES
                                  ('%s', 'DeWalt', 100.0, '%s'),
                                  ('%s', 'Bosch', 50.0, '%s'),
                                  ('%s', 'Makita', 200.0, '%s'),
                                  ('%s', 'Hitachi', 150.0, '%s');
                                 """,
                        UUID.randomUUID(), user.getId(),
                        UUID.randomUUID(), user.getId(),
                        UUID.randomUUID(), user.getId(),
                        UUID.randomUUID(), user.getId()),

                String.format(
                        """
                                -- Inserindo dados na tabela RENTAL
                                INSERT INTO RENTAL (id, rental_timestamp, devolution_timestamp, friend_id) VALUES
                                  ('%s', 1643723400, 1643726800, '34567890-1234-5678-9012-345678909012'),
                                  ('%s', 1643726800, 1643730200, '45678901-2345-6789-0123-456789012345'),
                                  ('%s', 1643730200, 1643733600, '67890123-4567-8901-2345-678901234567'),
                                  ('%s', 1643733600, 1643737000, '89012345-6789-0123-4567-890123456789');
                                """,
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID()
                )
        };
    }
}
