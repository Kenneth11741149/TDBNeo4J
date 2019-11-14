
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String QuerytoExecute = "MATCH (n) RETURN n ";
        
        
        //Executes the Cypher Query. Use the commented line below to just execute a query
        //ExecuteQuery(QuerytoExecute);
        
        
        //Executes and returns information. Use the commented line below to execute a query and retrieve information.
        //System.out.println(ExecuteRequestQuery(QuerytoExecute));
        
        System.out.println("Ended");
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //Favor no tocar gracias.
    public static void ExecuteQuery(String query) {
        try {
            DBA database = new DBA();
            database.query.execute(query);
            database.commit();
            database.Disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error during ExecuteQuery. Kenneth's Design.");
        }
    } //Execute String query.

    public static ArrayList ExecuteRequestQuery(String query) { //Returns an arraylist with the results
        try {
            DBA database = new DBA();
            database.query.execute(query);
            ResultSet rs = database.query.getResultSet(); //Tabla en la memoria RAM.
            ArrayList results = new ArrayList();
            while (rs.next()) { //Hago un cursor que vaya viendo tupla por tupla que vaya analizando.
                results.add(rs.getString(1));
            }
            database.Disconnect();
            return results;
        } catch (Exception e) {
            System.out.println("Error during ExecuteRequestQuery. Kenneth's Design.");
            e.printStackTrace();
            return null;
        }

    } //Execute a query that requests information from the database.

}
