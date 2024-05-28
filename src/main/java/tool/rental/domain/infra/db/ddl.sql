-- "USER" definition
                                
                CREATE TABLE IF NOT EXISTS "USER" (
                	id TEXT(36) NOT NULL,
                	username TEXT(25) NOT NULL,
                	password TEXT(100) NOT NULL,
                	has_mock INTEGER DEFAULT (0) NOT NULL,
                	CONSTRAINT USER_PK PRIMARY KEY (id)
                );
                
                CREATE UNIQUE INDEX IF NOT EXISTS USER_username_IDX ON "USER" (username);

                -- CACHE definition
                                
                CREATE TABLE IF NOT EXISTS CACHE (
                	id TEXT NOT NULL,
                	logged_user_id TEXT NOT NULL,
                	CONSTRAINT CACHE_PK PRIMARY KEY (id),
                	CONSTRAINT CACHE_USER_FK FOREIGN KEY (logged_user_id) REFERENCES "USER"(id) ON DELETE CASCADE
                );

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
                

                CREATE INDEX IF NOT EXISTS FRIEND_user_id_IDX ON FRIEND (user_id);


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
                

                CREATE INDEX IF NOT EXISTS RENTAL_friend_id_IDX ON RENTAL (friend_id);

                -- TOOL definition
                                
                CREATE TABLE IF NOT EXISTS TOOL (
                	id TEXT NOT NULL,
                	name TEXT NOT NULL,
                	brand TEXT NOT NULL,
                	cost REAL NOT NULL,
                	user_id TEXT NOT NULL,
                	CONSTRAINT TOOL_PK PRIMARY KEY (id),
                	CONSTRAINT TOOL_USER_FK FOREIGN KEY (user_id) REFERENCES "USER"(id) ON DELETE CASCADE
                );
                
                CREATE INDEX IF NOT EXISTS TOOL_user_id_IDX ON TOOL (user_id);
