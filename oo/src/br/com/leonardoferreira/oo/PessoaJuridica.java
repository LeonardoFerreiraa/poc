package br.com.leonardoferreira.oo;

public final class PessoaJuridica extends Pessoa {

    private String cnpj;

    public PessoaJuridica(String nome) {
        super(nome);
    }

    public PessoaJuridica(String nome, String cnpj) {
        super(nome);
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + " - CNPJ: " + cnpj+ ".";
    }

    @Override
    public void sauda(Pessoa pessoa) {
        System.out.println("Oi " + pessoa.getNome());
    }
}
