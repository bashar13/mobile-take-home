package com.bashar.rickmortyepisodes;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class EpisodesFetcher extends AsyncTask<String, Void, ArrayList<EpisodeDataModel>> {

    private String TAG = EpisodesFetcher.class.getSimpleName();
    ArrayList<EpisodeDataModel> episodeList = new ArrayList<>();
    public AsyncResponse delegate = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Toast.makeText(get,"Json Data is downloading",Toast.LENGTH_LONG).show();

    }

    @Override
    protected ArrayList<EpisodeDataModel> doInBackground(String... params) {

        HTTPHandler sh = new HTTPHandler();
        // Making a request to url and getting response
        String url = params[0];
        do {
            String jsonStr = sh.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray episodes = jsonObject.getJSONArray("results");
                    for (int i=0; i< episodes.length(); i++) {
                        JSONObject episode = episodes.getJSONObject(i);
                        EpisodeDataModel episodeDataModel = new EpisodeDataModel(episode);
                        episodeList.add(episodeDataModel);
                    }
                    JSONObject info = jsonObject.getJSONObject("info");
                    String nextUrl = info.getString("next");
                    url = nextUrl;
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            }

        } while(!url.isEmpty());


        //Log.e(TAG, "Response from url: " + jsonStr);
//        if (jsonStr != null) {
//            try {
//                JSONObject jsonObj = new JSONObject(jsonStr);
//
//                // Getting JSON Array node
//                JSONArray contacts = jsonObj.getJSONArray("contacts");
//
//                // looping through All Contacts
//                for (int i = 0; i < contacts.length(); i++) {
//                    JSONObject c = contacts.getJSONObject(i);
//                    String id = c.getString("id");
//                    String name = c.getString("name");
//                    String email = c.getString("email");
//                    String address = c.getString("address");
//                    String gender = c.getString("gender");
//
//                    // Phone node is JSON Object
//                    JSONObject phone = c.getJSONObject("phone");
//                    String mobile = phone.getString("mobile");
//                    String home = phone.getString("home");
//                    String office = phone.getString("office");
//
//                    // tmp hash map for single contact
//                    HashMap<String, String> contact = new HashMap<>();
//
//                    // adding each child node to HashMap key => value
//                    contact.put("id", id);
//                    contact.put("name", name);
//                    contact.put("email", email);
//                    contact.put("mobile", mobile);
//
//                    // adding contact to contact list
//                    contactList.add(contact);
//                }
//            } catch (final JSONException e) {
//                Log.e(TAG, "Json parsing error: " + e.getMessage());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Json parsing error: " + e.getMessage(),
//                                Toast.LENGTH_LONG).show();
//                    }
//                });
//
//            }
//
//        } else {
//            Log.e(TAG, "Couldn't get json from server.");
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(getApplicationContext(),
//                            "Couldn't get json from server. Check LogCat for possible errors!",
//                            Toast.LENGTH_LONG).show();
//                }
//            });
//        }

        return episodeList;
    }

    @Override
    protected void onPostExecute(ArrayList<EpisodeDataModel> result) {
        super.onPostExecute(result);

        delegate.updateUIOnProcessFinish(result);



    }
}
