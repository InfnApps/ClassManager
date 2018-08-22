package br.edu.infnet.classmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

import br.edu.infnet.classmanager.models.Answer;
import br.edu.infnet.classmanager.models.QuestionCard;
import br.edu.infnet.classmanager.utils.Constants;

public class ShowQuestionActivity extends AppCompatActivity {

    String answerKey;
    QuestionCard questionCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_question);


        Intent intent = getIntent();

        answerKey = intent.getStringExtra(Constants.QUESTION_FB_KEY);

        questionCard = (QuestionCard) intent.
                getSerializableExtra(Constants.QUESTIONCARD_KEY);

        DatabaseReference answerReference = FirebaseDatabase.getInstance().
                getReference(answerKey);

        //answerReference.addChildEventListener(new COmp;

        answerReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Answer answer = dataSnapshot.getValue(Answer.class);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );


    }
}
