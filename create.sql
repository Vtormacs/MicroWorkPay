create table tb_worker (id uuid not null, daily_income float8, name varchar(255), primary key (id));
create sequence hibernate_sequence start 1 increment 1;
create table tb_role (id int8 not null, role_name varchar(255), primary key (id));
create table tb_user (id uuid not null, email varchar(255), name varchar(255), password varchar(255), primary key (id));
create table tb_user_role (user_id uuid not null, role_id int8 not null, primary key (user_id, role_id));
alter table if exists tb_user add constraint UK_4vih17mube9j7cqyjlfbcrk4m unique (email);
alter table if exists tb_user_role add constraint FKea2ootw6b6bb0xt3ptl28bymv foreign key (role_id) references tb_role;
alter table if exists tb_user_role add constraint FK7vn3h53d0tqdimm8cp45gc0kl foreign key (user_id) references tb_user;
create sequence hibernate_sequence start 1 increment 1;
create table tb_role (id int8 not null, role_name varchar(255), primary key (id));
create table tb_user (id uuid not null, email varchar(255), name varchar(255), password varchar(255), primary key (id));
create table tb_user_role (user_id uuid not null, role_id int8 not null, primary key (user_id, role_id));
alter table if exists tb_user add constraint UK_4vih17mube9j7cqyjlfbcrk4m unique (email);
alter table if exists tb_user_role add constraint FKea2ootw6b6bb0xt3ptl28bymv foreign key (role_id) references tb_role;
alter table if exists tb_user_role add constraint FK7vn3h53d0tqdimm8cp45gc0kl foreign key (user_id) references tb_user;
create table tb_worker (id uuid not null, daily_income float8, name varchar(255), primary key (id));
