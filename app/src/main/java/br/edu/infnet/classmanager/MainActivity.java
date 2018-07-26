package br.edu.infnet.classmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User defaultUser = new User(1,
                            "Humberto",
                            "humberto@al.infnet.edu.br",
                            "ADS");

        questions = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            questions.add(new Question("Qual será a " + i + "a pergunta?",
                                            defaultUser,
                                            true));
        }

        QuestionAdapter adapter = new QuestionAdapter(questions);

        RecyclerView questionList = findViewById(R.id.questions_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        questionList.setLayoutManager(llm);

        questionList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        questionList.setAdapter(adapter);

    }


    public void askQuestion(View view){
        Intent intent = new Intent(this, QuestionComposer.class);
        startActivity(intent);
    }


}
