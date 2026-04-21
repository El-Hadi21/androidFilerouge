package com.example.accidentalert.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.example.accidentalert.adapter.IssueAdapter;
import com.example.accidentalert.interfaces.ClickableIssue;
import com.example.accidentalert.interfaces.Notifiable;
import com.example.accidentalert.model.Issue;
import com.example.accidentalert.model.IssueList;
import com.example.accidentalert.R;
import java.util.List;

public class Screen2Fragment extends Fragment implements ClickableIssue {

    private Notifiable notifiable;
    private IssueAdapter adapter;
    private List<Issue> issues;

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
        View view = inflater.inflate(R.layout.fragment_screen2, container, false);

        issues = IssueList.getInstance().getIssueList();
        adapter = new IssueAdapter(this, issues);

        ListView listView = view.findViewById(R.id.listview_issues);
        listView.setAdapter(adapter);

        notifiable.onFragmentDisplayed(1);
        return view;
    }

    @Override
    public void onRatingBarChange(int itemIndex, float value, IssueAdapter adapter, List<Issue> items) {
        items.get(itemIndex).setStatus(value);
        adapter.notifyDataSetChanged();
        notifiable.onDataChange(1, items.get(itemIndex), 3, value);
    }

    @Override
    public void onClickItem(List<Issue> items, int itemIndex) {
        notifiable.onDataChange(1, items.get(itemIndex), 0, itemIndex);
    }

    @Override
    public Context getContext() {
        return requireContext();
    }
}
