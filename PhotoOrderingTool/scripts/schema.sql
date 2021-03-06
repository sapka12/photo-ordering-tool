
    create table pot_items (
        id bigint not null auto_increment,
        flickr_photo_id varchar(255) not null,
        order_id bigint not null,
        primary key (id)
    );

    create table pot_orders (
        id bigint not null auto_increment,
        closingDate datetime,
        user_id bigint not null,
        primary key (id)
    );

    create table pot_phototype_counter (
        id bigint not null auto_increment,
        counter integer not null,
        type varchar(255),
        item_id bigint,
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

    alter table pot_phototype_counter 
        add index FKBDD3C5BF58D84BAE (item_id), 
        add constraint FKBDD3C5BF58D84BAE 
        foreign key (item_id) 
        references pot_items (id);
