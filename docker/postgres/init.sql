-- PostgreSQL initialization script for MiVoto database
-- This script runs when the container is first created

-- Enable UUID extension for UUID generation
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Enable pgcrypto for password hashing if needed
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Set timezone
SET timezone = 'America/Lima';

-- Create indexes will be handled by Flyway migrations
-- This script only sets up extensions and basic configuration
