# desafio-anotaai-backend-with-java
Esse Repositorio e a implementalção do desafio - https://github.com/githubanotaai/new-test-backend-nodejs/blob/e718f4cdfe1c7a4ce7802d9abcd79d3e327534ff/README.md


### Como rodar o projeto

Para iniciar a infra estrutura basta rodar o comando
```shell
# Iniciar mongodb + mongo-express
$ docker compose -f infra/compose.yml up -d
```
para acessar o Mongo Express basta acessar `http://localhost:8081/` e informar o usuario e senha configurado no `infra/compose.yml`