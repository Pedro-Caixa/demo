package CLasses;

import java.time.LocalDateTime;
import java.util.List;

    public class Historico {
        private long idCompra;
        private Usuario nome;
        private List<Produto> produtos;
        private LocalDateTime dataCompra;
        private double valorTotal;

        public Historico(long idCompra, Usuario nome, List<Produto> produtos, LocalDateTime dataCompra) {
            this.idCompra = idCompra;
            this.nome = nome;
            this.produtos = produtos;
            this.dataCompra = dataCompra;
            calcularValorTotal();
        }

        private void calcularValorTotal(){
            valorTotal = produtos.stream().mapToDouble(Produto::getPreco).sum();
        }

        public double getValorTotal(){return valorTotal;}
        public Usuario getnome(){return nome;}
        public List<Produto> getProdutos(){return produtos;}
        public LocalDateTime getDataCompra(){return dataCompra;}

}
