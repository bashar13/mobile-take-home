package com.bashar.rickmortyepisodes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bashar.rickmortyepisodes.ui.main.SectionsPagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CharacterListActivity extends AppCompatActivity implements AsyncResponseForCharacterList, AliveCharacterFragment.SendDeletedCharacter {

    private String TAG = CharacterListActivity.class.getSimpleName();
    CharacterFetcher asyncTask = new CharacterFetcher();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        String characterIds = intent.getStringExtra("CHARACTER_IDS");
        System.out.println(TAG + characterIds);

        asyncTask.delegate = this;

        getCharacters(characterIds);

    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        dataPasser = (OnDataPass) context;
//    }


    void getCharacters(String ids) {

        String urlStr = "https://rickandmortyapi.com/api/character/" + ids;
        System.out.println(TAG + urlStr);
        asyncTask.execute(urlStr);
    }

    @Override
    public void updateUIOnProcessFinish(ArrayList<CharacterDataModel> result) {

        System.out.println(TAG + result.size());
        ArrayList<CharacterDataModel> aliveCharacters = new ArrayList<>();
        ArrayList<CharacterDataModel> deadCharacters = new ArrayList<>();
        for (CharacterDataModel item: result) {
            if(item.getCharStatus().equalsIgnoreCase("Alive")){
                aliveCharacters.add(item);
            } else if(item.getCharStatus().equalsIgnoreCase("Dead")) {
                deadCharacters.add(item);
            }
        }


        Intent intent = new Intent("BROADCAST_DATA_SEND");
        Bundle bundle = new Bundle();
        bundle.putSerializable("ALIVE_CHAR", (Serializable)aliveCharacters);
        bundle.putSerializable("DEAD_CHAR", (Serializable)deadCharacters);
        intent.putExtra("CHAR_DATA", bundle);
        sendBroadcast(intent);
    }

    @Override
    public void sendData(ArrayList<CharacterDataModel> list) {
        String tag = "android:switcher:" + R.id.view_pager + ":" + 1;
        DeadCharacterFragment fragment = (DeadCharacterFragment)getSupportFragmentManager().findFragmentByTag(tag);
        fragment.addDeadCharacters(list);
    }
}