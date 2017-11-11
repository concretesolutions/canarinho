package br.com.concrete.canarinho.sample.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import br.com.concrete.canarinho.sample.R;
import br.com.concrete.canarinho.sample.ui.model.Watchers;

public class WatcherFragment extends BaseWatcherFragment {

    private EditText watcherEdit;
    private TextView watcherTitle;
    private TextInputLayout watcherInputLayout;
    private TextWatcher currentWatcher;

    public static WatcherFragment newInstance(Watchers model) {
        final WatcherFragment watcherFragment = new WatcherFragment();
        watcherFragment.setArguments(new Bundle());
        watcherFragment.setModel(model);
        return watcherFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View layout = inflater.inflate(R.layout.fragment_canarinho_watcher, null);
        watcherTitle = layout.findViewById(R.id.watcher_title);
        watcherInputLayout = layout.findViewById(R.id.edit_input_layout);
        watcherEdit = layout.findViewById(R.id.edit_text);
        bind(model);
        return layout;
    }

    public WatcherFragment bind(Watchers model) {

        if (currentWatcher != null) {
            watcherEdit.removeTextChangedListener(currentWatcher);
        }

        currentWatcher = model.setupWatcher(watcherInputLayout);
        watcherTitle.setText(model.getTitle());
        watcherEdit.setHint(model.getHint());
        watcherEdit.addTextChangedListener(currentWatcher);
        return this;
    }
}
