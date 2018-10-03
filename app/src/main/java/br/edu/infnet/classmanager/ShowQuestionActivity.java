package br.edu.infnet.classmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.DateFormat;

import br.edu.infnet.classmanager.models.Answer;
import br.edu.infnet.classmanager.models.QuestionCard;
import br.edu.infnet.classmanager.utils.Constants;

public class ShowQuestionActivity extends AppCompatActivity {

    String answerKey;
    QuestionCard questionCard;
    private ViewGroup mLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_question);


        Intent intent = getIntent();

        mLoader = findViewById(R.id.loader);
        mLoader.setVisibility(View.VISIBLE);

        answerKey = intent.getStringExtra(Constants.QUESTION_FB_KEY);

        questionCard = (QuestionCard) intent.
                getSerializableExtra(Constants.QUESTIONCARD_KEY);

        setUpQuestionCard(questionCard);

        DatabaseReference answerReference = FirebaseDatabase.getInstance().
                getReference("Answers").child(answerKey);


        //answerReference.addChildEventListener(new COmp;

        //answerReference.addValueEventListener()
        answerReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){
                            Answer answer = dataSnapshot.getValue(Answer.class);
                            TextView textView = findViewById(R.id.last_modified_time);
                            textView.setText(
                                    DateFormat.getTimeInstance().
                                            format(answer.getLastModified()));
                            textView = findViewById(R.id.last_modified_user);
                            textView.setText(answer.getUserName());
                            textView = findViewById(R.id.answer_text);
                            textView.setText(answer.getText());
                            mLoader.setVisibility(View.GONE);
                        } else {
                            Log.e("ANSWER", answerKey);
                            mLoader.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }


    private void setUpQuestionCard(QuestionCard questionCard){
        TextView textView = findViewById(R.id.question_body);
        textView.setText(questionCard.getBody());

        textView = findViewById(R.id.question_moment);
        textView.setText(DateFormat.getTimeInstance().
                format(questionCard.getMoment()));

        textView = findViewById(R.id.asker_name);
        textView.setText(questionCard.getAskerName());
    }
}