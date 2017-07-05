import oracle.CartridgeServices.ContextManager;

import java.util.HashMap;

/**
 * Created by paul on 22/06/17.
 */

public class Similarity {

    public static java.math.BigDecimal similarity(java.lang.String image1, java.lang.String image2, oracle.ODCI.ODCIIndexCtx ctx,
                                                  Index[] sctx, java.math.BigDecimal scanflg){

        try {
            Integer ctxkey = (Integer) ContextManager.ctx.keySet().toArray()[0];
            HashMap<String, java.math.BigDecimal> results = (HashMap<String, java.math.BigDecimal>) ContextManager.getContext(ctxkey);
            return results.get(image2);
        }
        catch(Exception e){
            Utils.print_log(e.getMessage());
            Utils.print_log("error while calculating similarity, returning null.");
            return null;
        }
    }
}



