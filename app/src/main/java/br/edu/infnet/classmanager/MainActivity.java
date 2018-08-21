package br.edu.infnet.classmanager;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

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

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    // Method to simulated a call for the authenticated user
    public static String getAuthenticatedUserName(){
        return "Josias";
    }
   ViewPager viewPager;

    private final int REQUEST_ANSWER = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        QuestionPanicPagerAdapter adapter = new QuestionPanicPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

    }

    public void askQuestion(View view){
        Intent intent = new Intent(this, QuestionComposer.class);
        startActivity(intent);
    }

    @Override
    public void onCardClick(DataSnapshot dataSnapshot) {
        //TODO: deal with already answered questions
        Intent intent = new Intent(this,
                QuestionFeedbackActivity.class);
        //Serializable ou Parcelable
        QuestionCard questionCard = dataSnapshot.getValue(QuestionCard.class);
        intent.putExtra("QuestionCard", questionCard);
        intent.putExtra("FirebaseKey", dataSnapshot.getKey());

        startActivity(intent);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode){
//            case REQUEST_ANSWER:
//                if(resultCode == RESULT_OK){
//                    int position = 0;
//                    //TODO: get position
//                    setQuestionAsAnswered(position);
//                } else{
//                    Toast.makeText(this, "CANCELOU",
//                            Toast.LENGTH_LONG).show();
//                }
//                break;
//        }
//
//    }

    private void setQuestionAsAnswered(int position){
        //TODO:
        Toast.makeText(this, "REMOVER PERGUNTA",
                Toast.LENGTH_LONG).show();
    }
}
