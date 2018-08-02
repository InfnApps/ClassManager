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

    List<QuestionCard> questionCards;
    public static final java.lang.String QUESTIONS_FILENAME = "questionCards.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String defaultUser = new String(1,
                            "Humberto",
                            "humberto@al.infnet.edu.br",
                            "ADS");

        questionCards = new ArrayList<>();
        for (int i = 1; i < 3; i++){
                questionCards.add(new QuestionCard("Qual será a " + i + "a pergunta?",
                        defaultUser,
                        true));
            }


        QuestionAdapter adapter = new QuestionAdapter(questionCards);

        RecyclerView questionList = findViewById(R.id.questions_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        //GridLayoutManager llm = new GridLayoutManager(this, 2);
        //StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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
