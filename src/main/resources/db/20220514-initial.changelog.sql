create table if not exists link
(
    id           UUID    not null
    constraint link_pk primary key,
    short_link         varchar not null,
    original_link      varchar not null,
    visits_count       bigint default 0,
    rank       bigint,
    creation_date_time timestamp with time zone
);
create index link_short_link_index
    on link (short_link);
create index link_visits_count_index
    on link (visits_count);
create index link_original_link_index
    on link (original_link);
create sequence short_url_id_sequence;