/**
 * Created by paul on 22/06/17.
 */


import oracle.CartridgeServices.ContextManager;

import java.awt.*;
import java.util.*;
import java.math.BigDecimal;
import oracle.jdbc.OracleTypes;
import oracle.jpub.runtime.MutableStruct;
import oracle.sql.STRUCT;
import oracle.sql.CustomDatum;
import oracle.sql.CustomDatumFactory;
import oracle.sql.Datum;
import java.sql.SQLException;

public class Index implements CustomDatum, CustomDatumFactory {

	static private LireHttpRequester requester = new LireHttpRequester("http://lireserv:8080/");
	static private java.lang.String images_path = "/corel-10k";

	static private final BigDecimal SUCCESS = new BigDecimal(0);
	static private final BigDecimal ERROR = new BigDecimal(1);

	static private final int CREATE = 11;
	static private final int ALTER = 12;
	static private final int INSERT = 13;
	static private final int UPDATE = 14;
	static private final int SIM = 15;

	static private final String sqlname = "SYS.IMAGE_INDEX";

	static private int[] struct_types = {OracleTypes.INTEGER};
	private MutableStruct keystorage; 

	static CustomDatumFactory[] factory = new CustomDatumFactory[1];

	public static CustomDatumFactory getFactory() {
		return new Index();
	}

	public CustomDatum create(Datum d, int sqltype) {
		if (d == null)
			return null;
		Index idx = new Index();	
		idx.keystorage = new MutableStruct((STRUCT) d, struct_types, factory);
		return idx;
	}

	public Index() {
		keystorage = new MutableStruct(new Object[1], struct_types, factory);
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
	public static java.math.BigDecimal ODCIIndexStart(Index[] sctx, oracle.ODCI.ODCIIndexInfo ia,
			oracle.ODCI.ODCIPredInfo pi, oracle.ODCI.ODCIQueryInfo qi, java.math.BigDecimal strt,
			java.math.BigDecimal stop, java.lang.String image_path , oracle.ODCI.ODCIEnv env)  {
		Utils.print_log("OCDI start paulo ");
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

			Integer key = ContextManager.setContext(parsed_results);
			sctx[0] = new Index();
			sctx[0].setContextKey(key);
		}
		catch(Exception e){
			Utils.print_log("Exception in start : "+e.getMessage());
			return ERROR;
		}

		return SUCCESS;
	}

	public void setContextKey(Integer context) throws SQLException {
		keystorage.setAttribute(0, context);
	}

	public Integer getContextKey() throws SQLException {
		return (Integer) keystorage.getAttribute(0);
	}

	public Datum toDatum(oracle.jdbc.driver.OracleConnection c) throws SQLException {
		return keystorage.toDatum(c, sqlname);
	}

	public java.math.BigDecimal ODCIIndexFetch(java.math.BigDecimal nrows, oracle.ODCI.ODCIRidList rids, oracle.ODCI.ODCIEnv env) {
		Utils.print_log("Entering fetch");

		try{
			Integer ctxkey = getContextKey();
			LinkedList<Image_Sim> results = (LinkedList<Image_Sim>) ContextManager.getContext(ctxkey);

			HashMap<java.lang.String, java.lang.String> filename_id = Utils.request_id();
			String[] rowids = new String[nrows.intValue() + 1];

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

	public java.math.BigDecimal ODCIIndexClose(oracle.ODCI.ODCIEnv env) {
		Utils.print_log("Index closed successfully");
		return SUCCESS;
	}

}
