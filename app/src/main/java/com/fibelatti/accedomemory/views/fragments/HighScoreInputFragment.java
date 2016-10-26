package com.fibelatti.accedomemory.views.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fibelatti.accedomemory.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HighScoreInputFragment extends DialogFragment {
    public final static String TAG = HighScoreInputFragment.class.getSimpleName();

    private IHighScoreInputFragmentListener listener;

    @BindView(R.id.dialog_input_group)
    TextInputLayout dialogHighScoreInputGroup;
    @BindView(R.id.dialog_input)
    EditText dialogHighScoreInput;
    @BindView(R.id.dialog_text)
    TextView dialogHighScoreText;
    @BindView(R.id.dialog_button_add)
    ImageButton dialogHighScoreButton;

    public HighScoreInputFragment() {
    }

    public static HighScoreInputFragment newInstance(int score, int rank) {
        HighScoreInputFragment f = new HighScoreInputFragment();

        Bundle args = new Bundle();
        args.putInt("score", score);
        args.putInt("rank", rank);
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.dialog_new_high_score, null);
        ButterKnife.bind(this, view);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.memory_game_dialog_title_new_high_score))
                .setView(view)
                .create();

        dialogHighScoreText.setText(getString(R.string.memory_game_dialog_text_new_high_score, getArguments().getInt("score"), getArguments().getInt("rank")));
        dialogHighScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateHighScoreDialogInput();
            }
        });

        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (IHighScoreInputFragmentListener) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    private void validateHighScoreDialogInput() {
        if (validatePlayerName(dialogHighScoreInput.getText().toString())) {
            dialogHighScoreInputGroup.setError(null);
            dialogHighScoreInputGroup.setErrorEnabled(false);
            listener.onHighScore(dialogHighScoreInput.getText().toString());

            getDialog().dismiss();
        } else {
            dialogHighScoreInputGroup.setError(getString(R.string.dialog_new_high_score_input_validation));
            requestFocus(dialogHighScoreText);
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public boolean validatePlayerName(String name) {
        String regex = "^[\\p{L} .'-]+$";

        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(name);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }
}
