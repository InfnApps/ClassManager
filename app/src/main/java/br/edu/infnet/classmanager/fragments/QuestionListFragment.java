package br.edu.infnet.classmanager.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.edu.infnet.classmanager.OnFragmentInteractionListener;
import br.edu.infnet.classmanager.QuestionAdapter;
import br.edu.infnet.classmanager.R;
import br.edu.infnet.classmanager.utils.Constants;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionListFragment extends Fragment {

    RecyclerView questionsList;
    OnFragmentInteractionListener fragmentInteractionListener;

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

        //List<QuestionCard> questionCards = new LinkedList<>();


        Bundle args = getArguments();
        if (args != null){
            String fbEndpoint = args.getString(Constants.QUESTIONS_ENDPOINT_KEY);
            final QuestionAdapter adapter = new QuestionAdapter(
                    fbEndpoint, fragmentInteractionListener);
            questionsList.setAdapter(adapter);
            questionsList.setLayoutManager(new LinearLayoutManager(getContext()));
            //String fbEndpoint = args.getString(Constants.QUESTIONS_ENDPOINT_KEY);
//            DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference(fbEndpoint);
//            // Lê dados do firebase
//            dbReference.addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    QuestionCard questionCard = dataSnapshot.getValue(QuestionCard.class);
//                    QuestionAdapter adapter = (QuestionAdapter) questionsList.getAdapter();
//                    adapter.addItem(questionCard);
//                }
//
//                @Override
//                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(DataSnapshot dataSnapshot) {
//                    //TODO:
//                }
//
//                @Override
//                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
        }
        else{
            QuestionAdapter adapter = new QuestionAdapter();
            questionsList.setAdapter(adapter);
            questionsList.setLayoutManager(new LinearLayoutManager(getContext()));
            Toast.makeText(getContext(), "Argumento vazio", Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            fragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            // lança execção
            throw new ClassCastException();
        }
    }

    public static QuestionListFragment newInstance(String fbEndpoint) {

        Bundle args = new Bundle();
        QuestionListFragment fragment = new QuestionListFragment();

        args.putString(Constants.QUESTIONS_ENDPOINT_KEY, fbEndpoint);
        fragment.setArguments(args);

        return fragment;
    }

}
