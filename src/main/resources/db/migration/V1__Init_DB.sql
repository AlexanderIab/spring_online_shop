-- USERS
create sequence user_seq start 1 increment 1;

create table users (
                       id int8 not null,
                       archive boolean not null,
                       email varchar(255),
                       name varchar(255),
                       password varchar(255),
                       role varchar(255),
                       activated boolean not null,
                       activate_code varchar(80),
                       primary key (id)
);
-- BUCKET
create sequence bucket_seq start 1 increment 1;

create table buckets (
                         id int8 not null,
                         user_id int8,
                         primary key (id)
);

-- LINK BETWEEN BUCKET AND USER
alter table if exists buckets
    add constraint buckets_fk_user
    foreign key (user_id) references users;

-- CATEGORY
create sequence category_seq start 1 increment 1;

create table categories (
                            id int8 not null,
                            category_enum varchar(255),
                            primary key (id)
);
-- PRODUCTS
create sequence product_seq start 1 increment 1;

create table products (
                          id int8 not null,
                          price float(53),
                          title varchar(255),
                          category_id int8,
                          primary key (id)
);

alter table if exists products
    add constraint products_fk_category
    foreign key (category_id) references categories;


-- PRODUCTS IN BUCKET
create table buckets_products (
                                 bucket_id int8 not null,
                                 product_id int8 not null
);

alter table if exists buckets_products
    add constraint buckets_products_fk_products
    foreign key (product_id) references products;

alter table if exists buckets_products
    add constraint buckets_products_fk_bucket
    foreign key (bucket_id) references buckets;
-- ORDERS
create sequence order_seq start 1 increment 1;

create table orders (
                        id int8 not null,
                        address varchar(255),
                        updated timestamp,
                        created timestamp,
                        status varchar(255),
                        sum float(53),
                        user_id int8,
                        primary key (id)
);

alter table if exists orders
    add constraint orders_fk_user
    foreign key (user_id) references users;

-- DETAILS OF ORDER
create sequence order_details_seq start 1 increment 1;

create table orders_details (
                                id int8 not null,
                                order_id int8 not null ,
                                product_id int8 not null,
                                quantity bigint,
                                price float(53),
                                primary key (id)
);

alter table if exists orders_details
    add constraint orders_details_fk_order
    foreign key (order_id) references orders;

alter table if exists orders_details
    add constraint orders_details_fk_product
    foreign key (product_id) references products;