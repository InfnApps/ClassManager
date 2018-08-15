package br.edu.infnet.classmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.edu.infnet.classmanager.utils.Constants;

public class QuestionComposer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_composer);
    }

    public void saveQuestion(View view){
        //pega referências para as views na interface
        EditText questionField = findViewById(R.id.question_field);
        CheckBox box = findViewById(R.id.anonymous_box);

        // pega os valores necessários para construir uma QuestionCard a partir das views
        String questionBody = questionField.getText().toString();
        boolean isAnonym = box.isChecked();
        String askerName = "Anônimo";
        if (!isAnonym){
            askerName = MainActivity.getAuthenticatedUserName();
        }
        final QuestionCard questionCard = new QuestionCard(questionBody, askerName, isAnonym);

        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference(Constants.ACTIVE_QUESTIONS_ENDPOINT);
        dbReference = dbReference.push();
        dbReference.setValue(questionCard);

        // encerra a Activity
        finish();
    }
}
