package CLasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/sistemarecomendacao";
    private static final String usuario = "root";
    private static final String senha = "1234";

    public static Connection conectar() {
        try {
            Connection conexao = DriverManager.getConnection(url, usuario, senha);
            System.out  .println("Conexão bem-sucedida!");
            return conexao;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }
}