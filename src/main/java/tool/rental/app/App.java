package tool.rental.app;


import tool.rental.domain.entities.Cache;
import tool.rental.domain.infra.db.DataBase;
import tool.rental.domain.repositories.CacheRepository;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.Toast;
import tool.rental.utils.ToastError;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The main application class.
 */
public class App {

    /**
     * The cache repository used to store and retrieve cache data.
     */
    private static final CacheRepository cacheRepository = new CacheRepository();

    /**
     * The main entry point of the application.
     *
     * @param args the command-line arguments
     * @throws Exception if an error occurs during the execution of the application
     */
    public static void main(String[] args) throws Exception {
        try {
            setupDB();
            runApp();
        } catch (Toast exc) {
            exc.display();
            if (exc.getStopRunTime()) {
                throw new Exception("Stop has been called.");
            }
        } catch (RuntimeException exc) {
            runApp();
        }
    }

    /**
     * Runs the application.
     *
     * @throws Toast if an error occurs during the execution of the application
     */
    public static void runApp() throws Toast {
        Cache cache = cacheRepository.getCache();
        if (cache != null) {
            Settings.setUser(cache.getUser());
            Settings.setFirstFrameAsMain();
        }

        PresentationFrame firstFrame = Settings.getFirstFrame();
        firstFrame.setVisible(true);
    }

    /**
     * Sets up the database by executing a series of SQL queries.
     *
     * @throws Toast if an error occurs during the setup of the database
     */
    public static void setupDB() throws Toast {
        String[] queries = new String[]{
                """
                -- "USER" definition
                
                CREATE TABLE IF NOT EXISTS "USER" (
                	id TEXT(36) NOT NULL,
                	username TEXT(25) NOT NULL,
                	password TEXT(100) NOT NULL,
                	has_mock INTEGER DEFAULT (0) NOT NULL,
                	CONSTRAINT USER_PK PRIMARY KEY (id)
                );
                """,
                """
                
                CREATE UNIQUE INDEX IF NOT EXISTS USER_username_IDX ON "USER" (username);
                
                """,

                """
                
                -- CACHE definition
                
                CREATE TABLE IF NOT EXISTS CACHE (
                	id TEXT NOT NULL,
                	logged_user_id TEXT NOT NULL,
                	CONSTRAINT CACHE_PK PRIMARY KEY (id),
                	CONSTRAINT CACHE_USER_FK FOREIGN KEY (logged_user_id) REFERENCES "USER"(id) ON DELETE CASCADE
                );
                
                """,
                """
                
                -- FRIEND definition
                
                CREATE TABLE IF NOT EXISTS FRIEND (
                	id TEXT NOT NULL,
                	name TEXT NOT NULL,
                	phone TEXT NOT NULL,
                	social_security TEXT NOT NULL,
                	user_id TEXT NOT NULL,
                	CONSTRAINT FRIENDS_PK PRIMARY KEY (id),
                	CONSTRAINT FRIEND_USER_FK FOREIGN KEY (user_id) REFERENCES "USER"(id) ON DELETE CASCADE
                );
                
                """,
                """
                
                CREATE INDEX IF NOT EXISTS FRIEND_user_id_IDX ON FRIEND (user_id);
                
                """,
                """
                
                -- RENTAL definition
                
                CREATE TABLE IF NOT EXISTS RENTAL (
                	id TEXT(36) NOT NULL,
                	rental_timestamp LONG NOT NULL,
                	devolution_timestamp LONG,
                	friend_id TEXT(36) NOT NULL,
                	tool_id TEXT(36) NOT NULL,
                	CONSTRAINT RENTAL_PK PRIMARY KEY (id),
                	CONSTRAINT RENTAL_FRIEND_FK FOREIGN KEY (friend_id) REFERENCES "FRIEND"(id) ON DELETE CASCADE,
                	CONSTRAINT RENTAL_TOOL_FK FOREIGN KEY (tool_id) REFERENCES "TOOL"(id) ON DELETE CASCADE
                );
                
                """,
                """
                
                CREATE INDEX IF NOT EXISTS RENTAL_friend_id_IDX ON RENTAL (friend_id);
                
                """,
                """
                
                -- TOOL definition
                
                CREATE TABLE IF NOT EXISTS TOOL (
                	id TEXT NOT NULL,
                	brand TEXT NOT NULL,
                	name TEXT NOT NULL,
                	cost REAL NOT NULL,
                	user_id TEXT NOT NULL,
                	CONSTRAINT TOOL_PK PRIMARY KEY (id),
                	CONSTRAINT TOOL_USER_FK FOREIGN KEY (user_id) REFERENCES "USER"(id) ON DELETE CASCADE
                );
                
                """,
                """
                
                CREATE INDEX IF NOT EXISTS TOOL_user_id_IDX ON TOOL (user_id);
                """
        };


        try (DataBase db = new DataBase()) {
            for (String query : queries) {
                PreparedStatement stm = db.connection.prepareStatement(query);
                db.executeUpdate(stm);
            }
        } catch (SQLException e) {
            throw new ToastError(String.format("Erro ao iniciar o banco de dados. %s", e),
                    "Erro de banco de dados.");
        }
    }
}
