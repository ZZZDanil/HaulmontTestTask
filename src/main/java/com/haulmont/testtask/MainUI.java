package com.haulmont.testtask;

import com.haulmont.testtask.ui.MainDoctorUI;
import com.haulmont.testtask.ui.MainPatientUI;
import com.haulmont.testtask.ui.MainRecipeUI;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    private VerticalLayout main;
    private Layout ui;

    @Override
    protected void init(VaadinRequest request) {

        main = new VerticalLayout();
        Layout bottom = HBottomLayout();
        ui = new MainPatientUI(getUI()).getPatientUI();
        main.setSizeFull();
        main.setMargin(true);
        main.addComponent(ui);
        main.addComponent(bottom);
        main.setExpandRatio(ui, 9);
        main.setExpandRatio(bottom, 1);
        main.setComponentAlignment(ui, Alignment.MIDDLE_CENTER);
        main.setComponentAlignment(bottom, Alignment.BOTTOM_CENTER);
        setContent(main);

    }

    private Layout HBottomLayout() {

        HorizontalLayout bottomLayout = new HorizontalLayout();
        Button patients = new Button("patients");
        Button doctors = new Button("doctors");
        Button recipes = new Button("recipes");

        bottomLayout.addComponent(patients);
        bottomLayout.addComponent(doctors);
        bottomLayout.addComponent(recipes);

        bottomLayout.setExpandRatio(patients, 1);
        bottomLayout.setExpandRatio(doctors, 1);
        bottomLayout.setExpandRatio(recipes, 1);

        patients.setSizeFull();
        doctors.setSizeFull();
        recipes.setSizeFull();
        bottomLayout.setSizeFull();

        main.setId("mainId");
        patients.addClickListener(e -> {
            Layout l = new MainPatientUI(this).getPatientUI();
            main.replaceComponent(ui, l);
            ui = l;
        });
        doctors.addClickListener(e -> {
            Layout l = new MainDoctorUI(this).getDoctorUI();
            main.replaceComponent(ui, l);
            ui = l;
        });
        recipes.addClickListener(e -> {
            Layout l = new MainRecipeUI(this).getRecipeUI();
            main.replaceComponent(ui, l);
            ui = l;
        });

        return bottomLayout;
    }

}
