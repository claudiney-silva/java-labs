# java-springboot-keycloak

Projeto utilizando oauth2 com keycloak

## Endpoints do keycloak

http://localhost:8082/auth/realms/demo/.well-known/openid-configuration

## Configurações do keycloak

- Criar um realm: demo
- Criar as roles: ROLE_USER e ROLE_ADMIN
- Criar um usuário: demo, atribuir uma senha e remover a opção Update Password do Required User Actions
  - Atribuir as roles ROLE_USER e ROLE_ADMIN para este usuário
- Criar um client scope: demo, com protocol openid-connect
  - Criar um mapper com o nome: authorities, mapper type: User Realm Role e Token Clain Name: authorities
- Criar um client: demo, com o protocol: openid-connect e access type: confidential.
  - Adicionar o scope demo

## Gerando o token do keycloak

### Postman - oauth2

- Grant Type: Password credentials
- Access Token URL: http://localhost:8082/auth/realms/demo/protocol/openid-connect/token
- Client ID: demo
- Client Secret: <secret do client gerado no keycloak>
- Username: demo
- Password: <senha do usuário>
- Scope: demo
- Client Authentication: Send client credentials in body
