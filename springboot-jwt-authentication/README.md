# JWT Authentication
Esta é uma aplicação simples demonstrando o uso de Jwt como 
camada de segurança. Além disso na implementação utilizamos
Postgres, Liquibase e algumas ferramentas de observability,
como: Prometheus, Grafana e Splunk.

## Alguns comandos referência

Deletar todos containers
```
docker rm -f $(docker ps -a -q)
```

Delete todos volumes
```
docker volume rm $(docker volume ls -q)
```

Gerar a imagem Docker localmente
```
mvn compile jib:dockerBuild
```

Gerar a image Docker e fazer o push
```
mvn compile jib:docker -Ddocker.registry.username=effetivo -Ddocker.registry.password=YOUR-PASSWORD
```

ou 

```
mvn install -Ddocker.registry.username=effetivo -Ddocker.registry.password=YOUR-PASSWORD
```