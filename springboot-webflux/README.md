# Webflux

Este projeto demonstra uma api com WebFlux.

Para execução do projeto deverão ser executadas as SQL abaixo no banco de dados:

```
CREATE SEQUENCE app_user_id_seq;
```
```
CREATE TABLE app_user (
    id int NOT NULL DEFAULT nextval('app_user_id_seq'),
    name varchar(255)
);
```

```
ALTER SEQUENCE app_user_id_seq OWNED BY app_user.id;
```

```
INSERT INTO app_user(name) VALUES ('John');
```

```
INSERT INTO app_user(name) VALUES ('David');
```

```
CREATE SEQUENCE app_auth_id_seq;
```
```
CREATE TABLE app_auth (
id int NOT NULL DEFAULT nextval('app_auth_id_seq'),
name varchar(125),
username varchar(125),
password varchar(255),
authorities varchar(255)
);
```

```
ALTER SEQUENCE app_auth_id_seq OWNED BY app_auth.id;
```

* Inserindo dois usuários com a senha: "password"
```
INSERT INTO app_auth(name, username, password, authorities) VALUES ('John', 'john', '{bcrypt}$2a$10$Dbpu8cknlimDC2k5m2OEdut/9fgEfHvY8APvNAzJlQtrar0pljujW', 'ROLE_USER,ROLE_ADMIN');
```

```
INSERT INTO app_auth(name, username, password, authorities) VALUES ('David', 'david', '{bcrypt}$2a$10$Dbpu8cknlimDC2k5m2OEdut/9fgEfHvY8APvNAzJlQtrar0pljujW', 'ROLE_USER');
```
