package com.example.demo;

import CLasses.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button listaProdutosButton;

    @FXML
    private Button carrinhoButton;

    @FXML
    private Button recomendacoesButton;

    @FXML
    private TextArea produtoTextArea;

    private List<Produto> todosProduto;

    public void setProdutos(List<Produto> produtos){
        this.todosProduto = produtos;
    }

    @FXML
        protected void handleListaProdutos(){
        StringBuilder produtosStr = new StringBuilder("Lista De Produtos");
        for (Produto produto : todosProduto){
            produtosStr.append("ID: ").append(produto.getId())
                    .append(", Nome: ").append(produto.getNome())
                    .append(", Preço: ").append(produto.getPreco())
                    .append(", Quantidade em estoque: ").append(produto.getQuantidade())
                    .append("\n");
        }
        produtoTextArea.setText(produtosStr.toString());
    }

}