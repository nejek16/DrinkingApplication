package info.devexchanges.navvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Fragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);

    }
    int[] IMAGES = {R.drawable.beer,R.drawable.white_wine};
    String[] DRINK_NAMES = {"Beer","White vine"};
    String[] ALCO_LEVEL = {"5%","9%"};
    String[] VOLUME = {"0.5l","0.2l"};



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        ListView favList = (ListView) view.findViewById(R.id.favlist);
        CustomAdapter customadapter = new CustomAdapter();

        favList.setAdapter(customadapter);

        //Hides view of other tab (fragment_content)
        // LinearLayout contentFrag=(LinearLayout) view.findViewById(R.id.viewAddDrink);
        // contentFrag.setVisibility(View.GONE);
    }

    TextView txtFavorites;
    TextView txtConsumed;

    private void init(View view) {
        txtFavorites = (TextView) view.findViewById(R.id.txtFavorites);
        txtFavorites.setText("Favourites");
        txtConsumed = (TextView) view.findViewById(R.id.txtConsumed);
        txtConsumed.setText("Consumed");
    }
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return IMAGES.length;//size of list
        }

        @Override
        public Object getItem(int i) {
            return null;//ce nedela poprav
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getActivity().getLayoutInflater().inflate(R.layout.favourite_list_item,null);
            ImageView icon =(ImageView)view.findViewById(R.id.list_item_thumbnail);
            ImageView add =(ImageView)view.findViewById(R.id.drink_add);
            TextView drink = (TextView)view.findViewById(R.id.list_item_drink);
            TextView alcolvl = (TextView)view.findViewById(R.id.list_item_alco);
            TextView volume = (TextView)view.findViewById(R.id.list_item_price);

            add.setImageResource(R.drawable.add);
            icon.setImageResource(IMAGES[i]);
            drink.setText(DRINK_NAMES[i]);
            alcolvl.setText(ALCO_LEVEL[i]);
            volume.setText(VOLUME[i]);
            return view;
        }
    }

}
