import projeto.DataBase;
import projeto.usuario.view.UsuarioView;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try { // Se conecta ao banco primeiro.
        DataBase.getConnection();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        // Iniciar sistema.
        UsuarioView Pousada = new UsuarioView();
        Pousada.login();

        try { // Garante que conexão seja fechada ao encerrar aplicação.
            DataBase.closeConnection();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }    
}