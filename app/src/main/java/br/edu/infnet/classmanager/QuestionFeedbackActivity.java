package br.edu.infnet.classmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class QuestionFeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_feedback);
    }

    public void setAsAnswered(View view){
        EditText commentField = findViewById(R.id.answer_comment_field);
        //TODO: adicionar comentários à pergunta

        Intent intent = new Intent();
        //intent.putExtra()
        setResult(RESULT_OK, intent);
        finish();
    }
}
