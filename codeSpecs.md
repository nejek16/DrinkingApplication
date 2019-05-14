# Code specifications
## Custom buttons
### Animations
```JAVA
   //When you want animatoin to occur call this function with instance of your button as argument
    private void animateBt(Button bt){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(250);

        bt.startAnimation(fadeIn);
    }
```
### Rounded
```XML
   <Button
        android:id="@+id/button" <!-- Change me-->
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@drawable/rounded1"
        android:layout_marginBottom="5dp"
        android:layout_marginLeft="70dp"
        android:layout_marginRight="70dp"
        android:textAlignment="center"
        android:padding="14dp"
        android:text="Sign In"
        android:textSize="16dp"
        android:textColor="#fff"/>
```

## Costom textInput
### Square
```XML
    <EditText
        android:id="@+id/editText2" <!-- Change me-->
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="fuck you"
        android:inputType="text"
        android:textColor="#000"
        android:background="@color/white"
        android:padding="12dp"
        android:layout_marginTop="20dp"
        android:layout_marginBottom="10dp"
        android:layout_marginLeft="20dp"
        android:layout_marginRight="20dp"
        android:drawablePadding="16dp"/>
```
### Rounded
```XML
    <EditText
        android:id="@+id/editText2" <!-- Change me-->
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="fuck you"
        android:inputType="text"
        android:textColor="#000"
        android:background="@drawable/rounded2"
        android:padding="12dp"
        android:layout_marginTop="20dp"
        android:layout_marginBottom="10dp"
        android:layout_marginLeft="20dp"
        android:layout_marginRight="20dp"
        android:drawablePadding="16dp"/>
```
## JSONs
### Default JSON -> CONSUMED
```JSON
{
    "alcoID":0,
    "time":null,
    "drinkID":1,
    "name":"Gin",
    "alco": 0.40,
    "icon": null,
    "favorite": false,
    "quantity":0.5,
    "cost": 35.0,
    "kcal":20
}
```
### Default JSON for types of drinks
```JSON
{
  "drinks": [
   {
     "drinkID":0,
     "name":"Jameson",
     "alco": 0.40,
     "icon": null,
     "favorite": true,
     "quantity":0.75,
     "cost": 25.0,
     "kcal":10
   },
   {
     "drinkID":1,
     "name":"Gin",
     "alco": 0.40,
     "icon": null,
     "favorite": false,
     "quantity":0.5,
     "cost": 35.0,
     "kcal":20
   }
  ]
}
```
