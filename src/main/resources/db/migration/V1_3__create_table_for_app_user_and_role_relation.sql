CREATE TABLE IF NOT EXISTS public.app_user_roles
(
    app_user_id bigint NOT NULL,
    roles_id bigint NOT NULL,
    CONSTRAINT app_user_roles_pkey PRIMARY KEY (app_user_id, roles_id),
    CONSTRAINT fk23e7b5jyl3ql41rk3566gywdd FOREIGN KEY (roles_id)
    REFERENCES public.role (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fkkwxexnudtp5gmt82j0qtytnoe FOREIGN KEY (app_user_id)
    REFERENCES public.app_user (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
)