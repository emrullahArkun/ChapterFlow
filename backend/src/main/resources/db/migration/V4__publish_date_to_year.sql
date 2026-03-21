-- Convert publish_date (VARCHAR) to publish_year (INTEGER)

-- Add new integer column
ALTER TABLE books ADD COLUMN publish_year INTEGER;

-- Migrate existing data: extract year from string values like "2023" or "Unknown Date"
UPDATE books
SET publish_year = CASE
    WHEN publish_date ~ '^\d{4}$' THEN publish_date::INTEGER
    ELSE NULL
END;

-- Drop old column
ALTER TABLE books DROP COLUMN publish_date;
