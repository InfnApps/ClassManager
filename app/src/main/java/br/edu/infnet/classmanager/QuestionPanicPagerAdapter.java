package br.edu.infnet.classmanager;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.edu.infnet.classmanager.utils.Constants;

public class QuestionPanicPagerAdapter extends FragmentPagerAdapter {

    private int FRAGMENTS_COUNT = 3;

    public QuestionPanicPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return QuestionListFragment.newInstance(Constants.QUESTIONS_ENDPOINT);
                //TODO: mostrar perguntas respondidas
            case 1:
                return new QuestionListFragment();
            case 2:
                return new PanicFragment();
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
                return "Ativas";
            case 1:
                return "Respondidas";
            case 2:
                return "Pânico";
            default:
                return "Desespero!";
        }
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }
}
