
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String QuerytoExecute = "MATCH (n) RETURN n ";
        
        
        //Executes the Cypher Query. Use the commented line below to just execute a query
        //ExecuteQuery(QuerytoExecute);
        
        
        //Executes and returns information. Use the commented line below to execute a query and retrieve information.
        //System.out.println(ExecuteRequestQuery(QuerytoExecute));
        
        //Insert an item
        int selection=1;
        while(selection !=-1){
            System.out.println("1/ Insert item");
            System.out.println("2/ Delete Data");
            System.out.println("3/ Read Item");
            System.out.println("4/ Update Item");
            System.out.println("5/ Exit");
            int choice;
            
            choice = sc.nextInt();
            if (selection == 1) {
                String type;
                System.out.println("1/ Person");
                System.out.println("2/ Enterprise");
                System.out.println("3/ Exit");
                type = sc.next();
                if (type=="1") {
                    
                }
                else if(type=="2"){
                    
                }
            }
            else if (choice==5) {
                selection = -1;
            }
            
        }
        System.out.println("Ended");
    }

    public static void InsertEnterprise(){
        
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
