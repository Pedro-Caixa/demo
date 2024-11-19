package CLasses;

public class TesteGerenciarProdutos {
    public static void main(String[] args) {
        GerenciarProdutos gerenciador = new GerenciarProdutos();

        Produto produtoTeste = new Produto(44, "Produto Teste", 10.99, 5);
        gerenciador.adicionarProduto(produtoTeste);

        gerenciador.listarProdutos();
    }
}
