package br.com.concretesolutions.canarinho.sample.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.concretesolutions.canarinho.sample.ui.activity.MainActivity;
import br.com.concretesolutions.canarinho.sample.ui.fragment.BaseWatcherFragment;
import br.com.concretesolutions.canarinho.sample.ui.model.Watchers;

public class WatchersPagerAdapter extends FragmentPagerAdapter {

    private Watchers[] models = Watchers.values();

    public WatchersPagerAdapter(MainActivity mainActivity) {
        super(mainActivity.getSupportFragmentManager());
    }

    @Override
    public Fragment getItem(int position) {
        final BaseWatcherFragment canarinhoWatcherFragment = models[position].buildFragment();
        canarinhoWatcherFragment.setModel(models[position]);
        return canarinhoWatcherFragment;
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
