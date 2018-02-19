#language: pt
Funcionalidade: Cadastro de contato
  Cenário: dados invalidos para cadastro de contatos
    Dado que exista um contato sem todos os dados obrigatórios preenchidos
    Quando for feita a requisição para o cadastro de um contato
    Então o sistema deve retornar erros de validação para o cadastro de contato
  Cenário: dados validos para cadastro de contatos
    Dado que exista um contato com todos os dados obrigatórios preenchidos
    Quando for feita a requisição para o cadastro de um contato
    Então o sistema deve cadastrar o contato com sucesso