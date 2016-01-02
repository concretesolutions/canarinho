package br.com.concretesolutions.canarinho.sample.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import br.com.concretesolutions.canarinho.sample.R;
import br.com.concretesolutions.canarinho.sample.ui.model.Watchers;
import br.com.concretesolutions.canarinho.watcher.BaseCanarinhoTextWatcher;

public abstract class WatcherFragment extends BaseWatcherFragment {

    private EditText watcherEdit;
    private TextView watcherTitle;
    private TextInputLayout watcherInputLayout;
    private TextWatcher currentWatcher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View layout = inflater.inflate(R.layout.fragment_canarinho_watcher, null);
        watcherTitle = (TextView) layout.findViewById(R.id.watcher_title);
        watcherInputLayout = (TextInputLayout) layout.findViewById(R.id.edit_input_layout);
        watcherEdit = (EditText) layout.findViewById(R.id.edit_text);
        bind(model);
        return layout;
    }

    public WatcherFragment bind(Watchers model) {

        watcherTitle.setText(model.getTitle());
        watcherEdit.setHint(model.getHint());

        if (currentWatcher != null) {
            watcherEdit.removeTextChangedListener(currentWatcher);
        }

        currentWatcher = getWatcher(getActivity());
        watcherEdit.addTextChangedListener(currentWatcher);

        if (currentWatcher instanceof BaseCanarinhoTextWatcher) {
            final BaseCanarinhoTextWatcher canarinhoWatcher = (BaseCanarinhoTextWatcher) currentWatcher;
            final Watchers.SampleEventoDeValidacao eventoDeValidacao = (Watchers.SampleEventoDeValidacao) canarinhoWatcher.getEventoDeValidacao();
            eventoDeValidacao.setTextInputLayout(watcherInputLayout);
        }

        return this;
    }

    protected abstract TextWatcher getWatcher(FragmentActivity activity);

}
