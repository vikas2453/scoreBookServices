create table user (username varchar(255) not null, account_non_expired boolean not null, account_non_locked boolean not null, credentials_non_expired boolean not null, emailaddress varchar(255), enabled boolean not null, last_logged_in date, login_failed_attempt integer not null, login_times integer not null, password varchar(255), security_answer varchar(255), security_question varchar(255), user_type varchar(255), primary key (username));
create table authority (id integer not null, authority varchar(255), username varchar(255), primary key (id));
alter table authority add constraint FKljgmd4h8prtl0nk1r6dj4by1r foreign key (username) references user;