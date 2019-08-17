package com.bashar.rickmortyepisodes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class AliveCharacterFragment extends Fragment {

    RecyclerView characterListView;
    ProgressBar progBarView;
    TextView noDataText;
    CharacterListAdapter characterListAdapter;
    private Paint p = new Paint();
    SendDeletedCharacter sendDeletedCharacter;
    ArrayList<CharacterDataModel> characterList = new ArrayList<>();
    ArrayList<CharacterDataModel> deletedCharacters = new ArrayList<>();


    public AliveCharacterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_character_list, container, false);

        characterListView = view.findViewById(R.id.aliveCharList);
        progBarView = view.findViewById(R.id.loadingPanel);
        noDataText = view.findViewById(R.id.noData);

        IntentFilter filter = new IntentFilter("BROADCAST_DATA_SEND");
        getActivity().getApplicationContext().
                registerReceiver(mHandleMessageReceiver, filter);

        enableSwipe();
        return view;
    }


    private void updateView(ArrayList<CharacterDataModel> list) {

        if(!list.isEmpty()) {
            characterList = list;
            //System.out.println("alive fragment = " + characterList.size());
            characterListAdapter = new CharacterListAdapter(characterList);
            characterListView.setHasFixedSize(true);
            characterListView.setLayoutManager(new LinearLayoutManager(getContext()));
            characterListView.setAdapter(characterListAdapter);
            noDataText.setVisibility(View.GONE);
        }
        else {
            noDataText.setText(getText(R.string.alive_char));
            noDataText.setVisibility(View.VISIBLE);
        }

        progBarView.setVisibility(View.GONE);

    }

    private void enableSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                deletedCharacters.add(characterList.get(position));
                characterListAdapter.removeItem(position);
                deletedCharacters.get(deletedCharacters.size()-1).setCharStatus("Dead");

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.GRAY);
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.dead);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,
                                (float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(characterListView);
    }


    private final BroadcastReceiver mHandleMessageReceiver = new
            BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Bundle bundle = intent.getBundleExtra("CHAR_DATA");
                    ArrayList<CharacterDataModel> characterList =
                            (ArrayList<CharacterDataModel>)bundle.getSerializable("ALIVE_CHAR");

                    Collections.sort(characterList);

                    System.out.println("Fragment alive = " + characterList.size());

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            deletedCharacters.clear();
        }
        else {
            if(deletedCharacters.size() != 0) {
                sendDeletedCharacter.sendData(deletedCharacters);
            }
        }
    }

    interface SendDeletedCharacter {
        void sendData(ArrayList<CharacterDataModel> list);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            sendDeletedCharacter = (SendDeletedCharacter) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
}

