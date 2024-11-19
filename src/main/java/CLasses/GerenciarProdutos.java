package CLasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciarProdutos {
    private List<Produto> listaProdutos;

    public GerenciarProdutos(){
        this.listaProdutos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto) {
        try(Connection conexao = ConexaoBanco.conectar()){
            String sql = "Insert into Produto (idProduto, nome, preco, quantidade) values (?,?,?,?)";
            assert conexao != null;
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setLong(1, produto.getId());
            stmt.setString(2, produto.getNome());
            stmt.setDouble(3,produto.getPreco());
            stmt.setInt(4, produto.getQuantidade());
            stmt.executeUpdate();
            System.out.printf("Produto Adicionada: %s ", produto.getNome());
        } catch (SQLException e) {
            System.out.printf("Erro ao adicionar produto: %s ", e.getMessage());
        }
    }

    public void listarProdutos(){
        try(Connection conexao = ConexaoBanco.conectar()){
            String sql = "select * from Produto";
            assert conexao != null;
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()){
                System.out.printf("Nenhum produto");
            }else{
                System.out.printf("Lista de Produtos");
                while (rs.next()){
                    System.out.printf("ID: %d | Nome: %s | Preco: %.2f | Quantidade: %d%n",
                            rs.getLong("idProduto"), rs.getString("nome"), rs.getDouble("preco"),  rs.getInt("quantidade"));
                }
            }
        } catch (SQLException e) {
            System.out.printf("Erro ao listar produtos: %s ", e.getMessage());
        }
    }

    public List<Produto> getListaProdutos() {
        return listaProdutos;
    }

}
