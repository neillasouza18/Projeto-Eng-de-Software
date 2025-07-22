package projeto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private static final String URL = "jdbc:postgresql://aws-0-sa-east-1.pooler.supabase.com:6543/postgres";
    private static final String USER = "postgres.nduczwwykxhdcywzqvyv";
    private static final String PASSWORD = "hotelpousada";

    // Implementação do singleton
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static int executeUpdate(String query, Object... params) throws SQLException {
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof java.util.Date) {
                    java.sql.Date sqlDate = new java.sql.Date(((java.util.Date) params[i]).getTime());
                    stmt.setDate(i + 1, sqlDate);
                } else {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            return stmt.executeUpdate();
        }
    }

    public static List<List<Object>> executeQuery(String query, Object... params) throws SQLException {
        List<List<Object>> results = new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                List<Object> row = new ArrayList<>();
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                results.add(row);
            }
        }
        return results;
    }

    public static void closeConnection() throws SQLException { // Aplicação encerrada implica fechamento automático também.
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

}
