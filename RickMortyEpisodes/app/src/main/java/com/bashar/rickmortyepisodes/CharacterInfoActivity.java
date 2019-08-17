package com.bashar.rickmortyepisodes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CharacterInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_info);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("CHAR_DATA");
        CharacterDataModel characterDataModel = (CharacterDataModel)bundle.getSerializable("CHAR_INFO");

        System.out.println(characterDataModel.getCharImage());

        ImageView charAvatar = findViewById(R.id.charAvatar);
        TextView charStatus = findViewById(R.id.charStatus);
        TextView charId = findViewById(R.id.charId);
        TextView charName = findViewById(R.id.charName);
        TextView charSpecies = findViewById(R.id.charSpecies);
        TextView charGender = findViewById(R.id.charGender);
        TextView charOrigin = findViewById(R.id.charOrigin);
        TextView charLocation = findViewById(R.id.charLocation);
        TextView charCreated = findViewById(R.id.charCreated);

        new DownloadImageTask(charAvatar).execute(characterDataModel.getCharImage());

        charStatus.setText(characterDataModel.getCharStatus());
        charId.setText(String.valueOf(characterDataModel.getCharId()));
        charName.setText(characterDataModel.getCharName());
        charSpecies.setText(characterDataModel.getCharSpecies());
        charGender.setText(characterDataModel.getCharGender());
        charOrigin.setText(characterDataModel.getCharOrigin());
        charLocation.setText(characterDataModel.getCharLocation());
        charCreated.setText(characterDataModel.getCharCreated().toString());
    }
}
