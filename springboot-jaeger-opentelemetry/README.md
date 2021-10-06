Suba o container docker do Jaeger
```
docker-compose up -d
```


Faça o download do .jar e copie na raiz do projeto
```
https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent-all.jar
```

Gere o .jar deste projeto
```
mvn clean package
```

Execute a aplicação com o agent
```
sh run.sh
```

Abra o terminal para gerar os eventos
```
http://localhost:8888/sample/NOME
```

Visualize o tracing no Jaeger
```
http://localhost:16686
```