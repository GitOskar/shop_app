CREATE SEQUENCE IF NOT EXISTS public.app_user_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.app_user
(
    id bigint NOT NULL DEFAULT nextval('app_user_id_seq'::regclass),
    created_date timestamp without time zone,
    last_modified_date timestamp without time zone,
    password_hash character varying(255) COLLATE pg_catalog."default",
    username character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT app_user_pkey PRIMARY KEY (id),
    CONSTRAINT uk_3k4cplvh82srueuttfkwnylq0 UNIQUE (username)
);
