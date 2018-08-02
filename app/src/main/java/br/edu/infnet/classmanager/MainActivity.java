package br.edu.infnet.classmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<QuestionCard> questionCards;
    public static final String QUESTIONS_FILENAME = "questionCards.txt";
    RecyclerView questionList;

    // Method to simulated a call for the authenticated user
    public static String getAuthenticatedUserName(){
        return "Josias";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User defaultUser = new User(1,
                            "Humberto",
                            "humberto@al.infnet.edu.br",
                            "ADS");

        questionCards = new ArrayList<>();
        for (int i = 1; i < 3; i++){
                questionCards.add(new QuestionCard("Qual será a " + i + "a pergunta?",
                        defaultUser.getName(),
                        true));
            }

        //
        QuestionAdapter adapter = new QuestionAdapter(questionCards);

        questionList = findViewById(R.id.questions_list);
        // LayoutManager para
        LinearLayoutManager llm = new LinearLayoutManager(this);
        //GridLayoutManager llm = new GridLayoutManager(this, 2);
        //StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        questionList.setLayoutManager(llm);

        questionList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        questionList.setAdapter(adapter);

    }


    @Override
    protected void onStart() {
        super.onStart();


        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        final List<QuestionCard> questionCards = new ArrayList<>();
                        File inputFile = new File(getFilesDir(), QUESTIONS_FILENAME);
                        try{
                            InputStream inputStream = new FileInputStream(inputFile);
                            InputStreamReader reader = new InputStreamReader(inputStream);
                            // para ler textos de maneira estruturada
                            BufferedReader bufferedReader = new BufferedReader(reader);

                            String line = bufferedReader.readLine();

                            while (line != null){
                                if (line.equals("#")){
                                    String body = bufferedReader.readLine();
                                    //TODO: deal with Date datatype
                                    String date = bufferedReader.readLine();
                                    String askerName = bufferedReader.readLine();
                                    boolean isAnonym = Boolean.parseBoolean(bufferedReader.readLine());
                                    QuestionCard questionCard = new QuestionCard(body, askerName, isAnonym);
                                    questionCards.add(questionCard);
                                }
                                line = bufferedReader.readLine();
                            }

                        } catch (final FileNotFoundException exception){
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                        } catch (final IOException exception){
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        questionList.post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        QuestionAdapter adapter = new QuestionAdapter(questionCards);
                                        questionList.setAdapter(adapter);
                                        // atualizar visualizção dos elementos da RecyclerView
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                        );

                    }
                }
        ).start();


    }

    public void askQuestion(View view){
        Intent intent = new Intent(this, QuestionComposer.class);
        startActivity(intent);
    }


}
