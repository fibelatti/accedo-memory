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

import com.fibelatti.accedomemory.Constants;
import com.fibelatti.accedomemory.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HighScoreInputFragment extends DialogFragment {
    public final static String TAG = HighScoreInputFragment.class.getSimpleName();

    private IHighScoreInputFragmentListener listener;

    private int score;
    private int rank;

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
        args.putInt(Constants.INTENT_EXTRA_SCORE, score);
        args.putInt(Constants.INTENT_EXTRA_RANK, rank);
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

        score = getArguments().getInt(Constants.INTENT_EXTRA_SCORE);
        rank = getArguments().getInt(Constants.INTENT_EXTRA_RANK);

        if (savedInstanceState != null) {
            dialogHighScoreInput.setText(savedInstanceState.getString(Constants.INTENT_EXTRA_NAME));
            score = Integer.parseInt(savedInstanceState.getString(Constants.INTENT_EXTRA_SCORE));
            rank = Integer.parseInt(savedInstanceState.getString(Constants.INTENT_EXTRA_RANK));
        }

        dialogHighScoreText.setText(getString(R.string.memory_game_dialog_text_new_high_score,
                score, rank));
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.INTENT_EXTRA_NAME, dialogHighScoreInput.getText().toString());
        outState.putString(Constants.INTENT_EXTRA_SCORE, Integer.toString(score));
        outState.putString(Constants.INTENT_EXTRA_RANK, Integer.toString(rank));
    }

    private void validateHighScoreDialogInput() {
        if (validatePlayerName(dialogHighScoreInput.getText().toString())) {
            dialogHighScoreInputGroup.setError(null);
            dialogHighScoreInputGroup.setErrorEnabled(false);
            listener.onHighScore(dialogHighScoreInput.getText().toString(), score);

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
