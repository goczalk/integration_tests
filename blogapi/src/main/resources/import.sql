--this script initiates db for h2 db (used in test profile)
insert into user (id, account_status, email, first_name, last_name) values (null, 'CONFIRMED', 'john@domain.com', 'John', 'Steward')
insert into user (id, account_status, email, first_name) values (null, 'NEW', 'brian@domain.com', 'Brian')
insert into user (id, account_status, email, first_name) values (null, 'CONFIRMED', 'anna@domain.com', 'Anna')
insert into user (id, account_status, email, first_name) values (null, 'REMOVED', 'klaudia@domain.com', 'Klaudia')

insert into blog_post values (null, 'Test blog post created by confirmed user', 1)
insert into blog_post values (null, 'Secong test blog post created by confirmed user', 1)