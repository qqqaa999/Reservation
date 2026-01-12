-- 공연 예약 시스템 스키마
-- MariaDB / InnoDB / utf8mb4 기준

CREATE TABLE IF NOT EXISTS users (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    login_id     VARCHAR(50)  NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    name         VARCHAR(100) NOT NULL,
    phone        VARCHAR(20),
    email        VARCHAR(100),
    created_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS venue (
                                     id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     name         VARCHAR(100) NOT NULL,
    address      VARCHAR(255),
    created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS hall (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    venue_id     BIGINT       NOT NULL,
    name         VARCHAR(100) NOT NULL,
    capacity     INT,
    created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_hall_venue
    FOREIGN KEY (venue_id) REFERENCES venue(id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS performance (
                                           id             BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           title          VARCHAR(200) NOT NULL,
    description    TEXT,
    age_limit      VARCHAR(50),
    running_time   INT,
    created_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS performance_schedule (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    performance_id     BIGINT      NOT NULL,
    hall_id            BIGINT      NOT NULL,
    start_at           DATETIME    NOT NULL,
    end_at             DATETIME,
    status             VARCHAR(20) NOT NULL DEFAULT 'OPEN', -- OPEN, SALES_END, CANCELED 등
    created_at         DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_schedule_performance
    FOREIGN KEY (performance_id) REFERENCES performance(id),
    CONSTRAINT fk_schedule_hall
    FOREIGN KEY (hall_id) REFERENCES hall(id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS seat (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    hall_id      BIGINT      NOT NULL,
    section      VARCHAR(50),
    row_no       VARCHAR(10),
    seat_no      VARCHAR(10),
    seat_label   VARCHAR(50),
    grade        VARCHAR(20),
    created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_seat_hall
    FOREIGN KEY (hall_id) REFERENCES hall(id),
    UNIQUE KEY uk_seat_unique (hall_id, section, row_no, seat_no)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS performance_seat (
    id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    performance_schedule_id  BIGINT      NOT NULL,
    seat_id                  BIGINT      NOT NULL,
    price                    INT         NOT NULL,
    status                   VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE', -- AVAILABLE, HOLD, RESERVED, SOLD
    created_at               DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_perfseat_schedule
    FOREIGN KEY (performance_schedule_id) REFERENCES performance_schedule(id),
    CONSTRAINT fk_perfseat_seat
    FOREIGN KEY (seat_id) REFERENCES seat(id),
    UNIQUE KEY uk_perfseat_unique (performance_schedule_id, seat_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS reservation (
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id               BIGINT      NOT NULL,
    performance_schedule_id BIGINT    NOT NULL,
    status                VARCHAR(20) NOT NULL,  -- CREATED, PAYMENT_PENDING, PAID, CANCELED 등
    total_amount          INT         NOT NULL,
    created_at            DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at            DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_reservation_user
    FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_reservation_schedule
    FOREIGN KEY (performance_schedule_id) REFERENCES performance_schedule(id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS reservation_item (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    reservation_id      BIGINT NOT NULL,
    performance_seat_id BIGINT NOT NULL,
    price               INT    NOT NULL,
    created_at          DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_resitem_reservation
    FOREIGN KEY (reservation_id) REFERENCES reservation(id),
    CONSTRAINT fk_resitem_perfseat
    FOREIGN KEY (performance_seat_id) REFERENCES performance_seat(id),
    UNIQUE KEY uk_resitem_unique (reservation_id, performance_seat_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS payment (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    reservation_id  BIGINT      NOT NULL,
    amount          INT         NOT NULL,
    method          VARCHAR(20) NOT NULL,     -- CARD, ACCOUNT, POINT 등
    provider        VARCHAR(50),
    status          VARCHAR(20) NOT NULL,     -- SUCCESS, FAIL, REFUND 등
    paid_at         DATETIME,
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payment_reservation
    FOREIGN KEY (reservation_id) REFERENCES reservation(id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
