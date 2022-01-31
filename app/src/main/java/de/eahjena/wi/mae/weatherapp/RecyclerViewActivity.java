package de.eahjena.wi.mae.weatherapp;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * shows content.xml
 * we execute the API call and get all the JSON Objects
 * those JSON Objects are put into an array list which we hand over to the recycler view
 * we also implement a method so that when an item from the recycler is clicked the details are shown
 */
public class RecyclerViewActivity extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener{

    public static final String EXTRA_NAME = "StationName";
    public static final String EXTRA_OPEN = "StationOpen";
    public static final String EXTRA_STREET = "StationStreet";
    public static final String EXTRA_HOUSE_NUMBER = "StationHouseNumber";
    public static final String EXTRA_ZIP = "StationZip";
    public static final String EXTRA_CITY = "StationCity";
    public static final String EXTRA_E5 = "StationE5";
    public static final String EXTRA_E10 = "StationE10";
    public static final String EXTRA_DIESEL = "StationDiesel";

    List<ContentModelClass> stationList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        stationList = new ArrayList<>();
        recyclerView = findViewById(R.id.rvStations);

        GetData getData = new GetData();
        getData.execute();
    }

    private void PutDataIntoRecyclerView(List<ContentModelClass> stationList){

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, stationList);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(RecyclerViewActivity.this);
    }

    /**
     *
     * @param position -> the position from the item in the RecyclerViewList that was clicked
     *                 when an item is clicked the DetailsActivity is called with all the details
     *                 belonging to the selected petrol station
     *
     *  putExtra -> is used to send information between activities -> sends a copy of the object
     */
    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(RecyclerViewActivity.this, DetailsActivity.class);
        ContentModelClass clickedItem = stationList.get(position);

        detailIntent.putExtra(EXTRA_NAME, clickedItem.getS_name());
        detailIntent.putExtra(EXTRA_OPEN,clickedItem.getS_open());
        detailIntent.putExtra(EXTRA_STREET, clickedItem.getS_street());
        detailIntent.putExtra(EXTRA_HOUSE_NUMBER, clickedItem.getS_house_number());
        detailIntent.putExtra(EXTRA_ZIP, clickedItem.getS_zip());
        detailIntent.putExtra(EXTRA_CITY, clickedItem.getS_city());
        detailIntent.putExtra(EXTRA_E5, clickedItem.getS_price_e5());
        detailIntent.putExtra(EXTRA_E10, clickedItem.getS_price_e10());
        detailIntent.putExtra(EXTRA_DIESEL, clickedItem.getS_price_diesel());

        startActivity(detailIntent);
    }

    /**
     *  AsyncTask -> for using threads <Params, Progress, Result>
     *      Param -> type of parameters sent to the task upn execution
     *      Progress -> type of progress units used in the background computation
     *      Result -> type of result from the background computation
     */
    public class GetData extends AsyncTask<String, String, String>{

        final static String TAG = "GetData";

        @Override
        protected String doInBackground(String... strings) {

            String data = "";

            try {
                String latitude = MainActivity.locationLat;
                String longitude = MainActivity.locationLong;
                String radius = MainActivity.spinnerRadius;
                URL url = new URL("https://creativecommons.tankerkoenig.de/json/list.php?lat="+latitude+"&lng="+longitude+"&rad="+radius+"&sort=dist&type=all&apikey=5fde221a-19b1-a8a1-1f7c-a032f0239719");
                //Berlin: lat=52.517&lng=13.388
                //EAH Jena lat=50.918&lng11.568
                //API Key: 5fde221a-19b1-a8a1-1f7c-a032f0239719
                //Bsp.: https://creativecommons.tankerkoenig.de/json/list.php?lat=52.517&lng=13.388&rad=15&sort=dist&type=all&apikey=5fde221a-19b1-a8a1-1f7c-a032f0239719
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //to read the data we need:
                InputStream inputStream = httpURLConnection.getInputStream();
                //to read data from InputStream:
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                //while line that comes from bufferedReader is not empty we will put this data into the data String line per line until it is empty
                while ((line = bufferedReader.readLine()) != null) {

                    data = data + line;

                }

                return data;
            }
                 catch (MalformedURLException e) {
                    Log.e(TAG, "getData() malformed URL", e);
                } catch (IOException e) {
                    Log.e(TAG, "getData() IOException", e);
                }

            return data;
        }

        /**
         *  Json Array "stations" that we get from API Call is analyzed -> gets JSON Objects -> passes to ModelClass
         *      -> passes to AdapterClass which displays into RecyclerView
         *  OnPostExecute() -> invoked on the UI thread after the background computation finishes
         *      -> the value returned by the background computation (result) is passed to this step as a parameter
         */
        @Override
        protected void onPostExecute(String s){
            //super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray stations = jsonObject.getJSONArray("stations");
                for (int i = 0; i< stations.length(); i++){

                    JSONObject stationsJSONObject = stations.getJSONObject(i);

                    ContentModelClass modelClass = new ContentModelClass();
                    modelClass.setS_brand(stationsJSONObject.getString("brand"));
                    modelClass.setS_name(stationsJSONObject.getString("name"));
                    modelClass.setShopOpen(stationsJSONObject.getString("isOpen"));
                    modelClass.setS_street(stationsJSONObject.getString("street"));
                    modelClass.setS_house_number(stationsJSONObject.getString("houseNumber"));
                    modelClass.setS_zip(stationsJSONObject.getString("postCode"));
                    modelClass.setS_city(stationsJSONObject.getString("place"));
                    modelClass.setS_price_e5(stationsJSONObject.getString("e5"));
                    modelClass.setS_price_e10(stationsJSONObject.getString("e10"));
                    modelClass.setS_price_diesel(stationsJSONObject.getString("diesel"));

                    stationList.add(modelClass);
                }


            } catch (JSONException e) {
                //e.printStackTrace();
                Log.e(TAG,"JSON Exception", e);
            }

            PutDataIntoRecyclerView(stationList);

        }
    }
}
