/* 
 * Samuel Benison Jeyaraj Victor
 * sambenison66@gmail.com
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


// Program to convert XLS rows to MySQL tuple
public class XLStoMySQLUpload {

    public static void main(String[] args) {
    	// Mapping the state name to the corresponding state code
    	// Eg: Arizona to 'AZ'
    	Map<String, String> stateMap = new HashMap<String, String>();
    	stateMap.put("ALABAMA", "AL");
    	stateMap.put("ALASKA", "AK");
    	stateMap.put("ARIZONA", "AZ");
    	stateMap.put("ARKANSAS", "AR");
    	stateMap.put("CALIFORNIA", "CA");
    	stateMap.put("COLORADO", "CO");
    	stateMap.put("CONNECTICUT", "CT");
    	stateMap.put("DELAWARE", "DE");
    	stateMap.put("DIST. OF COL.", "DC");
    	stateMap.put("FLORIDA", "FL");
    	stateMap.put("GEORGIA", "GA");
    	stateMap.put("HAWAII", "HI");
    	stateMap.put("IDAHO", "ID");
    	stateMap.put("ILLINOIS", "IL");
    	stateMap.put("INDIANA", "IN");
    	stateMap.put("IOWA", "IA");
    	stateMap.put("KANSAS", "KS");
    	stateMap.put("KENTUCKY", "KY");
    	stateMap.put("LOUISIANA", "LA");
    	stateMap.put("MAINE", "ME");
    	stateMap.put("MARYLAND", "MD");
    	stateMap.put("MASSACHUSETTS", "MA");
    	stateMap.put("MICHIGAN", "MI");
    	stateMap.put("MINNESOTA", "MN");
    	stateMap.put("MISSISSIPPI", "MS");
    	stateMap.put("MISSOURI", "MO");
    	stateMap.put("MONTANA", "MT");
    	stateMap.put("NEBRASKA", "NE");
    	stateMap.put("NEVADA", "NV");
    	stateMap.put("NEW HAMPSHIRE", "NH");
    	stateMap.put("NEW JERSEY", "NJ");
    	stateMap.put("NEW MEXICO", "NM");
    	stateMap.put("NEW YORK", "NY");
    	stateMap.put("NORTH CAROLINA", "NC");
    	stateMap.put("NORTH DAKOTA", "ND");
    	stateMap.put("OHIO", "OH");
    	stateMap.put("OKLAHOMA", "OK");
    	stateMap.put("OREGON", "OR");
    	stateMap.put("PENNSYLVANIA", "PA");
    	stateMap.put("RHODE ISLAND", "RI");
    	stateMap.put("SOUTH CAROLINA", "SC");
    	stateMap.put("SOUTH DAKOTA", "SD");
    	stateMap.put("TENNESSEE", "TN");
    	stateMap.put("TEXAS", "TX");
    	stateMap.put("UTAH", "UT");
    	stateMap.put("VERMONT", "VT");
    	stateMap.put("VIRGINIA", "VA");
    	stateMap.put("WASHINGTON", "WA");
    	stateMap.put("WEST VIRGINIA", "WV");
    	stateMap.put("WISCONSIN", "WI");
    	stateMap.put("WYOMING", "WY");
    	stateMap.put("VIRGIN ISLANDS", "VI");
    	stateMap.put("GUAM", "GU");
    	stateMap.put("PUERTO RICO", "PR");
    	long startTime = System.currentTimeMillis(); // Start Time
        try{
        	// AWS RDB Connection details
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager
            		.getConnection("jdbc:mysql://Your-connection-URL",
                            "Your-Username","Your-Pswd");
            con.setAutoCommit(false);
            PreparedStatement pstm = null ;
            // Reading the Input file
            FileInputStream input = new FileInputStream("C://Users//XXX//Documents//YYY//us-pci.xls");
            POIFSFileSystem fs = new POIFSFileSystem( input );
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0); // First Sheet
            Row row;
            // Reading the each Row one by one
            for(int i=5; i<56; i++){
                row = sheet.getRow(i);
                int year = 1990;
                int column = 0;
                String state = "";
                // For each Row, get the number of years (column)
                for(year=1990; year<2013; year++) {
	                state = row.getCell(0).getStringCellValue(); // State cell
	                String stateCode = stateMap.get(state.toUpperCase()); // Converting the state to statecode
	                int revenue = (int) row.getCell(column+1).getNumericCellValue(); // Revenue cell
	                int rank = 0;
	                try {
	                	rank = (int) row.getCell(column+2).getNumericCellValue(); // Rank cell
	                } catch(IllegalStateException e) {
	                	rank = 0;
	                }
	                // Insert the query in to AWS RDB
	                String sql = "INSERT INTO state_revenue VALUES('"+stateCode+"',"+revenue+","+rank+"," +year+ ")";
	                pstm = (PreparedStatement) con.prepareStatement(sql);
	                pstm.execute();
	                column = column + 3;
                }
                System.out.println("Imported row "+ i + " successfully..! State : " + state.toUpperCase());
            }
            con.commit();
            pstm.close();
            con.close();
            input.close();
            System.out.println("Success import excel to mysql table");
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
        	System.out.println("Time Taken for Importing Revenue data : " + timeTaken + " ms");
        }

    }
}