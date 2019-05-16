package info.devexchanges.navvp;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
                writeFile("[]",drinks);
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
        writeFile("[]",drinks);
    }

    public void setAge(int age){
        try {
            JSONObject json =new JSONObject(readFile(basicData));
            json.put("age",age);
            writeFile(json.toString(),basicData);
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public int getAge(){
        try {
            JSONObject json =new JSONObject(readFile(basicData));
            return json.getInt("age");
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG).show();
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
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public char getGender(){
        try {
            JSONObject json =new JSONObject(readFile(basicData));
            return (char)json.getInt("gender");
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG).show();
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
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return -1;
    }

    public void test() throws JSONException {
       // String a=readFile(consumed);
        //Toast.makeText(context,new JSONArray(a).getJSONObject(0).getString("name"),Toast.LENGTH_LONG).show();
        writeFile("[]",consumed);
    }

    public void addConsumed(String time,int drinkID,String name,Double alco,int icon,Boolean favorite,Double quantity,Double cost,Double kcal){
        try {
            int alcoID=getIdConsumed();
            JSONArray json =new JSONArray(readFile(consumed));
            json=json.put(new JSONObject("{\"alcoID\":"+alcoID+",\"time\":\""+time+"\",\"drinkID\":"+drinkID+",\"name\":\""+name+"\",\"alco\": "+alco+",\"icon\": "+icon+",\"favorite\": "+favorite+",\"quantity\":"+quantity+",\"cost\": "+cost+",\"kcal\":"+kcal+"}"));
            writeFile(json.toString(),consumed);
        } catch (Exception e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG).show();
        }
    }
    public void removeConsumed(int alcoID){
        try {
            List<JSONObject> allConsumed=getConsumed();
            for(int i=0;i<allConsumed.size();i++){
                if(allConsumed.get(i).getInt("alcoID")==alcoID){
                    allConsumed.remove(i);
                }
            }
            writeFile(new JSONArray(allConsumed).toString(),consumed);
        } catch (Exception e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG).show();
        }
    }
    public List<JSONObject> getConsumed(){
        List<JSONObject> allConsumed=new ArrayList<JSONObject>();
        try {
            JSONArray json =new JSONArray(readFile(consumed));
            for(int i=0;i<json.length();i++){
                allConsumed.add(json.getJSONObject(i));
            }
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed, try clearing application data!",Toast.LENGTH_LONG).show();
            return null;
        }
        return allConsumed;
    }
    /**
     Gets id for new consumed
     */
    private int getIdConsumed(){
        try {
            JSONArray json =new JSONArray(readFile(consumed));
            if(json.length()==0){
                return 0;
            }
            return json.getJSONObject(json.length()-1).getInt("alcoID")+1;
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG).show();
            return -1;
        }

    }


    public void addDrink(String name,Double alco,String icon,Boolean favorite,Double quantity,Double cost,Double kcal){
        try {
            int drinkID=getIdDrink();
            JSONArray json =new JSONArray(readFile(drinks));
            json=json.put(new JSONObject("{\"drinkID\":"+drinkID+",\"name\":"+name+",\"alco\": "+alco+",\"icon\": "+icon+",\"favorite\": "+favorite+",\"quantity\":"+quantity+",\"cost\": "+cost+",\"kcal\":"+kcal+"}"));
            writeFile(json.toString(),drinks);
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG).show();
        }
    }

    public void removeDrink(int drinkID){
        try {
            List<JSONObject> allDrinks=getDrinks();
            for(int i=0;i<allDrinks.size();i++){
                if(allDrinks.get(i).getInt("drinkID")== drinkID){
                    allDrinks.remove(i);
                }
            }
            writeFile(new JSONArray(allDrinks).toString(),drinks);
        } catch (Exception e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG).show();
        }
    }
    public List<JSONObject> getDrinks(){
        List<JSONObject> allDrinks=new ArrayList<JSONObject>();
        try {
            JSONArray json =new JSONArray(readFile(drinks));
            for(int i=0;i<json.length();i++){
                allDrinks.add(json.getJSONObject(i));
            }
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG).show();
            return null;
        }
        return allDrinks;
    }
    private int getIdDrink(){
        try {
            JSONArray json =new JSONArray(readFile(drinks));
            if(json.length()==0){
                return 0;
            }
            return json.getJSONObject(json.length()-1).getInt("drinkID")+1;
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG).show();
            return -1;
        }

    }
    public List<JSONObject> getFavDrinks(){
        List<JSONObject> allDrinks=new ArrayList<JSONObject>();
        try {
            JSONArray json =new JSONArray(readFile(drinks));
            for(int i=0;i<json.length();i++){
                if(json.getJSONObject(i).getBoolean("favorite")){
                    allDrinks.add(json.getJSONObject(i));
                }
            }
        } catch (JSONException e) {
            Toast.makeText(context,"ERROR: Data storage failed!",Toast.LENGTH_LONG).show();
            return null;
        }
        return allDrinks;
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
