package br.com.jpcribeiro.model;

public class Jogo {
    private int id;
    private String nome;
    private double preco;
    private String descricao;

    public Jogo(int id, String nome, double preco, String descricao) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }

        if (preco <= 0) {
            throw new IllegalArgumentException("Preço não pode ser menor ou igual a 0");
        }

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição inválida");
        }

        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }

        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço não pode ser menor ou igual a 0");
        }

        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição inválida");
        }

        this.descricao = descricao;
    }
}
