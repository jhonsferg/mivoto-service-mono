-- =====================================================
-- MiVoto Service - Database Schema (DDL)
-- System: Electronic Voting Platform
-- =====================================================

-- Extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

SET timezone = 'America/Lima';

-- =====================================================
-- TABLE: users
-- Entity: UserEntity
-- =====================================================
CREATE TABLE IF NOT EXISTS users (
    id              BIGSERIAL       PRIMARY KEY,
    document_number VARCHAR(20)     NOT NULL UNIQUE,
    first_name      VARCHAR(100)    NOT NULL,
    last_name       VARCHAR(100)    NOT NULL,
    email           VARCHAR(150)    NOT NULL UNIQUE,
    password        VARCHAR(255)    NOT NULL,
    role            VARCHAR(20)     NOT NULL CHECK (role IN ('VOTER', 'ADMIN', 'SUPERVISOR', 'AUDITOR')),
    active          BOOLEAN         NOT NULL,
    created_at      TIMESTAMP       NOT NULL,
    updated_at      TIMESTAMP,
    last_login      TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_document_number ON users (document_number);
CREATE INDEX IF NOT EXISTS idx_email           ON users (email);

-- =====================================================
-- TABLE: districts
-- Entity: DistrictEntity
-- =====================================================
CREATE TABLE IF NOT EXISTS districts (
    id                  BIGSERIAL       PRIMARY KEY,
    name                VARCHAR(200)    NOT NULL,
    code                VARCHAR(20)     NOT NULL UNIQUE,
    type                VARCHAR(50),
    parent_district_id  BIGINT          REFERENCES districts (id),
    registered_voters   INTEGER,
    active              BOOLEAN         NOT NULL,
    created_at          TIMESTAMP       NOT NULL,
    updated_at          TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_code            ON districts (code);
CREATE INDEX IF NOT EXISTS idx_parent_district ON districts (parent_district_id);

-- =====================================================
-- TABLE: elections
-- Entity: ElectionEntity
-- =====================================================
CREATE TABLE IF NOT EXISTS elections (
    id                    BIGSERIAL       PRIMARY KEY,
    title                 VARCHAR(200)    NOT NULL,
    description           TEXT,
    status                VARCHAR(20)     NOT NULL CHECK (status IN ('DRAFT', 'SCHEDULED', 'ACTIVE', 'CLOSED', 'CANCELLED')),
    start_date            TIMESTAMP       NOT NULL,
    end_date              TIMESTAMP       NOT NULL,
    max_votes_per_user    INTEGER,
    allows_blank_vote     BOOLEAN,
    requires_verification BOOLEAN,
    created_at            TIMESTAMP       NOT NULL,
    updated_at            TIMESTAMP,
    created_by            BIGINT          REFERENCES users (id)
);

CREATE INDEX IF NOT EXISTS idx_status ON elections (status);
CREATE INDEX IF NOT EXISTS idx_dates  ON elections (start_date, end_date);

-- =====================================================
-- TABLE: candidates
-- Entity: CandidateEntity
-- =====================================================
CREATE TABLE IF NOT EXISTS candidates (
    id          BIGSERIAL       PRIMARY KEY,
    election_id BIGINT          NOT NULL REFERENCES elections (id),
    number      INTEGER         NOT NULL,
    name        VARCHAR(200)    NOT NULL,
    party       VARCHAR(150),
    description TEXT,
    photo_url   VARCHAR(500),
    active      BOOLEAN         NOT NULL,
    vote_count  INTEGER         NOT NULL DEFAULT 0,
    created_at  TIMESTAMP       NOT NULL,
    updated_at  TIMESTAMP,
    CONSTRAINT uq_election_number UNIQUE (election_id, number)
);

CREATE INDEX IF NOT EXISTS idx_candidate_election_id     ON candidates (election_id);
CREATE INDEX IF NOT EXISTS idx_candidate_election_number ON candidates (election_id, number);

-- =====================================================
-- TABLE: votes
-- Entity: VoteEntity
-- =====================================================
CREATE TABLE IF NOT EXISTS votes (
    id                BIGSERIAL       PRIMARY KEY,
    user_id           BIGINT          NOT NULL,
    election_id       BIGINT          NOT NULL,
    candidate_id      BIGINT          NOT NULL,
    vote_hash         VARCHAR(64)     NOT NULL UNIQUE,
    status            VARCHAR(20)     NOT NULL CHECK (status IN ('PENDING', 'CONFIRMED', 'REJECTED', 'UNDER_REVIEW')),
    verified          BOOLEAN         NOT NULL,
    verification_code VARCHAR(50),
    voted_at          TIMESTAMP       NOT NULL,
    verified_at       TIMESTAMP,
    ip_address        VARCHAR(45),
    user_agent        VARCHAR(500),
    CONSTRAINT uq_user_election UNIQUE (user_id, election_id)
);

CREATE INDEX IF NOT EXISTS idx_vote_user_election ON votes (user_id, election_id);
CREATE INDEX IF NOT EXISTS idx_vote_election_id   ON votes (election_id);
CREATE INDEX IF NOT EXISTS idx_vote_hash          ON votes (vote_hash);

-- =====================================================
-- TABLE: voting_sessions
-- Entity: VotingSessionEntity
-- =====================================================
CREATE TABLE IF NOT EXISTS voting_sessions (
    id               BIGSERIAL       PRIMARY KEY,
    user_id          BIGINT          NOT NULL,
    session_token    VARCHAR(500)    NOT NULL UNIQUE,
    refresh_token    VARCHAR(500)    NOT NULL UNIQUE,
    created_at       TIMESTAMP       NOT NULL,
    expires_at       TIMESTAMP       NOT NULL,
    last_accessed_at TIMESTAMP,
    ip_address       VARCHAR(45),
    user_agent       VARCHAR(500),
    active           BOOLEAN         NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_session_user_id      ON voting_sessions (user_id);
CREATE INDEX IF NOT EXISTS idx_session_token        ON voting_sessions (session_token);
CREATE INDEX IF NOT EXISTS idx_refresh_token         ON voting_sessions (refresh_token);

-- =====================================================
-- TABLE: audit_logs
-- Entity: AuditLogEntity
-- =====================================================
CREATE TABLE IF NOT EXISTS audit_logs (
    id          BIGSERIAL       PRIMARY KEY,
    user_id     BIGINT,
    action      VARCHAR(50)     NOT NULL CHECK (action IN (
                    'LOGIN', 'LOGOUT', 'LOGIN_FAILED', 'PASSWORD_CHANGED',
                    'VOTE_CAST', 'VOTE_VERIFIED', 'VOTE_REJECTED',
                    'ELECTION_CREATED', 'ELECTION_UPDATED', 'ELECTION_STARTED',
                    'ELECTION_CLOSED', 'ELECTION_CANCELLED',
                    'CANDIDATE_ADDED', 'CANDIDATE_UPDATED', 'CANDIDATE_REMOVED',
                    'USER_CREATED', 'USER_UPDATED', 'USER_DELETED',
                    'USER_ACTIVATED', 'USER_DEACTIVATED',
                    'UNAUTHORIZED_ACCESS', 'DATA_ACCESSED', 'DATA_MODIFIED',
                    'PERMISSION_DENIED'
                )),
    entity      VARCHAR(50),
    entity_id   BIGINT,
    description TEXT,
    ip_address  VARCHAR(45),
    user_agent  VARCHAR(500),
    timestamp   TIMESTAMP       NOT NULL,
    metadata    TEXT
);

CREATE INDEX IF NOT EXISTS idx_audit_user_id  ON audit_logs (user_id);
CREATE INDEX IF NOT EXISTS idx_audit_action   ON audit_logs (action);
CREATE INDEX IF NOT EXISTS idx_audit_entity   ON audit_logs (entity, entity_id);
CREATE INDEX IF NOT EXISTS idx_audit_timestamp ON audit_logs (timestamp);

-- =====================================================
-- TABLE: vote_records
-- Entity: VoteRecordEntity
-- =====================================================
CREATE TABLE IF NOT EXISTS vote_records (
    id               BIGSERIAL       PRIMARY KEY,
    vote_id          BIGINT          NOT NULL,
    user_id          BIGINT          NOT NULL,
    election_id      BIGINT          NOT NULL,
    vote_hash        VARCHAR(64)     NOT NULL,
    timestamp        TIMESTAMP       NOT NULL,
    verified         BOOLEAN         NOT NULL,
    blockchain_hash  VARCHAR(128)
);

CREATE INDEX IF NOT EXISTS idx_record_vote_id     ON vote_records (vote_id);
CREATE INDEX IF NOT EXISTS idx_record_user_id     ON vote_records (user_id);
CREATE INDEX IF NOT EXISTS idx_record_election_id ON vote_records (election_id);
CREATE INDEX IF NOT EXISTS idx_record_vote_hash   ON vote_records (vote_hash);
