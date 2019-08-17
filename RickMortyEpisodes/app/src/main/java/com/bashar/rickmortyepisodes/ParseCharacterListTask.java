package com.bashar.rickmortyepisodes;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class ParseCharacterListTask extends AsyncTask<String, Void, ArrayList<CharacterDataModel>> {

    private String TAG = ParseCharacterListTask.class.getSimpleName();
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

        return characterList;
    }

    @Override
    protected void onPostExecute(ArrayList<CharacterDataModel> result) {
        super.onPostExecute(result);

        delegate.updateUIOnProcessFinish(result);
    }
}
