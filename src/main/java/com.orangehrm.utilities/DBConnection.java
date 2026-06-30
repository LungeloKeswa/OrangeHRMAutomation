package com.orangehrm.utilities;

import com.orangehrm.base.BaseClass;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {

    // final the value won't be changed
    private static final String DB_URL = "jdbc:mysql://localhost:3306/orangehrmnew";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final Logger logger = BaseClass.logger;

    public static Connection getDBConnection() {
        try {
            logger.info("Connecting to database...");
            Connection conn =  DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            logger.info("Connected to database successfully!");
            return conn;
        } catch (SQLException e) {
            logger.error("Error while establishing the DB Connection!");
            e.printStackTrace();
            return null;
        }

    }

    /// Get the employee details from BD and store in a map
    public static Map<String,String> getEmployeeDetails(String employee_id) {
        String query = "SELECT emp_firstname, emp_middle_name, emp_lastname FROM hs_hr_employee WHERE employee_id ="+employee_id;

        Map<String, String> employeeDetails = new HashMap<>();

        // created 3 objects
        try (Connection conn = getDBConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){
            logger.info("Executing query: "+query);

            if (rs.next()) {
                String firstName = rs.getString("emp_firstname");
                String middleName = rs.getString("emp_middle_name");
                String lastName = rs.getString("emp_lastname");

                // Store in a map
                employeeDetails.put("firstName", firstName);
                employeeDetails.put("middleName", middleName!=null? middleName:" ");
                employeeDetails.put("lastName", lastName);

                logger.info("Query executed Successfully!");
                logger.info("Employee Data Fetched: "+employeeDetails);
            }
            else {
                logger.error("Employee not found! ");
            }
        }
        catch (Exception e) {
            logger.error("Error while executing query: "+query);
            e.printStackTrace();
        }
        return employeeDetails;
    }
}
