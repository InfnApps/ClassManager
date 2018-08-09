package br.edu.infnet.classmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference(Constants.QUESTIONS_ENDPOINT);
        dbReference = dbReference.push();
        dbReference.setValue(questionCard);

//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            File outFile = new File(getFilesDir(), MainActivity.QUESTIONS_FILENAME);
//                            OutputStream outputStream = new FileOutputStream(outFile, true);
//                            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
//                            // escreve no arquivo
//                            writer.write("#\n");
//                            writer.write(questionCard.toString());
//
//                            writer.close();
//                        } catch (final FileNotFoundException exception){
//                            QuestionComposer.this.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(QuestionComposer.this, exception.getMessage(), Toast.LENGTH_LONG).show();
//                                }
//                            });
//
//                        } catch (final IOException exception){
//                            QuestionComposer.this.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(QuestionComposer.this, exception.getMessage(), Toast.LENGTH_LONG).show();
//                                }
//                            });
//                        }
//                    }
//                }
//        ).start();


        // encerra a Activity
        finish();
    }
}
