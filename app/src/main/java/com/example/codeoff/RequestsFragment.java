package com.example.codeoff;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by HP on 28-10-2017.
 */

public class RequestsFragment extends Fragment {

    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_requests, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Requests");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.verticalLayout);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.child("Request").getChildren()) {

                    for (DataSnapshot e : d.getChildren()) {

                        Request r = e.getValue(Request.class);
                        View v = inflater.inflate(R.layout.inflator_request, null);
                        TextView heading = (TextView) v.findViewById(R.id.requestName);
                        TextView name = (TextView) v.findViewById(R.id.uploaderName);
                        TextView description = (TextView) v.findViewById(R.id.requestDescription);
                        heading.setText(r.get_title());
                        name.setText(r.get_uploader());
                        description.setText(r.get_description());
                        LinearLayout commentsLayout = (LinearLayout) v.findViewById(R.id.scrollViewLayout);


                        for (int i = 0; i < r.get_comments().size(); i++) {

                            TextView tv = new TextView(getActivity());
                            tv.setText(r.get_comments().get(i).get_text()+"\n"+r.get_comments().get(i).get_commenter());
                            commentsLayout.addView(tv);

                        }

                        mainLayout.addView(v);
                    }



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}