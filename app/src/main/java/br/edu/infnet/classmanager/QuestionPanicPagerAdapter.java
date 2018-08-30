package br.edu.infnet.classmanager;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.edu.infnet.classmanager.fragments.PanicFragment;
import br.edu.infnet.classmanager.fragments.QuestionListFragment;
import br.edu.infnet.classmanager.utils.Constants;

public class QuestionPanicPagerAdapter extends FragmentPagerAdapter {

    private final int FRAGMENTS_COUNT = 3;

    public QuestionPanicPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PanicFragment();
            case 1:
                return QuestionListFragment.newInstance(Constants.ACTIVE_QUESTIONS_ENDPOINT);
                //TODO: mostrar perguntas respondidas
            case 2:
                return QuestionListFragment.newInstance(Constants.ANSWERED_QUESTIONS_ENDPOINT);
            default:
                //TODO: error prone
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Pânico";
            case 1:
                return "Ativas";
            case 2:
                return "Respondidas";
            default:
                return "Desespero!";
        }
    }



    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }
}
