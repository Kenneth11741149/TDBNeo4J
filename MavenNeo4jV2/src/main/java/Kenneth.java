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
                    ExistingCompany(); //Incomplete
                    break;
                case 4:
                    ExistingPerson(); //Incomplete
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
        System.out.println();
        System.out.println("Please enter your Complete Name:");
        String name = read.nextLine();
        System.out.println("Please enter your Age:");
        int age = RequestAge();
        System.out.println("Please enter your Address:");
        String address = read.nextLine();
        System.out.println("Please enter your Phone number:");
        String phonenumber = RequestPhoneNumber();
        System.out.println();
        System.out.println("Do you possess a health history?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.println("Enter your response? [1/2]");
        String History = YesNoConverter(LimitedIntEntry(1, 2));
        System.out.println("Do you possess physical health problems?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.println("Enter your response? [1/2]");
        String Physical = YesNoConverter(LimitedIntEntry(1, 2));
        System.out.println("Do you possess mental health problems?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.println("Enter your response? [1/2]");
        String Mental = YesNoConverter(LimitedIntEntry(1, 2));
        System.out.println("Are you, or have you been a participant of Military Services?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.println("Enter your response? [1/2]");
        String Military = YesNoConverter(LimitedIntEntry(1, 2));
        System.out.println("Do you possess a history with the Law?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.println("Enter your response? [1/2]");
        String Justice = YesNoConverter(LimitedIntEntry(1, 2));
        System.out.println("What type of academic studies do you possess?");
        System.out.println("1. Middle school or below");
        System.out.println("2. High School or below");
        System.out.println("3. University and above");
        int academic = LimitedIntEntry(1, 3);
        ArrayList<String> carreers;
        String AcademicPreparation = "";
        if (academic == 3) {
            carreers = CarreersIntToString(GetUserCarreers());
            for (int i = 0; i < carreers.size(); i++) {
                if (i < carreers.size() - 1) {
                    AcademicPreparation += carreers.get(i).toString() + ",";
                } else {
                    AcademicPreparation += carreers.get(i).toString();
                }
            }
            System.out.println(AcademicPreparation);
        } else if (academic == 1) {
            AcademicPreparation = "Middle School or below.";
        } else if (academic == 2) {
            AcademicPreparation = "High School or below.";
        }
        //////////////////////////////////////////////////////////////////////////////////////////Hace falta validar que no metan comillas a las strings.
        //Falta chequear que el address no tenga caracteres invalidos como el "
        //New Person Attributes(idNumber, name, age, address, phonenumber,HealthHistory,PhysicalProblems,MentalProblems,MilitaryService,LawIssues,AcademicPreparation
        String query = CypherPersonCreator(idnumber, name, age, address, phonenumber, History, Physical, Mental, Military, Justice, AcademicPreparation);
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
                System.out.println("3. View Job Applicants");
                System.out.println("4. Create New Job Offer.");
                System.out.println("5. Modify Job Offers.");
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
                            if (CheckName(name) == true) {
                                System.out.println("");
                                System.out.println("Name is already taken.");
                            } else {
                                ModifyNameCIF(CIF, name);
                            }
                        }

                        break;
                    case 3:
                    	//Watch all interest people on the jobs
                    	System.out.println();
                    	System.out.println();
                    	System.out.println("Applicants");
                    	ArrayList<String> idApplicants=new ArrayList<String>();
                    	idApplicants= peopleRequestingJobs(CIF);
                    	String continueContracts="1";
                    	while(continueContracts=="1"){
                    		int limit=0;
                    		for(int i=0; i<idApplicants.size(); i++){
                    			limit=i;
                    			String userName="";
                                        String useless = idApplicants.get(i);
                    			userName = ExecuteRequestQuery("MATCH (p:Person)\n" +"WHERE p.idNumber = \""+useless+ "\" "
                    				+ "Return p.Name");
                                        
                    			System.out.println(i+" "+idApplicants.get(i)+" "+userName);
                    		}
                    		System.out.println("Do you wish to hire any of the following personnel?");
                    		System.out.println("1/ Yes");
                    		System.out.println("2/ No");
                    		int decision = IntEntry();
                    		if(decision==1){
                    			System.out.println("Which one will you like to choose? [0 -"+limit+"]");
                    			int selectedPersonnel = LimitedIntEntry(0, limit);
                    			String idSelected = idApplicants.get(selectedPersonnel);
                    			String queryHire = CypherPersonContractCompanyRelationShipCreator(CIF, idSelected);

                    			//Deleters for Nodes and relationships
                    			String deleteRelationShip = CypherDeleteRelationShipRequest(CIF, idSelected);
                    			ExecuteQuery(deleteRelationShip);
                    			String deleteNode = CypherdeleteRequestNode(idSelected);
                    			ExecuteQuery(deleteRelationShip);
                    			continueContracts="Negative";
                    		}else{
                    			continueContracts="Negative";
                    		}

                    	}
                        break;
                    case 4:
                        System.out.println();
                        System.out.println();
                        System.out.println();
                        System.out.println("****Job Creation Application*****");
                        read = new Scanner(System.in);
                        System.out.println("Please enter the Job Description:");
                        String JobDescription = read.nextLine();
                        System.out.println("Please enter Job employment address:");
                        String JobAddress = read.nextLine();
                        System.out.println("Please enter Salary: [LPS]");
                        int Salary = IntEntry();
                        System.out.println("Please enter Job Conditions:");
                        String Conditions = read.nextLine();
                        System.out.println("Please enter Contract Type");
                        System.out.println("1. Temporal");
                        System.out.println("2. Permanent");
                        int Contract = LimitedIntEntry(1, 2);
                        String ContractType = "";
                        if (Contract == 1) {
                            ContractType = "Temporal";
                        } else if (Contract == 2) {
                            ContractType = "Permanent";
                        }
                        System.out.println("Please enter Daily Schedule Type");
                        System.out.println("1. PartTime");
                        System.out.println("2. FullTime");
                        int Scheduleresp = LimitedIntEntry(1, 2);
                        String Schedule = "";
                        if (Scheduleresp == 1) {
                            Schedule = "PartTime";
                        } else if (Scheduleresp == 2) {
                            Schedule = "FullTime";
                        }
                        System.out.println("*******Personal Requisites**********");
                        System.out.println("Minimum age requiered: (Of course above 18 and below 100)");
                        System.out.println("1. Specify.");
                        System.out.println("2. Does not matter.");
                        int ageresponse = LimitedIntEntry(1, 2);
                        int AgeRequierement = 0;
                        if (ageresponse == 1) {
                            System.out.println("Please specify the minimum age requiered.");
                            AgeRequierement = LimitedIntEntry(18, 100);
                        } else {
                            AgeRequierement = 0;
                        }

                        System.out.println();
                        System.out.println("Health History?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        System.out.println("3. Not Important");
                        int healthresponse = LimitedIntEntry(1, 3);
                        String HealthHistory = "";
                        if (healthresponse == 1) {
                            HealthHistory = "Yes";
                        } else if (healthresponse == 2) {
                            HealthHistory = "No";
                        } else if (healthresponse == 3) {
                            HealthHistory = "Not Important";
                        }

                        System.out.println();
                        System.out.println("Physical Health History?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        System.out.println("3. Not Important");
                        int physicalresponse = LimitedIntEntry(1, 3);
                        String PhysicalHistory = "";
                        if (physicalresponse == 1) {
                            PhysicalHistory = "Yes";
                        } else if (physicalresponse == 2) {
                            PhysicalHistory = "No";
                        } else if (physicalresponse == 3) {
                            PhysicalHistory = "Not Important";
                        }

                        System.out.println();
                        System.out.println("Mental Health History?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        System.out.println("3. Not Important");
                        int mentalresponse = LimitedIntEntry(1, 3);
                        String MentalHistory = "";
                        if (mentalresponse == 1) {
                            MentalHistory = "Yes";
                        } else if (mentalresponse == 2) {
                            MentalHistory = "No";
                        } else if (mentalresponse == 3) {
                            MentalHistory = "Not Important";
                        }

                        System.out.println();
                        System.out.println("Military Service History?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        System.out.println("3. Not Important");
                        int militaryresponse = LimitedIntEntry(1, 3);
                        String MilitaryHistory = "";
                        if (militaryresponse == 1) {
                            MilitaryHistory = "Yes";
                        } else if (militaryresponse == 2) {
                            MilitaryHistory = "No";
                        } else if (militaryresponse == 3) {
                            MilitaryHistory = "Not Important";
                        }

                        System.out.println();
                        System.out.println("law History?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        System.out.println("3. Not Important");
                        int lawresponse = LimitedIntEntry(1, 3);
                        String LawHistory = "";
                        if (lawresponse == 1) {
                            LawHistory = "Yes";
                        } else if (lawresponse == 2) {
                            LawHistory = "No";
                        } else if (lawresponse == 3) {
                            LawHistory = "Not Important";
                        }

                        /////
                        System.out.println();
                        System.out.println("*****What are the academic Requirements for this Job?");
                        System.out.println("1. Middle School or below");
                        System.out.println("2. High School or below");
                        System.out.println("3. University and above.");
                        int initialresponse = LimitedIntEntry(1, 3);
                        ArrayList<String> carreers;
                        String AcademicPreparation = "";
                        if (initialresponse == 3) {
                            carreers = CarreersIntToString(GetUserCarreers());
                            for (int i = 0; i < carreers.size(); i++) {
                                if (i < carreers.size() - 1) {
                                    AcademicPreparation += carreers.get(i).toString() + ",";
                                } else {
                                    AcademicPreparation += carreers.get(i).toString();
                                }
                            }
                            System.out.println(AcademicPreparation);
                        } else if (initialresponse == 1) {
                            AcademicPreparation = "Middle School or below.";
                        } else if (initialresponse == 2) {
                            AcademicPreparation = "High School or below.";
                        }

                        /////
                        System.out.println();
                        System.out.println("Experience in the area?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        System.out.println("3. Not Important");
                        int experienceresponse = LimitedIntEntry(1, 3);
                        String Experience = "";
                        if (experienceresponse == 1) {
                            Experience = "Yes";
                        } else if (experienceresponse == 2) {
                            Experience = "No";
                        } else if (experienceresponse == 3) {
                            Experience = "Not Important";
                        }

                        System.out.println("Schedule "+Schedule);
                                
                        System.out.println("How many spots are available for this Job? ");
                        int JobSpots = IntEntry();

                        String Cypher = CypherJobOfferCreator(CIF, JobDescription, ContractType, Schedule, JobAddress, Salary, Conditions, AgeRequierement, HealthHistory, PhysicalHistory, MentalHistory, MilitaryHistory, LawHistory, Experience, JobSpots, AcademicPreparation);
                        System.out.println(Cypher);
                        ExecuteQuery(Cypher);
                        String Cypher2 = CypherJobOfferRelationShipCreator(CIF, JobDescription,ContractType, Schedule, JobAddress, Salary, Conditions, AgeRequierement, HealthHistory, PhysicalHistory, MentalHistory, MilitaryHistory, LawHistory, Experience, JobSpots, AcademicPreparation);
                        System.out.println(Cypher2);
                        ExecuteQuery(Cypher2);
                        System.out.println(AcademicPreparation);
                        break;
                    default:
                        System.out.println("None Changed.");
                        break;
                }
                System.out.println("Do you want to return to the Company Menu? [S/N]");
                resp = read.nextLine().charAt(0);
            } // While Block with user response for the Menu.
        } else {
            System.out.println("CIF Invalido.");
        }

    } // Logs into an existing company's information and Enters the Company Menu.

    public void ExistingPerson() {
        read = new Scanner(System.in);
        System.out.println("Please enter your idNumber: ");
        String idNumber = read.nextLine();
        boolean PersonExists = CheckidNumber(idNumber);
        if (PersonExists) {
            System.out.println();
            System.out.println();
            System.out.println();
            char resp = 'S';
            while (resp == 'S' || resp == 's') {
                System.out.println("***** Person Menu *****");
                System.out.println("1. View Personal Information.");
                System.out.println("2. Modify Personal Information.");
                System.out.println("3. Create new Job Request.");
                System.out.println(" Enter your response [1 to 3]");
                int response = LimitedIntEntry(1, 3);
                switch (response) {
                    case 1:
                        String PersonalDetails = ExecuteRequestQuery("MATCH (p:Person)\n" + "WHERE p.idNumber = \"" + idNumber + "\"\n" + "RETURN p").toString();
                        System.out.println();
                        System.out.println(PersonalDetails);
                        break;
                    case 2:
                        break;
                    case 3:
                        System.out.println("Accessing data..");
                        System.out.println();
                        System.out.println("");
                        String careerCompare= RequestPersonCareer(idNumber);
                        ArrayList<String> jobRecommendationsUser  = new ArrayList<String>();
                        jobRecommendationsUser = jobRecommendations(careerCompare);
                        ArrayList<String> jobCIF  = new ArrayList<String>();
                        ArrayList<String> jobSalaryG  = new ArrayList<String>();
                        ArrayList<String> jobSchedule  = new ArrayList<String>();
                        
                        //Divided per Attribute, consumes more resource than the necessary
                        jobCIF= jobCIFGETTER(careerCompare);
                        jobSalaryG=jobSalary(careerCompare);
                        jobSchedule=jobTime(careerCompare);
                        System.out.println("Data acquired");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("Potential Jobs");
                        int counter=0;
                        for (int i = 0; i < jobRecommendationsUser.size(); i++) {
                            counter=i;
                            System.out.println(i+": "+jobRecommendationsUser.get(i)+" salary: LPS."+jobSalaryG+" Schedule "+jobSchedule);
                        }
                        String ContinueAdding="1";
                        int jobSelect;
                        while(ContinueAdding=="1"){
                            System.out.println("Select a job you like to apply: ");
                            System.out.println("0-"+counter);
                            jobSelect=read.nextInt();
                            String addQueryCypher="";
                            addQueryCypher = CypherJobRequestCreator(jobCIF.get(jobSelect), idNumber, jobRecommendationsUser.get(counter)
                                    , jobSalaryG.get(counter), jobSchedule.get(counter));
                            ExecuteQuery(addQueryCypher);
                            String addRelationQuery="";
                            addRelationQuery=CypherJobRequestRelationShipCreator(jobCIF.get(jobSelect), idNumber, jobRecommendationsUser.get(counter)
                                    , jobSalaryG.get(counter), jobSchedule.get(counter));
                            ExecuteQuery(addRelationQuery);
                            System.out.println("");
                            System.out.println("Continue applying? 1/ Yes, 2/No");
                            ContinueAdding=read.nextLine();
                            
                        }
                        break;
                    default:
                        System.out.println("Dato Invalido");
                        break;
                }
                read = new Scanner(System.in);
                System.out.println("Return to Person Menu? [S/N]");
                resp = read.nextLine().charAt(0);
            }

        } else {
            System.out.println("idNumber is invalid or not existing in our database.");
        }
    } //Logs into an existing person's information and enters the Person Menu

    public String CypherPersonCreator(String idNumber, String name, int age, String address, String phonenumber, String HealthHistory, String PhysicalProblems, String MentalProblems, String MilitaryService, String LawIssues, String AcademicPreparation) {
        //New Person Attributes(idNumber, name, age, address, phonenumber,HealthHistory,PhysicalProblems,MentalProblems,MilitaryService,LawIssues,Academic

        idNumber = "\"" + idNumber + "\"";
        name = "\"" + name + "\"";
        address = "\"" + address + "\"";
        phonenumber = "\"" + phonenumber + "\"";
        HealthHistory = "\"" + HealthHistory + "\"";
        PhysicalProblems = " \"" + PhysicalProblems + "\"";
        MentalProblems = "\"" + MentalProblems + "\"";
        MilitaryService = "\"" + MilitaryService + "\"";
        LawIssues = "\"" + LawIssues + "\"";
        AcademicPreparation = "\"" + AcademicPreparation + "\"";

        String cypher = "CREATE (a:Person{idNumber :" + idNumber + ", Name :" + name + ", Age :" + age + ", Address :" + address + ", PhoneNumber: " + phonenumber + ", HealthHistory: " + HealthHistory + ", PhysicalProblems: " + PhysicalProblems + ", MentalProblems: " + MentalProblems + ", MilitaryService: " + MilitaryService + ", LawIssues: " + LawIssues + ", AcademicPreparation :" + AcademicPreparation + "})";
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

    public String CypherJobOfferCreator(String CIF, String JobDescription, String ContractType, String Schedule, String JobAddress, int Salary, String conditions, int AgeRequirement, String HealthHistory, String PhysicalHistory, String MentalHistory, String MilitaryHistory, String LawResponse, String Experience, int JobSpots, String AcademicPreparation) {
        CIF = "\"" + CIF + "\"";
        String Cypher = "";
        JobDescription = "\"" + JobDescription + "\"";
        JobAddress = "\"" + JobAddress + "\"";
        conditions = "\"" + conditions + "\"";
        HealthHistory = "\"" + HealthHistory + "\"";
        PhysicalHistory = "\"" + PhysicalHistory + "\"";
        MentalHistory = "\"" + MentalHistory + "\"";
        MilitaryHistory = "\"" + MilitaryHistory + "\"";
        LawResponse = "\"" + LawResponse + "\"";
        Experience = "\"" + Experience + "\"";
        AcademicPreparation = "\"" + AcademicPreparation + "\"";
        ContractType = "\"" + ContractType + "\"";
        Schedule = "\"" + Schedule + "\"";

        Cypher = "CREATE (a:JobOffer{CompanyCIF : " + CIF + ", JobDescription : " + JobDescription + ", ContractType : " + ContractType + ", Schedule : " + Schedule + ", JobAddress : " + JobAddress + ", Salary : " + Salary + ", Conditions :" + conditions + ", AgeRequirement : " + AgeRequirement + ", HealthHistory : " + HealthHistory + ", PhysicalHistory :" + PhysicalHistory + ", MentalHistory : " + MentalHistory + ", MilitaryHistory : " + MilitaryHistory + ", LawResponse :" + LawResponse + ", Experience : " + Experience + ", JobSpots :" + JobSpots + ", AcademicRequirements :" + AcademicPreparation + "})";

        return Cypher;
    } // Creates the Cypher SQL statement for the creation of a Job offer, given the information. 

    public String CypherJobOfferRelationShipCreator(String CIF, String JobDescription, String ContractType, String Schedule, String JobAddress, int Salary, String conditions, int AgeRequirement, String HealthHistory, String PhysicalHistory, String MentalHistory, String MilitaryHistory, String LawResponse, String Experience, int JobSpots, String AcademicPreparation) {
        String CIF2 = CIF;
        CIF = "\"" + CIF + "\"";
        JobDescription = "\"" + JobDescription + "\"";
        JobAddress = "\"" + JobAddress + "\"";
        conditions = "\"" + conditions + "\"";
        HealthHistory = "\"" + HealthHistory + "\"";
        PhysicalHistory = "\"" + PhysicalHistory + "\"";
        MentalHistory = "\"" + MentalHistory + "\"";
        MilitaryHistory = "\"" + MilitaryHistory + "\"";
        LawResponse = "\"" + LawResponse + "\"";
        Experience = "\"" + Experience + "\"";
        ContractType = "\"" + ContractType + "\"";
        Schedule = "\"" + Schedule + "\"";
        AcademicPreparation = "\""+AcademicPreparation+"\"";
        String Cypher = "MATCH (a:Company),(b:JobOffer)\n"
                + "WHERE a.CIF = \"" + CIF2 + "\" AND b.CompanyCIF = " + CIF + " AND b.JobDescription = " + JobDescription + " AND b.ContractType = " + ContractType +" AND b.Schedule = "+Schedule+ " AND b.JobAddress = " + JobAddress + " AND b.Salary = " + Salary + " AND b.Conditions =" + conditions + " AND b.AgeRequirement = " + AgeRequirement + " AND b.HealthHistory = " + HealthHistory + " AND b.PhysicalHistory =" + PhysicalHistory + " AND b.MentalHistory = " + MentalHistory + " AND b.MilitaryHistory = " + MilitaryHistory + " AND b.LawResponse =" + LawResponse + " AND b.Experience = " + Experience + " AND b.JobSpots =" + JobSpots + " AND b.AcademicRequirements =" + AcademicPreparation + "\n"
                + "CREATE (a)-[r:Offers]->(b)";
        return Cypher;
    }
    
    public String CypherJobRequestCreator(String CIF, String userID, String jobType, String Salary, String Schedule){
        String CypherQuery="";
        CIF = "\"" + CIF + "\"";
        userID = "\"" + userID + "\"";
        jobType = "\"" + jobType + "\"";
        Salary = "\"" + Salary + "\"";
        Schedule = "\"" + Schedule + "\"";
        CypherQuery="CREATE (a:JobRequest{CompanyCIF: "+CIF+", IDPerson: "+userID+", jobDescription: "+jobType+", jobSalary: "+Salary+", jobSchedule: "+Schedule+"})";
        return CypherQuery;
    }
    
    public String CypherJobRequestRelationShipCreator(String CIF, String userID, String jobType, String Salary, String Schedule){
        String CypherQuery="";
        CIF = "\"" + CIF + "\"";
        userID = "\"" + userID + "\"";
        jobType = "\"" + jobType + "\"";
        Salary = "\"" + Salary + "\"";
        Schedule = "\"" + Schedule + "\"";
        CypherQuery="MATCH (a:Company), (b:JobRequest), (c:Person)\n"
                + "WHERE a.CIF = "+CIF+" AND b.CompanyCIF = "+CIF+ " AND c.idNumber ="+userID+" AND b.jobDescription ="+jobType+" AND b.jobSalary ="+Salary+" AND b.jobSchedule ="+Schedule+ "\n"
                + "CREATE (a)-[r:getRequested]->(b)-[s:request]->(c)";
        return CypherQuery;
    }

    public String CypherPersonContractCompanyRelationShipCreator(String CIF, String userID){
    	String CypherQuery="";
    	CIF = "\"" + CIF + "\"";
    	userID = "\"" + userID + "\"";
    	CypherQuery="MATCH (a:Company), (p:Person)\n"
    		+"WHERE a.CIF ="+CIF+" AND p.idNumber = "+ userID+"\n"
    		+ "CREATE (a)-[r:Hired]->(p)";
    	return CypherQuery;

    }

    public String CypherDeleteRelationShipRequest(String CIF, String userID){
    	//
    	String CypherQuery="";
    	CIF = "\"" + CIF + "\"";
    	userID = "\"" + userID + "\"";
    	CypherQuery="MATCH (:Person{idNumber: "+userID+"}) -[r:request]-(:JobRequest)-[s:getRequested]-(:Company{CIF: "+CIF+"}) DELETE r, s";
    	return CypherQuery;
    }

    public String CypherdeleteRequestNode(String userID){
    	String CypherQuery="";
    	userID = "\"" + userID + "\"";
    	CypherQuery="MATCH (:JobRequest{IDPerson: "+userID+"}) Delete n";
    	return CypherQuery;
    }

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
            if (temporal.length() == 15) {
                if (temporal.charAt(4) == '-' && temporal.charAt(9) == '-' && temporal.length() == 15) {
                    syntaxcheck = true;
                } //Checks for hyphens in the syntax in the due position.
            } else {
                syntaxcheck = false;
            }

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

    public ArrayList<String> peopleRequestingJobs(String CIF){
    	ArrayList<String> idSubjects = new ArrayList<String>();
    	ArrayList DatabaseRequest = ExecuteRequestQuery("MATCH (r:JobRequest)\n"+ "WHERE r.CompanyCIF = \""+ CIF +"\" "
    		+ "Return r.IDPerson");
    	for (int i = 0; i<DatabaseRequest.size();i++ ) {
    		idSubjects.add(String.valueOf(DatabaseRequest.get(i)));
    	}
    	return idSubjects;

    }//Verifies all applicants according to an enterprise CIF

    public int LimitedIntEntry(int LowerLimit, int HigherLimit) {
        int entry = 0;
        boolean valid = false;
        while (valid == false) {
            System.out.println("Ingrese va lor entre: " + LowerLimit + " Y " + HigherLimit);
            entry = IntEntry();
            if (entry >= LowerLimit && entry <= HigherLimit) {
                valid = true;
            }
        }
        return entry;
    } //Requests an Integer from the user, forcing him to enter one with a lower and higher limit.

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

                ArrayList DatabaseRequest = ExecuteRequestQuery("MATCH (p:Person)\n" + "WHERE p.PhoneNumber = \"" + temporal + "\"\n" + "RETURN p");
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
    
    public String RequestPersonCareer(String id){
        String career="";
        
        ArrayList DatabaseRequest = ExecuteRequestQuery("MATCH (p:Person)\n" + "WHERE p.idNumber = \"" + id + "\" Return p.AcademicPreparation");
        for (int i = 0; i < DatabaseRequest.size(); i++) {
            career+=String.valueOf(DatabaseRequest.get(i));
            //if (i+1!=DatabaseRequest.size()) {
            //    career+=",";
            //}
        }
        return career;
    }//Gets the person's career
    
    public ArrayList<String> jobRecommendations(String careerMatch){
        //System.out.println(careerMatch);
   
        
        ArrayList<String> possibleJobs = new ArrayList<String>();
        ArrayList DatabaseJobSimilarities  = ExecuteRequestQuery("MATCH (p:JobOffer)\n" + "WHERE p.AcademicRequirements = \"" + careerMatch + "\" "
                + "Return p.JobDescription");
        for (int i = 0; i < DatabaseJobSimilarities.size(); i++) {
            //System.out.println(DatabaseJobSimilarities.get(i));
            possibleJobs.add(String.valueOf(DatabaseJobSimilarities.get(i)));
        }
        return possibleJobs;
    }//Used for the job requests
    
    public ArrayList<String> jobCIFGETTER(String careerMatch){
        //System.out.println(careerMatch);
   
        
        ArrayList<String> possibleJobs = new ArrayList<String>();
        ArrayList DatabaseJobSimilarities  = ExecuteRequestQuery("MATCH (p:JobOffer)\n" + "WHERE p.AcademicRequirements = \"" + careerMatch + "\" "
                + "Return p.CompanyCIF");
        for (int i = 0; i < DatabaseJobSimilarities.size(); i++) {
            //System.out.println(DatabaseJobSimilarities.get(i));
            possibleJobs.add(String.valueOf(DatabaseJobSimilarities.get(i)));
        }
        return possibleJobs;
    }
    
    public ArrayList<String> jobSalary(String careerMatch){
        //System.out.println(careerMatch);
   
        
        ArrayList<String> possibleJobs = new ArrayList<String>();
        ArrayList DatabaseJobSimilarities  = ExecuteRequestQuery("MATCH (p:JobOffer)\n" + "WHERE p.AcademicRequirements = \"" + careerMatch + "\" "
                + "Return p.Salary");
        for (int i = 0; i < DatabaseJobSimilarities.size(); i++) {
            //System.out.println(DatabaseJobSimilarities.get(i));
            possibleJobs.add(String.valueOf(DatabaseJobSimilarities.get(i)));
        }
        return possibleJobs;
    }

    public ArrayList<String> jobTime(String careerMatch){
        //System.out.println(careerMatch);
   
        
        ArrayList<String> possibleJobs = new ArrayList<String>();
        ArrayList DatabaseJobSimilarities  = ExecuteRequestQuery("MATCH (p:JobOffer)\n" + "WHERE p.AcademicRequirements = \"" + careerMatch + "\" "
                + "Return p.Schedule");
        for (int i = 0; i < DatabaseJobSimilarities.size(); i++) {
            //System.out.println(DatabaseJobSimilarities.get(i));
            possibleJobs.add(String.valueOf(DatabaseJobSimilarities.get(i)));
        }
        return possibleJobs;
    }

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

    public boolean CheckName(String name) {
        boolean exists = false;
        ArrayList DatabaseRequest = ExecuteRequestQuery("MATCH (p:Company)\n" + "WHERE p.Name = \"" + name + "\" Return p");
        if (DatabaseRequest.size() == 0) {
            exists = false;
        } else {
            exists = true;
        }
        return exists;
    } //Checks if Name given exists in the database for companies that we have.

    public boolean CheckidNumber(String idNumber) {
        boolean exists = false;

        ArrayList DatabaseRequest = ExecuteRequestQuery("MATCH (p:Person)\n" + "WHERE p.idNumber = \"" + idNumber + "\"\n" + "RETURN p");
        if (DatabaseRequest.size() == 0) {
            exists = false;
        } else {
            exists = true;
        }
        return exists;
    } //Checks if the idNumber given exists in the database for people that we have.

    public void ModifyNameCIF(String CIF, String name) {

        String Cypher = "MATCH (a:Company )\n"
                + "Where a.CIF =  \"" + CIF + "\"\n"
                + "set a.Name = \"" + name + "\"";
        ExecuteQuery(Cypher);
        System.out.println("");
        System.out.println("Task Completed");

    } //Modifies the Name of the Company with the CIF as give.

    public void ModifyAddressCIF(String CIF, String address) {
        String Cypher = "MATCH (a:Company )\n"
                + "Where a.CIF =  \"" + CIF + "\"\n"
                + "set a.Address = \"" + address + "\"";
        ExecuteQuery(Cypher);
        System.out.println("");
        System.out.println("Task Completed");
    } // Modifies the Address of the Company with the CIF as given

    public void ModifyDirectorCIF(String CIF, String Director) {
        String Cypher = "MATCH (a:Company )\n"
                + "Where a.CIF =  \"" + CIF + "\"\n"
                + "set a.Director = \"" + Director + "\"";
        ExecuteQuery(Cypher);
        System.out.println("");
        System.out.println("Task Completed");

    }

    public ArrayList GetUserCarreers() {
        ArrayList carreers = new ArrayList();
        boolean anotherdegree = true;
        int anotherdegreeresponse;
        while (anotherdegree == true) {
            System.out.println("What degree do you possess? ");
            System.out.println("1. Bachelors Degree");
            System.out.println("2. Engineering Degree");
            System.out.println("Enter your response? [1/2]");
            int response = LimitedIntEntry(1, 2);
            if (response == 1) {
                PrintCarreers(response);
                int carreer = LimitedIntEntry(1, 12);
                carreers.add(carreer);
            } else if (response == 2) {
                PrintCarreers(2);
                int carreer = LimitedIntEntry(12, 19);
                carreers.add(carreer);
            }
            System.out.println("Do you possess another Degree?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.println("Enter your response? [1/2]");
            anotherdegreeresponse = LimitedIntEntry(1, 2);
            if (anotherdegreeresponse == 1) {
                anotherdegree = true;
                System.out.println("Responsive1");
            } else if (anotherdegreeresponse == 2) {
                System.out.println("Responsive2");
                anotherdegree = false;
            }
        }

        return carreers;
    } //Asks user and Returns an ArrayList of all the carreers the User possess.

    public int PrintCarreers(int response) {
        int p = 0;
        String[] carreers = new String[]{"Licenciatura en Diseño de Modas", "Licenciatura en Derecho", "Licenciatura en Gastronomía", "Licenciatura en Administración de la Hospitalidad y el Turismo",
            "Licenciatura en Mercadotecnia y Negocios Internacionales", "Licenciatura en Terapia Física y Ocupacional", "Licenciatura en Administración Industrial y de Negocios",
            "Licenciatura en Animación Digital y Diseño Interactivo", "Licenciatura en Finanzas", "Licenciatura en Nutrición", "Licenciatura en Medicina y Cirugía",
            "Licenciatura en Comunicación Audiovisual y Publicitaria", "Ingeniería en Mecatrónica", "Ingeniería en Industrial y de Sistemas", "Ingeniería en Telecomunicaciones y Electrónica",
            "Ingeniería en Energía", "Ingeniería Civil", "Ingeniería en Biomédica", "Ingeniería en Sistemas Computacionales"};
        if (response == 1) {
            for (int i = 0; i <= 11; i++) {
                System.out.println((i + 1) + ". " + carreers[i]);
            }
            System.out.println("What carreer do you possess?");
        } else if (response == 2) {
            for (int i = 12; i < carreers.length; i++) {
                System.out.println((i + 1) + ". " + carreers[i]);
            }
            System.out.println("What carreer do you possess?");
        }

        return p;
    } //Prints the Bachelors Carreers or the Engineering Carreers.  1=Bachelor 2=Engineering.

    public ArrayList CarreersIntToString(ArrayList intcarreers) {
        ArrayList<String> stringcarreers = new ArrayList<String>();
        String[] carreers = new String[]{"Licenciatura en Diseño de Modas", "Licenciatura en Derecho", "Licenciatura en Gastronomía", "Licenciatura en Administración de la Hospitalidad y el Turismo",
            "Licenciatura en Mercadotecnia y Negocios Internacionales", "Licenciatura en Terapia Física y Ocupacional", "Licenciatura en Administración Industrial y de Negocios",
            "Licenciatura en Animación Digital y Diseño Interactivo", "Licenciatura en Finanzas", "Licenciatura en Nutrición", "Licenciatura en Medicina y Cirugía",
            "Licenciatura en Comunicación Audiovisual y Publicitaria", "Ingeniería en Mecatrónica", "Ingeniería en Industrial y de Sistemas", "Ingeniería en Telecomunicaciones y Electrónica",
            "Ingeniería en Energía", "Ingeniería Civil", "Ingeniería en Biomédica", "Ingeniería en Sistemas Computacionales"};
        for (int i = 0; i < intcarreers.size(); i++) {
            stringcarreers.add(carreers[(Integer.parseInt(intcarreers.get(i).toString())) - 1]);
        }
        return stringcarreers;

    }

    public String YesNoConverter(int response) {
        String YesNo = "";
        if (response == 1) {
            YesNo = "Yes";
        } else if (response == 2) {
            YesNo = "No";
        }
        return YesNo;
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

