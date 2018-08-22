package br.edu.infnet.classmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;

import br.edu.infnet.classmanager.models.Answer;
import br.edu.infnet.classmanager.models.QuestionCard;
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
        EditText answerField = findViewById(R.id.answer_comment_field);
        //TODO: adicionar resposta à pergunta
        Answer answer = new Answer(answerField.getText().toString(),
                "Placeholder User");

        DatabaseReference questionReference = FirebaseDatabase.getInstance().
                getReference(Constants.ACTIVE_QUESTIONS_ENDPOINT).
                child(questionKey);
        //deleta do firebase

        questionReference.removeValue();

        DatabaseReference answeredQuestionsReference = FirebaseDatabase.getInstance().
                getReference(Constants.ANSWERED_QUESTIONS_ENDPOINT).push();
        //Adiciona à lista de respondidas, mas antes marca como respondida
        questionCard.setAnswered(true);
        answeredQuestionsReference.setValue(questionCard);



        DatabaseReference answersReference = FirebaseDatabase.getInstance().
                getReference(Constants.ANSWERS_ENDPOINT).
                child(answeredQuestionsReference.getKey());

        answersReference.setValue(answer);


        finish();

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
