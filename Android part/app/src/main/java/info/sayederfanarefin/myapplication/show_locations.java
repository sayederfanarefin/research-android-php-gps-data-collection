package info.sayederfanarefin.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class show_locations extends AppCompatActivity {
    private ListView news_list_view;
    int pagination_limit = 10;
    private database_guy dbg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        news_list_view = (ListView) findViewById(R.id.sho_locations_list);

        dbg = new database_guy(this.getApplicationContext().getDatabasePath("nemesis_local_db").toString(), "location_table");

        NewsAdapter newsAdapter = new NewsAdapter(this, R.layout.news_layout_unit);

        listView_adapter_setter(dbg.load(newsAdapter));
    }
    public void listView_adapter_setter(final NewsAdapter na){
        final pagination pg = new pagination(na);
        //newsAdapter2 = dbg.load(newsAdapter2);


        news_list_view.setAdapter(pg.load_current(na,0));

        news_list_view.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;
            private LinearLayout lBelow;

            int index = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;


            }

            private void isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                        && this.currentScrollState == SCROLL_STATE_IDLE) {
                    /** To do code here*/
                    if(++index <= pg.getTotalIndex()){

                        news_list_view.setAdapter(pg.load_current(na,index));

//                        DeveloperKey dk = new DeveloperKey();
                        news_list_view.setSelection(index*pagination_limit-1);

                    }

                }
            }
        });

    }
}
