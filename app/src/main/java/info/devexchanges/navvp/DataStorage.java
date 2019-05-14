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
    /**
     True if user first opens application
     */
    public boolean isFirstOpen(){
        String dataB=readFile(basicData);
            if(dataB==null){
                writeFile("{\"gender\":null, \"weight\":null, \"age\":null}",basicData);
                writeFile("[]",consumed);
                return true;
            }
        try {
            JSONObject json=new JSONObject(dataB);
            if(json.getString("gender")=="null" || json.getString("weight")=="null" || json.getString("age")=="null"){
                return true;
            }
        } catch (JSONException e) {
            return true;
        }
        return false;

    }

    public void clearData(){
        writeFile("{\"gender\":null, \"weight\":null, \"age\":null}",basicData);
        writeFile("[]",consumed);
    }

    public void setAge(int age){
        try {
            JSONObject json =new JSONObject(readFile(basicData));
            json.put("age",age);
            writeFile(json.toString(),basicData);
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
    /**
     M=male;F=female
     */
    public void setGender(char s){
        try {
            JSONObject json =new JSONObject(readFile(basicData));
            json.put("gender",s);
            writeFile(json.toString(),basicData);
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG);
            e.printStackTrace();
        }
    }

    public char getGender(){
        try {
            JSONObject json =new JSONObject(readFile(basicData));
            return (char)json.getInt("gender");
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG);
            e.printStackTrace();
        }
        return 'X';
    }

    public void setWeight(double w){
        try {
            JSONObject json =new JSONObject(readFile(basicData));
            json.put("weight",w);
            writeFile(json.toString(),basicData);
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG);
            e.printStackTrace();
        }
    }

    public double getWeight(){
        try {
            JSONObject json =new JSONObject(readFile(basicData));
            return json.getDouble("weight");
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG);
            e.printStackTrace();
        }
        return -1;
    }

    public void addConsumed(){

    }
    public List<JSONObject> getConsumed(){
        List<JSONObject> allConsumed=new ArrayList<JSONObject>();
        try {
            JSONArray json =new JSONArray(readFile(consumed));
            for(int i=0;i<json.length();i++){
                allConsumed.add(json.getJSONObject(i));
            }
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


    private void writeFile(String text,String path) {//make private

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
