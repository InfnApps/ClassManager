package br.edu.infnet.classmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class QuestionComposer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_composer);
    }

//    public void saveQuestion(View view){
//        EditText questionField = findViewById(R.id.question_field);
//        CheckBox box = findViewById(R.id.anonymous_box);
//
//        String user = String.getAnonymousUser();
//        /*if (!box.isChecked()){
//            user =
//        }*/
//        QuestionCard questionCard = new QuestionCard(questionField.getText().toString(),
//                user, box.isChecked());
//
//        File outFile = new File(getFilesDir(),MainActivity.QUESTIONS_FILENAME);
//        try{
//            if (!outFile.exists()){
//                outFile.createNewFile();
//            }
//            FileOutputStream outputStream = new FileOutputStream(outFile);
//            // Para lidar com strings
//            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
//            writer.write("#\n");
//            writer.write(questionCard.toString());
//            writer.close();
//        }catch (IOException exception){
//            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
//
//    }
}
