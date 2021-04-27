create table members(
    id number,
x	    login_id varchar2(20),
    login_password varchar2(130)
);

create index member_id_pk_idx on members(id);
create index member_login_id_unique_idx on members(login_id);

alter table members add constraint member_id_pk primary key (id);
alter table members add constraint member_login_id_unique unique(login_id);

create table chatroom(
    id number,
    name varchar2(30),
    constraints name_nn check(name is not null)
);

create index chatroom_id_pk_idx on chatroom(id);
alter table chatroom add constraint chatroom_id_pk primary key(id);

create table chat_enroll(
    member_id number,
    chatroom_id number,
    enroll_date date,
    constraints enroll_date_nn check(enroll_date is not null)
);

create index chat_enroll_member_id_chat_room_id_pk_idx on chat_enroll(member_id, chatroom_id);
create index chat_enroll_member_id_fk_idx on chat_enroll(member_id);
create index chat_enroll_chatroom_id_fk_idx on chat_enroll(enroll_date);

alter table chat_enroll add constraint chat_enroll_member_id_chatroom_id_pk primary key(member_id, chatroom_id);
alter table chat_enroll add constraint chat_enroll_member_id_fk foreign key(member_id) references members(id);
alter table chat_enroll add constraint chat_enroll_chatroom_id_fk foreign key(chatroom_id) references chatroom(id);

create table chat_message(
    id number,
    member_id number,
    chatroom_id number,
    message varchar2(150),
    send_date date,
    constraints send_date_nn check(send_date is not null)
);

create index chat_message_id_pk_idx on chat_message(id);
create index chat_message_member_id_fk_idx on chat_message(member_id);
create index chat_message_chatroom_id_fk_idx on chat_message(chatroom_id);
create index chat_message_send_date_idx on chat_message(send_date);

alter table chat_message add constraint chat_message_id_pk primary key(id);
alter table chat_message add constraint chat_message_member_id_fk foreign key(member_id) references members(id);
alter table chat_message add constraint chat_message_chatroom_id_fk foreign key(chatroom_id) references chatroom(id);