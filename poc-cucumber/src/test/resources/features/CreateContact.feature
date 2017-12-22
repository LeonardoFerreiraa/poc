# language: pt
Funcionalidade: Cadastro de contatos

  Cenário: O usuário deseja cadatrar um usuário com dados validos
    Dado dados validos para cadastro de contato
    Quando o usuário faz uma requisição para cadastro de contato
    Então o contato é salvo no banco de dados

  Cenário: O usuário entra com dados invalidos para cadastro de um contato
    Dado dados invalidos para cadastro de contato
    Quando o usuário faz uma requisição para cadastro de contato
    Então o sistema deve retornar erros de validacao para o cadastro de contato