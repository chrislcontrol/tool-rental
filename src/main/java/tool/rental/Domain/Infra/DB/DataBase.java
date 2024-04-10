package tool.rental.Domain.Infra.DB;

import tool.rental.Utils.ToastError;

import java.sql.*;

public class DataBase implements AutoCloseable {
    public Connection connection;
    public final ToastError ConnectionError = new ToastError(
            "Falha ao conectar ao banco de dados.",
            "Erro de banco de dados"
    );


    public DataBase() throws ToastError {
        this.connection = this.getConnection();
    }


    public ResultSet executeStatement(PreparedStatement statement) throws ToastError {
        try {
            statement.setQueryTimeout(30);
            return statement.executeQuery();

        } catch (SQLException exception) {
            throw this.ConnectionError;

        } finally {
            try {
                statement.close();
            } catch (SQLException exception) {
                throw this.ConnectionError;
            }
        }
    }

    private Connection getConnection() throws ToastError {
        try {

            if (this.connection != null) {
                return this.connection;
            }
            return DriverManager.getConnection("jbdc:sqlite:base.db");


        } catch (SQLException exception) {
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
        }
    }
}
