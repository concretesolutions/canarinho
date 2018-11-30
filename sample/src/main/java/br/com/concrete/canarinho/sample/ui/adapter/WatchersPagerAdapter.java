package br.com.concrete.canarinho.sample.ui.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import br.com.concrete.canarinho.sample.ui.activity.MainActivity;
import br.com.concrete.canarinho.sample.ui.model.Watchers;

public class WatchersPagerAdapter extends FragmentPagerAdapter {

    private Watchers[] models = Watchers.values();
    private Context context;

    public WatchersPagerAdapter(MainActivity mainActivity) {
        super(mainActivity.getSupportFragmentManager());
        this.context = mainActivity;
    }

    @Override
    public Fragment getItem(int position) {
        return models[position].buildFragment();
    }

    @Override
    public int getCount() {
        return models.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return models[position].getTitle();
    }
}
