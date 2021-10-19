
## Utilizando o okta cli, vamos criar uma app
```
okta login
okta apps create
```
* Escolher Web <ENTER>
* Escolher Other, e informar Redirect URL: [http://localhost:8001/login/oauth2/code/okta,http://localhost:8002/login/oauth2/code/okta]
* Informar para logout redirect: [http://localhost:8001,http://localhost:8002]

Para visualizar o client_id e o secret
```
cat .okta.env
```

As configurações devem ser feitas na pasta "./config", mas caso queira-se
manter os arquivos de configuração no github ou bucket s3, deve-se acessar
[este link](https://cloud.spring.io/spring-cloud-config/reference/html/#_git_backend) para mais instruções.