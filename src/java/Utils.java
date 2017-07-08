import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Created by paul on 03/07/17.
 */

public class Utils {

    public static void print_log(java.lang.String log) {
        try {
            File log_file = new File("ODCI.log");
            BufferedWriter bf = new BufferedWriter(new FileWriter(log_file, true));
            bf.write(log + "\n");
            bf.flush();
            bf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Image_Sim> parse_results(java.lang.String results){

        java.lang.String[] pairs = results.split(";");
        ArrayList<Image_Sim> parsed_results = new ArrayList<Image_Sim>(pairs.length);

        for(java.lang.String pair : pairs){
            java.lang.String[] sim_image = pair.split(",");
            parsed_results.add(new Image_Sim(sim_image[1], new java.math.BigDecimal(Double.parseDouble(sim_image[0]))));
        }

        return parsed_results;
    }

    public static HashMap<java.lang.String, java.lang.String> request_id(){ //to be implemented

        HashMap<java.lang.String, java.lang.String> filename_id = new HashMap<java.lang.String, java.lang.String>();
        try {
            Utils.print_log("Getting filename and id with sql request");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl", "SYS", "oracle");
            Utils.print_log("Connection created");
            String query = "SELECT ROWID, FILE_PATH FROM IMAGES_TABLE";

            Statement request = connection.createStatement();
            Utils.print_log("Blabla");

            ResultSet rs = request.executeQuery(query);
            Utils.print_log("Blibli");
            while(rs.next()) {
                filename_id.put(rs.getString("FILE_PATH"), rs.getString("ROWID"));
            }
            Utils.print_log("Blublu");

            request.close();
            Utils.print_log("Blaablaa");
            connection.close();
            Utils.print_log("Bliiblii");
        } catch (Exception e) {
	    Utils.print_log("Request id");
            Utils.print_log(e.getMessage());
        }
        return filename_id;
    }
}
