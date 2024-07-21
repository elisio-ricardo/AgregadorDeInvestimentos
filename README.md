# Agregador De Investimentos

## json criação user 
{
"username": "ricardo",
"email": "ricardo@gmail.com",
"password": "456"
}

## Post -> localhost:8080/v1/users/{userId}/accounts
json criação Accounts
{
"description": "Conta de investimentos",
"street": "rua",
"number": 500
}

## GET -> localhost:8080/v1/users/{userId}/accounts

## Post -> localhost:8080/v1/stock

{
"stockId": "PETR4", 
"description": "Petrobras"
}


## Post -> localhost:8080/v1/accounts/{accountId}/stocks
gera a quantidade de ações disponiveis
{
"stockId": "PETR4", 
"quantity": 100
}

## GET -> localhost:8080/v1/accounts/{accountId}/stocks


## Será necessario criar uma conta no site brapi e pegar o token,para fazer chamadas feing
