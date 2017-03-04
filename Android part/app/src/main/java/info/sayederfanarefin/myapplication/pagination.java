package info.sayederfanarefin.myapplication;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by NewUsername on 3/2/2017.
 */

public class pagination {
    //NewsAdapter main_newsadapter;
    News[] main_newses;

    //sint current_index = 0;
    int total_index = 0;
    int total_items = 0;
int pagination_limit=10;


    public pagination(NewsAdapter n){
        main_newses = new News[n.getCount()];
        for (int k =0; k <n.getCount(); k ++){
            main_newses[k] = n.getItem(k);
        }

        total_items = n.getCount();
        total_index = total_items/pagination_limit;
    }

    public NewsAdapter load_current(NewsAdapter na, int index ){
        na.clear();
        //Log.v("pagination", "index given:" + String.valueOf(index));
        for (int k = 0; k < (index * pagination_limit) + pagination_limit; k++){
            if(k <total_items){
                na.add(main_newses[k]);
            }
        }
        return na;
    }
    public int getTotalIndex(){
        return total_index;
    }
}
