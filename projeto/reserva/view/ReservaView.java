package projeto.reserva.view;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.List;

import projeto.quarto.controller.QuartoController;
import projeto.quarto.model.Quarto;
import projeto.reserva.controller.ReservaController;
import projeto.usuario.controller.UsuarioController;
import projeto.usuario.model.Usuario;
import projeto.reserva.model.Reserva;

public class ReservaView {
    UsuarioController usuarioController = new UsuarioController();
    QuartoController quartoController = new QuartoController();
    ReservaController reservaController = new ReservaController();
    Scanner scanner = new Scanner(System.in);

    public void menuReserva() {
        int opcao;
            System.out.println("\n");
            System.out.println("\t----------------------");
            System.out.println("\t Selecione uma opção:");
            System.out.println("\t----------------------\n");
            System.out.println("[1] - Realizar reserva");
            System.out.println("[2] - Listar reservas");
            System.out.println("[3] - Visualizar reserva");
            System.out.println("[4] - Editar reserva");
            System.out.println("[5] - Cancelar reserva");
            System.out.println("[6] - Verifica reserva");
            System.out.println("[0] - Voltar");
            System.out.println("");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer de entrada
            System.out.println("");
            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome de usuário: ");
                    String username = scanner.nextLine();
                    
                    System.out.print("Digite o número do quarto: ");
                    int numeroQuarto = Integer.parseInt(scanner.nextLine());

                    try {
                        Usuario usuario = usuarioController.getUsuarioByUsername(username);
                        Quarto quarto = quartoController.getQuartoByNumero(numeroQuarto);

                        if (usuario == null || quarto == null) {
                            System.out.println(usuario == null ? "Usuário não encontrado." : "Quarto não encontrado.");
                            break;
                        }

                        reservaController.realizarReserva(usuario, quarto, scanner);
                    } catch (SQLException e) {
                        System.out.println("Erro ao realizar reserva: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println(reservaController.listarReservas()); 
                           
                        break;
                    } catch (SQLException e) {
                        System.out.println("Erro ao listar reservas: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Digite o ID da reserva: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer de entrada
                
                    try {
                        reservaController.visualizarReserva(id);
                    } catch (SQLException e) {
                        System.out.println("Erro ao visualizar reserva: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("Digite o ID da reserva para edição: ");
                    int idEdit = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer de entrada
                   
                    try {
                        reservaController.editarReserva(idEdit, scanner);
                    } catch (SQLException e) {
                        System.out.println("Erro ao editar reserva: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.print("Digite o ID da reserva a ser cancelada: ");
                    int value = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer de entrada
            
                    try {
                        reservaController.cancelarReserva(value);
                    } catch (SQLException e) {
                        System.out.println("Erro ao cancelar reserva: " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.print("Digite o ID para verificar a reserva: ");
                    int input = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer de entrada
            
                    try {
                        System.out.println(reservaController.verificaReserva(input));
                    } catch (SQLException e) {
                        System.out.println("Erro ao cancelar reserva: " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
       
    }


    public void menuUser(Usuario usuario, Quarto quarto){
        int opcao;
        do {
            System.out.println("\n");
            System.out.println("\t----------------------");
            System.out.println("\t Selecione uma opção:");
            System.out.println("\t----------------------\n");
            
            System.out.println("[1] - Listar reservas");
            System.out.println("[2] - Realizar reserva");
            System.out.println("[3] - Cancelar reserva");
            System.out.println("[0] - voltar");
            System.out.println("");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer de entrada
            System.out.println("");
            switch (opcao) {
                case 1:
                    try {
                        List<Reserva> Reservas = reservaController.listarReservas();
                        for (Reserva reserva: Reservas) {
                            System.out.println(reserva);
                        }
                    } catch (SQLException e) {
                        System.out.println("Erro ao visualizar reserva: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Digite o ID da reserva a ser cancelada: ");
                    int value = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer de entrada
            
                    try {
                        reservaController.cancelarReserva(value);
                    } catch (SQLException e) {
                        System.out.println("Erro ao cancelar reserva: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        reservaController.realizarReserva(usuario, quarto, scanner);
                    } catch (SQLException e) {
                        System.out.println("Erro ao realizar reserva: " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (opcao != 0);
    }
}
