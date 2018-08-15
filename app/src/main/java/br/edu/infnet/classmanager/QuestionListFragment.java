package br.edu.infnet.classmanager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;

import br.edu.infnet.classmanager.utils.Constants;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionListFragment extends Fragment {

    RecyclerView questionsList;

    public QuestionListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_question_list, container, false);

        // pega o RecyclerView da interface do fragmento
        questionsList = rootView.findViewById(R.id.questions_list);

        List<QuestionCard> questionCards = new LinkedList<>();
        final QuestionAdapter adapter = new QuestionAdapter(questionCards);
        questionsList.setAdapter(adapter);
        questionsList.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle args = getArguments();
        if (args != null){
            String fbEndpoint = args.getString(Constants.QUESTIONS_ENDPOINT_KEY);
            DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference(fbEndpoint);
            // Lê dados do firebase
            dbReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    QuestionCard questionCard = dataSnapshot.getValue(QuestionCard.class);
                    QuestionAdapter adapter = (QuestionAdapter) questionsList.getAdapter();
                    adapter.addItem(questionCard);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    //TODO:
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else{
            Toast.makeText(getContext(), "Argumento vazio", Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    public static QuestionListFragment newInstance(String fbEndpoint) {

        Bundle args = new Bundle();
        QuestionListFragment fragment = new QuestionListFragment();

        args.putString(Constants.QUESTIONS_ENDPOINT_KEY, fbEndpoint);
        fragment.setArguments(args);

        return fragment;
    }

}
