#!/bin/bash

set -euo pipefail

# enable debug
# set -x

echo "configuring sqs"
echo "==================="
LOCALSTACK_HOST=localhost
AWS_REGION=us-east-1

# Criando uma fila 
awslocal --endpoint-url=http://${LOCALSTACK_HOST}:4566 sqs create-queue --queue-name "catalog-update" --region ${AWS_REGION} --attributes VisibilityTimeout=30
# Criando um topico
awslocal --endpoint-url=http://${LOCALSTACK_HOST}:4566 sns create-topic --name "catalog-emit" --region ${AWS_REGION}
# Trabalhando com assinaturas SQS para SNS
awslocal sns subscribe --topic-arn "arn:aws:sns:us-east-1:000000000000:catalog-emit" --protocol sqs --notification-endpoint "arn:aws:sqs:us-east-1:000000000000:catalog-update"

# Criando usuario 
awslocal iam create-user --user-name local_user_aws
awslocal iam create-access-key --user-name local_user_aws
