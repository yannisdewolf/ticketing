insert into groups (name) values ('developers');
insert into groups (name) values ('dba');
insert into groups (name) values ('marketing');
insert into groups (name) values ('finance');
insert into groups (name) values ('hr');


insert into users (email, first_name, last_name) values ('ednamode@mail.be', 'Edna', 'Mode');
insert into users (email, first_name, last_name) values ('RandleMcMurphy@mail.be', 'Randle', 'Mc Murphy');
insert into users (email, first_name, last_name) values ('normanbates@mail.be', 'Norman', 'Bates');
insert into users (email, first_name, last_name) values ('maximus@mail.be', 'maximus', 'Gladiator');
insert into users (email, first_name, last_name) values ('grommit@mail.be', 'Grommit', 'Dog');
insert into users (email, first_name, last_name) values ('jamesbond@mail.be', 'James', 'Bond');
insert into users (email, first_name, last_name) values ('hansolo@mail.be', 'Han', 'Solo');
insert into users (email, first_name, last_name) values ('indianajones@mail.be', 'Indiana', 'Jones');


insert into user_group(user_id, group_id)
values
((select id from users where first_name='Edna'), (select id from groups where name='developers')),
((select id from users where first_name='Randle'), (select id from groups where name='developers')),
((select id from users where first_name='Norman'), (select id from groups where name='developers')),
((select id from users where first_name='maximus'), (select id from groups where name='developers')),
((select id from users where first_name='maximus'), (select id from groups where name='dba')),
((select id from users where first_name='maximus'), (select id from groups where name='marketing')),
((select id from users where first_name='maximus'), (select id from groups where name='finance')),
((select id from users where first_name='maximus'), (select id from groups where name='hr')),
((select id from users where first_name='Grommit'), (select id from groups where name='dba')),
((select id from users where first_name='James'), (select id from groups where name='marketing')),
((select id from users where first_name='Han'), (select id from groups where name='finance')),
((select id from users where first_name='Indiana'), (select id from groups where name='hr'));


insert into projects(name)
values ('flyer1'), ('flyer2');
