# language: pt

Funcionalidade: buscar um contato por id

  Cenário: O usuário deseja ver dados de um contato especifico
    Dado contato salvo no banco de dados
    Quando o usuário realiza a requisição para a busca de contato
    Então o sistema deve retorna o usuário cadastrado no banco de dados

  Cenário: O usuário deseja ver dados de um contato inexistente
    Quando o usuário realiza a requisição para a busca de contato
    Então o sistema deve retornar mensagem de erro dizendo que o contato não existe