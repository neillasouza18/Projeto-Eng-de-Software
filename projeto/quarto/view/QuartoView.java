package projeto.quarto.view;
import java.util.Scanner;
import java.sql.SQLException;

import projeto.quarto.controller.QuartoController;

public class QuartoView {
    QuartoController quartoUser = new QuartoController();
    Scanner scanner = new Scanner(System.in);

    public void quartoCadastro(){
        int opcao;
            System.out.println("\n");
            System.out.println("");
            System.out.println("\t----------------------");
            System.out.println("\t Selecione uma opção:");
            System.out.println("\t----------------------\n");
            System.out.println("[1] - Cadastrar quarto");
            System.out.println("[2] - Listar quartos ");
            System.out.println("[3] - Visualizar quarto");
            System.out.println("[4] - Editar quarto ");
            System.out.println("[5] - Excluir quarto ");
            System.out.println("[6] - Verificar disponibilidade ");
            System.out.println("[0] - Voltar");
            System.out.println("");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            System.out.println("");
            switch (opcao) {
                case 1:
                    try{
                        quartoUser.inserirQuarto(scanner);
                    }catch(SQLException e){
                        System.out.println("Erro ao cadastrar quarto "+ e.getMessage());
                    }     
                    break;
                case 2:
                    System.out.println("Listando quartos");
                    try {
                        System.out.println(quartoUser.listarQuartos());
                    } catch (SQLException e) {
                        System.out.println("Erro ao listar usuários: " + e.getMessage());
                    }
                    break;    
                case 3:
                    System.out.println("Digite o numero do quarto que deseja visualizar: ");
                    int num = scanner.nextInt();
                    try {
                        quartoUser.visualizarQuarto(num);
                    } catch (SQLException e) {
                        System.out.println("Erro ao visualizar usuário: " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        quartoUser.editarQuarto(scanner);
                    } catch (SQLException e) {
                        System.out.println("Erro ao editar usuário: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Digite o numero do quarto que deseja remover: ");
                    int excluir = scanner.nextInt();
                    try {
                        quartoUser.excluirQuarto(excluir);
                    } catch (SQLException e) {
                        System.out.println("Erro ao remover quarto " + e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        quartoUser.verificarDisponibilidadePorData(scanner);
                    } catch (SQLException e) {
                        System.out.println("Erro ao remover usuário: " + e.getMessage());
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


    public void menuQuarto(){
        int opcao;
            System.out.println("\n");
            System.out.println("");
            System.out.println("\t----------------------");
            System.out.println("\t Selecione uma opção:");
            System.out.println("\t----------------------\n");
            System.out.println("[1] - Listar quartos ");
            System.out.println("[2] - Verificar disponibilidade");
            System.out.println("[0] - Voltar");
            System.out.println("");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            System.out.println("");
            switch (opcao) {
                    case 1:
                    System.out.println("Listando quartos");
                    try {
                        System.out.println("");
                        System.out.println(quartoUser.listarQuartos());
                        System.out.println("");
                    } catch (SQLException e) {
                        System.out.println("Erro ao listar usuários: " + e.getMessage());
                    }
                    break;    
                case 2:
                    try {
                        System.out.println("");
                        quartoUser.verificarDisponibilidadePorData(scanner);
                        System.out.println("");

                    } catch (SQLException e) {
                        System.out.println("Erro ao verificar disponibilidade: " + e.getMessage());
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
}
