-- Drop the existing types if they exist
DROP TYPE IF EXISTS room_type CASCADE;
DROP TYPE IF EXISTS room_status CASCADE;

-- Create the room_type enum if it doesn't exist
DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'room_type') THEN
        CREATE TYPE room_type AS ENUM ('STANDARD', 'DELUXE', 'VIP');
    END IF;
END $$;

-- Create the room_status enum if it doesn't exist
DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'room_status') THEN
        CREATE TYPE room_status AS ENUM ('AVAILABLE', 'OCCUPIED', 'MAINTENANCE', 'CLEANING');
    END IF;
END $$;

-- Drop the existing table if it exists
DROP TABLE IF EXISTS rooms CASCADE;

-- Create the rooms table if it doesn't exist
CREATE TABLE IF NOT EXISTS rooms (
    id UUID PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    image_url VARCHAR(255),
    price DECIMAL(10,2),
    capacity INTEGER,
    type VARCHAR(50) NOT NULL CHECK (type IN ('STANDARD', 'DELUXE', 'VIP')),
    status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE' CHECK (status IN ('AVAILABLE', 'OCCUPIED', 'MAINTENANCE', 'CLEANING'))
); 