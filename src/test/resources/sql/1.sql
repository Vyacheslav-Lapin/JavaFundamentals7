create table if not exists students (
  id       identity,
  name     varchar not null,
  group_id int
);