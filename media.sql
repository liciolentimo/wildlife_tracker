--
-- PostgreSQL database dump
--

-- Dumped from database version 10.6 (Ubuntu 10.6-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 10.6 (Ubuntu 10.6-0ubuntu0.18.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: animals; Type: TABLE; Schema: public; Owner: licio
--

CREATE TABLE public.animals (
    id integer NOT NULL,
    name character varying,
    age character varying,
    health character varying,
    endangered boolean DEFAULT false,
    species character varying
);


ALTER TABLE public.animals OWNER TO licio;

--
-- Name: animals_id_seq; Type: SEQUENCE; Schema: public; Owner: licio
--

CREATE SEQUENCE public.animals_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.animals_id_seq OWNER TO licio;

--
-- Name: animals_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: licio
--

ALTER SEQUENCE public.animals_id_seq OWNED BY public.animals.id;


--
-- Name: animals_sightings; Type: TABLE; Schema: public; Owner: licio
--

CREATE TABLE public.animals_sightings (
    animal_id integer,
    sighting_id integer
);


ALTER TABLE public.animals_sightings OWNER TO licio;

--
-- Name: sightings; Type: TABLE; Schema: public; Owner: licio
--

CREATE TABLE public.sightings (
    id integer NOT NULL,
    location character varying,
    spotted timestamp without time zone,
    rangername character varying
);


ALTER TABLE public.sightings OWNER TO licio;

--
-- Name: sightings_id_seq; Type: SEQUENCE; Schema: public; Owner: licio
--

CREATE SEQUENCE public.sightings_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sightings_id_seq OWNER TO licio;

--
-- Name: sightings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: licio
--

ALTER SEQUENCE public.sightings_id_seq OWNED BY public.sightings.id;


--
-- Name: animals id; Type: DEFAULT; Schema: public; Owner: licio
--

ALTER TABLE ONLY public.animals ALTER COLUMN id SET DEFAULT nextval('public.animals_id_seq'::regclass);


--
-- Name: sightings id; Type: DEFAULT; Schema: public; Owner: licio
--

ALTER TABLE ONLY public.sightings ALTER COLUMN id SET DEFAULT nextval('public.sightings_id_seq'::regclass);


--
-- Data for Name: animals; Type: TABLE DATA; Schema: public; Owner: licio
--

COPY public.animals (id, name, age, health, endangered, species) FROM stdin;
6		\N	\N	f	
16	lion	newborn	healthy	t	cat
17	elephant	\N	\N	f	mammal
18	tiger	young	okay	t	cat
\.


--
-- Data for Name: animals_sightings; Type: TABLE DATA; Schema: public; Owner: licio
--

COPY public.animals_sightings (animal_id, sighting_id) FROM stdin;
16	16
\.


--
-- Data for Name: sightings; Type: TABLE DATA; Schema: public; Owner: licio
--

COPY public.sightings (id, location, spotted, rangername) FROM stdin;
16	Zone A	2019-02-17 10:03:00.104519	rang
\.


--
-- Name: animals_id_seq; Type: SEQUENCE SET; Schema: public; Owner: licio
--

SELECT pg_catalog.setval('public.animals_id_seq', 18, true);


--
-- Name: sightings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: licio
--

SELECT pg_catalog.setval('public.sightings_id_seq', 16, true);


--
-- Name: animals animals_pkey; Type: CONSTRAINT; Schema: public; Owner: licio
--

ALTER TABLE ONLY public.animals
    ADD CONSTRAINT animals_pkey PRIMARY KEY (id);


--
-- Name: sightings sightings_pkey; Type: CONSTRAINT; Schema: public; Owner: licio
--

ALTER TABLE ONLY public.sightings
    ADD CONSTRAINT sightings_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

