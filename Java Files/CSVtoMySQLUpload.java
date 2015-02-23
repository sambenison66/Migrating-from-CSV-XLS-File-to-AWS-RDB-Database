/* 
 * Samuel Benison Jeyaraj Victor
 * sambenison66@gmail.com
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import au.com.bytecode.opencsv.CSVReader;

//Program to convert CSV rows to MySQL tuple
public class CSVtoMySQLUpload {

    public static void main(String[] args) throws Exception {
    	long startTime = System.currentTimeMillis(); // Start Time
        try{
        	// AWS RDB Connection details
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager
            		.getConnection("jdbc:mysql://Your-connection-URL",
            				"Your-Username","Your-Pswd");
            con.setAutoCommit(false);
            PreparedStatement pstm = null ;
            // Input file Details
            String file = "C://Users//XXX//Documents//YYY//hd2013.csv";
            CSVReader csvReader = null;
            System.out.println("Reading the CSV File...");
    		try {
    			// Read the CSV file
    			csvReader = new CSVReader(new FileReader(file));

    		} catch (Exception e) {
    			e.printStackTrace();
    			throw new Exception("Error occured while executing file. "
    					+ e.getMessage());
    		}
    		
    		// CSV File header
    		String[] headerRow = csvReader.readNext();
    		
    		String[] nextLine = null;

    		if (headerRow == null) {
    			throw new FileNotFoundException(
    					"No columns defined in given CSV file." +
    					"Please check the CSV file format.");
    		}
    		int count = 0;
    		System.out.println("Inserting the Rows from CSV to Amazon RDB.");
    		System.out.println("  This might take serveral minutes.. Please wait..");
    		// Reading the each Row one by one
    		while ((nextLine = csvReader.readNext()) != null) {
    			int ins_id = Integer.parseInt(nextLine[0]); // ins id
    			String ins_Name = nextLine[1].replace("'", ""); // ins name
    			String ins_Address = nextLine[2].replace("'", ""); // ins address
    			String ins_City = nextLine[3].replace("'", ""); // ins city
    			String ins_State = nextLine[4]; // ins state
    			String ins_Zip = nextLine[5]; // ins zip
    			// Insert the query in to AWS RDB
    			String sql = "INSERT INTO state_institution VALUES("+ins_id+",'"+ins_Name+"','"+ins_Address+
    					"','" +ins_City+"','"+ins_State+"','"+ins_Zip+ "')";
                pstm = (PreparedStatement) con.prepareStatement(sql);
                pstm.execute();
    			count = count + 1;
    		}
    		con.commit();
            pstm.close();
            con.close();
            csvReader.close();
    		System.out.println("CSV to MySQL Import completed successfully..!!");
    		System.out.println("Total Number of Tuples Inserted : " +count);
        }catch(ClassNotFoundException e){
            System.out.println(e);
        }catch(SQLException ex){
            System.out.println(ex);
        }catch(IOException ioe){
            System.out.println(ioe);
        }finally {
        	// Time Calculation
        	long endTime = System.currentTimeMillis();
        	long timeTaken = endTime - startTime;
        	System.out.println("Time Taken for Importing Institution data : " + timeTaken + " ms");
        }
    }
}