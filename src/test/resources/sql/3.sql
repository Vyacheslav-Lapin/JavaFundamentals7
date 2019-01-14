create table if not exists Person (
  id         identity,
  first_name varchar(255) not null,
  last_name  varchar(255),
  permission boolean         default false,
  dob        date,
  email      varchar(255) not null,
  password   varchar(255) not null,
  address    varchar(255),
  telephone  varchar(20)
);

insert into Person (first_name, last_name, permission, dob, email, password, address, telephone)
values ('Jose', 'Eglesias', true, '1980-06-15', 'Jose_Eglesias@mail.es', 'qwerty', 'Franco square, 5/1, 10',
        '+38007654321');

insert into Person (first_name, last_name, permission, dob, email, password, address, telephone)
values ('John', 'Eglesias', true, '1980-06-15', 'John_Eglesias@mail.es', 'qwerty', 'Franco square, 5/1, 10',
        '+38007654321');

insert into Person (first_name, last_name, dob, email, password, address, telephone)
values ('Pit', 'Eglesias', '1980-06-15', 'Pit_Eglesias@mail.es', 'qwerty', 'Franco square, 5/1, 10',
        '+38007654321');

insert into Person (first_name, last_name, permission, dob, email, password, address, telephone)
values ('Aisha', 'Eglesias', true, '1980-06-15', 'Aisha_Eglesias@mail.es', 'qwerty', 'Franco square, 5/1, 10',
        '+38007654321');

create table if not exists Role (
  email varchar(255) not null,
  role  varchar(15)  not null,
  primary key (email, role),
  foreign key (email) references Person (email)
);
insert into Role (email, role) values ('Jose_Eglesias@mail.es', 'user');
insert into Role (email, role) values ('Jose_Eglesias@mail.es', 'admin');
insert into Role (email, role) values ('John_Eglesias@mail.es', 'user');
insert into Role (email, role) values ('Pit_Eglesias@mail.es', 'user');
insert into Role (email, role) values ('Aisha_Eglesias@mail.es', 'user');

create table if not exists Gun (
  id      identity,
  name    varchar(255) not null,
  caliber double       not null
);
insert into Gun (name, caliber) values ('Kolt', 11.52);
insert into Gun (name, caliber) values ('Beretta', 6.35);
insert into Gun (name, caliber) values ('Glock', 9.0);
insert into Gun (name, caliber) values ('AKM-47', 7.62);
insert into Gun (name, caliber) values ('AK-74', 5.45);

create table if not exists GunInstance (
  id       identity,
  modelId int not null,
  foreign key (modelId) references Gun (id)
);
insert into GunInstance (modelId) values (1);
insert into GunInstance (modelId) values (1);
insert into GunInstance (modelId) values (1);
insert into GunInstance (modelId) values (1);
insert into GunInstance (modelId) values (1);
insert into GunInstance (modelId) values (1);
insert into GunInstance (modelId) values (2);
insert into GunInstance (modelId) values (2);
insert into GunInstance (modelId) values (2);
insert into GunInstance (modelId) values (2);
insert into GunInstance (modelId) values (3);
insert into GunInstance (modelId) values (3);
insert into GunInstance (modelId) values (4);
insert into GunInstance (modelId) values (4);
insert into GunInstance (modelId) values (4);
insert into GunInstance (modelId) values (4);
insert into GunInstance (modelId) values (4);
insert into GunInstance (modelId) values (4);
insert into GunInstance (modelId) values (4);
insert into GunInstance (modelId) values (4);
insert into GunInstance (modelId) values (5);
insert into GunInstance (modelId) values (5);
insert into GunInstance (modelId) values (5);
insert into GunInstance (modelId) values (5);
insert into GunInstance (modelId) values (5);
