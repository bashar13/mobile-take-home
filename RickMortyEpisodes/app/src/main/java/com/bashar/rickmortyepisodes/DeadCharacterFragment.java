package com.bashar.rickmortyepisodes;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;


public class DeadCharacterFragment extends Fragment {


    RecyclerView characterListView;
    ArrayList<CharacterDataModel> characterList = new ArrayList<>();
    public DeadCharacterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alive_character, container, false);

        characterListView = view.findViewById(R.id.aliveCharList);

        IntentFilter filter = new IntentFilter("BROADCAST_DATA_SEND");
        getActivity().getApplicationContext().
                registerReceiver(mHandleMessageReceiver, filter);

        System.out.println("Dead Fragment on Create");
        view.setId(R.id.aliveCharList);
        return view;
    }

    private void updateView(ArrayList<CharacterDataModel> characterList) {

        //System.out.println("dead fragment = " + characterList.size());
        CharacterListAdapter characterListAdapter = new CharacterListAdapter(characterList);
        characterListView.setHasFixedSize(true);
        characterListView.setLayoutManager(new LinearLayoutManager(getContext()));
        characterListView.setAdapter(characterListAdapter);
    }

    private final BroadcastReceiver mHandleMessageReceiver = new
            BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Bundle bundle = intent.getBundleExtra("CHAR_DATA");
                    characterList = (ArrayList<CharacterDataModel>)bundle.getSerializable("DEAD_CHAR");

                    Collections.sort(characterList);
                    System.out.println("Fragment dead = " + characterList.size());

                    updateView(characterList);
                }
            };


    @Override
    public void onDestroy() {
        try {

            getActivity().getApplicationContext().
                    unregisterReceiver(mHandleMessageReceiver);

        } catch (Exception e) {
            Log.e("UnRegister Error", "> " + e.getMessage());
        }
        super.onDestroy();
    }

    protected void addDeadCharacters(ArrayList<CharacterDataModel> list) {
        characterList.addAll(list);
        Collections.sort(characterList);
        updateView(characterList);
    }


}
