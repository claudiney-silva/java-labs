# Passos para Executar

## Subindo Cassandra no Docker

```
docker-compose up
```

## Criando

Liste os containers e copie o ID do Cassandra

```
docker ps
```

Entrando no bash do container

```
docker exec -it <ID_CONTAINER> bash
```

## Entrando no Cqlsh

```
cqlsh
```

## Criando um keyspace

```
CREATE KEYSPACE mykeyspace WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};
```

## Selecionando o keyspace

```
use mykeyspace;
```

## Criando uma tabela com Cqlsh

```
CREATE TABLE Product( id bigint PRIMARY KEY, name text);
```

## Listando todos keyspaces

```
describe keyspaces;
```

## Listando todas tabelas

```
describe tables;
```

## Detalhes da tabela

```
describe table product;
```

## Listando todos Produtos

```
select * from product;
```
