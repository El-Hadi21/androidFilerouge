package com.example.accidentalert.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.accidentalert.interfaces.Notifiable;
import com.example.accidentalert.model.Issue;
import com.example.accidentalert.R;

public class Screen1Fragment extends Fragment {

    private Notifiable notifiable;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (requireActivity() instanceof Notifiable)
            notifiable = (Notifiable) requireActivity();
        else
            throw new AssertionError(requireActivity().getClass().getName() + " must implement Notifiable");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen1, container, false);

        if (getArguments() != null && getArguments().containsKey("issue")) {
            Issue issue = getArguments().getParcelable("issue");
            bindDetailView(view, issue);
        } else {
            bindHomeView(view);
        }

        notifiable.onFragmentDisplayed(0);
        return view;
    }

    private void bindHomeView(View view) {
        view.findViewById(R.id.btn_sos).setOnClickListener(click -> {
            String location = ((com.example.accidentalert.MainActivity) requireActivity()).lastKnownLocation;
            if (location.isEmpty()) location = "Position inconnue";
            notifiable.onDataChange(0, null, 1, location);
        });

        view.findViewById(R.id.card_report).setOnClickListener(click ->
            notifiable.onClick(1));

        view.findViewById(R.id.card_map).setOnClickListener(click ->
            notifiable.onClick(2));
    }

    private void bindDetailView(View view, Issue issue) {
        ((TextView) view.findViewById(R.id.tv_detail_title)).setText(issue.getTitle());
        ((TextView) view.findViewById(R.id.tv_detail_location)).setText(issue.getLocation());
        ((TextView) view.findViewById(R.id.tv_detail_type)).setText(issue.getType());

        RatingBar rb = view.findViewById(R.id.rb_detail);
        rb.setRating(issue.getStatus());
        rb.setOnRatingBarChangeListener((bar, value, fromUser) ->
            notifiable.onDataChange(0, issue, 3, value));

        view.findViewById(R.id.btn_back_to_list).setOnClickListener(click ->
            notifiable.onClick(1));
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
