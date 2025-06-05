DROP TABLE IF EXISTS reviews;

CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    photo TEXT NOT NULL,
    review_date DATE NOT NULL,
    UNIQUE (user_id, room_id)
); 