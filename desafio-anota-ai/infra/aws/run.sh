#!/bin/bash

set -euo pipefail

# enable debug
# set -x

LOCALSTACK_HOST=localhost
AWS_REGION=us-east-1

echo "==================================="
echo "Configurando ambiente" 
echo "HOST: ${LOCALSTACK_HOST}"
echo "REGION: ${AWS_REGION}"
echo "==================================="

# Criando uma fila 
awslocal --endpoint-url=http://${LOCALSTACK_HOST}:4566 sqs create-queue --queue-name "catalog-update" --region ${AWS_REGION} --attributes VisibilityTimeout=30
# Criando um topico
awslocal --endpoint-url=http://${LOCALSTACK_HOST}:4566 sns create-topic --name "catalog-emit" --region ${AWS_REGION}
# Trabalhando com assinaturas SQS para SNS
awslocal sns subscribe --topic-arn "arn:aws:sns:${AWS_REGION}:000000000000:catalog-emit" --protocol sqs --notification-endpoint "arn:aws:sqs:${AWS_REGION}:000000000000:catalog-update"

# Criando usuario 
awslocal iam create-user --user-name local_user_aws
awslocal iam create-access-key --user-name local_user_aws

# Criando lambda
zip function.zip index.mjs
awslocal lambda create-function --function-name catalogEmitConsumer --runtime nodejs20.x --zip-file fileb://function.zip --handler index.handler --role arn:aws:iam::000000000000:role/lambda-role
awslocal lambda create-event-source-mapping --function-name catalogEmitConsumer --event-source-arn arn:aws:sqs:${AWS_REGION}:000000000000:catalog-update --batch-size 10 --enabled

awslocal s3api create-bucket --bucket anotaai-catalog-marketplace
rm function.zip