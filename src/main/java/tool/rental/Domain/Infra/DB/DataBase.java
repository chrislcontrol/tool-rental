package tool.rental.Domain.Infra.DB;

import tool.rental.Utils.ToastError;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Objects;

public class DataBase implements AutoCloseable {
    public Connection connection;
    public final ToastError ConnectionError = new ToastError(
            "Falha ao conectar ao banco de dados.",
            "Erro de banco de dados"
    );


    public DataBase() throws ToastError {
        this.connection = this.getConnection();
        System.out.println("DB Connection open.");

    }

    public ResultSet executeStatement(PreparedStatement statement) throws ToastError {
        try {
            System.out.println("Query: "+ statement);
            statement.setQueryTimeout(30);
            return statement.executeQuery();

        } catch (SQLException exception) {
            throw this.ConnectionError;

        }
    }

    private Connection getConnection() throws ToastError {
        try {

            if (this.connection != null) {
                return this.connection;
            }

            Class.forName("org.sqlite.JDBC");

            String dir = Paths.get(
                    "src",
                    "main",
                    "java", "tool",
                    "rental",
                    "Domain",
                    "Infra",
                    "DB",
                    "db"
            ).toString();
            return DriverManager.getConnection("jdbc:sqlite:" + dir);

        } catch (SQLException | ClassNotFoundException exception) {
            throw this.ConnectionError;
        }
    }

    @Override
    public void close() throws ToastError {
        if (this.connection == null) {
            return;
        }

        try {
            this.connection.close();
            this.connection = null;

        } catch (SQLException exception) {
            throw new ToastError(
                    "Falha ao encerrar sessão do banco de dados.",
                    "Erro de banco de dados."
            );
        } finally {
            System.out.println("DB Connection closed.");
        }
    }
}
