create database if not exists bbs default character set 'utf8mb4';
use bbs;
create table userinfo(
    uid int primary key auto_increment,
    login_name varchar(50) unique not null,
    username varchar(50) not null,
    password varchar(50) not null,
    head varchar(1024),
    create_time datetime default now()
)default charset='utf8mb4';

create table discuss(
    comment_id int primary key auto_increment,
    title varchar(50) not null,
    score int not null,
    content varchar(4096) not null,
    create_time datetime default now(),
    uid int
)default charset='utf8mb4';