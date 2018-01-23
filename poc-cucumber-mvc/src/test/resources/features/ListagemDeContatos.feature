#language: pt
Funcionalidade: listagem de contatos
  Cenário: O usuário deseja ver todos os contatos salvos no sistema
    Dado contatos salvo no banco de dados
    Quando o usuário realiza a requisição para a listagem de contatos
    Então o sistema deve retornar todos os contatos cadastrados
