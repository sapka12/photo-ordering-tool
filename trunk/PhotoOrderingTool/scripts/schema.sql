
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
        password varchar(255),
        name varchar(255),
        primary key (id)
    );

    alter table items 
        add index FK5FDE7C029A0943D (order_id), 
        add constraint FK5FDE7C029A0943D 
        foreign key (order_id) 
        references orders (id);

    alter table orders 
        add index FKC3DF62E5510C9837 (user_id), 
        add constraint FKC3DF62E5510C9837 
        foreign key (user_id) 
        references users (id);
