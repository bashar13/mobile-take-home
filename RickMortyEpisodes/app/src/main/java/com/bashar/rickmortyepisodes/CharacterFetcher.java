package com.bashar.rickmortyepisodes;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class CharacterFetcher extends AsyncTask<String, Void, ArrayList<CharacterDataModel>> {

    private String TAG = CharacterFetcher.class.getSimpleName();
    ArrayList<CharacterDataModel> characterList = new ArrayList<>();
    public AsyncResponseForCharacterList delegate = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Toast.makeText(get,"Json Data is downloading",Toast.LENGTH_LONG).show();

    }

    @Override
    protected ArrayList<CharacterDataModel> doInBackground(String... params) {

        HTTPHandler sh = new HTTPHandler();
        // Making a request to url and getting response
        String url = params[0];

        String jsonStr = sh.makeServiceCall(url);
        try {
            JSONArray characters = new JSONArray(jsonStr);

            for (int i=0; i< characters.length(); i++) {
                JSONObject character = characters.getJSONObject(i);
                CharacterDataModel characterDataModel = new CharacterDataModel(character);
                characterList.add(characterDataModel);
            }
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }

        for (CharacterDataModel item: characterList) {
            System.out.println(item.getCharName());
        }

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

        return characterList;
    }

    @Override
    protected void onPostExecute(ArrayList<CharacterDataModel> result) {
        super.onPostExecute(result);

        delegate.updateUIOnProcessFinish(result);

    }
}
