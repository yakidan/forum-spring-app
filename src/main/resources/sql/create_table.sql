create table users
(
    id       bigserial
        constraint users_pk
            primary key,
    email    varchar(255) not null,
    first_name    varchar(50),
    last_name    varchar(50),
    password varchar(255) not null
);
create table topics
(
    id          bigserial
        constraint topics_pk
            primary key,
    title       varchar(150) not null,
    description varchar(400) not null,
    user_id     bigint       not null,
    foreign key (user_id) references users (id)
);


create table comments
(
    id       bigserial
        constraint comments_pk
            primary key,
    title    varchar(500) not null,
    topic_id bigint not null,
    user_id  bigint not null,
    foreign key (topic_id) references topics (id),
    foreign key (user_id) references users (id)

);


