CREATE TABLE person(
    id    bigint                NOT NULL,
    role_id    bigint                NOT NULL,
    first_name  character varying  NOT NULL,
    last_name  character varying  NOT NULL,
    login character varying NOT NULL,
    dob date NOT NULL,
    password character varying NOT NULL,
    email character varying NOT NULL
);
