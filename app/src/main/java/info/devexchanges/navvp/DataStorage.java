package info.devexchanges.navvp;

import android.content.Context;
import android.util.JsonReader;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.NotActiveException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class DataStorage {
    private Context context;
    private File file;

    public DataStorage(Context context){
        this.context=context;
    }

    public boolean isFirstOpen(){
        try {
            JSONObject json=new JSONObject(readFile());
            return false;
        } catch (JSONException e) {
            writeFile("{\"consumed\":[], \"drinks\":[], \"gender\":null, \"weight\":null, \"age\":null}");
            return true;

        }

    }

    public List<JSONObject> getDataGetConsumed(){
        List<JSONObject> allConsumed=new ArrayList<JSONObject>();
        try {
            JSONObject json =new JSONObject(readFile());

            JSONArray cons =new JSONArray(json.get("consumed").toString());
            //cons.put("{\"drink\":\"Lasko\"}");
            for(int i=0;i<cons.length();i++){
                allConsumed.add(new JSONObject(cons.get(i).toString()));
            }
            //String drink=new JSONObject(cons.get(0).toString()).get("drink").toString();
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed, try clearing application data!",Toast.LENGTH_LONG);
            return null;
        }
        return allConsumed;
    }
    /**
     Gets id for new consumed
     */
    private int getIdConsumed(){
        try {
            JSONObject json =new JSONObject(readFile());

            JSONArray cons =new JSONArray(json.get("consumed").toString());
            String id=new JSONObject(cons.get(cons.length()).toString()).get("id").toString();
            return Integer.parseInt(id)+1;
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed, try clearing application data!",Toast.LENGTH_LONG);
            return -1;
        }

    }


    public void writeFile(String text) {//make private

        try {
            FileOutputStream fileOutputStream = context.openFileOutput("DrinkingAppData.txt", MODE_PRIVATE);
            fileOutputStream.write(text.getBytes());
            fileOutputStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFile() {
        try {
            FileInputStream fileInputStream = context.openFileInput("DrinkingAppData.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines + "\n");
            }

            return stringBuffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
