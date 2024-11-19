package CLasses;

import java.sql.*;
import java.util.*;

public class SistemaRecomendacaoWeka {

    private static Connection conectar() throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
        String usuario = "root";
        String senha = "1234";
        return DriverManager.getConnection(url, usuario, senha);
    }

    public static List<String> recomendarProdutos(int idUsuario) {
        List<String> recomendacoes = new ArrayList<>();
        String sql = "SELECT p.nome AS ProdutoRecomendado, COUNT(c.idUsuario) AS QuantidadeCompras " +
                "FROM Compra c " +
                "JOIN Produto p ON c.idProduto = p.idProduto " +
                "WHERE c.idUsuario != ? " +
                "GROUP BY p.idProduto " +
                "ORDER BY QuantidadeCompras DESC " +
                "LIMIT 5";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String produtoRecomendado = rs.getString("ProdutoRecomendado");
                recomendacoes.add(produtoRecomendado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recomendacoes;
    }

    public static void main(String[] args) {
        int idUsuario = 1;  // Exemplo de id do usuário que receberá as recomendações
        List<String> recomendacoes = recomendarProdutos(idUsuario);

        System.out.println("Produtos recomendados para o usuário " + idUsuario + ":");
        for (String produto : recomendacoes) {
            System.out.println(produto);
        }
    }
}
