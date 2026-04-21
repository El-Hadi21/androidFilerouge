package com.example.accidentalert.model;

import java.util.ArrayList;
import java.util.List;

public class IssueList {
    private static IssueList instance;
    private static List<Issue> issueList = new ArrayList<>();

    static {
        issueList.add(new Issue("Carambolage — Bd de l'Arénas",
            "3 voitures, voie gauche bloquée", "Bd de l'Arénas, Nice",
            "CARAMBOLAGE", 0f, 3, System.currentTimeMillis()));
        issueList.add(new Issue("Accrochage — Av. du Verdun",
            "2 véhicules, voie droite dégagée", "Av. du Verdun, Nice",
            "ACCROCHAGE", 2f, 2, System.currentTimeMillis() - 1080000));
        issueList.add(new Issue("Véhicule en panne — A8",
            "Bande d'arrêt d'urgence", "A8, Nice",
            "VEHICULE_SEUL", 1f, 1, System.currentTimeMillis() - 2040000));
    }

    private IssueList() {}

    public static synchronized IssueList getInstance() {
        if (instance == null) instance = new IssueList();
        return instance;
    }

    public List<Issue> getIssueList() {
        return issueList;
    }

    public void addIssue(Issue issue) {
        issueList.add(issue);
    }
}
