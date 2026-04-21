package com.example.tpprojet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public final class IssueFactory {

    private IssueFactory() {
    }

    public static Issue createIssue(String title, String description, String environment) {
        String id = UUID.randomUUID().toString();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        float gravity = 0f;
        int imageResId = R.drawable.menu3;
        return new Issue(id, title, date, description, gravity, imageResId, environment);
    }
}
