package br.com.leonardoferreira.oo;

public abstract class Pessoa {

    protected String nome;

    protected Integer idade;

    public Pessoa(String nome) {
        this.nome = nome;
    }

    public abstract void sauda(Pessoa pessoa);

    public void dizNome() {
        System.out.println("Olá eu sou " + nome);
    }

    public final void dizIdade() {
        System.out.println("Eu tenho " + idade + " anos");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null) {
            this.nome = nome;
        }
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }
}
