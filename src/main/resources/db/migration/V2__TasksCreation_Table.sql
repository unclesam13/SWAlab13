CREATE TABLE tasks (
    id INTEGER PRIMARY KEY NOT NULL,
    user_id INTEGER NOT NULL,
    title TEXT NOT NULL,
    description TEXT,
    created_at DATETIME2(1) NOT NULL DEFAULT CURRENT_TIMESTAMP, --I used Datetime2 for the Timestamps
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

