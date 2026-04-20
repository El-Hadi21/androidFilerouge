package com.example.tpprojet;

import java.util.ArrayList;
import java.util.List;

public class IssueList {

    private static IssueList instance;
    private final List<Issue> items;

    private IssueList() {
        items = new ArrayList<>();
        items.add(new Issue(
                "1",
                "Nid-de-poule dangereux",
                "2026-04-18",
                "Nid-de-poule profond au milieu de la voie principale.",
                3.0f,
                R.drawable.menu1
        ));
        items.add(new Issue(
                "2",
                "Feu tricolore en panne",
                "2026-04-19",
                "Le feu reste éteint sur un carrefour fréquenté.",
                4.0f,
                R.drawable.menu2
        ));
        items.add(new Issue(
                "3",
                "Accident mineur signalé",
                "2026-04-20",
                "Collision légère provoquant un ralentissement.",
                2.0f,
                R.drawable.menu3
        ));
    }

    public static synchronized IssueList getInstance() {
        if (instance == null) {
            instance = new IssueList();
        }
        return instance;
    }

    public List<Issue> getItems() {
        return items;
    }
}
