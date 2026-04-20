package com.example.tpprojet;

import android.content.Context;

import java.util.List;

public interface ClickableIssue {
    void onRatingBarChange(int itemIndex, float value, IssueAdapter adapter, List<Issue> items);
    void onClickItem(List<Issue> items, int itemIndex);
    Context getContext();
}
