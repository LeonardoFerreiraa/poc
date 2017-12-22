# language: pt

Funcionalidade: atualização de contatos

  Cenário: Quando o usuário deseja atualizar dados de um contato
    Dado dados validos para alteração de um contato
    E um contato já salvo no banco de dados pronto para alteração
    Quando o usuário faz uma requisição para alteração de contato
    Então o contato é atualizado no banco de dados

  Cenário: Quando o usuário deseja atualizar dados de um contato passando informações invalidas
    Dado dados invalidos para alteração de um contato
    E um contato já salvo no banco de dados pronto para alteração
    Quando o usuário faz uma requisição para alteração de contato
    Então o sistema deve retornar erros de validacao para a alteração de contato