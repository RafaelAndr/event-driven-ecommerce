create database ipurchasecustomers

create table customers (
    code serial not null primary key,
    name varchar(150) not null,
    cpf char(11) not null,
    address varchar(100),
    number varchar(10),
    district varchar(100),
    email varchar(150),
    telephone varchar(20)
)

create table products (
    code serial not null primary key,
    name varchar(100) not null,
    unit_value decimal(16,2) not null
)


create table orders (
    code serial not null primary key,
    customer_code bigint not null,
    order_date timestamp not null default now(),
    payment_key text,
    observations text,
    status varchar(20) check ( status in ('REALIZADO', 'PAGO', 'FATURADO', 'ENVIADO', 'ERRO_PAGAMENTO', 'PREPARANDO_ENVIO')),
    total decimal(16, 2) not null,
    tracking_code varchar(255),
    url_nf text
)

create table order_item (
    code serial not null primary key,
    order_code bigint not null references orders(code),
    product_code bigint not null,
    amount int not null,
    unit_value decimal(16,2) not null
)

