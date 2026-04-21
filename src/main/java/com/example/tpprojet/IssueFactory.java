package com.example.tpprojet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public final class IssueFactory {

    private static final float DEFAULT_GRAVITY = 0f;
    private static final int DEFAULT_IMAGE_RES_ID = R.drawable.menu3;

    private IssueFactory() {
    }

    public static Issue createIssue(String title, String description, String environment) {
        String id = UUID.randomUUID().toString();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return new Issue(id, title, date, description, DEFAULT_GRAVITY, DEFAULT_IMAGE_RES_ID, environment);
    }
}
