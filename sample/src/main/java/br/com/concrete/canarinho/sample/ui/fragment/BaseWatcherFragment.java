package br.com.concrete.canarinho.sample.ui.fragment;

import android.support.v4.app.Fragment;

import br.com.concrete.canarinho.sample.ui.model.Watchers;

public abstract class BaseWatcherFragment extends Fragment {

    protected Watchers model;

    public void setModel(Watchers model) {
        this.model = model;
    }
}
