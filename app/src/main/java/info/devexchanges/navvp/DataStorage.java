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

    //Paths
    private static final String basicData  = "ATbasicData.json";
    private static final String consumed  = "ATconsumed.json";
    private static final String drinks  = "ATdrinks.json";

    public DataStorage(Context context){
        this.context=context;
    }

    public boolean isFirstOpen(){
        try {
            JSONObject json=new JSONObject(readFile(basicData));
            return false;
        } catch (JSONException e) {
            writeFile("{\"gender\":null, \"weight\":null, \"age\":null}","ATbasicData.json");
            return true;

        }

    }

    public void setAge(int age){
        try {
            JSONObject json =new JSONObject(readFile(basicData));
            json.put("age",age);
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG);
            e.printStackTrace();
        }
    }

    public int getAge(){
        try {
            JSONObject json =new JSONObject(readFile(basicData));
            return json.getInt("age");
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG);
            e.printStackTrace();
        }
        return -1;
    }

    public List<JSONObject> getDataGetConsumed(){
        List<JSONObject> allConsumed=new ArrayList<JSONObject>();
        try {
            JSONObject json =new JSONObject(readFile("ATconsumed.json"));

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
            JSONObject json =new JSONObject(readFile("ATconsumed.json"));

            JSONArray cons =new JSONArray(json.get("consumed").toString());
            String id=new JSONObject(cons.get(cons.length()).toString()).get("id").toString();
            return Integer.parseInt(id)+1;
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed, try clearing application data!",Toast.LENGTH_LONG);
            return -1;
        }

    }

    public void test(){
        try {
            JSONArray a=new JSONArray("[]");
            Toast.makeText(context,"Yes",Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Toast.makeText(context,"No",Toast.LENGTH_SHORT).show();
        }

    }

    public void writeFile(String text,String path) {//make private

        try {
            FileOutputStream fileOutputStream = context.openFileOutput(path, MODE_PRIVATE);
            fileOutputStream.write(text.getBytes());
            fileOutputStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFileConsumed(String text) {//make private

        try {
            FileOutputStream fileOutputStream = context.openFileOutput("DrinkingAppDataConsumed.txt", MODE_PRIVATE);
            fileOutputStream.write(text.getBytes());
            fileOutputStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String readFileConsumed() {
        try {
            FileInputStream fileInputStream = context.openFileInput("DrinkingAppDataConsumed.txt");
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

    private String readFile(String path) {
        try {
            FileInputStream fileInputStream = context.openFileInput(path);
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
