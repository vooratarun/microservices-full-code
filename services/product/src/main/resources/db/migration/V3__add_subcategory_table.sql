create table if not exists subcategory
(
    id          integer not null
        primary key,
    description varchar(255),
    name        varchar(255),
    category_id integer
        constraint fk_subcategory_category
            references category
);

create sequence if not exists subcategory_seq increment by 50;

alter table product add column subcategory_id integer;
alter table product add constraint fk_product_subcategory foreign key (subcategory_id) references subcategory;
