package com.example.accidentalert.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import androidx.fragment.app.Fragment;
import com.example.accidentalert.interfaces.Notifiable;
import com.example.accidentalert.R;

public class Screen5Fragment extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_screen5, container, false);

        Switch swNotif = view.findViewById(R.id.sw_notif);
        swNotif.setOnCheckedChangeListener((btn, checked) ->
            notifiable.onDataChange(4, null, checked ? 1 : 0, "notifications"));

        notifiable.onFragmentDisplayed(4);
        return view;
    }
}
