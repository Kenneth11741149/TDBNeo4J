
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBA {

    private Connection con;
    public Statement query;

    public DBA (){
        Connect();
    }
    
   

    public void Connect() {
        try {
            System.out.println("Attempting to connect to database.");
            con = DriverManager.getConnection("jdbc:neo4j:bolt://localhost/?user=neo4j,password=1234,scheme=basic");
            query = con.createStatement();
            System.out.println("Connection with the database successfull.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error establishing a connection with the database. Kenneth's Design");
        }
    } //Establish a connection with the database.

    public void Disconnect() {

        try {
            query.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error Disconnecting with the database. Kenneth's Design");
        }
    } //Disconnect from the database
    
    public void commit(){
       try{
           con.commit();
       }catch(Exception e){
           System.out.println("Error on the database commit. Kenneth's Design.");
       }
   } //Commit changes
    
}
