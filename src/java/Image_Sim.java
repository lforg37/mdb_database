import java.awt.*;
import java.math.BigDecimal;

/**
 * Created by paul on 05/07/17.
 */
public class Image_Sim implements Comparable{

    public java.lang.String image;
    public java.math.BigDecimal sim;

    public Image_Sim(java.lang.String image, java.math.BigDecimal sim){
        this.image = image;
        this.sim = sim;
    }

    @Override
    public int compareTo(Object obj){
        Image_Sim o = (Image_Sim) obj;
        if(this.sim.compareTo(o.sim)==-1) return -1;
        if(this.sim.compareTo(o.sim)==0) return 0;
        if(this.sim.compareTo(o.sim)==1) return 1;

        return 0;
    }
}
