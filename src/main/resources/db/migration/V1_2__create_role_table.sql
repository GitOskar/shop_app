CREATE SEQUENCE IF NOT EXISTS public.role_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.role
(
    id bigint NOT NULL DEFAULT nextval('role_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT role_pkey PRIMARY KEY (id),
    CONSTRAINT uk_8sewwnpamngi6b1dwaa88askk UNIQUE (name)
);