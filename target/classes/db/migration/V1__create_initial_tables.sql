CREATE TABLE USERS(
    id BIGSERIAL primary key,
    email varchar(254) not null,
    password varchar(60) not null,
    createdTimestamp timestamp,
    lastUpdatedTimestamp timestamp
);

CREATE TABLE Poll(
    id BIGSERIAL primary key,
    title varchar(200) not null,
    description varchar(5000),
    createdTimestamp timestamp,
    lastUpdatedTimestamp timestamp,
    owner_id BIGINT REFERENCES USERS(id)
);

CREATE TABLE Choice(
    id BIGSERIAL primary key,
    text varchar,
    poll_id BIGINT REFERENCES Poll(id)
);

CREATE TABLE Voter(
    uuid UUID primary key
);

CREATE TABLE Vote(
    id BIGSERIAL primary key,
    choice_id BIGINT REFERENCES Choice(id),
    voter_id UUID REFERENCES Voter(uuid)
);

