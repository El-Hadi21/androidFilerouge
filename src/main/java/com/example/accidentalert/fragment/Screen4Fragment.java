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

public class Screen4Fragment extends Fragment implements ClickableIssue {
    private Notifiable notifiable;
    private IssueAdapter adapter;

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
        View view = inflater.inflate(R.layout.fragment_screen4, container, false);

        List<Issue> issues = IssueList.getInstance().getIssueList();
        adapter = new IssueAdapter(this, issues);

        ListView listView = view.findViewById(R.id.listview_feed);
        listView.setAdapter(adapter);

        notifiable.onFragmentDisplayed(3);
        return view;
    }

    @Override
    public void onRatingBarChange(int itemIndex, float value, IssueAdapter a, List<Issue> items) {
        items.get(itemIndex).setStatus(value);
        a.notifyDataSetChanged();
    }

    @Override
    public void onClickItem(List<Issue> items, int itemIndex) {
        notifiable.onDataChange(3, items.get(itemIndex), 0, itemIndex);
    }

    @Override
    public Context getContext() { 
        return requireContext(); 
    }
}
