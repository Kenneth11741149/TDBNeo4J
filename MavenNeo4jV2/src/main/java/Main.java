

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static TesterBeta tb = new TesterBeta();

    public static void main(String[] args) {
        System.out.println("Started");
        Kenneth test = new Kenneth();
        test.fakemain();
        //ExecuteQuery("");
        
        
        
        ///////////////////////////////////////////////////////NOOO BORRARR SE PODRIAN OCUPAR LUEGO.
        
        
        
        
       // ArrayList DatabaseRequest = ExecuteRequestQuery("MATCH (p:Company)\n" +
//"WHERE p.Name = \"Erickson\" Return p");
       // System.out.println(DatabaseRequest.size());
       // System.out.println(DatabaseRequest.toString());
        
        
        //ArrayList DatabaseRequest = ExecuteRequestQuery("MATCH (p:Person)\n" + "WHERE p.PhoneNumber = \"" + "9637-4467" + "\"\n" + "RETURN p");
        //System.out.println(DatabaseRequest.size());
        
        
        ///////////////////////////////////////////////////// NO BORRAR SE PODRIAN OCUPAR LIEGO
        
        
        
        /*int selection=1;
        while(selection !=-1){
            System.out.println("1/ Insert item");
            System.out.println("2/ Delete Data");
            System.out.println("3/ Read Item");
            System.out.println("4/ Update Item");
            System.out.println("5/ Exit");
            int choice=0;

            choice = sc.nextInt();
            System.out.println("...");
            System.out.println(" ");
            System.out.println("...");
            System.out.println(choice);
            if (choice == 1) {
                int type;
                System.out.println("1/ Person");
                System.out.println("2/ Enterprise");
                System.out.println("3/ Exit");
                type = sc.nextInt();
                if (type==1) {
                    String dataFrame="";
                    dataFrame= tb.createUser();
                    //Retrieved and ready for delivery
                    
                }
                else if(type==2){
                    String dataEFrame="";
                    dataEFrame = tb.createEnterprise();
                }
            }
            if (choice == 2) {
                System.out.println("1/ Delete User");
                System.out.println("2/ Delete Enterprise");
                System.out.println("3/ *** CORE PURGE ***");
                int select;
                select = sc.nextInt();
                if (select == 1) {
                    String Identifier="";
                    System.out.println("Enter the ID: ");
                    Identifier= sc.nextLine();
                    String deleteTuple="";
                    deleteTuple = tb.deleteUser(Identifier);
                    ExecuteQuery(deleteTuple);
                }else if(select == 2){
                    String Identifier="";
                    System.out.println("Enter the IP: ");
                    Identifier= sc.nextLine();
                    String deleteTuple="";
                    deleteTuple = tb.deleteEnterprise(Identifier);
                    ExecuteQuery(deleteTuple);
                }else if(select == 3){
                    System.out.println("WARNING");
                    System.out.println("Core purge will delete all data"
                            + "stored in the database");
                    System.out.println("Continue? \n1/ Yes, 2/No");
                    String securityChoice="";
                    securityChoice = sc.nextLine();
                    if (securityChoice=="1") {
                        String queryExecutePurge="MATCH (n)\n" +"OPTIONAL MATCH (n)-[r]-()\n" +"DELETE n,r";
                        ExecuteQuery(queryExecutePurge);
                    }else{
                        System.out.println(".. Process killed ..");
                    }
                }
            }
            else if (choice==5) {
                selection = -1;
            }
            
        }*/
        System.out.println("Ended");
        String QuerytoExecute = "MATCH (n) RETURN n";

        //Executes the Cypher Query. Use the commented line below to just execute a query
        //ExecuteQuery(QuerytoExecute);
        //Executes and returns information. Use the commented line below to execute a query and retrieve information.
        //System.out.println(ExecuteRequestQuery(QuerytoExecute));
    }

    //Favor no tocar gracias.
    public static void ExecuteQuery(String query) {
        try {
            DBA database = new DBA();
            database.query.execute(query);
            //database.commit();
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
