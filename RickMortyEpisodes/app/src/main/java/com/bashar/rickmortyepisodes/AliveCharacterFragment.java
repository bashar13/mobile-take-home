package com.bashar.rickmortyepisodes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;


public class AliveCharacterFragment extends Fragment {

    RecyclerView characterListView;
    AdapterCharacterList adapterCharacterList;


    public AliveCharacterFragment() {
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

        return view;
    }


    private void updateView(ArrayList<CharacterDataModel> characterList) {

        System.out.println("alive fragment = " + characterList.size());
        adapterCharacterList = new AdapterCharacterList(characterList);
        characterListView.setHasFixedSize(true);
        characterListView.setLayoutManager(new LinearLayoutManager(getContext()));
        characterListView.setAdapter(adapterCharacterList);
    }

//    private void enableSwipe(){
//        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
//                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                int position = viewHolder.getAdapterPosition();
//
//                if (direction == ItemTouchHelper.LEFT){
////                    final ColorSpace.Model deletedModel = imageModelArrayList.get(position);
////                    final int deletedPosition = position;
//                    adapter.removeItem(position);
//                    // showing snack bar with Undo option
//                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), " removed from Recyclerview!", Snackbar.LENGTH_LONG);
//                    snackbar.setAction("UNDO", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            // undo is selected, restore the deleted item
//                            adapter.restoreItem(deletedModel, deletedPosition);
//                        }
//                    });
//                    snackbar.setActionTextColor(Color.YELLOW);
//                    snackbar.show();
//                } else {
//                    final Model deletedModel = imageModelArrayList.get(position);
//                    final int deletedPosition = position;
//                    adapter.removeItem(position);
//                    // showing snack bar with Undo option
//                    Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), " removed from Recyclerview!", Snackbar.LENGTH_LONG);
//                    snackbar.setAction("UNDO", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            // undo is selected, restore the deleted item
//                            adapter.restoreItem(deletedModel, deletedPosition);
//                        }
//                    });
//                    snackbar.setActionTextColor(Color.YELLOW);
//                    snackbar.show();
//                }
//            }
//
//            @Override
//            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//
//                Bitmap icon;
//                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
//
//                    View itemView = viewHolder.itemView;
//                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
//                    float width = height / 3;
//
//                    if(dX > 0){
//                        p.setColor(Color.parseColor("#388E3C"));
//                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
//                        c.drawRect(background,p);
//                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.delete);
//                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
//                        c.drawBitmap(icon,null,icon_dest,p);
//                    } else {
//                        p.setColor(Color.parseColor("#D32F2F"));
//                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
//                        c.drawRect(background,p);
//                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.delete);
//                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
//                        c.drawBitmap(icon,null,icon_dest,p);
//                    }
//                }
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//            }
//        };
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);
//    }

    private final BroadcastReceiver mHandleMessageReceiver = new
            BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Bundle bundle = intent.getBundleExtra("CHAR_DATA");
                    ArrayList<CharacterDataModel> characterList = (ArrayList<CharacterDataModel>)bundle.getSerializable("ALIVE_CHAR");

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
}

