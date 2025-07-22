package projeto.usuario.view;
import java.util.Scanner;
import java.sql.SQLException;

import projeto.usuario.controller.UsuarioController;
import projeto.usuario.model.Usuario;
import projeto.quarto.controller.QuartoController;
import projeto.quarto.view.QuartoView;
import projeto.reserva.view.ReservaView;
import projeto.pagamento.view.PagamentoView;
import projeto.reserva.controller.ReservaController;

public class UsuarioView {
    private Scanner scanner;
    private UsuarioController controleUser;
    private QuartoView quartoView;
    private ReservaView reservaView;
    private PagamentoView pagamentoView;
    

    public UsuarioView() {
        this.scanner = new Scanner(System.in);
        this.controleUser = new UsuarioController();
        this.quartoView = new QuartoView();
        this.reservaView = new ReservaView();
        this.pagamentoView = new PagamentoView();
    }

    ReservaController reserva = new ReservaController();
    QuartoController quarto = new QuartoController();

   
    public void login() {
        int opcao;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n\t----------------------");
            System.out.println("\t Selecione uma opção:");
            System.out.println("\t----------------------\n");
            System.out.println("[1] - Acessar como ADM");
            System.out.println("[2] - Acessar como usuário");
            System.out.println("[0] - Sair");
            System.out.println("");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();  
    
            switch (opcao) {
                case 1:
                    try {
                        if(controleUser.autenticarAdmin(scanner)){
                            menuPrincipal();
                        }
                        else{
                            System.out.println("");
                            System.out.println("\tNome de usuario ou senha estao incorretos.");
                            System.out.println("");
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao autenticar: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Você já tem uma conta? (sim/nao)");
                    String resposta = scanner.nextLine();
                    if (resposta.equalsIgnoreCase("sim")) {
                       try {
                            Usuario usuario = controleUser.autenticarUser(scanner);
                            if (usuario != null) {
                                menuUsuario(usuario);
                            }
                       } catch (Exception e) {
                        System.out.println("Erro ao logar usuario: "+ e.getMessage());
                       }
                    } else if (resposta.equalsIgnoreCase("nao")) {
                        try {
                            controleUser.inserirUsuario(scanner);
                        } catch (SQLException e) {
                            System.out.println("Erro ao criar usuário: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Resposta inválida, por favor digite 'sim' ou 'nao'.");
                    }
                    break;
                case 0:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
    

    public void menuPrincipal() {
        int opcao;
        while (true) {
            System.out.println("\n\t----------------------");
            System.out.println("\t Selecione uma opção:");
            System.out.println("\t----------------------\n");
            System.out.println("[1] - Gerenciar usuarios");
            System.out.println("[2] - Gerenciar quartos");
            System.out.println("[3] - Gerenciar reservas");
            System.out.println("[4] - Gerenciar pagamentos");
            System.out.println("[0] - Voltar");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    gerenciarUsuario(scanner);
                    break;
                case 2:
                    quartoView.quartoCadastro();;
                    break;
                case 3:
                    reservaView.menuReserva();;
                    break;
                case 4:
                    pagamentoView.menuPagamento();;
                    break;
                case 0:
                System.out.println("Voltando...");
                    return;  
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    public void gerenciarUsuario(Scanner scanner) {
        System.out.println("\nSelecione uma opção:");
        System.out.println("[1] - Remover");
        System.out.println("[2] - Visualizar");
        System.out.println("[3] - Listar");
        System.out.println("[4] - Editar");
        System.out.println("[0] - Voltar");
        System.out.println("");
        System.out.print("Selecione a opção: ");
        int opcaoGerencial = scanner.nextInt();
        scanner.nextLine(); 
    
        switch (opcaoGerencial) {
      
          case 1:
            System.out.println("Digite o Username do usuario que deseja remover: ");
            String userRemover = scanner.nextLine();
            try {
                controleUser.excluirUsuario(userRemover);
            } catch (SQLException e) {
                System.out.println("Erro ao remover usuário: " + e.getMessage());
            }
            break;
          case 2:
            System.out.println("Digite o Username do usuario que deseja visualizar: ");
            String userVisualizar = scanner.nextLine();
            try {
                controleUser.visualizarUsuario(userVisualizar);
            } catch (SQLException e) {
                System.out.println("Erro ao visualizar usuário: " + e.getMessage());
            }
            break;
          case 3:
            System.out.println("");
            System.out.println("Listando todos os usuários...");
            try {
                controleUser.listarUsuarios();
            } catch (SQLException e) {
                System.out.println("Erro ao listar usuários: " + e.getMessage());
            }
            break;
          case 4:
            try {
                controleUser.editarUsuario(scanner);
            } catch (SQLException e) {
                System.out.println("Erro ao editar usuário: " + e.getMessage());
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

    public void menuUsuario(Usuario usuario) {
        int opcao;
        while (true) {
            System.out.println("\n\t----------------------");
            System.out.println("\t Selecione uma opção:");
            System.out.println("\t----------------------\n");
            System.out.println("[1] - Visualizar quartos");
            System.out.println("[2] - Realizar reserva");
            System.out.println("[3] - Visualizar pagamentos");
            System.out.println("[0] - Voltar");
            System.out.println("");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    quartoView.menuQuarto();
                    break;
                case 2:
                System.out.println("Digite o id do quarto disponivel: ");
                int valor = Integer.parseInt(scanner.nextLine());
                    try {
                        if(quarto.verificarDisponibilidade(valor)){
                            reservaView.menuUser(usuario, quarto.getQuartoByNumero(valor));
                        }else{
                            System.out.println("O quarto se encontra indisponivel.");
                        }
                            
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        pagamentoView.menuUser();
                    } catch (Exception e) {
                        System.out.println("Voce ainda nao realizou uma reserva:" + e.getMessage());
                    }
                   
                    break;
                case 0:
                    System.out.println("Voltando...");
                    return; 
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}
