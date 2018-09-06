package br.com.leonardoferreira.oo;

public class Application {

    public static void main(String[] args) {
        PessoaFisica pessoaFisica = new PessoaFisica("Romney", "123.321.321-13");
        pessoaFisica.dizNome();
        pessoaFisica.dizIdade();

        PessoaJuridica pessoaJuridica = new PessoaJuridica("S2IT", "21383210/00001");
        pessoaJuridica.dizNome();
        pessoaJuridica.dizIdade();

        pessoaFisica.sauda(pessoaJuridica);
        pessoaJuridica.sauda(pessoaFisica);
    }

}
