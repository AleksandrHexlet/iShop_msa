CREATE TABLE IF NOT EXISTS tb_category(
    id serial primary key,
    name varchar(199) NOT NULL,
    url varchar(199) NOT NULL,
    parent_id INTEGER REFERENCES tb_category(id)
);
