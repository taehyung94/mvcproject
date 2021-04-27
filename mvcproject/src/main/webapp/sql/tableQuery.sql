create table members(
    id number,
    login_id varchar2(20),
    login_password varchar2(100)
);

create index member_id_pk_idx on members(id);
create index member_login_id_unique_idx on members(login_id);

alter table members add constraint member_id_pk primary key (id);
alter table members add constraint member_login_id_unique unique(login_id);

create sequence member_seq;