package br.edu.infnet.classmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;

import br.edu.infnet.classmanager.models.Answer;
import br.edu.infnet.classmanager.models.QuestionCard;
import br.edu.infnet.classmanager.models.QuestionRating;
import br.edu.infnet.classmanager.utils.Constants;

public class ShowQuestionActivity extends AppCompatActivity {

    String answerKey;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_question);

        firebaseAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        answerKey = intent.getStringExtra(Constants.QUESTION_FB_KEY);
        QuestionCard questionCard = (QuestionCard) intent.getSerializableExtra(Constants.QUESTION_CARD_KEY);

        QuestionRating questionRating = null;
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null) {
            questionRating = questionCard.getCurrentUserRating(firebaseUser.getUid());
        }

        setUpQuestionCard(questionCard, questionRating);
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference answerReference = firebaseDatabase.getReference("Answers").child(answerKey);

        //answerReference.addChildEventListener(new COmp;

        //answerReference.addValueEventListener()
        answerReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Answer answer = dataSnapshot.getValue(Answer.class);
                    answer.setId(dataSnapshot.getKey());
//                    answer.setId(dataSnapshot.getRef().getKey());

                    TextView textView = findViewById(R.id.last_modified_time);
                    textView.setText(DateFormat.getTimeInstance().format(answer.getLastModified()));

                    textView = findViewById(R.id.last_modified_user);
                    textView.setText(answer.getUserName());

                    textView = findViewById(R.id.answer_text);
                    textView.setText(answer.getText());
                } else {
                    Log.e("ANSWER", answerKey);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void setUpQuestionCard(final QuestionCard questionCard, final QuestionRating questionRating){
        TextView textView = findViewById(R.id.question_body);
        textView.setText(questionCard.getBody());

        textView = findViewById(R.id.question_moment);
        textView.setText(DateFormat.getTimeInstance().format(questionCard.getMoment()));

        textView = findViewById(R.id.asker_name);
        textView.setText(questionCard.getAskerName());

        RatingBar ratingBar = findViewById(R.id.answered_question_rating);

        if(questionRating != null) {
            ratingBar.setRating(questionRating.getRating());
        }

        textView = findViewById(R.id.answered_question_rating_average);
        textView.setText(String.format("(%.1f)", questionCard.getAverageRatings()));

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(fromUser) {
                    QuestionRating currentQuestionRating;
                    if  (questionRating == null)  {
                        currentQuestionRating = new QuestionRating();
                        questionCard.getQuestionRatingList().add(currentQuestionRating);
                    } else {
                        currentQuestionRating = questionRating;
                    }

                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if(firebaseUser == null) { // TODO: guardar sessão atual de um usuário anônimo (é possível avaliar diversas vezes sem nem reiniciar o app)
                        currentQuestionRating.setRatingUserId(null); // proteção redundante
                    } else {
                        currentQuestionRating.setRatingUserId(firebaseUser.getUid());
                    }
                    currentQuestionRating.setRating(rating);

                    DatabaseReference answeredQuestionsReference = firebaseDatabase.getReference(Constants.ANSWERED_QUESTIONS_ENDPOINT).child(answerKey);
                    answeredQuestionsReference.setValue(questionCard);

                    Toast.makeText(ShowQuestionActivity.this, "Pergunta avaliada com " + ((int) rating) + "  estrelas com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
