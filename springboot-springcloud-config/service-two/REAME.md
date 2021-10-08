## Service-One

Esta app utiliza o config server.

Caso haja uma alteração nas configurações, basta executar o comando abaixo para atualizar:

```
curl -u serviceOneUser:serviceOnePassword -X POST http://localhost:8001/actuator/refresh
```