create table banking_core.customer
(
    customer_number varchar(37) not null
        constraint customer_pk
            primary key,
    name            varchar     not null,
    birth_date      date        not null,
    mobile_number   varchar     not null,
    email           varchar     not null,
    address         varchar     not null,
    cif             varchar
);

comment
on table banking_core.customer is 'customer table data for maybank core schema';

comment
on column banking_core.customer.customer_number is 'id for customer';

comment
on constraint customer_pk on banking_core.customer is 'customer primary key';

comment
on column banking_core.customer.name is 'customer name';

comment
on column banking_core.customer.birth_date is 'customer birth date';

comment
on column banking_core.customer.mobile_number is 'customer phone number ';

comment
on column banking_core.customer.email is 'customer email address';

comment
on column banking_core.customer.address is 'customer home address';

alter table banking_core.customer
    owner to devadmin;

create table banking_core.card
(
    pan    varchar(20) not null
        constraint card_pk
            primary key,
    pin    varchar     not null,
    status varchar(20) not null
        constraint card_status
            check ((status)::text = ANY
        ((ARRAY ['inactive':: character varying, 'active':: character varying, 'expired':: character varying])::text[])
) ,
    expiry_date     timestamp with time zone default (now() + '5 years'::interval) not null,
    customer_number varchar(37)                                                    not null
        constraint card_customer_number_fk
            references banking_core.customer
);

comment
on table banking_core.card is 'card table for maybank core schema';

comment
on column banking_core.card.pan is 'card number';

comment
on column banking_core.card.pin is 'personal identification number';

comment
on column banking_core.card.status is 'card status';

comment
on column banking_core.card.expiry_date is 'card expiry date';

comment
on column banking_core.card.customer_number is 'customer number fk';

comment
on constraint card_customer_number_fk on banking_core.card is 'customer number fk';

alter table banking_core.card
    owner to devadmin;

create unique index cif_unique_idx
    on banking_core.customer (cif);

comment
on index banking_core.cif_unique_idx is 'CIF Indexes';

create table banking_core.key
(
    service_id    varchar(37)                            not null
        constraint key_pk
            primary key,
    private_key   text                                   not null,
    registered_at timestamp with time zone default now() not null,
    updated_at    timestamp with time zone default now() not null,
    public_key    text                                   not null
);

comment
on table banking_core.key is 'Key Rotation Table';

comment
on column banking_core.key.service_id is 'Key Id Column';

comment
on column banking_core.key.private_key is 'Generated key for AES crypto';

comment
on column banking_core.key.registered_at is 'Service registration timestamp';

comment
on column banking_core.key.updated_at is 'Generation key timestamp';

comment
on column banking_core.key.public_key is 'Public Key RSA';

alter table banking_core.key
    owner to devadmin;

