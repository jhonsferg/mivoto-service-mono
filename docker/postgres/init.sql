-- Enable UUID extension for UUID generation
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create schema if not exists (Flyway effectively manages this, but good to have)
CREATE SCHEMA IF NOT EXISTS mivoto;

-- Set search path
SET search_path TO mivoto, public;
