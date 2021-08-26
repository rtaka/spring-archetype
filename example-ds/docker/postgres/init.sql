create table game (
    id integer primary key,
    host varchar(256) not null,
    held_at date not null
);

create table participant (
    id integer primary key,
    name varchar(256) not null
);

create table athlete (
    id integer primary key,
    participant_id integer not null,
    name varchar(256) not null
);

create table game_participant_assignment (
    id integer primary key,
    game_id integer not null,
    participant_id integer not null
);

create table sport (
    id integer primary key,
    name varchar(256) not null
);

create table discipline (
    id integer primary key,
    sport_id integer not null,
    name varchar(256) not null
);

create table event (
    id integer primary key,
    sport_id integer not null,
    discipline_id integer,
    name varchar(256) not null
);

create table game_event_assignment (
    id integer primary key,
    game_id integer not null,
    event_id integer not null
);

create table athlete_event_assignment (
    id integer primary key,
    athlete_id integer not null,
    event_id integer not null
);

insert into sport (id, name) values
(1, 'サッカー'),
(2, '空手'),
(3, 'バスケットボール');

insert into discipline (id, sport_id, name) values
(1, 2, '形'),
(2, 2, '組手'),
(3, 3, '3x3バスケットボール'),
(4, 3, 'バスケットボール');

insert into event (id, sport_id, discipline_id, name) values
(1, 1, null, '男子サッカー'),
(2, 1, null, '女子サッカー'),
(3, 2, 1, '男子 形'),
(4, 2, 2, '男子 組手67キロ級'),
(5, 2, 2, '男子 組手75キロ級'),
(6, 2, 2, '男子 組手75キロ超級'),
(7, 2, 1, '女子 形'),
(8, 2, 2, '女子 組手55キロ級'),
(9, 2, 2, '女子 組手61キロ級'),
(10, 2, 2, '女子 組手61キロ超級'),
(11, 3, 3, '男子 3x3バスケットボール'),
(12, 3, 3, '女子 3x3バスケットボール'),
(13, 3, 4, '男子 バスケットボール'),
(14, 3, 4, '女子 バスケットボール');