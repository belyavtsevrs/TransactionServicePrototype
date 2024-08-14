create table exchange_rate
(
    close  double       not null,
    id     bigint auto_increment
        primary key,
    date   varchar(255) null,
    symbol varchar(255) null
);

create table flyway_schema_history
(
    installed_rank int                                 not null
        primary key,
    version        varchar(50)                         null,
    description    varchar(200)                        not null,
    type           varchar(20)                         not null,
    script         varchar(1000)                       not null,
    checksum       int                                 null,
    installed_by   varchar(100)                        not null,
    installed_on   timestamp default CURRENT_TIMESTAMP not null,
    execution_time int                                 not null,
    success        tinyint(1)                          not null
);

create index flyway_schema_history_s_idx
    on flyway_schema_history (success);

create table transaction_limit
(
    limit_sum                double       null,
    id                       bigint       not null
        primary key,
    expense_category         varchar(255) not null,
    limit_currency_shortname varchar(255) null,
    limit_date_time          varchar(255) null
);

create table transaction
(
    account_from       int          null,
    account_to         int          null,
    currency_shortname varchar(3)   null,
    limit_exceeded     bit          null,
    sum                double       null,
    id                 bigint auto_increment
        primary key,
    limit_id           bigint       null,
    date_time          varchar(255) null,
    expense_category   varchar(255) null,
    constraint UK924ljs8hhi16f2ahc85tv05l5
        unique (limit_id),
    constraint FKd2ttbv66hiy2s1eoijon4impo
        foreign key (limit_id) references transaction_limit (id)
);
