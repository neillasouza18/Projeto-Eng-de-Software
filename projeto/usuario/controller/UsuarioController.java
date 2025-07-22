package projeto.usuario.controller;
import projeto.usuario.model.Usuario;

import projeto.DataBase;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class UsuarioController {
    public void inserirUsuario(Scanner scanner) throws SQLException {
        System.out.print("Digite o cpf: ");
        String cpf = scanner.nextLine();

        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o endereço: ");
        String endereco = scanner.nextLine();

        System.out.print("Digite o email: ");
        String email = scanner.nextLine();

        System.out.print("Digite o telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Digite o nome de usuário: ");
        String usuario = scanner.nextLine();

        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        String query = "INSERT INTO usuario (cpf, nome, endereco, email, telefone, usuario, senha, admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int rowsAffected = DataBase.executeUpdate(query, cpf, nome, endereco, email, telefone, usuario, senha, false);

        if (rowsAffected > 0) {
            System.out.println("");
            System.out.println("--------------------------------\n");
            System.out.println("Usuário adicionado com sucesso.\n");
            System.out.println("----------------------------------");
        } else {
            System.out.println("Erro ao adicionar o usuário.");
        }
    }

    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuario";
        List<List<Object>> result = DataBase.executeQuery(query);

        for (List<Object> row : result) {
            String cpf = (String) row.get(0);
            String nome = (String) row.get(1);
            String endereco = (String) row.get(2);
            String email = (String) row.get(3);
            String telefone = (String) row.get(4);
            String username = (String) row.get(5);
            String senha = (String) row.get(6);

            usuarios.add(new Usuario(cpf, nome, endereco, email, telefone, username, senha, false));
        }
        System.out.println(usuarios);
        return usuarios;
    }

    public void editarUsuario(Scanner scanner) throws SQLException {
        System.out.println("");
        System.out.print("Digite o nome de usuário que deseja editar: ");
        String username = scanner.nextLine();

        String query = "SELECT * FROM usuario WHERE usuario = ?";
        List<List<Object>> result = DataBase.executeQuery(query, username);

        if (result.isEmpty()) {
            throw new SQLException("Usuário não encontrado.");
        } else {
            List<Object> userData = result.get(0);

            System.out.print("Digite o novo nome (anterior: " + userData.get(1) + "): ");
            String nome = scanner.nextLine();

            System.out.print("Digite o novo endereço (anterior: " + userData.get(2) + "): ");
            String endereco = scanner.nextLine();

            System.out.print("Digite o novo email (anterior: " + userData.get(3) + "): ");
            String email = scanner.nextLine();

            System.out.print("Digite o novo telefone (anterior: " + userData.get(4) + "): ");
            String telefone = scanner.nextLine();

            System.out.print("Digite o novo usuário (anterior: " + userData.get(5) + "): ");
            String newUsername = scanner.nextLine();

            System.out.print("Digite a nova senha: ");
            String senha = scanner.nextLine();

            String updateQuery = "UPDATE usuario SET nome = ?, endereco = ?, email = ?, telefone = ?, usuario = ?, senha = ? WHERE usuario = ?";
            int rowsAffected = DataBase.executeUpdate(updateQuery, nome, endereco, email, telefone, newUsername, senha, username);

            if (rowsAffected > 0) {
                System.out.println("------------------------------\n");
                System.out.println("Usuário editado com sucesso.\n");
                System.out.println("------------------------------\n");
            } else {
                throw new SQLException("Erro ao editar o usuário.");
            }
        }
    }

    public String visualizarUsuario(String username) throws SQLException {
        String query = "SELECT * FROM usuario WHERE usuario = ?";
        List<List<Object>> result = DataBase.executeQuery(query, username);

        if (result.isEmpty()) {
            return "Usuário não encontrado.";
        } else {
            List<Object> userData = result.get(0);
            String userInfo = "Usuário encontrado: " + userData.toString();
            System.out.println("\nUsuário encontrado!\n" + userInfo);
            return userInfo;
        }
    }

    public void excluirUsuario(String username) throws SQLException {
        String checkAdminQuery = "SELECT admin FROM usuario WHERE usuario = ?";
        List<List<Object>> resultado = DataBase.executeQuery(checkAdminQuery, username);

        if (resultado.isEmpty()) {
            throw new SQLException("Usuário não encontrado.");
        }

        boolean isAdmin = (boolean) resultado.get(0).get(0);

        if (isAdmin) {
            throw new SQLException("Não pode excluir um usuário admin.");
        }

        // Procede a deletar
        String query = "DELETE FROM usuario WHERE usuario = ?";
        int rowsAffected = DataBase.executeUpdate(query, username);

        if (rowsAffected > 0) {
            System.out.println("");
            System.out.println("--------------------------------\n");
            System.out.println("Usuario excluido com sucesso.\n");
            System.out.println("----------------------------------");
        } else {
            throw new SQLException("Usuario nao encontrado.");
        }
    }

    public boolean autenticarAdmin(Scanner scanner) throws SQLException {
        System.out.print("Digite o nome de usuário: ");
        String username = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        String query = "SELECT * FROM usuario WHERE usuario = ? AND senha = ? AND admin = true";
        List<List<Object>> result = DataBase.executeQuery(query, username, senha);

        if (result.isEmpty()) {
            throw new SQLException("Administrador não encontrado.");
        }

        return !result.isEmpty();
    }

    public Usuario autenticarUser(Scanner scanner) throws SQLException {
        System.out.print("Digite o nome de usuário: ");
        String username = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        String query = "SELECT * FROM usuario WHERE usuario = ? AND senha = ?";
        List<List<Object>> result = DataBase.executeQuery(query, username, senha);

        if (result.isEmpty()) {
            throw new SQLException("Usuário não encontrado.");
        }

        List<Object> userData = result.get(0);
        String cpf = (String) userData.get(0);
        String nome = (String) userData.get(1);
        String endereco = (String) userData.get(2);
        String email = (String) userData.get(3);
        String telefone = (String) userData.get(4);
        String usuario = (String) userData.get(5);
        String senhaUsuario = (String) userData.get(6);
        boolean admin = (boolean) userData.get(7);

        return new Usuario(cpf, nome, endereco, email, telefone, usuario, senhaUsuario, admin);
    }

    public Usuario getUsuarioByUsername(String username) throws SQLException {
        String query = "SELECT * FROM usuario WHERE usuario = ?";
        List<List<Object>> resultado = DataBase.executeQuery(query, username);

        if (resultado.isEmpty()) {
            return null;
        }

        List<Object> linha = resultado.get(0);
        String cpf = (String) linha.get(0);
        String nome = (String) linha.get(1);
        String endereco = (String) linha.get(2);
        String email = (String) linha.get(3);
        String telefone = (String) linha.get(4);
        String usuario = (String) linha.get(5);
        String senha = (String) linha.get(6);
        boolean admin = (boolean) linha.get(7);

        return new Usuario(cpf, nome, endereco, email, telefone, usuario, senha, admin);
    }

}