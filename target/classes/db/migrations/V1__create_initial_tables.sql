CREATE TABLE Poll(
  id BIGSERIAL primary key,
  title varchar,
  description varchar,
  date timestamp
);

CREATE TABLE Choice(
  id BIGSERIAL primary key,
  text varchar,
  poll_id BIGINT REFERENCES Poll(id),
);

CREATE TABLE Voter(
  uuid UUID primary key,
);

CREATE TABLE Vote(
  id BIGSERIAL primary key,
  choice_id BIGINT REFERENCES Choice(id),
  voter_id UUID REFERENCES Voter(uuid)
);

