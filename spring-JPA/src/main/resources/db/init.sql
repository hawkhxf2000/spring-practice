drop table if exists customer;
create table customer (
    custID int primary key auto_increment,
    username varchar(50) not null unique,
    password varchar(50) not null,
    email varchar(50),
    create_time timestamp default current_timestamp()
);


