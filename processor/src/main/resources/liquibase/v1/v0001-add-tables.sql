create table "users"
(
    "id"        uuid primary key,
    "user_name" varchar(40)  not null,
    "email"     varchar(40)  not null,
    "password"  varchar(200) not null,
    unique ("user_name"),
    unique ("email")
);

create table "role"
(
    "id"        uuid,
    "role_name" varchar(10) not null,
    primary key ("id")
);

create table "users_roles"
(
    "user_id" uuid not null,
    "role_id" uuid not null,
    foreign key ("user_id") references "users" ("id"),
    foreign key ("role_id") references "role" ("id")
);

create table "request"
(
    "id"          uuid         not null,
    "name"        varchar(100) not null,
    "description" varchar(200),
    "price"       int          not null,
    "img"         varchar(100),
    "status"      varchar(15)  not null,
    "user_id"     uuid         not null,
    primary key ("id"),
    foreign key ("user_id") references "users" ("id")
);

create table "product"
(
    "id"          uuid        not null,
    "name"        varchar(50) not null,
    "description" varchar(200),
    "img"         varchar(100),
    "price"       int         not null,
    "is_sold"     bool        not null,
    primary key ("id")
);

create table "history"
(
    "id"         uuid not null,
    "user_id"    uuid not null,
    "product_id" uuid not null,
    "date"       timestamp without time zone default now(),
    primary key ("id"),
    foreign key ("user_id") references "users" ("id"),
    foreign key ("product_id") references "product" ("id")
);

insert into role (id, role_name)
values ('123e4567-e89b-12d3-a456-426655440000', 'ADMIN'),
       ('223e4567-e89b-12d3-a456-426655440000', 'PROVIDER'),
       ('323e4567-e89b-12d3-a456-426655440000', 'USER');

insert into users (id, user_name, email, password)
values ('e37b5e5c-7133-45b7-8461-c4f6006d0477',
        'admin',
        'adminadmin@admin.ru',
        '$2a$08$QVptKb9N3qjnUnCPkaUTXOD5ADlArYlVO6TiMH3XNxkf87sUygQvG');

insert into users_roles(user_id, role_id)
values ('e37b5e5c-7133-45b7-8461-c4f6006d0477',
        '123e4567-e89b-12d3-a456-426655440000');