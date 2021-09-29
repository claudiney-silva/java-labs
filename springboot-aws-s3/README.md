# Localstack

## Passo 0: Subir o localstack
```
docker-compose up -d
```

## Passo 1: Configurando o aws-cli
```
aws configure --profile "localstack"
```
- AWS Access Key ID: asdf (não é usado pelo localstack)
- AWS Secret Access Key: asdf (não é usado pelo localstack)
- Default region name: us-east-1
- Default output format: json

## Passo 2: Bucket S3

* Listar todos Buckets
```
aws s3 ls \
--endpoint-url http://localhost:4566 \
--profile localstack
```

* Criar Manualmente
```
aws s3 mb s3://effetivo-bucket \
--endpoint-url http://localhost:4566 \
--profile localstack

```

* Listar arquivos de um Bucket
```
aws s3 ls s3://effetivo-bucket \
--endpoint-url http://localhost:4566 \
--profile localstack 
```

* Enviar arquivo para um Bucket
```
aws s3 cp logo.png s3://mybucket/logo.png \
--endpoint-url http://localhost:4566 \
--profile localstack 
```