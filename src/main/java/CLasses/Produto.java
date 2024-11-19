package CLasses;

public class Produto {
    private long id;
    private String nome;
    private double preco;
    private int quantidade;

    public Produto(long id, String nome, double preco, int quantidade){
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public long getId(){return id;}
    public String getNome(){return nome;}
    public double getPreco(){return preco;}
    public int getQuantidade(){return quantidade;}
    public void AtualizaQuantidade(int quantidadeVendida){this.quantidade = quantidadeVendida;}
}
