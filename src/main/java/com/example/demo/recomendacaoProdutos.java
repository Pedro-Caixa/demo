package com.example.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class recomendacaoProdutos {

    // Classe para representar um produto comprado
    static class ProdutoComprado {
        String nome;
        String descricao;
        String caminhoImagem;

        ProdutoComprado(String nome, String descricao, String caminhoImagem) {
            this.nome = nome;
            this.descricao = descricao;
            this.caminhoImagem = caminhoImagem;
        }
    }

    // Lista estática para armazenar o histórico de compras
    static ArrayList<ProdutoComprado> historicoCompras = new ArrayList<>();

    public static void main(String[] args) {
        criarTelaLogin();
    }

    public static void atualizarPainelProdutos(JPanel painelProdutos2) {
        painelProdutos2.removeAll();  // Remove todos os produtos atuais

        String url = "jdbc:mysql://127.0.0.1:3306/sistemarecomendacao";
        String user = "root";
        String password = "senha123";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT nome, preco, quantidade FROM Produto";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                JPanel ProdutosInicial = new JPanel();
                ProdutosInicial.setLayout(new BorderLayout());
                ProdutosInicial.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                ProdutosInicial.setBackground(Color.WHITE);

                JPanel imagePanel = new JPanel();
                imagePanel.setPreferredSize(new Dimension(200, 150));
                imagePanel.setBackground(Color.LIGHT_GRAY);
                ProdutosInicial.add(imagePanel, BorderLayout.NORTH);

                String nomeProduto = rs.getString("nome");
                JLabel labelNome = new JLabel(nomeProduto);
                labelNome.setFont(new Font("Arial", Font.BOLD, 16));
                labelNome.setHorizontalAlignment(SwingConstants.CENTER);
                ProdutosInicial.add(labelNome, BorderLayout.CENTER);

                JPanel infoPanel = new JPanel(new GridLayout(1, 2, 5, 0));
                JLabel labelPreco = new JLabel(String.format("R$ %.2f", rs.getDouble("preco")));
                JLabel labelQuantidade = new JLabel("Qtd: " + rs.getString("quantidade"));
                labelPreco.setFont(new Font("Arial", Font.PLAIN, 14));
                labelQuantidade.setFont(new Font("Arial", Font.PLAIN, 14));
                infoPanel.add(labelPreco);
                infoPanel.add(labelQuantidade);
                ProdutosInicial.add(infoPanel, BorderLayout.SOUTH);

                JButton botaoDetalhe = new JButton("DETALHES");
                botaoDetalhe.setPreferredSize(new Dimension(120, 30));
                botaoDetalhe.addActionListener(e -> criarTelaDetalhes(nomeProduto));
                ProdutosInicial.add(botaoDetalhe, BorderLayout.EAST);

                painelProdutos2.add(ProdutosInicial);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        painelProdutos2.revalidate();  // Revalida o layout após a atualização
        painelProdutos2.repaint();     // Reapinta o painel para refletir as mudanças
    }


    public static void criarTelaLogin() {
        JFrame frameLogin = new JFrame("Login");
        frameLogin.setExtendedState(JFrame.MAXIMIZED_BOTH); // fullscreen
        frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new GridLayout(3, 2, 10, 10));
        painelCentral.setPreferredSize(new Dimension(300, 150)); // mantém o tamanho do painel

        JLabel labelUsuario = new JLabel("Usuário:");
        labelUsuario.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField campoUsuario = new JTextField();
        campoUsuario.setPreferredSize(new Dimension(150, 25)); // ajusta o tamanho do campo

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setHorizontalAlignment(SwingConstants.CENTER);

        JPasswordField campoSenha = new JPasswordField();
        campoSenha.setPreferredSize(new Dimension(150, 25)); // ajusta o tamanho do campo

        JButton botaoLogin = new JButton("Login");
        JLabel mensagem = new JLabel("", SwingConstants.CENTER);

        botaoLogin.addActionListener(e -> {
            String usuarioCorreto = "usuario";
            String senhaCorreta = "senha";

            String usuario = campoUsuario.getText();
            String senha = new String(campoSenha.getPassword());

            if (usuario.equals(usuarioCorreto) && senha.equals(senhaCorreta)) {
                frameLogin.dispose();
                criarTelaProdutos();
            } else {
                mensagem.setText("Usuário ou senha inválidos.");
            }
        });

        painelCentral.add(labelUsuario);
        painelCentral.add(campoUsuario);
        painelCentral.add(labelSenha);
        painelCentral.add(campoSenha);
        painelCentral.add(botaoLogin);
        painelCentral.add(mensagem);

        frameLogin.setLayout(new GridBagLayout());
        frameLogin.add(painelCentral);

        frameLogin.setVisible(true);
    }

    public static void criarTelaProdutos() {
        JFrame frameProdutos = new JFrame("Recomendação de Produtos");
        frameProdutos.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameProdutos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelProdutos = new JPanel(new BorderLayout());
        painelProdutos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel painelProdutos2 = new JPanel();
        painelProdutos2.setLayout(new GridLayout(0, 3, 20, 20));

        atualizarPainelProdutos(painelProdutos2);  // Preenche os produtos inicialmente

        JScrollPane scrollPane = new JScrollPane(painelProdutos2);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        painelProdutos.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton historicoButton = new JButton("Histórico");
        historicoButton.addActionListener(e -> criarTelaHistorico());
        bottomPanel.add(historicoButton);

        JButton addButton = new JButton("Adicionar Produto");
        addButton.addActionListener(e -> criarTelaAdicionarProduto(painelProdutos2));  // Passa o painel de produtos para atualizar
        bottomPanel.add(addButton);

        JButton removeButton = new JButton("Remover Produto");
        removeButton.addActionListener(e -> criarTelaRemoverProduto(painelProdutos2));  // Passa o painel de produtos para atualizar
        bottomPanel.add(removeButton);

        painelProdutos.add(bottomPanel, BorderLayout.SOUTH);
        frameProdutos.add(painelProdutos);
        frameProdutos.setVisible(true);
    }



    public static void criarTelaDetalhes(String nomeProduto) {
        JFrame frameDetalhes = new JFrame(nomeProduto);
        frameDetalhes.setExtendedState(JFrame.MAXIMIZED_BOTH); // fullscreen
        frameDetalhes.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Apenas fecha esta janela

        JPanel painelDetalhes = new JPanel();
        painelDetalhes.setLayout(new BorderLayout(10, 10));
        painelDetalhes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // margens

        // Botão de Voltar
        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setFont(new Font("Arial", Font.PLAIN, 16));
        botaoVoltar.addActionListener(e -> frameDetalhes.dispose());

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTopo.add(botaoVoltar);
        painelDetalhes.add(painelTopo, BorderLayout.NORTH);

        // Imagem do produto
        JLabel labelImagem = new JLabel();
        labelImagem.setHorizontalAlignment(SwingConstants.CENTER);
        // Substitua "caminho/para/imagem.png" pelo caminho real da imagem
        labelImagem.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\ferna\\Downloads\\NOTEBOOK.png")
                .getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH)));
        painelDetalhes.add(labelImagem, BorderLayout.EAST);

        // Nome e descrição do produto
        JPanel painelInfo = new JPanel();
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));

        JLabel labelNome = new JLabel(nomeProduto, SwingConstants.CENTER);
        labelNome.setFont(new Font("Arial", Font.BOLD, 30));
        labelNome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea descricao = new JTextArea("Descrição do produto: Este é um excelente produto.");
        descricao.setEditable(false);
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);
        descricao.setFont(new Font("Arial", Font.PLAIN, 16));
        descricao.setBackground(painelInfo.getBackground());
        descricao.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelInfo.add(labelNome);
        painelInfo.add(Box.createRigidArea(new Dimension(0, 10)));
        painelInfo.add(descricao);

        painelDetalhes.add(painelInfo, BorderLayout.CENTER);

        // Botão de Comprar
        JButton botaoComprar = new JButton("Comprar");
        botaoComprar.setFont(new Font("Arial", Font.PLAIN, 16));
        botaoComprar.addActionListener(e -> {
            // Adiciona o produto ao histórico de compras
            historicoCompras.add(new ProdutoComprado(nomeProduto, descricao.getText(), "caminho/para/imagem.png"));
            JOptionPane.showMessageDialog(frameDetalhes, "Produto comprado com sucesso!");
        });

        JPanel painelCompra = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelCompra.add(botaoComprar);
        painelDetalhes.add(painelCompra, BorderLayout.SOUTH);

        frameDetalhes.add(painelDetalhes);
        frameDetalhes.setVisible(true);
    }

    public static void criarTelaAdicionarProduto(JPanel painelProdutos2) {
        JFrame frameAdicionar = new JFrame("Adicionar Produto");
        frameAdicionar.setSize(400, 300);
        frameAdicionar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelAdicionar = new JPanel(new GridLayout(4, 2, 10, 10));
        panelAdicionar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel labelNome = new JLabel("Nome do Produto:");
        JTextField campoNome = new JTextField();

        JLabel labelPreco = new JLabel("Preço:");
        JTextField campoPreco = new JTextField();

        JLabel labelQuantidade = new JLabel("Quantidade:");
        JTextField campoQuantidade = new JTextField();

        JButton botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(e -> {
            String nome = campoNome.getText();
            double preco = Double.parseDouble(campoPreco.getText());
            int quantidade = Integer.parseInt(campoQuantidade.getText());

            String url = "jdbc:mysql://127.0.0.1:3306/sistemarecomendacao";
            String user = "root";
            String password = "senha123";

            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                String sql = "INSERT INTO Produto (nome, preco, quantidade) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nome);
                stmt.setDouble(2, preco);
                stmt.setInt(3, quantidade);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(frameAdicionar, "Produto adicionado com sucesso!");
                atualizarPainelProdutos(painelProdutos2);  // Atualiza o painel de produtos
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frameAdicionar, "Erro ao adicionar produto: " + ex.getMessage());
            }
        });

        panelAdicionar.add(labelNome);
        panelAdicionar.add(campoNome);
        panelAdicionar.add(labelPreco);
        panelAdicionar.add(campoPreco);
        panelAdicionar.add(labelQuantidade);
        panelAdicionar.add(campoQuantidade);
        panelAdicionar.add(new JLabel());  // Placeholder
        panelAdicionar.add(botaoSalvar);

        frameAdicionar.add(panelAdicionar);
        frameAdicionar.setVisible(true);
    }

    public static void criarTelaRemoverProduto(JPanel painelProdutos2) {
        JFrame frameRemover = new JFrame("Remover Produto");
        frameRemover.setSize(400, 300);
        frameRemover.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelRemover = new JPanel(new BorderLayout());
        panelRemover.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JComboBox<String> comboProdutos = new JComboBox<>();

        String url = "jdbc:mysql://127.0.0.1:3306/sistemarecomendacao";
        String user = "root";
        String password = "senha123";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT nome FROM Produto";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                comboProdutos.addItem(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frameRemover, "Erro ao carregar produtos: " + e.getMessage());
        }

        JButton botaoRemover = new JButton("Remover");
        botaoRemover.addActionListener(e -> {
            String produtoSelecionado = (String) comboProdutos.getSelectedItem();

            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                String sql = "DELETE FROM Produto WHERE nome = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, produtoSelecionado);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(frameRemover, "Produto removido com sucesso!");
                atualizarPainelProdutos(painelProdutos2);  // Atualiza o painel de produtos
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frameRemover, "Erro ao remover produto: " + ex.getMessage());
            }
        });

        panelRemover.add(comboProdutos, BorderLayout.CENTER);
        panelRemover.add(botaoRemover, BorderLayout.SOUTH);
        frameRemover.add(panelRemover);
        frameRemover.setVisible(true);
    }


    public static void criarTelaHistorico() {
        JFrame frameHistorico = new JFrame("historico de compras");
        frameHistorico.setExtendedState(JFrame.MAXIMIZED_BOTH); // fullscreen
        frameHistorico.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelHistorico = new JPanel();
        painelHistorico.setLayout(new BoxLayout(painelHistorico, BoxLayout.Y_AXIS));

        String url = "jdbc:mysql://127.0.0.1:3306/sistemarecomendacao";
        String user = "root";
        String password = "senha123";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Consulta para buscar o histórico de compras do primeiro usuário
            String sql = "SELECT p.nome, p.preco, p.idProduto, c.dataCompra " +
                    "FROM Compra c " +
                    "JOIN Produto p ON c.idProduto = p.idProduto " +
                    "WHERE c.idUsuario = 1";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Adiciona os produtos comprados
            while (rs.next()) {
                String nomeProduto = rs.getString("nome");
                double precoProduto = rs.getDouble("preco");
                String dataCompra = rs.getDate("dataCompra").toString();

                JPanel itemProduto = new JPanel();
                itemProduto.setLayout(new BorderLayout());
                itemProduto.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                itemProduto.setPreferredSize(new Dimension(600, 100));
                itemProduto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

                // Nome e data do produto
                JPanel painelInfo = new JPanel();
                painelInfo.setLayout(new BorderLayout());

                JLabel labelNome = new JLabel(nomeProduto, SwingConstants.LEFT);
                labelNome.setFont(new Font("Arial", Font.BOLD, 16));

                JLabel labelPreco = new JLabel("Preço: R$ " + precoProduto, SwingConstants.LEFT);
                labelPreco.setFont(new Font("Arial", Font.PLAIN, 14));

                JLabel labelData = new JLabel("Data da compra: " + dataCompra, SwingConstants.LEFT);
                labelData.setFont(new Font("Arial", Font.ITALIC, 12));

                painelInfo.add(labelNome, BorderLayout.NORTH);
                painelInfo.add(labelPreco, BorderLayout.CENTER);
                painelInfo.add(labelData, BorderLayout.SOUTH);

                itemProduto.add(painelInfo, BorderLayout.CENTER);
                painelHistorico.add(itemProduto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(painelHistorico);
        frameHistorico.add(scrollPane, BorderLayout.CENTER);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.addActionListener(e -> {
            frameHistorico.dispose();  // Fecha o histórico
        });

        frameHistorico.add(botaoVoltar, BorderLayout.SOUTH);
        frameHistorico.setVisible(true);
    }

    public static void atualizarProdutos(JPanel painelProdutos2) {
        painelProdutos2.removeAll();  // Remove todos os produtos atuais

        String url = "jdbc:mysql://127.0.0.1:3306/sistemarecomendacao";
        String user = "root";
        String password = "senha123";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT nome, preco, quantidade FROM Produto";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Criar painel de produto
                JPanel ProdutosInicial = new JPanel();
                ProdutosInicial.setLayout(new BorderLayout());
                ProdutosInicial.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                ProdutosInicial.setBackground(Color.WHITE);

                // Configuração do painel de imagem
                JPanel imagePanel = new JPanel();
                imagePanel.setPreferredSize(new Dimension(200, 150));
                imagePanel.setBackground(Color.LIGHT_GRAY);
                ProdutosInicial.add(imagePanel, BorderLayout.NORTH);

                // Configuração do label de nome do produto
                String nomeProduto = rs.getString("nome");
                JLabel labelNome = new JLabel(nomeProduto);
                labelNome.setFont(new Font("Arial", Font.BOLD, 16));
                labelNome.setHorizontalAlignment(SwingConstants.CENTER);
                ProdutosInicial.add(labelNome, BorderLayout.CENTER);

                // Painel de informações com preço e quantidade
                JPanel infoPanel = new JPanel(new GridLayout(1, 2, 5, 0));
                JLabel labelPreco = new JLabel(String.format("R$ %.2f", rs.getDouble("preco")));
                JLabel labelQuantidade = new JLabel("Qtd: " + rs.getString("quantidade"));
                labelPreco.setFont(new Font("Arial", Font.PLAIN, 14));
                labelQuantidade.setFont(new Font("Arial", Font.PLAIN, 14));
                infoPanel.add(labelPreco);
                infoPanel.add(labelQuantidade);
                ProdutosInicial.add(infoPanel, BorderLayout.SOUTH);

                // Botão de detalhes
                JButton botaoDetalhe = new JButton("DETALHES");
                botaoDetalhe.setPreferredSize(new Dimension(120, 30));
                botaoDetalhe.addActionListener(e -> {
                    criarTelaDetalhes(nomeProduto);
                });
                ProdutosInicial.add(botaoDetalhe, BorderLayout.EAST);

                painelProdutos2.add(ProdutosInicial);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar produtos: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }

        painelProdutos2.revalidate();
        painelProdutos2.repaint();
    }



}