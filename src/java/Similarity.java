import oracle.CartridgeServices.ContextManager;

import java.util.HashMap;

/**
 * Created by paul on 22/06/17.
 */

public class Similarity {

    static private LireHttpRequester requester = new LireHttpRequester("http://lireserv:8080/");
    static private final int COMPARE = 16;

    public static java.math.BigDecimal similarity(java.lang.String image1, java.lang.String image2, oracle.ODCI.ODCIIndexCtx ctx,
                                                  Index[] sctx, java.math.BigDecimal scanflg){

	 try {
		if (image1 == null || image2 == null) {
			return null;
		}

            if(sctx == null) {
                java.util.Map<String, String> params_map = new HashMap<String, String>();
		Utils.print_log("Paul was here");
                params_map.put("img", image2);
                params_map.put("indexed_img", image1);
                String response = requester.ask_request(COMPARE, params_map);

                if (response.equals("error")) {
                    Utils.print_log("error while requesting compare, returning null.");
                    return null;
                }

                //Utils.print_log(response);
                return new java.math.BigDecimal(Double.parseDouble(response));
            }

            else{
		    Integer resultsKey = (Integer) ContextManager.ctx.keySet().toArray()[0];
		    Results results = (Results) ContextManager.getContext(resultsKey);
		    return results.getSimilarity(image1);
            }
        }
        catch(Exception e){
            Utils.print_log(e.getMessage());
            Utils.print_log("error while calculating similarity, returning null.");
            return null;
        }
    }
}
