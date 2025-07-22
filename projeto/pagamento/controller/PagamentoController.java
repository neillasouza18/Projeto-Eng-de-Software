package projeto.pagamento.controller;
import projeto.DataBase;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import projeto.pagamento.model.Pagamento;

public class PagamentoController {
    public SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public boolean registrarPagamento(Scanner scanner, int numeroReserva) throws SQLException {
        Date data = new Date();


        System.out.print("Digite o método de pagamento: ");
        String metodoPagamento = scanner.nextLine();

        String query = "INSERT INTO pagamentos (data, valor, \"metodoPagamento\", \"numeroReserva\") VALUES (?, ?, ?, ?)";
        int rowsAffected = DataBase.executeUpdate(query, data, 0,  metodoPagamento, numeroReserva);

        if (rowsAffected > 0) {
            System.out.println("\n--------------------------------\n");
            System.out.println("Pagamento registrado com sucesso.\n");
            System.out.println("----------------------------------");
            return true;
        } else {
            System.out.println("\n--------------------------------\n");
            System.out.println("Falha ao registrar pagamento.\n");
            System.out.println("----------------------------------");
            return false;
        }
    }

    public void visualizarPagamento(int id) throws SQLException {
        String query = "SELECT * FROM pagamentos WHERE id = ?";
        List<List<Object>> results = DataBase.executeQuery(query, id);

        if (results.isEmpty()) {
            throw new SQLException("Pagamento não encontrado.");
        }

        List<Object> pagamentoData = results.get(0);
        int pagamentoId = (int) pagamentoData.get(0);
        Date data = (Date) pagamentoData.get(1);
        float valor = (float) pagamentoData.get(2);
        String metodoPagamento = (String) pagamentoData.get(3);

        System.out.println("Pagamento encontrado:");
        System.out.println("ID: " + pagamentoId);
        System.out.println("Data: " + sdf.format(data));
        System.out.println("Valor: " + valor);
        System.out.println("Método de Pagamento: " + metodoPagamento);
        System.out.println("------------------------------\n");
    }

    public List<Pagamento> listarPagamentos() throws SQLException {
        List<Pagamento> pagamentos = new ArrayList<>();
        String query = "SELECT * FROM pagamentos";
        
        List<List<Object>> results = DataBase.executeQuery(query);

        for (List<Object> row : results) {
            int id = (int) row.get(0);
            Date data = (Date) row.get(1);
            float valor = (float) row.get(2);
            String metodoPagamento = (String) row.get(3);
            int numeroReserva = (int) row.get(4);
            
            pagamentos.add(new Pagamento(id, data, valor, metodoPagamento, numeroReserva));
        }
        
        return pagamentos;
    }

    public void editarPagamento(Scanner scanner) throws SQLException {
        System.out.println("");
        System.out.print("Digite o ID do pagamento que deseja editar: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        // Buscar pagamento pelo ID
        String query = "SELECT * FROM pagamentos WHERE id = ?";
        List<List<Object>> results = DataBase.executeQuery(query, id);

        if (results.isEmpty()) {
            throw new SQLException("Pagamento não encontrado.");
        }
        
        List<Object> row = results.get(0);  // Só existe um pagamento com esse ID
        float valorAntigo = (float) row.get(2);
        String metodoPagamentoAntigo = (String) row.get(3);

        // Solicitar novo valor
        float novoValor = 0;
        boolean valorValido = false;
        while (!valorValido) {
            System.out.print("Digite o novo valor do pagamento (anterior: " + valorAntigo + "): ");
            String valorInput = scanner.nextLine().trim();
            try {
                novoValor = Float.parseFloat(valorInput);
                valorValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Por favor, digite um número válido.");
            }
        }

        // Solicitar novo método de pagamento
        System.out.print("Digite o novo método de pagamento (anterior: " + metodoPagamentoAntigo + "): ");
        String novoMetodoPagamento = scanner.nextLine();

        // Atualizando o banco de dados
        String updateQuery = "UPDATE pagamentos SET valor = ?, \"metodoPagamento\" = ? WHERE id = ?";
        DataBase.executeUpdate(updateQuery,novoValor, novoMetodoPagamento, id);

        System.out.println("------------------------------\n");
        System.out.println("Pagamento editado com sucesso.\n");
        System.out.println("------------------------------\n");
    }
    
    public void excluirPagamento(int id) throws SQLException {
        String query = "SELECT * FROM pagamentos WHERE id = ?";
        List<List<Object>> pagamentos = DataBase.executeQuery(query, id);
        
        if (pagamentos.isEmpty()) {
            throw new SQLException("Pagamento não encontrado.");
        }

        query = "DELETE FROM pagamentos WHERE id = ?";
        DataBase.executeUpdate(query, id);

        System.out.println("Pagamento excluído com sucesso.\n");
    }
}