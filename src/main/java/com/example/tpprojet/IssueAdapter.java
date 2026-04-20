package com.example.tpprojet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IssueAdapter extends ArrayAdapter<Issue> {

    private final ClickableIssue clickableIssue;
    private final List<Issue> items;

    public IssueAdapter(@NonNull Context context, @NonNull List<Issue> items, @NonNull ClickableIssue clickableIssue) {
        super(context, 0, items);
        this.items = items;
        this.clickableIssue = clickableIssue;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.item_issue, parent, false);
        }

        ImageView imageView = row.findViewById(R.id.iv_issue_image);
        TextView titleView = row.findViewById(R.id.tv_issue_title);
        TextView dateView = row.findViewById(R.id.tv_issue_date);
        RatingBar ratingBar = row.findViewById(R.id.rb_issue_gravity);

        Issue issue = getItem(position);
        if (issue != null) {
            imageView.setImageResource(issue.getImageResId());
            titleView.setText(issue.getTitle());
            dateView.setText(issue.getDate());

            ratingBar.setOnRatingBarChangeListener(null);
            ratingBar.setRating(issue.getGravity());
            ratingBar.setOnRatingBarChangeListener((bar, value, fromUser) -> {
                if (fromUser) {
                    clickableIssue.onRatingBarChange(position, value, this, items);
                }
            });
        }

        return row;
    }
}
