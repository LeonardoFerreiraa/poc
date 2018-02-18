# language: pt
Funcionalidade: remoção de contato

  Cenário: o usuário deve ser capaz de remover contatos
    Dado um contato já salvo no banco de dados pronto para remoção
    Quando é feita a requisição para remoção do contato
    Então o contato é removido do banco de dados