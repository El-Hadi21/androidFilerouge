package com.example.tpprojet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

public class Screen2Fragment extends Fragment implements ClickableIssue {

    private Notifiable notifiable;
    private List<Issue> items;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen2, container, false);

        ListView listView = view.findViewById(R.id.list_issues);
        items = IssueList.getInstance().getItems();
        IssueAdapter adapter = new IssueAdapter(requireContext(), items, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, itemView, position, id) -> onClickItem(items, position));

        if (notifiable != null) {
            notifiable.onFragmentDisplayed(2);
        }

        return view;
    }

    @Override
    public void onRatingBarChange(int itemIndex, float value, IssueAdapter adapter, List<Issue> items) {
        if (itemIndex >= 0 && itemIndex < items.size()) {
            items.get(itemIndex).setGravity(value);
        }
    }

    @Override
    public void onClickItem(List<Issue> items, int itemIndex) {
        if (notifiable != null && itemIndex >= 0 && itemIndex < items.size()) {
            notifiable.onDataChange(2, items.get(itemIndex), 1, itemIndex);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        notifiable = null;
    }
}
