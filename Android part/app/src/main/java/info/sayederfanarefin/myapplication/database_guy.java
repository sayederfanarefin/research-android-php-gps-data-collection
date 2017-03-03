package info.sayederfanarefin.myapplication;


        import android.app.Activity;
        import android.content.ContentValues;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Handler;
        import android.os.Message;
        import android.util.Log;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;

/**
 * Created by NewUsername on 12/20/2016.
 */

public class database_guy {

    SQLiteDatabase db;
    String table_name;
    Activity activity;
    String var1[],var2[],var3[],var4[],var5[],var6[],var7[],var8[];
    int count =0;

    public database_guy(String database_location, String table_name){
        this.table_name = table_name;
        db = SQLiteDatabase.openDatabase(database_location, null, 0);
        load_from_db();
    }


    public Cursor run_query(){
        return db.rawQuery("SELECT * FROM "+table_name, null);
    }

    public Cursor run_query(String x){
        return db.rawQuery(x, null);
    }


    public void load_from_db(){
        count =0;
        Cursor c = run_query();
        var1 = new String[c.getCount()];
        var2 = new String[c.getCount()];
        var3 = new String[c.getCount()];
        var4 = new String[c.getCount()];
        var5 = new String[c.getCount()];
        var6 = new String[c.getCount()];
        var7 = new String[c.getCount()];
        var8 = new String[c.getCount()];


        if(c.getCount() > 0) {
            while (c.moveToNext()) {

                if(c.getColumnCount() >= 1){
                    var1[count] = c.getString(0);
                }
                if(c.getColumnCount() >= 2){
                    var2[count] = c.getString(1);
                }
                if(c.getColumnCount() >= 3){
                    var3[count] = c.getString(2);
                }
                if(c.getColumnCount() >= 4){
                    var4[count] = c.getString(3);
                }
                if(c.getColumnCount() >= 5){
                    var5[count] = c.getString(4);
                }
                if(c.getColumnCount() >= 6){
                    var6[count] = c.getString(5);
                }
                if(c.getColumnCount() >= 7){
                    var7[count] = c.getString(6);
                }
                if(c.getColumnCount() >= 8){
                    var8[count] = c.getString(7);
                }else{

                }
                count++;
            }
        }
        c.close();
    }
//    //news
//    public NewsAdapter load(NewsAdapter na){
//        load_from_db();
//
//        for (int i = 0; i < count; i++) {
//            News n = new News();
//            n.id = var1[i];
//            n.title = var2[i];
//            n.body = var3[i];
//            n.date = var4[i];
//            n.url = var5[i];
//            n.local_url = var6[i];
//            na.add(n);
//        }
//        return na;
//    }




    public void save(JSONObject result_jObj){
        String[] query_tobe_executed = {""};
        if(table_name.equals("news")) {
            String[] identifierss = {"id", "news_title", "news_body", "date_", "image_url", "local_image_url"};
            query_tobe_executed = build_query_strings(result_jObj, identifierss);
        }else if(table_name.equals("youtube_videos")) {
            String[] identifierss = {"id", "video_name", "video_link", "date_"};
            query_tobe_executed = build_query_strings(result_jObj, identifierss);
        }else if(table_name.equals("concerts")) {
            String[] identifierss = {"id", "concert_title", "concert_about", "concert_lat", "concert_lon", "date_"};
            query_tobe_executed = build_query_strings(result_jObj, identifierss);
        }else if(table_name.equals("album")) {
            String[] identifierss = {"id", "album_title", "album_art_url", "album_year", "date_", "album_art_local"};
            query_tobe_executed = build_query_strings(result_jObj, identifierss);
        }else if(table_name.equals("music")) {
            String[] identifierss = {"title", "album_id", "track_no", "url", "id", "date_", "source_local"};
            query_tobe_executed = build_query_strings(result_jObj, identifierss);
        }
        for(int h=0; h < query_tobe_executed.length;h++){
            db.execSQL(query_tobe_executed[h]);
        }
        //reCheckDatabase();
    }

    public void reCheckDatabase(){
        String ids[];
        String ids_2[];
        Cursor c = run_query("SELECT id FROM "+table_name);
        ids = new String[c.getCount()];
        ids_2 = new String[c.getCount()];
        int a = 0;
        while (c.moveToNext()) {
            ids [a] = c.getString(0);
            ids_2 [a] = c.getString(0);
        }
        for(int i =0; i <ids.length; i++){
            int count = 0;
            for(int j =0; j < ids_2.length; j++){
                if(ids[i].equals(ids_2[j])){
                    count++;
                }
            }
            if(count >1){
                String delete_duplicate_query = "DELETE FROM "+ table_name + " WHERE id = "+ ids[i];
                run_query(delete_duplicate_query);
            }
        }
    }

    public void  setActivity(Activity a ){
        this.activity= a ;
    }
//
//    public void cache(String url_column_name, final String local_url_column_name, JSONObject jo, final Handler hand){
//        try {
//            String ids = jo.getString("id");
//            ids = ids.substring(1, ids.length() - 1);
//            String[] idss = ids.split(",");
//            for (int l = 0; l < idss.length; l++){
//
//                final String id_current = idss[l];
//                String temp_query = "SELECT "+url_column_name+ " FROM "+table_name+" WHERE id = " + id_current;
//                Cursor cc = run_query(temp_query);
//
//                String url_ = "" ;
//                while (cc.moveToNext()) {
//                    url_ = cc.getString(0);
//                }
//                final download_save_guy dsg = new download_save_guy(activity);
//
//                Handler h = new Handler() {
//                    @Override
//                    public void handleMessage(Message msg) {
//                        switch (msg.what) {
//                            case 0:
//                                ContentValues cv = new ContentValues();
//                                cv.put(local_url_column_name,dsg.local_url);
//                                db.update(table_name, cv, "id=" + id_current, null);
//                                ///////////////////////////////////////////////////
//                                hand.sendEmptyMessage(0);
//                                ///////////////////////////////////////
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                };
//
//                dsg.download(url_, h);
//
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//    }

    public String[] build_query_strings (JSONObject result_jObj, String[] identifiers){

        String[] query_strings={};
        try {
            query_strings = new String[disect_value(result_jObj.getString(identifiers[0])).length];
            int quer_strings_pointer = 0;

            for(int j =0; j < query_strings.length; j++){
                query_strings[j] = "INSERT INTO "+table_name+" VALUES (";
            }

            for(int i =0; i < identifiers.length;i++){
                String returned_array[] = disect_value(result_jObj.getString(identifiers[i]));
            /*for (int ii = 0; ii < returned_array.length; ii++) {
                Log.v("returned array  ", returned_array[ii]);
            }*/
                for (int ii = 0; ii < returned_array.length; ii++) {
                    String temp = returned_array[ii];

                    String returned_array_unit = temp.replaceFirst("\"","");//substring(1, temp.length());

                    if(returned_array_unit.length() >1 && returned_array_unit.charAt(returned_array_unit.length()-1) == '"'){
                        returned_array_unit = returned_array_unit.substring(0, returned_array_unit.length()-1);
                    }

                    query_strings[ii] += "'"+returned_array_unit+"',";
                }
            }

            // complete query
            for(int j =0; j < query_strings.length; j++){
                String temp_query_string = query_strings[j];
                char temp_query_string_array[] = temp_query_string.substring(temp_query_string.length()-4,temp_query_string.length()).toCharArray();
                for(int k = 0;k<temp_query_string_array.length;k++){

                    Log.v("char array", String.valueOf(temp_query_string_array));
                    if(temp_query_string_array[k] == ','){

                        int the_comma = temp_query_string.lastIndexOf(",");
                        Log.v("====camma", query_strings[j].substring(0, the_comma));
                        query_strings[j] = query_strings[j].substring(0, the_comma);
                        break;
                    }
                }
                query_strings[j] += " )";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return query_strings;
    }
    public String[] disect_value(String x){
        x =x.substring(1, x.length() - 1);
        String[] x_array = x.split("\",");
        return x_array;
    }
    public String filter_string (String in){
        String toBeReturned = in.replaceAll("'", "");
        return toBeReturned;
    }
    public String getLastInsertedId(){
        String selectQuery = "SELECT date_ FROM "+table_name;
        Cursor cursor = db.rawQuery(selectQuery, null);
        String[] data = new String[cursor.getCount()];
        int count_temp = 0;
        while (cursor.moveToNext()) {
            data[count_temp] = cursor.getString(0);
            count_temp++;
        }
        cursor.close();

        if(data.length > 0){
            String latest = data[0];
            for (int j = 1; j < data.length; j++) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd h:m:s");
                try {
                    if (sdf.parse(data[j]).after(sdf.parse(latest))) {
                        latest = data[j];
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            selectQuery = "SELECT id FROM "+table_name+" WHERE date_='" + latest + "'";
            cursor = db.rawQuery(selectQuery, null);
            data = new String[cursor.getCount()];
            count_temp = 0;
            while (cursor.moveToNext()) {
                data[count_temp] = cursor.getString(0);
                count_temp++;
            }
        }
        return data[0];
    }

    public int current_size(){
        return 0;
    }
}
