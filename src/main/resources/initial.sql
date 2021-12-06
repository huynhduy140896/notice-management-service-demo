insert into role (name, description) values 
('ADMIN', 'use for admin only'), 
('USER', 'use for user only');

insert into users (username, password, first_name, last_name, email) values 
('peter','$2a$10$Xtnu1GESoqCJSVn4AT45a.5DJ6xcePYQeIVQSbbfL/GvJo/dkuTES', 'Duy', 'Huynh', 'duy123@yahoo.com');

insert into user_roles (user_id, role_id) values (1,1);