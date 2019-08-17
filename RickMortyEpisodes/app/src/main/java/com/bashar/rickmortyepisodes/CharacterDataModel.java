package com.bashar.rickmortyepisodes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class CharacterDataModel implements Serializable, Comparable<CharacterDataModel> {

    private String TAG = CharacterDataModel.class.getSimpleName();
    private int charId;
    private String charName;
    private String charStatus;
    private String charSpecies;
    private String charGender;
    private String charOrigin;
    private String charLocation;
    private String charImage;
    private Date charCreated;


    CharacterDataModel(JSONObject charObject) {

        try {
            charId = charObject.getInt("id");
            charName = charObject.getString("name");
            charStatus = charObject.getString("status");
            charSpecies = charObject.getString("species");
            charGender = charObject.getString("gender");

            JSONObject origin = charObject.getJSONObject("origin");
            charOrigin = origin.getString("name");

            JSONObject location = charObject.getJSONObject("location");
            charLocation = location.getString("name");

            charImage = charObject.getString("image");
            String created = charObject.getString("created");
            charCreated = convertStringToDate(created);

        } catch (final JSONException e) {
            Log.e(TAG, "JSON parsing error" + e.getMessage());
        }


    }

    int getCharId() {
        return charId;
    }

    String getCharName() {
        return charName;
    }

    String getCharStatus() {
        return charStatus;
    }

    String getCharSpecies() {
        return charSpecies;
    }

    String getCharGender() {
        return charGender;
    }

    String getCharOrigin() {
        return charOrigin;
    }

    String getCharLocation() {
        return charLocation;
    }

    String getCharImage() {
        return charImage;
    }

    Date getCharCreated() {
        return charCreated;
    }

    private Date convertStringToDate(String dateStr) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (final Exception e) {
            Log.e(TAG, "Date Conversion error" + e.getMessage());
        }

        return date;
    }

    @Override
    public int compareTo(CharacterDataModel o) {
        return getCharCreated().compareTo(o.getCharCreated());
    }
}
