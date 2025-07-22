package projeto.quarto.controller;

import projeto.quarto.model.Quarto;
import projeto.DataBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class QuartoController {
    public SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public void inserirQuarto(Scanner scanner) throws SQLException {
        System.out.print("Digite o número do quarto: ");
        int numero = Integer.parseInt(scanner.nextLine());

        System.out.print("Digite as comodidades do quarto: ");
        String comodidades = scanner.nextLine();

        System.out.print("Digite a capacidade do quarto: ");
        int capacidade = Integer.parseInt(scanner.nextLine());

        System.out.print("Digite a tarifa do quarto: ");
        float tarifa = Float.parseFloat(scanner.nextLine());

        System.out.print("Disponível (true/false): ");
        boolean disponibilidade = Boolean.parseBoolean(scanner.nextLine());

        
        System.out.print("Data de início (dd-MM-yyyy): ");
        Date verificaData = null;

        try {
            verificaData = sdf.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Data inválida." + e.getMessage());
            return;
        }

        Quarto novoQuarto = new Quarto(numero, comodidades, capacidade, tarifa, verificaData, disponibilidade);
        criarQuarto(novoQuarto);
        System.out.println("");
        System.out.println("--------------------------------\n");
        System.out.println("Quarto adicionado com sucesso.\n");
        System.out.println("----------------------------------");
    }

    public void criarQuarto(Quarto quarto) throws SQLException {
        // Verifica se o quarto já existe no banco de dados
        String queryVerificar = "SELECT numero FROM quarto WHERE numero = ?";
        List<List<Object>> resultado = DataBase.executeQuery(queryVerificar, quarto.getNumero());

        if (!resultado.isEmpty()) {
            throw new SQLException("Quarto já existe.");
        }

        // Insere o novo quarto no banco de dados
        String queryInserir = "INSERT INTO quarto (numero, comodidades, capacidade, tarifa, \"verificaData\", disponivel) VALUES (?, ?, ?, ?, ?, ?)";
        DataBase.executeUpdate(queryInserir, quarto.getNumero(), quarto.getComodidades(), quarto.getCapacidade(), quarto.getTarifa(), quarto.getVerificaData(), quarto.getDisponibilidade());

        System.out.println("Quarto criado com sucesso.");
    }

    public List<Quarto> listarQuartos() throws SQLException {
        List<Quarto> quartos = new ArrayList<>();
        String query = "SELECT * FROM quarto";
        
        List<List<Object>> resultado = DataBase.executeQuery(query);
        
        for (List<Object> linha : resultado) {
            int numero = (int) linha.get(0);
            String comodidades = (String) linha.get(1);
            int capacidade = (int) linha.get(2);
            float tarifa = (float) linha.get(3);
            java.sql.Date verificaData = (java.sql.Date) linha.get(4); 
            boolean disponivel = (boolean) linha.get(5);
            
            quartos.add(new Quarto(numero, comodidades, capacidade, tarifa, verificaData ,disponivel));
        }
        
        return quartos;
    }

    public void excluirQuarto(int numero) throws SQLException {
        List<List<Object>> quartos = DataBase.executeQuery("SELECT * FROM quarto WHERE numero = ?", numero);
        if (quartos.isEmpty()) {
            throw new SQLException("Quarto não encontrado.");
        }

        DataBase.executeUpdate("DELETE FROM quarto WHERE numero = ?", numero);

        System.out.println("");
        System.out.println("--------------------------------\n");
        System.out.println("Quarto excluído com sucesso.\n");
        System.out.println("----------------------------------");
    }

    public boolean verificarDisponibilidade(int numero) throws SQLException {
        List<List<Object>> quartos = DataBase.executeQuery("SELECT disponivel FROM quarto WHERE numero = ?", numero);
        if (quartos.isEmpty()) {
            throw new SQLException("Quarto não encontrado.");
        }
        return (boolean) quartos.get(0).get(0);
    }

    public void editarQuarto(Scanner scanner) throws SQLException {
        System.out.println("");
        System.out.print("Digite o número do quarto que deseja editar: ");
        int numero = Integer.parseInt(scanner.nextLine());

        List<List<Object>> quartos = DataBase.executeQuery("SELECT * FROM quarto WHERE numero = ?", numero);
        
        if (quartos.isEmpty()) {
            throw new SQLException("Quarto não encontrado.");
        }

        List<Object> quartoData = quartos.get(0);
        Quarto quarto = new Quarto(
            (int) quartoData.get(0),
            (String) quartoData.get(1),
            (int) quartoData.get(2),
            (float) quartoData.get(3),
            (Date) quartoData.get(4),
            (boolean) quartoData.get(5)
        );

        System.out.print("Digite as novas comodidades (anterior: " + quarto.getComodidades() + "): ");
        quarto.setComodidades(scanner.nextLine());

        System.out.print("Digite a nova capacidade (anterior: " + quarto.getCapacidade() + "): ");
        quarto.setCapacidade(Integer.parseInt(scanner.nextLine()));

        System.out.print("Digite a nova tarifa (anterior: " + quarto.getTarifa() + "): ");
        quarto.setTarifa(Float.parseFloat(scanner.nextLine()));

        System.out.print("Digite a nova disponibilidade (Sim/Não) (anterior: " + (quarto.getDisponibilidade() ? "Sim" : "Não") + "): ");
        boolean novaDisponibilidade = scanner.nextLine().trim().equalsIgnoreCase("sim");
        quarto.setDisponibilidade(novaDisponibilidade);

        DataBase.executeUpdate("UPDATE quarto SET comodidades = ?, capacidade = ?, tarifa = ?, disponivel = ? WHERE numero = ?",
            quarto.getComodidades(), quarto.getCapacidade(), quarto.getTarifa(), quarto.getDisponibilidade(), quarto.getNumero());

        System.out.println("------------------------------\n");
        System.out.println("Quarto editado com sucesso.\n");
        System.out.println("------------------------------\n");
    }
    
    public List<Integer> verificarDisponibilidadePorData(Scanner scanner) throws SQLException {
        List<Integer> quartosDisponiveis = new ArrayList<>();
        System.out.print("Inclua a data para a verificação (dd-MM-yyyy): ");

        Date dataRequerida = null;
        
        try {
            dataRequerida = sdf.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.err.println("Formato de data inválido. Use o formato dd-MM-yyyy.");
            return quartosDisponiveis;
        }
        java.sql.Date sqlDataRequerida =  new java.sql.Date(dataRequerida.getTime());
        List<List<Object>> quartos = DataBase.executeQuery("SELECT numero, \"verificaData\" FROM quarto WHERE \"verificaData\" = ?", sqlDataRequerida);

        for (List<Object> quartoData : quartos) {
            int numero = (int) quartoData.get(0);
            Date verificaData = (Date) quartoData.get(1);
            
            if (verificaData != null && verificaData.equals(dataRequerida)) {
                quartosDisponiveis.add(numero);
            }
        }

        System.out.println("Disponível(eis):");
        System.out.println(quartosDisponiveis);
        
        return quartosDisponiveis;
    }

    public String visualizarQuarto(int numero) throws SQLException {
        List<List<Object>> quartosData = DataBase.executeQuery("SELECT numero, comodidades, capacidade, tarifa, \"verificaData\", disponivel FROM quarto WHERE numero = ?", numero);
        
        if (quartosData.isEmpty()) {
            return "Quarto não encontrado.";
        }
        
        List<Object> quartoData = quartosData.get(0); // Assume que há no máximo um quarto com esse número
        int numeroQuarto = (int) quartoData.get(0);
        String comodidades = (String) quartoData.get(1);
        int capacidade = (int) quartoData.get(2);
        float tarifa = (float) quartoData.get(3);
        Date verificaData = (Date) quartoData.get(4);
        boolean disponibilidade = (boolean) quartoData.get(5);
        
        Quarto quarto = new Quarto(numeroQuarto, comodidades, capacidade, tarifa, verificaData, disponibilidade);
        
        System.out.println("\nQuarto encontrado!\n" + quarto.toString());
        return "Quarto encontrado: " + quarto.toString();
    }

    public Quarto getQuartoByNumero(int numero) throws SQLException {
        String query = "SELECT * FROM quarto WHERE numero = ?";
        List<List<Object>> resultado = DataBase.executeQuery(query, numero);

        if (resultado.isEmpty()) {
            throw new SQLException("Quarto inexistente.");
        }

        List<Object> linha = resultado.get(0);
        int numeroQuarto = (int) linha.get(0);
        String comodidades = (String) linha.get(1);
        int capacidade = (int) linha.get(2);
        float tarifa = (float) linha.get(3);
        Date verificaData = (Date) linha.get(4);
        boolean disponibilidade = (boolean) linha.get(5);

        return new Quarto(numeroQuarto, comodidades, capacidade, tarifa, verificaData, disponibilidade);
    }
}
