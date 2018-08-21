package br.edu.infnet.classmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;

import br.edu.infnet.classmanager.utils.Constants;

public class QuestionFeedbackActivity extends AppCompatActivity {

    String questionKey;
    QuestionCard questionCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_feedback);

        Intent intent = getIntent();

        questionKey = intent.getStringExtra(Constants.QUESTION_FB_KEY);

        questionCard = (QuestionCard) intent.
                getSerializableExtra(Constants.QUESTIONCARD_KEY);


        TextView body = findViewById(R.id.question_body);
        TextView moment = findViewById(R.id.question_moment);
        TextView userName = findViewById(R.id.asker_name);

        body.setText(questionCard.getBody());
        moment.setText(DateFormat.getTimeInstance().format(questionCard.getMoment()));
        userName.setText(questionCard.getAskerName());
    }

    public void setAsAnswered(View view){
        EditText commentField = findViewById(R.id.answer_comment_field);
        //TODO: adicionar comentários à pergunta


        DatabaseReference questionReference = FirebaseDatabase.getInstance().
                getReference(Constants.ACTIVE_QUESTIONS_ENDPOINT).
                child(questionKey);
        //deleta do firebase
        finish();

        questionReference.removeValue();

        DatabaseReference answeredReference = FirebaseDatabase.getInstance().
                getReference(Constants.ANSWERED_QUESTIONS_ENDPOINT).push();

        //Adiciona à lista de respondidas
        answeredReference.setValue(questionCard);



        //TODO: add and stop progress dialog on completion
                /*.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
            }
        });*/



        //Intent intent = new Intent();
        //intent.putExtra()
        //setResult(RESULT_OK, intent);

    }
}
