create table app_user (
    username varchar(50) primary key,
    password_hash varchar(100) not null,
    role varchar(20) not null,
    enabled boolean not null
);
