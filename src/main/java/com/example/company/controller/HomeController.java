package com.example.company.controller;

import static java.util.Arrays.asList;

import com.example.company.view.ShowOptionsView;

import java.util.List;


public class HomeController extends AbstractController {
    private ShowOptionsView view;
    private List<String> model;
//    public HomeController(Event event) {
//        super(event);
//        view = new ShowOptionsView();
//    }

    public void run() {
        model = asList("1. Create", "2. Resume", "3. Exit");
        view.promptOptions(model);
    }


}
