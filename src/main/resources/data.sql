drop table if exists student cascade;

create table student (
    id varchar(60) primary key,
    first_name varchar(100) not null,
    middle_name varchar(100) null,
    last_name varchar(100) not null,
    phone_number varchar(10) not null,
    email_address varchar(124) not null,
    updated timestamp not null default current_timestamp()
);

insert into student (id, first_name, middle_name, last_name, email_address, phone_number) values
('8367294aea27', 'Stella', null, 'Dixon', 'sdixon@yahoo.com', '3522332164'),
('e856c3399d0b', 'Emma', 'Gianna', 'Cunningham', 'emma.cunningham@gmail.com', '7311063034'),
('47bc145acd56', 'Carlos', 'Lyndon', 'Perkins', 'carlosp@mindex.com', '1136532503'),
('a1177622d51d', 'Jack', null, 'Thomas', 'jackthomas@hvcc.edu', '1135433110'),
('33be9382a13a', 'Sophia', 'Elise', 'Kelly', 'sophia.kelly@shen.edu', '5021401244'),
('03e7aa961336', 'Vivian', 'Mason', 'Wilson', 'vwilson@cbs.com', '6206525045'),
('5d1cea7af6dd', 'Sam', null, 'Cooper', 'sam.cooper@meta.com', '2523733264'),
('3139fd13fa85', 'Ashton', 'Carina', 'Foster', 'ashtonf@nycreates', '4653236364');
