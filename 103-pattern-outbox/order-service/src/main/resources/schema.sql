create table orders (
    id bigint auto_increment primary key,
    product_id bigint not null,
    product_name varchar(100) not null,
    unit_price_in_cents int not null,
    quantity int not null
);
create table outbox (
    id bigint auto_increment primary key,
    topic varchar(100) not null,
    message_key varchar(100) not null,
    payload varchar(1000) not null,
    created_at timestamp not null,
    sent_at timestamp
);
