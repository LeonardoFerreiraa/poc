package br.com.leonardoferreira.oo;

public final class PessoaFisica extends Pessoa {

    private String cpf;

    public PessoaFisica(String nome) {
        super(nome);
    }

    public PessoaFisica(String nome, String cpf) {
        super(nome);
        this.cpf = cpf;
    }

    @Override
    public void sauda(Pessoa pessoa) {
        System.out.println("Olá " + pessoa.getNome());
    }

    @Override
    public void dizNome() {
        System.out.println("Olá eu sou " + nome + " e tenho o cpf " + cpf);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
