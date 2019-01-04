/*
 * Teste de conexão SQLite
 */
package sqlite;

import connect.ConnectSQLite;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sávio Andres
 */
public class SQLite {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConnectSQLite conec = new ConnectSQLite();
        conec.conectar();
        String sqlInserir = "INSERT INTO user ("
                + "id,"
                + "nome,"
                + "idade"
                + ") VALUES(?,?,?)"
                + ";";
        
        PreparedStatement preparedStatement = conec.criarPreparedStatement(sqlInserir);
        
        try {
            preparedStatement.setInt(1, 4);
            preparedStatement.setString(2, "cicrano");
            preparedStatement.setInt(3, 12);
            
            int resultado = preparedStatement.executeUpdate();
            
            if (resultado == 1) {
                System.out.println("Sucesso");
            } else {
                System.out.println("Falha");
            }
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SQLite.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            conec.desconectar();
        }
        
        //Buscar dados
        ResultSet resultSet = null;
        Statement statement = null;
        
        conec.conectar();
        String query = "SELECT * FROM user;";
        
        statement = conec.criarStatement();
        
        try {
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                System.out.println("Pessoa:");
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("Nome: " + resultSet.getString("nome"));
                System.out.println("Idade: " + resultSet.getInt("idade"));
                System.out.println("-----------------------");
            }
            
        } catch(SQLException e) {
            System.err.println(e);
        } finally {
            try {
                resultSet.close();
                statement.close();
                conec.desconectar();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        
    }
    
}
