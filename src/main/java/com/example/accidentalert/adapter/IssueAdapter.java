package com.example.accidentalert.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.accidentalert.interfaces.ClickableIssue;
import com.example.accidentalert.model.Issue;
import java.util.List;

public class IssueAdapter extends ArrayAdapter<Issue> {

    private final List<Issue> items;
    private final LayoutInflater mInflater;
    private final ClickableIssue callBack;

    public IssueAdapter(@NonNull ClickableIssue callBack, List<Issue> items) {
        super(callBack.getContext(), 0);
        this.items = items;
        this.callBack = callBack;
        mInflater = LayoutInflater.from(callBack.getContext());
    }

    public int getCount() { 
        return items.size(); 
    }
    
    public Issue getItem(int position) { 
        return items.get(position); 
    }
    
    public long getItemId(int position) { 
        return position; 
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = mInflater.inflate(R.layout.item_issue, parent, false);

        TextView tvTitle    = row.findViewById(R.id.tv_issue_title);
        TextView tvDesc     = row.findViewById(R.id.tv_issue_desc);
        TextView tvLocation = row.findViewById(R.id.tv_issue_location);
        TextView tvTime     = row.findViewById(R.id.tv_issue_time);
        TextView tvType     = row.findViewById(R.id.tv_issue_type);
        ImageView ivPriority= row.findViewById(R.id.iv_priority);
        RatingBar rb        = row.findViewById(R.id.rb_issue_status);

        Issue issue = items.get(position);
        tvTitle.setText(issue.getTitle());
        tvDesc.setText(issue.getDescription());
        tvLocation.setText(issue.getLocation());
        tvType.setText(issue.getType());

        long diff = System.currentTimeMillis() - issue.getTimestamp();
        tvTime.setText(diff < 60000 ? "à l'instant" :
                       diff < 3600000 ? (diff/60000) + " min" :
                       (diff/3600000) + "h");

        int icon = issue.getPriority() == 3 ? R.drawable.ic_priority_high :
                   issue.getPriority() == 2 ? R.drawable.ic_priority_medium :
                                              R.drawable.ic_priority_low;
        ivPriority.setImageResource(icon);

        rb.setRating(issue.getStatus());
        rb.setOnRatingBarChangeListener((bar, value, fromUser) ->
            callBack.onRatingBarChange(position, value, this, items));

        row.setOnClickListener(click ->
            callBack.onClickItem(items, position));

        return row;
    }
}
