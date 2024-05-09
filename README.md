# desafio-anotaai-backend-with-java

Esse Repositorio e a implementalção do desafio - https://github.com/githubanotaai/new-test-backend-nodejs/blob/e718f4cdfe1c7a4ce7802d9abcd79d3e327534ff/README.md

### Como rodar o projeto

Para iniciar a infra estrutura basta rodar o comando

```shell
# Iniciar mongodb + localstack
$ docker compose -f ./infra/compose.yml up -d

# Instala awscli-local
$ pip install awscli-local

# Instala awscli-local
$ pip install awscli

$ cd desafio-anota-ai/infra/aws

# Adiciona permissão para rodar o script
chmod +x ./run.sh

# Rodar script para criar ambiente(sqs, sns, s3, lambda) no localstack
$ ./run.sh
...
{
    "AccessKey": {
        "UserName": "local_user_aws",
        "AccessKeyId": "LKIAQAAAAAAALMWXT3XB",
        "Status": "Active",
        "SecretAccessKey": "bND9Ap4HJvwbsS9Aeh8AeHd3MITo/Fe5+VO9cEvJ",
        "CreateDate": "2024-04-27T20:54:36Z"
    }
}
...
```

> Após rodar o script para criar o ambiente no localstack é necessario copiar `AccesskeyId` e `SecretAccessKey` e copiar para application.properties

### Acesso

#### Localstack

para acessar o painel do localstack e visualizar os logs da `lambda` ou o arquivo no `s3` basta acessar `https://app.localstack.cloud`

#### Endpoints

Host: `http://localhost:8080`

- Listar Categorias `GET /api/v1/category`
- Criar Categoria `POST /api/v1/category`
- Atualizar Categoria `PUT /api/v1/category/{id}`
- Deletar Categoria `DELETE /api/v1/category/{id}`

- Listar Produtos `GET /api/v1/product`
- Criar Produto `POST /api/v1/product`
- Atualizar Produto `PUT /api/v1/product/{id}`
- Deletar Produto `DELETE /api/v1/product/{id}`
