CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    name TEXT
);

CREATE TABLE IF NOT EXISTS chat_threads (
                                       id BIGSERIAL PRIMARY KEY,
                                       name TEXT NOT NULL,
                                       created_at TIMESTAMP NOT NULL,
                                       user_id BIGINT NOT NULL,
                                       FOREIGN KEY (user_id) REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS messages (
                                            id BIGSERIAL PRIMARY KEY,
                                            content TEXT NOT NULL,
                                            created_at TIMESTAMP NOT NULL,
                                            chatThread_id BIGINT NOT NULL,
                                            isLLMGenerated BOOLEAN NOT NULL,
                                            FOREIGN KEY (chatThread_id) REFERENCES chat_threads(id)
);

-- Create test users
INSERT INTO users (email, password, name)
VALUES ('csekas@ctrlspace.dev', '123456', 'Chris Sekas');

INSERT INTO users (email, password, name)
VALUES ('alkisti@ctrlspace.dev', '123456', 'Alkisti');

INSERT INTO users (email, password, name)
VALUES ('nick@ctrlspace.dev', '123456789', 'Nick');

INSERT INTO users (email, password, name)
VALUES ('george@ctrspace.dev', '43f43gt45', 'George');

-- create test threads
INSERT INTO chat_threads (name, created_at, user_id)
VALUES ('Thread 1', '2025-05-27 14:30:00', 1);

INSERT INTO chat_threads (name, created_at, user_id)
VALUES ('Thread 2', '2025-05-27 14:30:00', 3);

INSERT INTO chat_threads (name, created_at, user_id)
VALUES ('Thread 3', '2025-05-27 14:30:00', 2);

INSERT INTO chat_threads (name, created_at, user_id)
VALUES ('Thread 4', '2025-05-27 14:30:00', 2);

-- create test messages
INSERT INTO messages (content, created_at, chatThread_id, isLLMGenerated)
VALUES ('Message 1', '2025-05-27 14:30:00', 2, FALSE);

INSERT INTO messages (content, created_at, chatThread_id, isLLMGenerated)
VALUES ('Message 2', '2025-05-27 14:30:00', 3, FALSE);

INSERT INTO messages (content, created_at, chatThread_id, isLLMGenerated)
VALUES ('Message 3', '2025-05-27 14:30:00', 2, FALSE);




