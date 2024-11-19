package CLasses;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class SistemaRecomendacao {
    private final List<Historico> todosHistoricos;
    private final List<Produto> todosProdutos;

    public SistemaRecomendacao(List<Historico> historicos, List<Produto> produtos) {
        this.todosHistoricos = historicos;
        this.todosProdutos = produtos;
    }

    public List<Produto> gerarRecomendacoes(Usuario usuario, int numeroRecomendacoes) {
        
        List<Historico> historicoUsuario = getHistoricoUsuario(usuario);

        if (historicoUsuario.isEmpty()) {
            return gerarRecomendacoesSemHistorico(numeroRecomendacoes);
        } else {
            return gerarRecomendacoesComHistorico(historicoUsuario, numeroRecomendacoes);
        }
    }

    private List<Historico> getHistoricoUsuario(Usuario usuario) {
        return todosHistoricos.stream()
                .filter(h -> h.getnome().getid() == usuario.getid())
                .toList();
    }

    private List<Produto> gerarRecomendacoesSemHistorico(int numeroRecomendacoes) {
       
        Map<Produto, Integer> frequenciaCompras = new HashMap<>();

        for (Historico historico : todosHistoricos) {
            for (Produto produto : historico.getProdutos()) {
                frequenciaCompras.merge(produto, 1, Integer::sum);
            }
        }

        return frequenciaCompras.entrySet().stream()
                .sorted(Map.Entry.<Produto, Integer>comparingByValue().reversed())
                .limit(numeroRecomendacoes)
                .map(Map.Entry::getKey)
                .toList();
    }

    private List<Produto> gerarRecomendacoesComHistorico(List<Historico> historicoUsuario, int numeroRecomendacoes) {
       
        Map<Produto, Double> scoresProdutos = new HashMap<>();

       
        for (Historico historico : historicoUsuario) {
            double pesoRecencia = calcularPesoRecencia(historico.getDataCompra());

            for (Produto produto : historico.getProdutos()) {
                scoresProdutos.merge(produto, pesoRecencia, Double::sum);
            }
        }

      
        Set<Produto> produtosRecomendados = new HashSet<>();

        for (Produto produtoComprado : scoresProdutos.keySet()) {
            List<Produto> similares = encontrarProdutosSimilares(produtoComprado);
            produtosRecomendados.addAll(similares);
        }

        return produtosRecomendados.stream()
                .sorted((p1, p2) -> scoresProdutos.getOrDefault(p2, 1.0)
                        .compareTo(scoresProdutos.getOrDefault(p1, 2.0)))
                .limit(numeroRecomendacoes)
                .toList();
    }

    private double calcularPesoRecencia(LocalDateTime dataCompra) {
        long diasAtras = ChronoUnit.DAYS.between(dataCompra, LocalDateTime.now());
        return Math.exp(-diasAtras / 30.0);
    }

    private List<Produto> encontrarProdutosSimilares(Produto produto) {
        double precoMin = produto.getPreco() * 0.5;
        double precoMax = produto.getPreco() * 3.0;

        return todosProdutos.stream()
                .filter(p -> p.getId() != produto.getId())
                .filter(p -> p.getPreco() >= precoMin && p.getPreco() <= precoMax)
                .limit(5)
                .toList();
    }
}
