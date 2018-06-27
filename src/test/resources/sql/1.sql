create table students (
  id       identity,
  name     varchar not null,
  group_id int
);

insert into students(name, group_id)
values('Вася Пупкин', 123456);