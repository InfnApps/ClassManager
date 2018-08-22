package br.edu.infnet.classmanager;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import br.edu.infnet.classmanager.models.QuestionCard;
import br.edu.infnet.classmanager.utils.Constants;

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
        Intent intent;
        QuestionCard questionCard = dataSnapshot.getValue(QuestionCard.class);
        if (questionCard.isAnswered()){
            intent = new Intent(this,
                    ShowQuestionActivity.class);

        } else {
            intent = new Intent(this,
                    QuestionFeedbackActivity.class);
        }
           //Serializable ou Parcelable

        intent.putExtra(Constants.QUESTIONCARD_KEY, questionCard);
        intent.putExtra(Constants.QUESTION_FB_KEY, dataSnapshot.getKey());

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
