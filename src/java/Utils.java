import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;

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

    public static LinkedList<Image_Sim> parse_results(java.lang.String results){

        LinkedList<Image_Sim> parsed_results = new LinkedList<>();
        java.lang.String[] pairs = results.split("\n");

        for(java.lang.String pair : pairs){
            java.lang.String[] sim_image = pair.split(",");
            parsed_results.add(new Image_Sim(sim_image[1], new java.math.BigDecimal(Double.parseDouble(sim_image[0]))));
        }

        return parsed_results;
    }

    public static HashMap<java.lang.String, java.lang.String> request_id(){ //to be implemented

        HashMap<java.lang.String, java.lang.String> filename_id = new HashMap<>();
        try {
            Utils.print_log("Getting filename and id with sql request");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521", "user_mmdb", "user_mmdb");
            String query = "SELECT ROWID, IMAGE_PATH FROM IMAGES_TABLE";

            Statement request = connection.createStatement();

            ResultSet rs = request.executeQuery(query);
            while(rs.next()) {
                filename_id.put(rs.getString("IMAGE_PATH"), rs.getString("ROWID"));
            }

            request.close();
            connection.close();
        } catch (Exception e) {
            Utils.print_log(e.getMessage());
        }
        return filename_id;
    }
}
