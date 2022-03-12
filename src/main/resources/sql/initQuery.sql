CREATE TABLE "ingredients" (id serial primary key,
name character varying NOT NULL,
price double precision NOT NULL DEFAULT 0,
count integer NOT NULL DEFAULT 0,
CONSTRAINT uniq_ingredients_name UNIQUE (name));

CREATE TABLE "categories" (id serial primary key,
name character varying NOT NULL,
CONSTRAINT uniq_categories_name UNIQUE (name));

CREATE TABLE "food&drinks" (id serial primary key,
category_id integer NOT NULL REFERENCES categories (id),
name character varying NOT NULL);

CREATE TABLE "recipes" (id serial primary key,
"food&drinks_id" integer NOT NULL REFERENCES "food&drinks"(id),
ingredients_id integer NOT NULL REFERENCES ingredients(id),
weight integer);

CREATE TABLE "menus" (id serial primary key,
name character varying NOT NULL,
CONSTRAINT uniq_menus_name UNIQUE(name));

CREATE TABLE "menu_elements" (id serial primary key,
"food&drinks_id" integer NOT NULL REFERENCES "food&drinks"(id),
menus_id integer NOT NULL REFERENCES menus(id));

CREATE TABLE "proposals" (id serial primary key,
date timestamp without time zone);

CREATE TABLE "proposal_elements" (id SERIAL PRIMARY KEY,
proposals_id integer NOT NULL REFERENCES proposals(id),
menus_id integer NOT NULL REFERENCES menus(id));