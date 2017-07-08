
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.*;

/**
 * Created by paul on 08/07/17.
 */
public class Results {

    private ArrayList<Image_Sim> sim_results;
    private HashMap<String, String> filename_id;
    private HashMap<String, java.math.BigDecimal> image_sim;

    public Results(ArrayList<Image_Sim> sim_results, HashMap<String, String> filename_id){
        this.sim_results = sim_results;
        this.filename_id = filename_id;
        this.image_sim = new HashMap<String, BigDecimal>();

        for(Image_Sim is : this.sim_results){
            image_sim.put(is.image, is.sim);
        }
    }

    public ArrayList<Image_Sim> getSim_Results(){
        return sim_results;
    }

    public HashMap<String, String> getFilename_id(){
        return filename_id;
    }
    
    public java.math.BigDecimal getSimilarity(String image){
        return image_sim.get(image);
    }
}
