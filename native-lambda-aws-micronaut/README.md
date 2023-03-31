## Criando a base image
```shell
cd BaseBuildImage
docker build . -t basebuildimage
```

## Executando o build nativo com a base image
```shell
docker run -it -v /opt/apache-maven-3.8.6:/usr/lib/maven -v ~/.m2:/root/.m2 -v $(pwd):/project --rm --entrypoint ./build.sh basebuildimage:latest
```

## Executando build a partir de dockerfile
```shell
mvn clean package -Dpackaging=docker
docker create --name myfunc native-lambda-aws-micronaut:latest
docker cp myfunc:/home/app/function.zip ./target/function.zip
docker rm -f myfunc
docker image rm native-lambda-aws-micronaut:latest
```