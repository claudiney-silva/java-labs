## Steps

### Criando o projeto

```
mvn archetype:generate \
       -DarchetypeGroupId=io.quarkus \
       -DarchetypeArtifactId=quarkus-amazon-lambda-http-archetype \
       -DarchetypeVersion=2.10.1.Final
```

### Package

```
mvn clean package
```

### Sam package

```
sam package --template-file target/sam.jvm.yaml --output-template-file packaged.yaml --s3-bucket effetivo-lambda
```

### Sam deploy

```
sam deploy --template-file packaged.yaml --capabilities CAPABILITY_IAM --stack-name stack-lambda-quarkus-http
```