package br.edu.infnet.classmanager;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import java.text.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter {

    List<QuestionCard> questionCards;
    OnFragmentInteractionListener fragmentInteractionListener;

    public QuestionAdapter(List<QuestionCard> questionCards) {
        this.questionCards = questionCards;
    }

    public QuestionAdapter() {
        this.questionCards = new ArrayList<>();
    }

    public QuestionAdapter(String fbEndpoint,
                           OnFragmentInteractionListener listener) {
        this.questionCards = new ArrayList<>();
        fragmentInteractionListener = listener;


        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference(fbEndpoint);
        // Lê dados do firebase
        dbReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                QuestionCard questionCard = dataSnapshot.getValue(QuestionCard.class);
                //QuestionAdapter adapter = (QuestionAdapter) questionsList.getAdapter();
                //adapter.
                addItem(questionCard);
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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.question_card, parent, false);



        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentInteractionListener != null){
                    fragmentInteractionListener.onCardClick(view);
                }
//                Context context = view.getContext();
//                context.startActivity(
//                        new Intent(context, QuestionFeedbackActivity.class));
            }
        });

        return new QuestionViewHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        QuestionCard questionCard = questionCards.get(position);

        QuestionViewHolder vh = (QuestionViewHolder) holder;
        vh.body.setText(questionCard.getBody());
        vh.moment.setText(DateFormat.getTimeInstance().format(questionCard.getMoment()));
        vh.userName.setText(questionCard.getAskerName());

    }

    @Override
    public int getItemCount() {
        return questionCards.size();
    }


    public void addItem(QuestionCard questionCard){
        questionCards.add(0, questionCard);
        //notifyItemRangeChanged(0, getItemCount());
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        questionCards.remove(position);
        notifyItemRemoved(position);
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {

        public TextView body;
        public TextView moment;
        public TextView userName;
        public QuestionViewHolder(View itemView) {
            super(itemView);
            body = itemView.findViewById(R.id.question_body);
            moment = itemView.findViewById(R.id.question_moment);
            userName = itemView.findViewById(R.id.asker_name);
        }
    }
}
