CREATE SCHEMA application;

CREATE TABLE application.event_type (id serial primary key, name varchar(100));

INSERT INTO application.event_type (name) values ('museum'), ('cinema'), ('theatre');

CREATE TABLE application.place (id serial primary key, name varchar(100), address varchar(100), city varchar(100));

CREATE TABLE application.event(
id serial primary key,
name varchar(100),
event_type_id int REFERENCES application.event_type(id),
event_date timestamp,
place_id int REFERENCES application.place(id));

CREATE TABLE application.ticket(
id serial primary key,
event_id int REFERENCES application.event(id),
client_email varchar(100),
price numeric(9,2),
is_sold boolean);























