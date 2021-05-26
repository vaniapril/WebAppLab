CREATE DATABASE IF NOT EXISTS db;
use db;
create table if not exists cryptocurrency
(
    id         varchar(32)  null,
    name       varchar(255) null,
    high52week double       null,
    low52week  double       null,
    current    double       null,
    code       varchar(255) null
);

create table if not exists monetaryunit
(
    id         varchar(32)  null,
    name       varchar(255) null,
    high52week double       null,
    low52week  double       null,
    current    double       null,
    code       varchar(255) null,
    country    varchar(255) null
);

create table if not exists preciousmetal
(
    id         varchar(32)  null,
    name       varchar(255) null,
    high52week double       null,
    low52week  double       null,
    current    double       null,
    code       varchar(255) null
);

create table if not exists stock
(
    id         varchar(32)  null,
    name       varchar(255) null,
    high52week double       null,
    low52week  double       null,
    current    double       null,
    code       varchar(255) null
);