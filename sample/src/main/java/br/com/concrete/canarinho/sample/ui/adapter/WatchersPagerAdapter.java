package br.com.concrete.canarinho.sample.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import br.com.concrete.canarinho.sample.ui.model.Watchers;

public class WatchersPagerAdapter extends FragmentPagerAdapter {

    private Watchers[] models = Watchers.values();

    public WatchersPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
