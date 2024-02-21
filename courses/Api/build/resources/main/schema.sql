create table POST (
    id Bigint auto_increment primary key,
    title varchar (400) not null,
    content varchar (2000) null,
    created timestamp
);
Create table COMMENT(
id Bigint Auto_Increment Primary Key,
post_id Bigint not null,
content Varchar(2000) null,
created timestamp
);

alter table COMMENT
add constraint comment_post_id
foreign key (post_id) references post(id)