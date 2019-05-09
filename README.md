# Drinking application

## Colors
```#001445  - dark blue```
```#387edc  - light blue```
```#ffffff  - white```


## Random code
### Button open new activity
```Java
Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DesActivity.class);
                intent.putExtra("string", "Go to other Activity from a ViewPager Fragment");
                startActivity(intent);
            }
        });
```
