import java.util.Scanner;
public class TesterBeta {
   static Scanner read = new Scanner(System.in); 
   
   //Node Creatos
   /*
   Used for only node creation
   Can't replace or delete data
   See the following methods for updating and deleting items
   */
    public  String createUser(){
        String returnData="";
        String name="";
        String GPA="";
        String legalities="";
        String academic="";
        String laborEXP="";
        String contractAsk="";
        String salaryAsked="";
        String ID="";
        
        System.out.println("User name: ");
        name = read.nextLine();
        System.out.println("GPA: ");
        GPA = read.nextLine();
        System.out.println("Legalities: ");
        legalities = read.nextLine();
        System.out.println("Academic status: ");
        academic=read.nextLine();
        System.out.println("Labor experience: ");
        laborEXP= read.nextLine();
        System.out.println("Asked contract: ");
        contractAsk = read.nextLine();
        System.out.println("Asked salary: ");
        salaryAsked= read.nextLine();
        System.out.println("ID: ");
        ID = read.nextLine();
        
        //builder
        returnData+="Create(p:Person {";
        returnData+="Name: \" "+name+"\" ,";
        returnData+="ID: \" "+ID+"\" ,";
        returnData+="GPA: \" "+GPA+"\" ,";
        returnData+="Academic: \" "+academic+"\" ,";
        returnData+="Labor: \" "+laborEXP+"\" ,";
        returnData+="Contract: \" "+contractAsk+"\" ,";
        returnData+="Salary: \" "+salaryAsked+"\" ";
        returnData+="})";
        //System.out.println(returnData);

        return returnData;
    }
    
    public String createEnterprise(){
        String returnENT="";
        String agencyName="";
        String EnterPriseIP="";
        String DirectorName="";
        String availableJobs="";
        String address="";
        System.out.println("Enterprise name: ");
        agencyName = read.nextLine();
        System.out.println("IP: ");
        EnterPriseIP = read.nextLine();
        System.out.println("Director: ");
        DirectorName = read.nextLine();
        System.out.println("Address ");
        address = read.nextLine();
        availableJobs="100";
        
        //Builder
        returnENT+="Create{e:Enterprise{";
        returnENT+="EnterpriseName: \" "+agencyName+"\" ,";
        returnENT+="IP: \" "+EnterPriseIP+"\" ,";
        returnENT+="DirectorName: \" "+DirectorName+"\" ,";
        returnENT+="Address: \" "+address+"\" ,";
        returnENT+="AvailableJobs: \" "+availableJobs+"\" ";
        returnENT+="})";
        return returnENT;
    }
    
    //Deleters
    public String deleteUser(String ID){
        String retVal="";
        retVal+="MATCH (n {";
        retVal+="ID: ";
        retVal+="'"+ID+"'";
        retVal+=" }) DETACH DELETE n";
        return retVal;
    }
    
}
