FROM postgres:9.6-alpine

COPY ./docker/db/script1-scheme.sql /docker-entrypoint-initdb.d/script1-scheme.sql
COPY ./docker/db/script2-data.sql   /docker-entrypoint-initdb.d/script2-data.sql

ENV POSTGRES_DB=book_shop
ENV POSTGRES_USER=Vovik
ENV POSTGRES_PASSWORD=Kharkiv_2021
ENV PGDATA=/data
