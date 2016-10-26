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

public class InputHighScoreFragment extends DialogFragment {
    public interface InputHighScoreListener {
        void onInputHighScore(String name);
    }

    private InputHighScoreListener listener;

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

    public InputHighScoreFragment() {
    }

    public static InputHighScoreFragment newInstance(int score, int rank) {
        InputHighScoreFragment f = new InputHighScoreFragment();

        Bundle args = new Bundle();
        args.putInt("score", score);
        args.putInt("rank", rank);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        score = getArguments().getInt("score");
        rank = getArguments().getInt("rank");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.dialog_new_high_score, null);
        ButterKnife.bind(this, view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.memory_game_dialog_title_new_high_score));
        builder.setView(view);

        dialogHighScoreText.setText(getString(R.string.memory_game_dialog_text_new_high_score, score, rank));
        dialogHighScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateHighScoreDialogInput();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (InputHighScoreListener) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    private void validateHighScoreDialogInput() {
        if (validatePlayerName(dialogHighScoreInput.getText().toString())) {
            dialogHighScoreInputGroup.setError(null);
            dialogHighScoreInputGroup.setErrorEnabled(false);
            listener.onInputHighScore(dialogHighScoreInput.getText().toString());

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
