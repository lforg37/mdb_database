/**
 * Created by paul on 28/06/17.
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LireHttpRequester {

    static private final int CREATE = 11;
    static private final int ALTER = 12;
    static private final int INSERT = 13;
    static private final int UPDATE = 14;
    static private final int SIM = 15;
    static private final int COMPARE =16;

    private java.lang.String server_address;

    public LireHttpRequester(String server_address){
        this.server_address = server_address;
    }


    private String post_request(java.lang.String request, java.lang.String params){

        try {
            URL url = new URL(request);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
	    connection.setConnectTimeout(150000);

            connection.setUseCaches(false);
            connection.setDoOutput(true);
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            dos.writeBytes(params);
            dos.flush();
            dos.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            java.lang.String line;
            StringBuilder response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            connection.disconnect();
            return response.toString();
        }
        catch(Exception e){
            Utils.print_log(e.getMessage());
            return "error";
        }

    }

    public String ask_request(int type, java.util.Map<String, String> params){

        java.lang.String request = "";
        java.lang.String params_string = "";
        switch(type){
            case CREATE:
                request = server_address + "indexdir?";
                break;
            case INSERT:
                request = server_address + "indeximg?";
                break;
            case ALTER:
                request = server_address + "indexdir?";
                break;
            case UPDATE:
                request = server_address + "indeximg?";
                break;
            case SIM:
                request = server_address + "search?";
                break;
            case COMPARE:
                request = server_address + "compare?";
                break;
            default:
                return "error";
        }

        for (java.util.Map.Entry<String, String> entry : params.entrySet()) {
            params_string += entry.getKey();
            params_string += "=";
            params_string += entry.getValue();
            params_string += "&";
        }

        if(params_string.length() > 0){
            params_string = params_string.substring(0, params_string.length() - 1);
        }

        System.out.println(request + "    " + params_string);

        return post_request(request, params_string);
    }
}
