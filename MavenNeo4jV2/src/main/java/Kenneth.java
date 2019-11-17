
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Kenneth {

    Scanner read = new Scanner(System.in);

    public Kenneth() {

    }

    public int IntEntry() {
        int entry = 0;
        boolean entryvalidation = false;
        while (entryvalidation == false) {
            try {
                entry = read.nextInt();
                read = new Scanner(System.in);
                entryvalidation = true;
            } catch (Exception e) {
                System.out.println("Data Type incompatible, please try again.");
            }
        } // Forzando al usuario a que ingrese bien las cosas
        return entry;
    } // Metodo de validacion de entradas enteras.

    public void fakemain() {
        char resp = 'S';
        while (resp == 'S' || resp == 's') {

            System.out.println("*****Menu*****");
            System.out.println("1. New Solicitant Person.");
            System.out.println("2. New Solicitant Company.");
            System.out.println("3. Exisiting Company.");
            System.out.println("4. Existing Person.");
            System.out.println("5. Job Match.");
            int entry = IntEntry();
            switch (entry) {
                case 1:
                    NewSolicitantPerson();
                    break;
                case 2:
                    NewSolicitantCompany();
                    break;
                case 3:
                    ExistingCompany();
                    break;

                case 4:
                    break;
                default:
                    break;

            }
            System.out.println("Desea volver al menu [S/N]");
            resp = read.nextLine().charAt(0);
        }
    } //Fake Main Thread.

    public void NewSolicitantPerson() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("********** New Solicitant Application ************");
        System.out.println("Please enter your National Identification Number:");
        String idnumber = RequestNationalIdentification();
        System.out.println("Please enter your Complete Name:");
        String name = read.nextLine();
        System.out.println("Please enter your Age:");
        int age = RequestAge();
        System.out.println("Please enter your Address:");
        String address = read.nextLine();
        System.out.println("Please enter your Phone number:");
        String phonenumber = RequestPhoneNumber();
        //Falta agregar cosas

        // Falta chequear que no exista la persona en la database.
        //Falta chequear que el address no tenga caracteres invalidos como el "
        String query = CypherPersonCreator(idnumber, name, age, address, phonenumber);
        System.out.println(query);
        ExecuteQuery(query);
        System.out.println();
        System.out.println();
        System.out.println();
    } //Creates a new Solicitant Person

    public void NewSolicitantCompany() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("********** New Solicitant Company Application ************");

        String name = RequestCompanyName();

        String CIF = RequestCompanyCIF();
        System.out.println("Please enter the name of the Director of the Company:");
        read = new Scanner(System.in);
        String director = read.nextLine();
        System.out.println("Please enter the address of the Company:");
        String address = read.nextLine();

        String Cypher = CypherCompanyCreator(name, CIF, director, address); //Orders the creation of the Cypher Statement that will be used to create the company in NEO4J. Homologous to CypherPersonCreator.
        ExecuteQuery(Cypher); //Orders the execution of the Cypher Query.

        System.out.println();
        System.out.println();
        System.out.println();

    } //Creates a new Solicitant Company.

    public void ExistingCompany() {
        read = new Scanner(System.in);
        System.out.println("Please enter your CIF:");
        String CIF = read.nextLine();
        boolean companyexists = CheckCIF(CIF); //Checks if the company is in the database.
        if (companyexists) { //If the company exists log them into the menu
            char resp = 's'; 
            while (resp == 's' || resp == 'S') { //a While with user input tosimulte a menu for the Company Menu.
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println("**** Company Menu ******");
                System.out.println("1. View Company Information.");
                System.out.println("2. Modify Company Information.");
                System.out.println("3. View Job Offers.");
                System.out.println("3. Create New Job Offer.");
                System.out.println("4. Modify Job Offers.");
                int option = IntEntry();
                switch (option) {
                    case 1: 
                        System.out.println("");
                        ArrayList DatabaseRequest = ExecuteRequestQuery("MATCH (p:Company)\n" + "WHERE p.CIF = \"" + CIF + "\" Return p");
                        System.out.println(DatabaseRequest.size());
                        for (int i = 0; i < DatabaseRequest.size(); i++) {
                            System.out.println(DatabaseRequest);
                        }
                        break;
                    case 2:
                        System.out.println("");
                        System.out.println("What Company information do you want to change?");
                        System.out.println("1. Name");
                        System.out.println("2. Address");
                        System.out.println("3. Direction");
                        System.out.println("4. None");
                        int option2 = IntEntry();
                        if (option2 == 2) {
                            System.out.println("Please enter New Address:");
                            String address = read.nextLine();
                            ModifyAddressCIF(CIF, address);
                        } else if (option2 == 3) {
                            System.out.println("Please enter Director:");
                            String Director = read.nextLine();
                            ModifyDirectorCIF(CIF, Director);
                        } else if (option2 == 1) {
                            System.out.println("Please enter Name:");
                            String name = read.nextLine();
                            if(CheckName(name) == true){ 
                                System.out.println("");
                                System.out.println("Name is already taken.");
                            } else {
                                ModifyNameCIF(CIF, name);
                            }
                        }

                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("None Changed.");
                        break;
                }
                System.out.println("Do you want to return to the Company Menu? [S/N]");
                resp = read.nextLine().charAt(0);
            } // While Block with user response for the Meny.
        } else {
            System.out.println("CIF Invalido.");
        }

    } // Logs into an existing company's information and requests new personnelle.

    public String CypherPersonCreator(String idNumber, String name, int age, String address, String phonenumber) {
        idNumber = "\"" + idNumber + "\"";
        name = "\"" + name + "\"";
        address = "\"" + address + "\"";
        phonenumber = "\"" + phonenumber + "\"";
        String cypher = "CREATE (a:Person{idNumber :" + idNumber + ", Name :" + name + ", Age :" + age + ", Address :" + address + ", PhoneNumber: " + phonenumber + "})";
        return cypher;
    } //Creates the Cypher SQL Statement for the Creation of a person, given the information.

    public String CypherCompanyCreator(String name, String CIF, String director, String address) {
        name = "\"" + name + "\"";
        CIF = "\"" + CIF + "\"";
        director = "\"" + director + "\"";
        address = "\"" + address + "\"";
        String Cypher = "CREATE (a:Company{Name : " + name + ", CIF : " + CIF + ", Director : " + director + ", Address :" + address + "})";
        return Cypher;
    } //Creates the Cypher SQL Statement for the creation of a Company, given the information.

    public String RequestNationalIdentification() {
        ///////////////////       IMPORTANT

        //Any changes done to this method should be reflected on the RequestPhoneNumberMethod
        /////////////////
        String IdentificationNumber = "";
        read = new Scanner(System.in);
        int exec = 0; //Variable para que entre al while
        boolean valid = false;
        while (valid == false) {
            System.out.println("Please insert your Id number syntax XXXX-XXXX-XXXXX");
            String temporal = read.nextLine();
            Boolean syntaxcheck = false; // Variable used to check if the syntax is correct
            if (temporal.charAt(4) == '-' && temporal.charAt(9) == '-' && temporal.length() == 15) {
                syntaxcheck = true;
            } //Checks for hyphens in the syntax in the due position.
            Boolean numbercheck = true; // Variable to check if The XXXX in the syntax are numbers.
            if (syntaxcheck) {
                for (int i = 0; i < temporal.length(); i++) {
                    if (i != 4 && i != 9) {
                        try {
                            int breaker = Integer.parseInt(Character.toString(temporal.charAt(i))); // This assignation should fail if the character is not an Integer.
                        } catch (Exception e) {
                            e.printStackTrace();
                            numbercheck = false; //Integer check failed. So it does not numbercheck.
                        }

                    }
                }
            } //If the syntax is correct, then check that every character is an Integer.
            if (syntaxcheck && numbercheck) {

                ArrayList DatabaseRequest = ExecuteRequestQuery("MATCH (p:Person)\n" + "WHERE p.idNumber = \"" + temporal + "\"\n" + "RETURN p");
                if (DatabaseRequest.size() == 0) {
                    valid = true;
                    IdentificationNumber = temporal;
                } else {
                    System.out.println("That National Identification Number is already existent.");
                    valid = false;
                }

            } //If it has the correct syntax and it has digits inside then it is valid and should be ready to retrn the number.
        } //While it is an invalid Entry for the ID number, keep forcing the user to enter it correctly.
        return IdentificationNumber;
    } //Method to request Idenfication Number, verifies that is unique.

    public int RequestAge() {
        int age = 0;

        boolean valid = false;
        while (valid == false) {
            System.out.println("Age range from 18 - 100");
            age = IntEntry();
            if (age >= 18 && age <= 100) {
                valid = true;
            }
        }
        return age;
    } //Method for forcing the user to request age. Age range from 18-100

    public String RequestPhoneNumber() {
        ///////////////////       IMPORTANT

        //Any changes done to this method should be reflected on the RequestPhoneNumberMethod
        /////////////////
        String PhoneNumber = "";
        read = new Scanner(System.in);
        int exec = 0; //Variable para que entre al while
        boolean valid = false;
        while (valid == false) {
            System.out.println("Please insert your Phone Number syntax XXXX-XXXX");
            String temporal = read.nextLine();
            Boolean syntaxcheck = false; // Variable used to check if the syntax is correct
            if (temporal.length() == 9) {
                if (temporal.charAt(4) == '-') {
                    syntaxcheck = true;
                }

            } //Checks for hyphens in the syntax in the due position.
            Boolean numbercheck = true; // Variable to check if The XXXX in the syntax are numbers.
            if (syntaxcheck) {
                for (int i = 0; i < temporal.length(); i++) {
                    if (i != 4) {
                        try {
                            int breaker = Integer.parseInt(Character.toString(temporal.charAt(i))); // This assignation should fail if the character is not an Integer.
                        } catch (Exception e) {
                            e.printStackTrace();
                            numbercheck = false; //Integer check failed. So it does not numbercheck.
                        }

                    }
                }
            } //If the syntax is correct, then check that every character is an Integer.
            if (syntaxcheck && numbercheck) {

                String temporal2 = "\"" + temporal + "\"";

                ArrayList DatabaseRequest = ExecuteRequestQuery("MATCH (p:Person)\n" + "WHERE p.PhoneNumber = \"" + temporal2 + "\"\n" + "RETURN p");
                if (DatabaseRequest.size() == 0) {
                    valid = true;
                    PhoneNumber = temporal;
                } else {
                    System.out.println("The PhoneNumber is already existent.");
                    valid = false;
                } // This validation checks on the database for existing phone number.

            } //If it has the correct syntax and it has digits inside then it is valid and should be ready to retrn the number.
        } //While it is an invalid Entry for the ID number, keep forcing the user to enter it correctly.
        return PhoneNumber;

    } //Readapted RequestIdentificationNumber to work for PhoneNumber. Verifies it is unique.

    public String RequestCompanyName() {
        read = new Scanner(System.in);
        String name = "";
        String temporal = "";
        boolean valid = false;

        while (valid == false) {
            System.out.println("Please enter the Company Name:");
            temporal = read.nextLine();
            ArrayList DatabaseRequest = ExecuteRequestQuery("MATCH (p:Company)\n" + "WHERE p.Name = \"" + temporal + "\" Return p");
            if (DatabaseRequest.size() == 0) {
                valid = true;
                name = temporal;
            } else {
                System.out.println();
                System.out.println("Name is already existent in our databases.");
                valid = false;
            }
        } //Forcing the user to enter correct names.

        return name;
    } //Requests the name for the company and verifies if it is unique

    public String RequestCompanyCIF() {
        String CIF = "";
        read = new Scanner(System.in);
        boolean valid = false;
        String temporal = "";
        while (valid == false) {
            System.out.println("Please enter the CIF of the Company:");
            temporal = read.nextLine();

            ArrayList DatabaseRequest = ExecuteRequestQuery("MATCH (p:Company)\n" + "WHERE p.CIF = \"" + CIF + "\" Return p");
            if (DatabaseRequest.size() == 0) {
                valid = true;
                CIF = temporal;
            } else {
                System.out.println();
                System.out.println("CIF is already existent in our databases.");
                valid = false;
            }
        } //Forcing the user to enter correct CIF.

        return CIF;

    } //Requests Company CIF, verifies it is unique.

    public boolean CheckCIF(String CIF) {
        boolean exists = false;
        ArrayList DatabaseRequest = ExecuteRequestQuery("MATCH (p:Company)\n" + "WHERE p.CIF = \"" + CIF + "\" Return p");
        if (DatabaseRequest.size() == 0) {
            exists = false;
        } else {
            exists = true;
        }
        return exists;
    } //Checks if the CIF parameter is of one the companies the database has registered.
    
    public boolean CheckName(String name){
        boolean exists = false;
        ArrayList DatabaseRequest = ExecuteRequestQuery("MATCH (p:Company)\n" + "WHERE p.Name = \"" + name + "\" Return p");
        if(DatabaseRequest.size() == 0){
            exists = false; 
        } else {
            exists = true;
        }
        return exists;
    } //Checks if Name Exists in the database for companies that we have.
    
    public void ModifyNameCIF(String CIF, String name){
        
        String Cypher = "MATCH (a:Company )\n" +
"Where a.CIF =  \""+CIF+"\"\n" +
"set a.Name = \""+name+"\"";
        ExecuteQuery(Cypher);
        System.out.println("");
        System.out.println("Task Completed");
        
    } //Modifies the Name of the Company with the CIF as give.
    
    public void ModifyAddressCIF(String CIF, String address){
        String Cypher = "MATCH (a:Company )\n" +
"Where a.CIF =  \""+CIF+"\"\n" +
"set a.Address = \""+address+"\"";
        ExecuteQuery(Cypher);
        System.out.println("");
        System.out.println("Task Completed");
    } // Modifies the Address of the Company with the CIF as given
    
    public void ModifyDirectorCIF(String CIF, String Director){
        String Cypher = "MATCH (a:Company )\n" +
"Where a.CIF =  \""+CIF+"\"\n" +
"set a.Director = \""+Director+"\"";
        ExecuteQuery(Cypher);
        System.out.println("");
        System.out.println("Task Completed");
        
    }

    public static void ExecuteQuery(String query) {
        try {
            DBA database = new DBA();
            database.query.execute(query);
            // database.commit();
            database.Disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "The id of the person is already existent.");
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
