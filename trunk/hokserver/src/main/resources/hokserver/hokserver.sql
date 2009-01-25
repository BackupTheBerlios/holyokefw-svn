-- pg_dump --disable-dollar-quoting -s -x -U postgres hokconfig >~/tmp/x.sql

--
-- PostgreSQL database dump
--

--SET client_encoding = 'UTF8';
--SET standard_conforming_strings = off;
--SET check_function_bodies = false;
--SET client_min_messages = warning;
--SET escape_string_warning = off;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

--COMMENT ON SCHEMA public IS 'Standard public schema';


--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

--CREATE PROCEDURAL LANGUAGE plpgsql;


--SET search_path = public, pg_catalog;

--
-- Name: r_configs_get(character varying, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION r_configs_get(_rsname character varying, _appname character varying, _custname character varying, _username character varying, _password_md5 character varying) RETURNS refcursor
    AS '
DECLARE
	rc REFCURSOR;
BEGIN
-- Sample of creating a small database and then querying it:
-- select w_apps_del(''oa'');
-- select w_apps_add(''oa'');
-- select w_apps_addvers(''oa'', ''1.8'', ''http://oa.org/1.8'', false);
-- select w_apps_addvers(''oa'', ''1.9'', ''http://oa.org/1.9'', true);
-- select w_custs_add(''droxbury'');
-- select w_apps_addcust(''oa'', ''droxbury'', ''1.8'');
-- select w_users_add(''oa'', ''droxbury'', ''citibob'', ''password'');
--
-- select r_configs_get(''rs1'', ''oa'',''droxbury'',''citibob'',md5(''password''));
-- fetch all in rs1;

rc=_rsname;
OPEN rc for

select
apps.config as apps_config,
	apps.defaultversion,
   apps.name as apps_name,
av.version,
case when apps.defaultversion = av.version then 1 else 0 end as priority,
av.url,
av.config as app_vers_config,
custs.name as custs_name,
custs.config as custs_config,
ac.config as app_custs_config,
users.config as users_config,
users.name as users_name
from apps
inner join custs on (custs.name = _custname)
inner join users on (users.name = _username
	and users.appid = apps.appid
	and users.custid = custs.custid)
inner join app_custs ac on (
	ac.appid = apps.appid
	and ac.custid = custs.custid)
inner join app_vers av on (
	av.appid = apps.appid
	and (av.version = ac.version or av.version = apps.defaultversion))
where apps.name = _appname
and users.password_md5 = _password_md5
order by priority;

return rc;

END;
'
    LANGUAGE plpgsql;


--
-- Name: w_apps_add(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION w_apps_add(_name character varying) RETURNS integer
    AS '
BEGIN
    BEGIN
	insert into apps (name) values (_name);
	return currval(''apps_appid_seq'');
    EXCEPTION WHEN unique_violation THEN
            -- do nothing
            return -1;
    END;
END;
'
    LANGUAGE plpgsql;


--
-- Name: w_apps_addcust(character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION w_apps_addcust(_appname character varying, _custname character varying, _version character varying) RETURNS boolean
    AS '
BEGIN
    BEGIN
	insert into app_custs(appid, custid, "version") values (
	(select appid from apps where name = _appname),
	(select custid from custs where name = _custname),
	_version);
	return true;
    EXCEPTION WHEN unique_violation THEN
	update app_custs set "version"=_version
	where appid = (select appid from apps where name = _appname)
	and custid = (select custid from custs where name = _custname);
	return false;
    END;
END;
'
    LANGUAGE plpgsql;



--
-- Name: w_apps_addvers(character varying, character varying, character varying, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION w_apps_addvers(_appname character varying, _version character varying, _url character varying, _isdefault boolean) RETURNS boolean
    AS '
DECLARE
	ret bool;
BEGIN
    BEGIN
	insert into app_vers(appid, "version", url) values (
	(select appid from apps where name = _appname),
	_version, _url);
	ret = true;
    EXCEPTION WHEN unique_violation THEN
	update app_vers set url=_url
	where appid = (select appid from apps where name = _appname)
	and "version" = _version;
	ret = false;
    END;

    if _isdefault then
	update apps set defaultversion=_version
	where appid = (select appid from apps where name = _appname);
    end if;
    return ret;
END;
'
    LANGUAGE plpgsql;



--
-- Name: w_apps_del(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION w_apps_del(_appname character varying) RETURNS void
    AS '
BEGIN
	delete from app_custs
	where appid = (select appid from apps where name = _appname);

	delete from app_vers
	where appid = (select appid from apps where name = _appname);

	delete from users
	where appid = (select appid from apps where name = _appname);

	delete from apps where name = _appname;
END;
'
    LANGUAGE plpgsql;



--
-- Name: w_apps_delcust(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION w_apps_delcust(_appname character varying, _custname character varying) RETURNS void
    AS '
BEGIN
	delete from app_custs
	where appid = (select appid from apps where name = _appname)
	and custid = (select custid from custs where name = _custname);
	
END;
'
    LANGUAGE plpgsql;



--
-- Name: w_apps_delvers(character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION w_apps_delvers(_appname character varying, _version character varying) RETURNS void
    AS '
BEGIN
	delete from app_vers
	where appid = (select appid from apps where name = _appname);

	update app_custs set version = null
	where appid = (select appid from apps where name = _appname)
	and "version" = _version;
	
	update apps set defaultversion = null
	where appid = (select appid from apps where name = _appname)
	and "defaultversion" = _version;
	
END;
'
    LANGUAGE plpgsql;



--
-- Name: w_custs_add(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION w_custs_add(_name character varying) RETURNS integer
    AS '
BEGIN
    BEGIN
	insert into custs (name) values (_name);
	return currval(''custs_custid_seq'');
    EXCEPTION WHEN unique_violation THEN
            -- do nothing
            return -1;
    END;
END;
'
    LANGUAGE plpgsql;



--
-- Name: w_custs_del(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION w_custs_del(_custname character varying) RETURNS void
    AS '
BEGIN
	delete from users
	where custid = (select custid from custs where name = _custname);

	delete from app_custs
	where custid = (select custid from custs where name = _custname);

	delete from custs
	where name = _custname;
END;
'
    LANGUAGE plpgsql;



--
-- Name: w_users_add(character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION w_users_add(_appname character varying, _custname character varying, _name character varying, _password character varying) RETURNS boolean
    AS '
BEGIN
    BEGIN
	insert into users (appid, custid, name, password_md5) values (
	(select appid from apps where name = _appname),
	(select custid from custs where name = _custname),
	_name, md5(_password));
	return true;
    EXCEPTION WHEN unique_violation THEN
	update users set password_md5 = md5(_password)
	where appid = (select appid from apps where name = _appname)
	and custid = (select custid from custs where name = _custname);
            return false;
    END;
END;
'
    LANGUAGE plpgsql;



--
-- Name: w_users_del(character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION w_users_del(_appname character varying, _custname character varying, _name character varying) RETURNS void
    AS '
BEGIN
	delete from users
	where appid = (select appid from apps where name = _appname)
	and custid = (select custid from custs where name = _custname)
	and name = _name;
END;
'
    LANGUAGE plpgsql;



SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: app_custs; Type: TABLE; Schema: public; Owner: citibob; Tablespace: 
--

CREATE TABLE app_custs (
    appid integer NOT NULL,
    custid integer NOT NULL,
    version character varying(30),
    config bytea,
    configgmt timestamp without time zone
);



--
-- Name: app_vers; Type: TABLE; Schema: public; Owner: citibob; Tablespace: 
--

CREATE TABLE app_vers (
    appid integer NOT NULL,
    version character varying(30) NOT NULL,
    url character varying(200) NOT NULL,
    config bytea,
    configgmt timestamp without time zone
);



--
-- Name: apps; Type: TABLE; Schema: public; Owner: citibob; Tablespace: 
--

CREATE TABLE apps (
    appid integer NOT NULL,
    name character varying(100) NOT NULL,
    defaultversion character varying(30),
    config bytea,
    configgmt timestamp without time zone
);



--
-- Name: apps_appid_seq; Type: SEQUENCE; Schema: public; Owner: citibob
--

CREATE SEQUENCE apps_appid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



--
-- Name: apps_appid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: citibob
--

ALTER SEQUENCE apps_appid_seq OWNED BY apps.appid;


--
-- Name: custs; Type: TABLE; Schema: public; Owner: citibob; Tablespace: 
--

CREATE TABLE custs (
    custid integer NOT NULL,
    name character varying(100) NOT NULL,
    config bytea,
    configgmt timestamp without time zone
);



--
-- Name: custs_custid_seq; Type: SEQUENCE; Schema: public; Owner: citibob
--

CREATE SEQUENCE custs_custid_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;



--
-- Name: custs_custid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: citibob
--

ALTER SEQUENCE custs_custid_seq OWNED BY custs.custid;


--
-- Name: users; Type: TABLE; Schema: public; Owner: citibob; Tablespace: 
--

CREATE TABLE users (
    appid integer NOT NULL,
    custid integer NOT NULL,
    name character varying(100) NOT NULL,
    password_md5 character varying(100) NOT NULL,
    config bytea,
    configgmt timestamp without time zone
);



--
-- Name: appid; Type: DEFAULT; Schema: public; Owner: citibob
--

ALTER TABLE apps ALTER COLUMN appid SET DEFAULT nextval('apps_appid_seq'::regclass);


--
-- Name: custid; Type: DEFAULT; Schema: public; Owner: citibob
--

ALTER TABLE custs ALTER COLUMN custid SET DEFAULT nextval('custs_custid_seq'::regclass);


--
-- Name: app_custs_pkey; Type: CONSTRAINT; Schema: public; Owner: citibob; Tablespace: 
--

ALTER TABLE ONLY app_custs
    ADD CONSTRAINT app_custs_pkey PRIMARY KEY (appid, custid);


--
-- Name: app_vers_pkey; Type: CONSTRAINT; Schema: public; Owner: citibob; Tablespace: 
--

ALTER TABLE ONLY app_vers
    ADD CONSTRAINT app_vers_pkey PRIMARY KEY (appid, version);


--
-- Name: apps_name_key; Type: CONSTRAINT; Schema: public; Owner: citibob; Tablespace: 
--

ALTER TABLE ONLY apps
    ADD CONSTRAINT apps_name_key UNIQUE (name);


--
-- Name: apps_pkey; Type: CONSTRAINT; Schema: public; Owner: citibob; Tablespace: 
--

ALTER TABLE ONLY apps
    ADD CONSTRAINT apps_pkey PRIMARY KEY (appid);


--
-- Name: custs_name_key; Type: CONSTRAINT; Schema: public; Owner: citibob; Tablespace: 
--

ALTER TABLE ONLY custs
    ADD CONSTRAINT custs_name_key UNIQUE (name);


--
-- Name: custs_pkey; Type: CONSTRAINT; Schema: public; Owner: citibob; Tablespace: 
--

ALTER TABLE ONLY custs
    ADD CONSTRAINT custs_pkey PRIMARY KEY (custid);


--
-- Name: users_appid_key; Type: CONSTRAINT; Schema: public; Owner: citibob; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_appid_key UNIQUE (appid, custid, name);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: citibob; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (appid, custid, name);


--
-- PostgreSQL database dump complete
--



-- Function: r_configs_get(character varying, character varying, character varying, character varying, character varying)

DROP FUNCTION r_configs_get(character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION r_configs_get(_rsname character varying, _appname character varying, _custname character varying)
  RETURNS refcursor AS
'
DECLARE
	rc REFCURSOR;
BEGIN
-- Sample of creating a small database and then querying it:
-- select w_apps_del('oa');
-- select w_apps_add('oa');
-- select w_apps_addvers('oa', '1.8', 'http://oa.org/1.8', false);
-- select w_apps_addvers('oa', '1.9', 'http://oa.org/1.9', true);
-- select w_custs_add('droxbury');
-- select w_apps_addcust('oa', 'droxbury', '1.8');
-- select w_users_add('oa', 'droxbury', 'citibob', 'password');
--
-- select r_configs_get('rs1', 'oa','droxbury');
-- fetch all in rs1;

rc=_rsname;
OPEN rc for

select
apps.config as apps_config,
	apps.defaultversion,
   apps.name as apps_name,
av.version,
case when apps.defaultversion = av.version then 1 else 0 end as priority,
av.url,
av.config as app_vers_config,
custs.name as custs_name,
custs.config as custs_config,
ac.config as app_custs_config
from apps
inner join custs on (custs.name = _custname)
inner join app_custs ac on (
	ac.appid = apps.appid
	and ac.custid = custs.custid)
inner join app_vers av on (
	av.appid = apps.appid
	and (av.version = ac.version or av.version = apps.defaultversion))
where apps.name = _appname
order by priority;

return rc;

END;
'
  LANGUAGE 'plpgsql' VOLATILE;

