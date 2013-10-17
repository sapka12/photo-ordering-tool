
    alter table items 
        drop 
        foreign key FK5FDE7C0C5E24AA6;

    alter table orders 
        drop 
        foreign key FKC3DF62E5D1F5F0AE;

    drop table if exists items;

    drop table if exists orders;

    drop table if exists users;

    create table items (
        id bigint not null auto_increment,
        photoId varchar(255),
        quantity integer not null,
        order_id bigint,
        primary key (id)
    );

    create table orders (
        id bigint not null auto_increment,
        closingDate date,
        user_id bigint,
        primary key (id)
    );

    create table users (
        id bigint not null auto_increment,
        admin_role bit,
        password varchar(255),
        name varchar(255) unique,
        primary key (id)
    );

    alter table items 
        add index FK5FDE7C0C5E24AA6 (order_id), 
        add constraint FK5FDE7C0C5E24AA6 
        foreign key (order_id) 
        references orders (id);

    alter table orders 
        add index FKC3DF62E5D1F5F0AE (user_id), 
        add constraint FKC3DF62E5D1F5F0AE 
        foreign key (user_id) 
        references users (id);
