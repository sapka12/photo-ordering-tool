
    alter table pot_items 
        drop 
        foreign key FK89002956C5E24AA6;

    alter table pot_orders 
        drop 
        foreign key FKA125540FD1F5F0AE;

    drop table if exists pot_items;

    drop table if exists pot_orders;

    drop table if exists pot_users;

    create table pot_items (
        id bigint not null auto_increment,
        flickr_photo_id varchar(255) not null,
        quantity integer,
        order_id bigint not null,
        primary key (id)
    );

    create table pot_orders (
        id bigint not null auto_increment,
        closingDate date,
        user_id bigint not null,
        primary key (id)
    );

    create table pot_users (
        id bigint not null auto_increment,
        admin_role bit,
        password varchar(255),
        name varchar(255) not null unique,
        primary key (id)
    );

    alter table pot_items 
        add index FK89002956C5E24AA6 (order_id), 
        add constraint FK89002956C5E24AA6 
        foreign key (order_id) 
        references pot_orders (id);

    alter table pot_orders 
        add index FKA125540FD1F5F0AE (user_id), 
        add constraint FKA125540FD1F5F0AE 
        foreign key (user_id) 
        references pot_users (id);
