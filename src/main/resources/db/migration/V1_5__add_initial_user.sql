INSERT INTO public.app_user(id, created_date, last_modified_date, password_hash, username)
VALUES (nextval('app_user_id_seq'), null, null, '$2a$10$2EXI/kPNLPISa2lUiIzGV.ljdU1VDUdyVKSTz8aSaLsziwJwmkdPm', 'admin');
-- username: admin
-- password: admin

INSERT INTO public.app_user_roles(app_user_id, roles_id)
VALUES((select id from app_user where username = 'admin'), (select id from role where name = 'ADMIN'));

INSERT INTO public.app_user_roles(app_user_id, roles_id)
VALUES((select id from app_user where username = 'admin'), (select id from role where name = 'EMPLOYEE'));