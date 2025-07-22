package projeto.reserva.controller;
import projeto.reserva.model.Reserva;
import projeto.usuario.model.Usuario;
import projeto.DataBase;
import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Scanner;
import projeto.pagamento.controller.PagamentoController;
import projeto.quarto.model.Quarto;

public class ReservaController {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    PagamentoController pagamentoController = new PagamentoController();

    public void realizarReserva(Usuario usuario, Quarto quarto, Scanner scanner) throws SQLException {
        // Coleta as informações de data de início e término para a reserva
        System.out.println("Digite as informações da reserva");

        System.out.print("Data de início (dd-MM-yyyy): ");
        Date dataInicio = null;
        try {
            dataInicio = sdf.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Data inválida.");
            return;
        }

        System.out.print("Data de término (dd-MM-yyyy): ");
        Date dataTermino = null;
        try {
            dataTermino = sdf.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Data inválida.");
            return;
        }

        java.sql.Date sqlDataInicio = new java.sql.Date(dataInicio.getTime());
        java.sql.Date sqlDataTermino = new java.sql.Date(dataTermino.getTime());

        java.sql.Date dataCheckin = sqlDataInicio;
        java.sql.Date dataCheckout = sqlDataTermino;

        // Criação da reserva
        String queryReserva = "INSERT INTO reserva (\"dataInicio\", \"dataTermino\", \"dataCheckin\", \"dataCheckout\", \"validaReserva\", \"cpfUsuario\", \"numeroQuarto\") VALUES (?, ?, ?, ?, ?, ?, ?)";
        String cpfUsuario = usuario.getCpf();
        int numeroQuarto = quarto.getNumero();

        int rowsAffected = DataBase.executeUpdate(queryReserva, sqlDataInicio, sqlDataTermino, dataCheckin, dataCheckout, true, cpfUsuario, numeroQuarto);

        if (rowsAffected > 0) {
            System.out.println("--------------------------------");
            System.out.println("Reserva realizada com sucesso.");
            System.out.println("--------------------------------");

            // Recupera o número da reserva (supondo que o número seja gerado automaticamente)
            int numeroReserva = obterUltimoNumeroReserva(); // Aqui você precisa implementar a lógica para pegar o último número de reserva gerado

            // Agora realiza o pagamento, passando o número da reserva
            Boolean pagamento = pagamentoController.registrarPagamento(scanner, numeroReserva);

            if (pagamento) {
                System.out.println("Pagamento realizado com sucesso: " + pagamento);
            } else {
                System.out.println("Falha ao realizar pagamento.");
            }
        } else {
            System.out.println("Falha ao realizar reserva.");
        }
    }

    public List<Reserva> listarReservas() throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        
        String query = "SELECT * FROM reserva";
        List<List<Object>> results = DataBase.executeQuery(query);

        for (List<Object> row : results) {
            // Certifique-se de que os dados estão sendo recuperados nas posições corretas
            int numeroReserva = (int) row.get(0);
            Date dataInicio = (Date) row.get(1);
            Date dataTermino = (Date) row.get(2);
            Date dataCheckin = (Date) row.get(3);
            Date dataCheckout = (Date) row.get(4);
            boolean validaReserva = (boolean) row.get(5);
            String cpfUsuario = (String) row.get(6);
            int numeroQuarto = (int) row.get(7);

            // Criação do objeto Reserva
            Reserva reserva = new Reserva(numeroReserva, dataInicio, dataTermino, dataCheckin, dataCheckout, validaReserva, cpfUsuario, numeroQuarto);
            reservas.add(reserva);
        }
        
        return reservas;
    }

    public void visualizarReserva(int idReserva) throws SQLException {
        List<Reserva> reservas = listarReservas();
        boolean encontrado = false;
        for (Reserva reserva : reservas) {
            if (reserva.getId() == idReserva) {
                System.out.println("Reserva encontrada:");
                System.out.println("ID: " + reserva.getId());
                System.out.println("Data de Início: " + sdf.format(reserva.getDataInicio()));
                System.out.println("Data de Término: " + sdf.format(reserva.getDataTermino()));
                System.out.println("Data de Checkin: " + sdf.format(reserva.getDataCheckin()));
                System.out.println("Data de Checkout: " + sdf.format(reserva.getDataCheckout()));

                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new SQLException("Reserva não encontrada.");
        }
    }

    public Date getDataCheckin(int idReserva) throws SQLException {
        List<Reserva> reservas = listarReservas();
        for (Reserva reserva : reservas) {
            if (reserva.getId() == idReserva) {
                return reserva.getDataCheckin();
            }
        }
        return null;
    }

    public Date getDataCheckout(int idReserva) throws SQLException {
        String query = "SELECT \"dataCheckout\" FROM reserva WHERE \"numeroReserva\" = ?";
        List<List<Object>> resultado = DataBase.executeQuery(query, idReserva);

        if (!resultado.isEmpty() && resultado.get(0).get(0) != null) {
            return (Date) resultado.get(0).get(0);
        }

        return null;
    }

    public void cancelarReserva(int idReserva) throws SQLException {
        if (!verificaReserva(idReserva)) {
            throw new SQLException("Reserva não encontrada.");
        }

        String queryCancelarPagamento = "DELETE FROM pagamentos WHERE \"numeroReserva\" = ?";
        DataBase.executeUpdate(queryCancelarPagamento, idReserva);

        String queryCancelarReserva = "DELETE FROM reserva WHERE \"numeroReserva\" = ?";
        DataBase.executeUpdate(queryCancelarReserva, idReserva);

        System.out.println("Reserva e pagamento cancelados com sucesso.");
    }

    public void editarReserva(int idReserva, Scanner scanner) throws SQLException {
        if (!verificaReserva(idReserva)) {
            throw new SQLException("Reserva não encontrada.");
        }

        System.out.print("Digite a nova data de início (dd-MM-yyyy): ");
        java.util.Date novaDataInicio = null;
        try {
            novaDataInicio = sdf.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Data inválida.");
            return;
        }

        System.out.print("Digite a nova data de término (dd-MM-yyyy): ");
        java.util.Date novaDataTermino = null;
        try {
            novaDataTermino = sdf.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Data inválida.");
            return;
        }

        System.out.print("Digite a nova data de check-in (dd-MM-yyyy): ");
        java.util.Date novaDataCheckin = null;
        try {
            novaDataCheckin = sdf.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Data inválida.");
            return;
        }

        System.out.print("Digite a nova data de check-out (dd-MM-yyyy): ");
        java.util.Date novaDataCheckout = null;
        try {
            novaDataCheckout = sdf.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Data inválida.");
            return;
        }

        String queryAtualizar = "UPDATE reserva SET \"dataInicio\" = ?, \"dataTermino\" = ?, \"dataCheckin\" = ?, \"dataCheckout\" = ? WHERE \"numeroReserva\" = ?";
        DataBase.executeUpdate(queryAtualizar, novaDataInicio, novaDataTermino, novaDataCheckin, novaDataCheckout, idReserva);

        System.out.println("Reserva atualizada com sucesso.");
    }

    public void excluirReserva(int idReserva) throws SQLException {
        if (!verificaReserva(idReserva)) {
            throw new SQLException("Reserva não encontrada.");
        }

        String queryExcluir = "DELETE FROM reserva WHERE \"numeroReserva\" = ?";
        int linhasAfetadas = DataBase.executeUpdate(queryExcluir, idReserva);

        if (linhasAfetadas > 0) {
            System.out.println("Reserva excluída com sucesso.");
        } else {
            throw new SQLException("Erro ao excluir a reserva.");
        }
    }

    public boolean verificaReserva(int idReserva) throws SQLException {
        List<Reserva> reservas = listarReservas();
        for (Reserva reserva : reservas) {
            if (reserva.getId() == idReserva) {
                return true;  
            }
        }
        return false;  // Reserva não encontrada
    }

    public int obterUltimoNumeroReserva() throws SQLException {
        String query = "SELECT MAX(\"numeroReserva\") FROM reserva";
        List<List<Object>> result = DataBase.executeQuery(query);

        if (result.isEmpty() || result.get(0).get(0) == null) {
            throw new SQLException("Não foi possível obter o número da reserva.");
        }

        return (int) result.get(0).get(0);
    }
}
