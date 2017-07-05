/**
 * Created by paul on 22/06/17.
 */


import oracle.CartridgeServices.ContextManager;

import java.awt.*;
import java.util.*;
import java.math.BigDecimal;


public class Index {

    static private LireHttpRequester requester = new LireHttpRequester("http://huile-de-palme.ml:8080/");
    //static private LireHttpRequester requester = new LireHttpRequester("http://lireserv:8080/");
    static private java.lang.String images_path = "/home/OMD/CorelDB";

    static private final BigDecimal SUCCESS = new BigDecimal(1);
    static private final BigDecimal ERROR = new BigDecimal(0);

    static private final int CREATE = 11;
    static private final int ALTER = 12;
    static private final int INSERT = 13;
    static private final int UPDATE = 14;
    static private final int SIM = 15;

    public Index(){
    }

    public static java.math.BigDecimal ODCIIndexCreate(oracle.ODCI.ODCIIndexInfo info, java.lang.String params, oracle.ODCI.ODCIEnv env){

        java.util.Map<String, String> params_map = new HashMap<String, String>();
        params_map.put("dir", images_path);
        String response = requester.ask_request(CREATE, params_map);

        if(response.equals("error")){
            Utils.print_log("error while creating the index");
            return ERROR;
        }
        Utils.print_log("Index created successfully");
        Utils.print_log(response);
        return SUCCESS;

    }

    public static java.math.BigDecimal ODCIIndexAlter(oracle.ODCI.ODCIIndexInfo info, java.lang.String params, java.math.BigDecimal alter_options,
                                     oracle.ODCI.ODCIEnv env) {

        java.util.Map<String, String> params_map = new HashMap<String, String>();
        params_map.put("dir", images_path);
        String response = requester.ask_request(ALTER, params_map);

        if(response.equals("error")){
            Utils.print_log("error while altering the index");
            return ERROR;
        }
        Utils.print_log("Index altered successfully");
        Utils.print_log(response);
        return SUCCESS;
    }

    public static java.math.BigDecimal ODCIIndexDrop(oracle.ODCI.ODCIIndexInfo info, oracle.ODCI.ODCIEnv env) {

        Utils.print_log("Index dropped.");
        return SUCCESS;
    }

    public static java.math.BigDecimal ODCIIndexInsert(oracle.ODCI.ODCIIndexInfo info, java.lang.String rid, java.lang.String newval,
                                      oracle.ODCI.ODCIEnv env) {

        java.util.Map<String, String> params_map = new HashMap<String, String>();
        params_map.put("img", newval);
        String response = requester.ask_request(INSERT, params_map);

        if(response.equals("error")){
            Utils.print_log("error while inserting an image in the index");
            return ERROR;
        }
        Utils.print_log(response);
        Utils.print_log("Image inserted successfully");
        return SUCCESS;
    }

    public static java.math.BigDecimal ODCIIndexDelete(oracle.ODCI.ODCIIndexInfo info, java.lang.String rid, java.lang.String oldval,
                                      oracle.ODCI.ODCIEnv env) {
        Utils.print_log("Image " + oldval +  " has been delete.");
        return SUCCESS;
    }

    public static java.math.BigDecimal ODCIIndexUpdate(oracle.ODCI.ODCIIndexInfo info, java.lang.String rid, java.lang.String oldval,
                                      java.lang.String newval, oracle.ODCI.ODCIEnv env) {

        java.util.Map<String, String> params_map = new HashMap<String, String>();
        params_map.put("img", newval);
        String response = requester.ask_request(UPDATE, params_map);

        if(response.equals("error")){
            Utils.print_log("error while updating an image in the index");
            return ERROR;
        }
        Utils.print_log(response);
        Utils.print_log("Image updated successfully");
        return SUCCESS;
    }


    /**
     *
     * @param sctx
     * @param ia
     * @param pi
     * @param qi
     * @param strt type of operator return type
     * @param stop type of operator return type
     * @param image_path param for similarity
     * @param env
     * @return
     */
    public static java.math.BigDecimal ODCIIndexStart(oracle.ODCI.ODCIIndexCtx sctx, oracle.ODCI.ODCIIndexInfo ia,
                                     oracle.ODCI.ODCIPredInfo pi, oracle.ODCI.ODCIQueryInfo qi, java.math.BigDecimal strt,
                                     java.math.BigDecimal stop, java.lang.String image_path , oracle.ODCI.ODCIEnv env)  {

        try {
            java.util.Map<String, String> params_map = new HashMap<String, String>();
            params_map.put("img", image_path);
            String response = requester.ask_request(SIM, params_map);

            if (response.equals("error")) {
                Utils.print_log("error while retrieving images from the index");
                return ERROR;
            }

            Utils.print_log(response);
            Utils.print_log("Images retrieved successfully");
            LinkedList<Image_Sim> parsed_results = Utils.parse_results(response);

            if(strt != null){
                ListIterator<Image_Sim> it = parsed_results.listIterator();
                while(it.hasNext() && it.next().sim.compareTo(strt)==-1){
                    it.remove();
                }
            }

            if(stop != null){
                ListIterator<Image_Sim> it = parsed_results.listIterator();
                int end_index = 0;
                while(it.hasNext() && it.next().sim.compareTo(stop)==-1){
                    end_index++;
                }
                parsed_results.subList(0, end_index);
            }

            ContextManager.setContext(parsed_results);
        }
        catch(Exception e){
            Utils.print_log(e.getMessage());
            return ERROR;
        }

        return SUCCESS;
    }

    public static java.math.BigDecimal ODCIIndexFetch(oracle.ODCI.ODCIIndexCtx self, java.math.BigDecimal nrows, oracle.ODCI.ODCIRidList rids, oracle.ODCI.ODCIEnv env) {
        try{
            Integer ctxkey = (Integer) ContextManager.ctx.keySet().toArray()[0];
            LinkedList<Image_Sim> results = (LinkedList<Image_Sim>) ContextManager.getContext(ctxkey);

            HashMap<java.lang.String, java.lang.String> filename_id = Utils.request_id();
            String[] rowids = new String[nrows + 1];

            for(int i = 0; i< nrows.intValue(); i++){
                rowids[i] = filename_id.get(results.get(i).image);
            }

            rids.setArray(rowids);

        }
        catch(Exception e){
            Utils.print_log(e.getMessage());
            return ERROR;
        }

        return SUCCESS;
    }

    public static java.math.BigDecimal ODCIIndexClose(oracle.ODCI.ODCIIndexCtx self, oracle.ODCI.ODCIEnv env) {
        Utils.print_log("Index closed successfully");
        return SUCCESS;
    }

}
