create table users
(
    id              bigserial primary key,
    last_name       text      not null,
    first_name      text      not null,
    middle_name     text,
    age             bigint    not null,
    login           text      not null unique,
    status          text      not null,
    password        text      not null unique,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

create table roles
(
    id                bigserial   primary key,
    name              varchar(50) not null unique,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);

create table users_roles
(
    user_id           bigint not null references users (id),
    role_id           bigint not null references roles (id),
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp,
    primary key (user_id, role_id)
);

create table contracts
(
    id           bigserial primary key,
    company_name text      not null,
    event_name   text      not null,
    admin_id     bigint    not null references users (id),
    status       text      not null,
    created_at   timestamp default current_timestamp,
    updated_at   timestamp default current_timestamp,
    unique (company_name, admin_id)
);

create table events
(
    id          bigserial primary key,
    contract_id bigint    not null unique references contracts (id),
    name        text      not null unique,
    cost        bigint    not null,
    status      text      not null,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table event_members
(
    id          bigserial primary key,
    event_id    bigint    not null references events (id),
    user_id     bigint    not null references users (id),
    pcr_test    text      not null,
    status      text      not null,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp,
    unique (event_id, user_id)
);
