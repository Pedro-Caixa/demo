/*import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public static void criarTelaHistorico() {
    JFrame frameHistorico = new JFrame("historico de compras");
    frameHistorico.setExtendedState(JFrame.MAXIMIZED_BOTH); // fullscreen
    frameHistorico.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel painelHistorico = new JPanel();
    painelHistorico.setLayout(new BoxLayout(painelHistorico, BoxLayout.Y_AXIS));

    String url = "jdbc:mysql://127.0.0.1:3306/sistemarecomendacao";
    String user = "root";
    String password = "1234";

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
        /* Chame outro metodo, se necessario, para voltar ao menu principal
    });

    frameHistorico.add(botaoVoltar, BorderLayout.SOUTH);
    frameHistorico.setVisible(true);
 }
*/

/*IDEIA

/*    String[] produtos = {
                "Produto 1", "Produto 2", "Produto 3",
                "Produto 4", "Produto 5", "Produto 6",
                "Produto 7", "Produto 8", "Produto 9"
        };

        for (String produto : produtos) {
            JPanel painelProduto = new JPanel();
            painelProduto.setLayout(new BorderLayout());
            painelProduto.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel labelImagem = new JLabel();
            labelImagem.setHorizontalAlignment(SwingConstants.CENTER);
            labelImagem.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\ferna\\Downloads\\NOTEBOOK.png")

                    .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));

            JLabel labelNome = new JLabel(produto, SwingConstants.CENTER);
            labelNome.setFont(new Font("Arial", Font.BOLD, 16));

            JButton botao = new JButton("Detalhes");
            botao.addActionListener(e -> criarTelaDetalhes(produto));

            painelProduto.add(labelImagem, BorderLayout.CENTER);
            painelProduto.add(labelNome, BorderLayout.NORTH);
            painelProduto.add(botao, BorderLayout.SOUTH);

            painelProdutos.add(painelProduto);
        }
*/