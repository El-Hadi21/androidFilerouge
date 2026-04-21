package com.example.accidentalert.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.accidentalert.interfaces.Notifiable;
import com.example.accidentalert.R;

public class Screen3Fragment extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_screen3, container, false);
        notifiable.onFragmentDisplayed(2);
        return view;
    }
}
