package com.example.tpprojet;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Locale;

public class Screen3Fragment extends Fragment {

    private static final int ISSUE_LIST_FRAGMENT_INDEX = 1;

    private Notifiable notifiable;
    private EditText currentTargetEditText;

    private final ActivityResultLauncher<Intent> voiceLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    ArrayList<String> matches = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (matches != null && !matches.isEmpty() && currentTargetEditText != null) {
                        currentTargetEditText.setText(matches.get(0));
                    }
                }
            }
    );

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (requireActivity() instanceof Notifiable) {
            notifiable = (Notifiable) requireActivity();
        } else {
            throw new AssertionError("Classe "
                    + requireActivity().getClass().getName()
                    + " ne met pas en œuvre Notifiable.");
        }
    }

    private void startVoiceRecognition(EditText target) {
        currentTargetEditText = target;
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.screen3_voice_prompt));
        try {
            voiceLauncher.launch(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(requireContext(), getString(R.string.screen3_voice_unavailable), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen3, container, false);

        TextInputLayout titleLayout = view.findViewById(R.id.layout_issue_title);
        TextInputLayout descLayout = view.findViewById(R.id.layout_issue_desc);
        EditText titleInput = view.findViewById(R.id.input_issue_title);
        EditText descInput = view.findViewById(R.id.input_issue_desc);
        RadioGroup environmentGroup = view.findViewById(R.id.group_environment);
        Button submitButton = view.findViewById(R.id.btn_submit_issue);

        titleLayout.setEndIconOnClickListener(v -> startVoiceRecognition(titleInput));
        descLayout.setEndIconOnClickListener(v -> startVoiceRecognition(descInput));

        submitButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String description = descInput.getText().toString().trim();

            int selectedId = environmentGroup.getCheckedRadioButtonId();
            String environment = null;
            if (selectedId != View.NO_ID) {
                RadioButton selected = view.findViewById(selectedId);
                environment = selected.getText().toString();
            }

            if (title.isEmpty() || description.isEmpty() || environment == null) {
                Toast.makeText(requireContext(), getString(R.string.screen3_fields_required), Toast.LENGTH_SHORT).show();
                return;
            }

            Issue issue = IssueFactory.createIssue(title, description, environment);
            IssueList.getInstance().getItems().add(issue);

            if (notifiable != null) {
                notifiable.onClick(ISSUE_LIST_FRAGMENT_INDEX);
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        notifiable = null;
    }
}
