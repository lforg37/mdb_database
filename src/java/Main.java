import oracle.ODCI.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by paul on 04/07/17.
 */
public class Main {

    public static void main(String[] args){

        //System.out.println(Index.ODCIIndexCreate(new ODCIIndexInfo(), "/home/OMD/CorelDB", new ODCIEnv()));
        //Index.ODCIIndexStart(new ODCIIndexCtx(), new ODCIIndexInfo(), new ODCIPredInfo(), new ODCIQueryInfo(),
         //       0, 0, "/home/OMD/CorelDB/sc_indoor/457079.jpg", 5, new ODCIEnv());

        HashMap<String, String> test = new HashMap<String, String>();
        test.put("test1", "val1");
        test.put("test2", "val2");

        for(Iterator<Map.Entry<String, String>> it = test.entrySet().iterator(); it.hasNext(); ){
            Map.Entry<java.lang.String,String> entry = it.next();
            if(entry.getValue().equals("val1")) it.remove();
        }

        for(String key : test.keySet()){
            System.out.println(test.get(key));
        }
    }
}
