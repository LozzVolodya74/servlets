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

CREATE TABLE role(
    id    bigint                NOT NULL,
    name character varying NOT NULL
);

ALTER TABLE ONLY person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);

ALTER TABLE ONLY person
    ADD CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES role (id) ON UPDATE CASCADE ON DELETE RESTRICT;
