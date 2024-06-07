package tool.rental.domain.infra.db;

import tool.rental.utils.ToastError;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * A database class that provides a connection to a SQLite database and executes queries and updates.
 */
public class DataBase implements AutoCloseable {

    /**
     * The connection to the database.
     */
    public Connection connection;

    /**
     * A ToastError instance that represents a connection error.
     */
    public final ToastError ConnectionError = new ToastError(
            "Falha ao conectar ao banco de dados.",
            "Erro de banco de dados"
    );

    /**
     * An enumeration that represents the type of database operation.
     */
    private enum METHOD {
        /**
         * Represents a query operation.
         */
        UPDATE,
        /**
         * Represents an update operation.
         */
        QUERY
    }

    /**
     * Creates a new database instance and establishes a connection to the database.
     *
     * @throws ToastError if a connection error occurs
     */
    public DataBase() throws ToastError {
        this.connection = this.getConnection();
        System.out.println("DB Connection open.");
    }

    /**
     * Executes a query on the database using the given prepared statement.
     *
     * @param statement the prepared statement to execute
     * @return the result set of the query
     * @throws ToastError if a query error occurs
     */
    public ResultSet executeQuery(PreparedStatement statement) throws ToastError {
        return this.executeStatement(statement, METHOD.QUERY);
    }

    /**
     * Executes an update on the database using the given prepared statement.
     *
     * @param statement the prepared statement to execute
     * @throws ToastError if an update error occurs
     */
    public void executeUpdate(PreparedStatement statement) throws ToastError {
        this.executeStatement(statement, METHOD.UPDATE);
    }

    /**
     * Executes a statement on the database using the given prepared statement and method.
     *
     * @param statement the prepared statement to execute
     * @param method the type of operation (query or update)
     * @return the result set of the query, or null if the operation is an update
     * @throws ToastError if a statement error occurs
     */
    private ResultSet executeStatement(PreparedStatement statement, METHOD method) throws ToastError {
        try {
            System.out.println("Query: " + statement);
            statement.setQueryTimeout(30);

            if (Objects.requireNonNull(method) == METHOD.QUERY) {
                return statement.executeQuery();
            }
            statement.executeUpdate();
            return null;

        } catch (SQLException exception) {
            String message = exception.getMessage();
            if (message.equals("query does not return ResultSet") || message.equals("Query does not return results")) {
                return null;
            }
            System.out.println(message);
            throw this.ConnectionError;

        }
    }

    /**
     * Establishes a connection to the database.
     *
     * @return the connection to the database
     * @throws ToastError if a connection error occurs
     */
    private Connection getConnection() throws ToastError {
        try {

            if (this.connection != null) {
                return this.connection;
            }

            Class.forName("org.sqlite.JDBC");

            String dir = Paths.get(
                    "src",
                    "main",
                    "java",
                    "tool",
                    "rental",
                    "domain",
                    "infra",
                    "db",
                    "db"
            ).toString();
            return DriverManager.getConnection("jdbc:sqlite:" + dir);

        } catch (SQLException | ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
            throw this.ConnectionError;
        }
    }

    /**
     * Closes the connection to the database.
     *
     * @throws ToastError if a close error occurs
     */
    @Override
    public void close() throws ToastError {
        if (this.connection == null) {
            return;
        }

        try {
            this.connection.close();
            this.connection = null;

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());

            throw new ToastError(
                    "Falha ao encerrar sessão do banco de dados.",
                    "Erro de banco de dados."
            );
        } finally {
            System.out.println("DB Connection closed.");
        }
    }
}
