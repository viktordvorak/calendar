-- Uživatelé (dva povolené Google účty)
CREATE TABLE users (
    id            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    google_sub    VARCHAR(255)    NOT NULL,
    email         VARCHAR(255)    NOT NULL,
    display_name  VARCHAR(100)    NOT NULL,
    color         VARCHAR(7)      NOT NULL,
    created_at    DATETIME(3)     NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    PRIMARY KEY (id),
    UNIQUE KEY uq_users_google_sub (google_sub),
    UNIQUE KEY uq_users_email (email)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- Úkoly s termínem (den, volitelně i čas)
CREATE TABLE tasks (
    id               BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT,
    title            VARCHAR(255)     NOT NULL,
    note             TEXT             NULL,
    due_date         DATE             NOT NULL,
    due_time         TIME             NULL,
    owner_id         BIGINT UNSIGNED  NOT NULL,
    created_by_id    BIGINT UNSIGNED  NOT NULL,
    is_shared        BOOLEAN          NOT NULL DEFAULT FALSE,
    status           ENUM('OPEN', 'DONE') NOT NULL DEFAULT 'OPEN',
    completed_at     DATETIME(3)      NULL,
    completed_by_id  BIGINT UNSIGNED  NULL,
    created_at       DATETIME(3)      NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    updated_at       DATETIME(3)      NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    PRIMARY KEY (id),
    CONSTRAINT fk_tasks_owner FOREIGN KEY (owner_id) REFERENCES users (id),
    CONSTRAINT fk_tasks_created_by FOREIGN KEY (created_by_id) REFERENCES users (id),
    CONSTRAINT fk_tasks_completed_by FOREIGN KEY (completed_by_id) REFERENCES users (id),
    KEY idx_tasks_due_date (due_date),
    KEY idx_tasks_owner_due (owner_id, due_date),
    KEY idx_tasks_shared_due (is_shared, due_date)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- Události s časovým rozsahem (uloženo v UTC)
CREATE TABLE events (
    id             BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT,
    title          VARCHAR(255)     NOT NULL,
    note           TEXT             NULL,
    starts_at      DATETIME(3)      NOT NULL,
    ends_at        DATETIME(3)      NOT NULL,
    all_day        BOOLEAN          NOT NULL DEFAULT FALSE,
    owner_id       BIGINT UNSIGNED  NOT NULL,
    created_by_id  BIGINT UNSIGNED  NOT NULL,
    is_shared      BOOLEAN          NOT NULL DEFAULT FALSE,
    created_at     DATETIME(3)      NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    updated_at     DATETIME(3)      NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
    PRIMARY KEY (id),
    CONSTRAINT fk_events_owner FOREIGN KEY (owner_id) REFERENCES users (id),
    CONSTRAINT fk_events_created_by FOREIGN KEY (created_by_id) REFERENCES users (id),
    CONSTRAINT chk_events_time_range CHECK (ends_at >= starts_at),
    KEY idx_events_starts_at (starts_at),
    KEY idx_events_owner_starts (owner_id, starts_at),
    KEY idx_events_shared_starts (is_shared, starts_at)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
