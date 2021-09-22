# Comandos

## Criando Consumer

Executar na pasta bin

```
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic ticket --group grupo01
```

## Criando Producer

Executar na pasta bin

```
./kafka-console-producer.sh --broker-list localhost:9092 --topic ticket
```

## Visualizar Logs

A partir do docker-compose

```
docker-compose logs zookeeper | grep -i binding
```

```
docker-compose logs kafka | grep -i binding
```

## Criando um Tópico

```
docker-compose exec kafka \kafka-topics --create --topic my-topic --partitions 1 --replication-factor 1 --if-not-exists --zookeeper localhost:2181
```

## Confirmando Tópico Criado

```
docker-compose exec kafka \kafka-topics --describe --topic my-topic --zookeeper localhost:2181
```

## Produzindo Mensagens

```
docker-compose exec kafka \bash -c "seq 100 | kafka-console-producer --request-required-acks 1 --broker-list localhost:9092 --topic my-topic && echo 'Produced 100 messages.'"
```

## Consumindo Mensagens

```
docker-compose exec kafka \kafka-console-consumer --bootstrap-server localhost:29092 --topic my-topic --from-beginning --max-messages 100
```
