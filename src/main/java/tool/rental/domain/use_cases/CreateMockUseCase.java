package tool.rental.domain.use_cases;

import tool.rental.domain.entities.User;
import tool.rental.domain.infra.db.DataBase;
import tool.rental.domain.repositories.UserRepository;
import tool.rental.utils.ToastError;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * This class represents a use case for creating mock data in the database.
 * It inserts mock data into the FRIEND, TOOL, and RENTAL tables for a given user.
 */
public class CreateMockUseCase {
    private final UserRepository userRepository = new UserRepository(); // Repository for users

    /**
     * Executes the use case to create mock data for the given user.
     *
     * @param user The user for whom mock data needs to be created.
     * @throws ToastError if an error occurs during the execution.
     */
    public void execute(User user) throws ToastError {
        String[] queries = this.createQueries(user); // Create SQL queries for inserting mock data
        try (DataBase db = new DataBase()) {
            // Execute each SQL query to insert mock data into the database
            for (String query : queries) {
                PreparedStatement stm = db.connection.prepareStatement(query);
                db.executeUpdate(stm);
            }

            // Set the mock flag for the user in the repository
            this.userRepository.setMock(user, true);

        } catch (SQLException e) {
            throw new ToastError("Erro ao criar o mock", "Erro ao criar mock");
        }
    }

    /**
     * Creates SQL queries to insert mock data for the given user into the FRIEND, TOOL, and RENTAL tables.
     *
     * @param user The user for whom mock data needs to be created.
     * @return Array of SQL queries to insert mock data.
     */
    private String[] createQueries(User user) {
        String[][] ids = new String[3][4]; // Array to store generated UUIDs for mock data
        String userId = user.getId(); // User ID

        // Generate UUIDs for mock data
        for (String[] idArray : ids) {
            for (int i = 0; i < idArray.length; i++) {
                idArray[i] = UUID.randomUUID().toString();
            }
        }

        // SQL queries to insert mock data into FRIEND, TOOL, and RENTAL tables
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

