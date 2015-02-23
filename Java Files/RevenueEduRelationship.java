/* 
 * Samuel Benison Jeyaraj Victor
 * sambenison66@gmail.com
 */

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//Program to calculate State Revenue - Education Relationship
public class RevenueEduRelationship {
    
    public static void main (String[] args) {
    	
    	// HashMap to store the total number of institutions per state
    	Map<String, Integer> instCount = new HashMap<String, Integer>();
    	Scanner in = new Scanner(System.in);
    	String option = null;
    	// Get the input choice Average or Year by Year
    	while(true) {
	    	System.out.println("Select an option.\n 1.Average Revenue Report\n 2.Year-by-Year Revenue Report");
	    	System.out.println("Enter your option here:");
	    	option = in.nextLine();
	    	if(option.equals("1") || option.equals("2")) {
	    		in.close();
	    		break;
	    	} else {
	    		System.out.println("Invalid Option..!!");
	    	}
    	}
    	long startTime = System.currentTimeMillis(); // Start Time
        try {
        	// AWS RDB Connection details
        	Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://Your-connection-URL";
            Connection conn = DriverManager.getConnection(url,"Your-Username","Your-Pswd");
            Statement stmt = conn.createStatement();
            ResultSet rs;
            
            // Query to get the no.of Institutions count
            String query1 = "Select ins_State, Count(ins_Name) As ins_Count "
            		+ "From state_institution Group By ins_State";
            rs = stmt.executeQuery(query1);
            // For each row, loop the process
            while ( rs.next() ) {
                int count = rs.getInt("ins_Count"); // Ins count
                String state = rs.getString("ins_State"); // State
                instCount.put(state, count); // Store it in Hashmap
            }
            rs.close();
            // Loop based on user option
            if(option.equals("1")) {
            	// Query for Average calculation
	            String query2 = "Select State, ROUND(Avg(Revenue)) as Avg_Revenue, "
	            		+ "ROUND(Avg(Rank)) as Avg_Rank From state_revenue GROUP BY State "
	            		+ "ORDER BY Avg(Revenue) DESC";
	            rs = stmt.executeQuery(query2);
	            System.out.println("State  Avg.Revenue Avg.Rank  No.of.Institution");
	            while ( rs.next() ) {
	                int revenue = rs.getInt("Avg_Revenue");
	                int rank = rs.getInt("Avg_Rank");
	                String state = rs.getString("State");
	                int institution = instCount.get(state); // Get from HashMap
	                System.out.println(state + "\t" + revenue + "\t\t" + rank + "\t\t" + institution);
	            }
	            rs.close();
            } else if(option.equals("2")) {
            	// Query for Year-by-Year calculation
	            String query3 = "Select State, Revenue, Rank, RevYear "
	            		+ "From state_revenue ORDER BY State ASC";
	            rs = stmt.executeQuery(query3);
	            System.out.println("State  Revenue  Rank  RevYear  No.of.Institution");
	            while ( rs.next() ) {
	                int revenue = rs.getInt("Revenue");
	                int rank = rs.getInt("Rank");
	                int year = rs.getInt("RevYear");
	                String state = rs.getString("State");
	                int institution = instCount.get(state); // Get from HashMap
	                System.out.println(state + "\t" + revenue + "\t" + rank + "\t" + year + "\t" + institution);
	            }
	            rs.close();
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
        	// Time Calculation
        	long endTime = System.currentTimeMillis();
        	long timeTaken = endTime - startTime;
        	System.out.println("\n\n Time Taken for calculating the Relationship : " + timeTaken + " ms");
        }     
    }
}