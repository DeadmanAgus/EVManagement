insert into ev(id, model, make, battery_capacity) values(11001, 'Model1', 1997, 11.5);
insert into ev(id, model, make, battery_capacity) values(11002, 'Model2', 1998, 12.5);
insert into ev(id, model, make, battery_capacity) values(11003, 'Model3', 1999, 13.5);

insert into user(id, name, address) values(10001, 'Tom Well', '5th Avenue');
insert into user(id, name, address) values(10002, 'Jack Being', '6th Avenue');
insert into user(id, name, address) values(10003, 'Jill String', '7th Avenue');

insert into user_ev(ev_id, user_id) values(11002, 10001);
insert into user_ev(ev_id, user_id) values(11001, 10002);
insert into user_ev(ev_id, user_id) values(11002, 10002);