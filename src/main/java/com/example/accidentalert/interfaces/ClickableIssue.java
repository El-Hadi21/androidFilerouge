package com.example.accidentalert.interfaces;

import android.content.Context;
import com.example.accidentalert.adapter.IssueAdapter;
import com.example.accidentalert.model.Issue;
import java.util.List;

public interface ClickableIssue {
    void onRatingBarChange(int itemIndex, float value, IssueAdapter adapter, List<Issue> items);
    void onClickItem(List<Issue> items, int itemIndex);
    Context getContext();
}
