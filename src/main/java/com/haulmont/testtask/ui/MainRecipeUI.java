package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.DaoRecipe;
import com.haulmont.testtask.db.RecipeHSQL;
import com.haulmont.testtask.pojo.Priority;
import com.haulmont.testtask.pojo.Recipe;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.*;

import java.util.List;
import java.util.Objects;

public class MainRecipeUI {

    private final VerticalLayout recipeUI;

    private DaoRecipe db;

    private List<Recipe> recipes;
    private UI context;
    private long selected = 2;
    private Grid<Recipe> table;
    private String createAbout;
    private long createPatient;
    private long createDoctor;
    private String createCreateDate;
    private String createDuration;
    private String createPriority;
    private String editAbout;
    private long editPatient;
    private long editDoctor;
    private String editCreateDate;
    private String editDuration;
    private String editPriority;
    private String textFilterPatient = "";
    private String textFilterPriority = "";
    private String textFilterAbout = "";
    private SerializablePredicate<Recipe> filter;
    private ListDataProvider<Recipe> ldp;

    public MainRecipeUI(UI context) {


        db = new RecipeHSQL();
        recipes = db.getAllRecipe();

        this.context = context;
        recipeUI = new VerticalLayout();
        table = new Grid<>();
        table.addColumn(Recipe::getAbout).setCaption("about")
                .setResizable(true).setExpandRatio(1)
                .setMinimumWidth(0).setWidth(400)
                .setStyleGenerator(recipe -> "v-align-left");
        table.addColumn(Recipe::getDoctor).setCaption("Doctor ID")
                .setResizable(true).setExpandRatio(1)
                .setMinimumWidth(0);
        table.addColumn(Recipe::getPatient).setCaption("Patient ID")
                .setResizable(true).setExpandRatio(1)
                .setMinimumWidth(0);
        table.addColumn(Recipe::getCreateDate).setCaption("Create Date")
                .setResizable(true).setExpandRatio(1)
                .setMinimumWidth(0);
        table.addColumn(Recipe::getDuration).setCaption("Duration")
                .setResizable(true).setExpandRatio(1)
                .setMinimumWidth(0);
        table.addColumn(Recipe::getPriority).setCaption("Priority")
                .setResizable(true).setExpandRatio(1)
                .setMinimumWidth(0);
        table.setItems(recipes);
        creteFilter();
        Label pLabel = new Label("Patient: ");
        Label prLabel = new Label("Priority: ");
        Label aLabel = new Label("About: ");
        TextField textFieldPatient = new TextField();
        TextField textFieldPriority = new TextField();
        TextField textFieldAbout = new TextField();
        Button useFilter = new Button("Use");
        HorizontalLayout toolBar = new HorizontalLayout();

        Button add = recipeAddButton();
        Button edit = recipeEditButton();
        Button del = recipeDeleteButton();


        textFieldAbout.setWidth("100%");
        textFieldPatient.setWidth("100%");
        textFieldPriority.setWidth("100%");
        useFilter.setWidth("100%");
        add.setWidth("100%");
        edit.setWidth("100%");
        del.setWidth("100%");

        textFieldPatient.addValueChangeListener(valueChangeEvent ->
                textFilterPatient = valueChangeEvent.getValue());
        textFieldPriority.addValueChangeListener(valueChangeEvent ->
                textFilterPriority = valueChangeEvent.getValue());
        textFieldAbout.addValueChangeListener(valueChangeEvent ->
                textFilterAbout = valueChangeEvent.getValue());
        useFilter.addClickListener(clickEvent ->
                ldp.setFilter(filter));

        toolBar.addComponent(aLabel);
        toolBar.addComponent(textFieldAbout);
        toolBar.addComponent(pLabel);
        toolBar.addComponent(textFieldPatient);
        toolBar.addComponent(prLabel);
        toolBar.addComponent(textFieldPriority);
        toolBar.addComponent(useFilter);

        toolBar.addComponent(add);
        toolBar.addComponent(edit);
        toolBar.addComponent(del);


        toolBar.setExpandRatio(textFieldPatient, 1);
        toolBar.setExpandRatio(textFieldPriority, 1);
        toolBar.setExpandRatio(textFieldAbout, 3);
//        toolBar.setExpandRatio(pLabel, 1);
//        toolBar.setExpandRatio(prLabel, 1);
//        toolBar.setExpandRatio(aLabel, 1);
        toolBar.setExpandRatio(useFilter, 1);
        toolBar.setExpandRatio(add, 1);
        toolBar.setExpandRatio(edit, 1);
        toolBar.setExpandRatio(del, 1);


        toolBar.setComponentAlignment(pLabel, Alignment.MIDDLE_LEFT);
        toolBar.setComponentAlignment(prLabel, Alignment.MIDDLE_LEFT);
        toolBar.setComponentAlignment(aLabel, Alignment.MIDDLE_LEFT);

        toolBar.setComponentAlignment(textFieldPatient, Alignment.TOP_LEFT);
        toolBar.setComponentAlignment(textFieldPriority, Alignment.TOP_LEFT);
        toolBar.setComponentAlignment(textFieldAbout, Alignment.TOP_LEFT);

        toolBar.setComponentAlignment(useFilter, Alignment.TOP_LEFT);
        toolBar.setComponentAlignment(add, Alignment.TOP_RIGHT);
        toolBar.setComponentAlignment(edit, Alignment.TOP_RIGHT);
        toolBar.setComponentAlignment(del, Alignment.TOP_RIGHT);

        table.addSelectionListener(e -> {
            Recipe r = e.getFirstSelectedItem().get();
            selected = r.getId();
        });

        Label l = new Label("Recipes");
        l.setWidth("100%");
        recipeUI.addComponent(l);
        recipeUI.addComponent(toolBar);
        recipeUI.addComponent(table);
        recipeUI.setComponentAlignment(l, Alignment.TOP_CENTER);
        recipeUI.setComponentAlignment(table, Alignment.BOTTOM_CENTER);
        recipeUI.setExpandRatio(toolBar, 1);
        recipeUI.setExpandRatio(table, 8);
        table.setWidth("100%");
        table.setHeight("100%");
        toolBar.setSpacing(false);
        toolBar.setWidth("100%");
        recipeUI.setSizeFull();
        recipeUI.setSpacing(false);
        recipeUI.setMargin(false);
    }

    public Layout getRecipeUI() {
        return recipeUI;
    }


    private Button recipeAddButton() {
        Button b = new Button("Add");
        b.setId("test id, it's just work? ");
        b.addClickListener(clickEvent -> context.addWindow(recipeCreateWindow()));
        b.setSizeUndefined();
        return b;
    }

    private Button recipeEditButton() {
        Button b = new Button("Edit");
        b.addClickListener(clickEvent -> context.addWindow(Objects.requireNonNull(recipeEditWindow())));
        b.setSizeUndefined();
        return b;
    }

    private Button recipeDeleteButton() {
        Button b = new Button("Delete");
        b.addClickListener(clickEvent -> context.addWindow(recipeDeleteWindow()));
        b.setSizeUndefined();
        return b;
    }

    private Window recipeCreateWindow() {
        Window subWindow = new Window("Create");
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);
        HorizontalLayout param = new HorizontalLayout();
        TextField r1 = new TextField("about");
        TextField r2 = new TextField("patient");
        TextField r3 = new TextField("doctor");
        TextField r4 = new TextField("createDate");
        TextField r5 = new TextField("duration");
        TextField r6 = new TextField("priority");
        param.addComponent(r1);
        param.addComponent(r2);
        param.addComponent(r3);
        param.addComponent(r4);
        param.addComponent(r5);
        param.addComponent(r6);
        Binder<String> b1 = new Binder<>();
        Binder<String> b2 = new Binder<>();
        Binder<String> b3 = new Binder<>();
        Binder<String> b4 = new Binder<>();
        Binder<String> b5 = new Binder<>();
        Binder<String> b6 = new Binder<>();
        b1.forField(r1).withValidator(s -> {
            if (s.length() > 0) {
                return true;
            }
            return false;
        }, "For example: text").bind(string -> string, null);
        b2.forField(r2).withValidator(s -> {
            try {// почему-то не работает...
                Long.parseLong(s);
                    return true;
            } catch (NumberFormatException e) {
            }
            return false;
        }, "For example: 0 or 1").bind(string -> string, null);
        b3.forField(r3).withValidator(s -> {
            try {
                Long.parseLong(s);
                return true;
            } catch (NumberFormatException e) {
            }
            return false;
        }, "For example: 0 or 1").bind(string -> string, null);
        b4.forField(r4).withValidator(s -> {
            if (s.matches("^\\\\d{2}.\\\\d{2}.\\\\d{2}$")) {
                return true;
            }
            return false;
        }, "For example: 31.12.20").bind(string -> string, null);
        b5.forField(r5).withValidator(s -> {
            if (s.length() > 0) {
                return true;
            }
            return false;
        }, "For example: 14").bind(string -> string, null);
        b6.forField(r6).withValidator(s -> {
            if (s.equals(Priority.CITO) || s.equals(Priority.NORMAL)|| s.equals(Priority.STATIM)) {
                return true;
            }
            return false;
        }, "For example: Cito or Normal or Statim").bind(string -> string, null);
        b1.validate();
        b2.validate();
        b3.validate();
        b4.validate();
        b5.validate();
        b6.validate();


        param.getComponent(0).setSizeFull();
        r1.addValueChangeListener(valueChangeEvent -> createAbout = valueChangeEvent.getValue());
        r1.setSizeFull();
        r2.addValueChangeListener(valueChangeEvent -> createPatient = Long.parseLong(valueChangeEvent.getValue()));
        r2.setSizeFull();
        r3.addValueChangeListener(valueChangeEvent -> createDoctor = Long.parseLong(valueChangeEvent.getValue()));
        r3.setSizeFull();
        r4.addValueChangeListener(valueChangeEvent -> createCreateDate = valueChangeEvent.getValue());
        r4.setSizeFull();
        r5.addValueChangeListener(valueChangeEvent -> createDuration = valueChangeEvent.getValue());
        r5.setSizeFull();
        r6.addValueChangeListener(valueChangeEvent -> createPriority = valueChangeEvent.getValue());

        param.setExpandRatio(r1, 1);
        param.setExpandRatio(r2, 1);
        param.setExpandRatio(r3, 1);
        param.setExpandRatio(r4, 1);
        param.setExpandRatio(r5, 1);
        param.setExpandRatio(r6, 1);
        param.setSizeFull();

        subContent.addComponent(param);
        subContent.addComponent(new Button("Create",
                clickEvent -> {
                    Recipe addD = new Recipe(100L, createAbout, createPatient,
                            createDoctor, createCreateDate, createDuration, createPriority);
                    if (db.setRecipe(addD)) {
                        recipes.add(addD);
                        table.clearSortOrder();
                    } else {
                        context.addWindow(recipeErrorWindow());
                    }
                    subWindow.close();
                }));
        subWindow.center();
        subWindow.setModal(true);
        return subWindow;
    }

    private Window recipeEditWindow() {
        Window subWindow = new Window("Edit");
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);

        Recipe r = recipes.stream().filter(f -> selected == f.getId()).findFirst().get();
        HorizontalLayout first = new HorizontalLayout();
        first.addComponent(new Label("First:"));
        first.addComponent(new Label(r.getAbout()));
        first.addComponent(new Label("" + r.getPatient()));
        first.addComponent(new Label("" + r.getDoctor()));
        first.addComponent(new Label(r.getCreateDate()));
        first.addComponent(new Label(r.getDuration()));
        first.addComponent(new Label(r.getPriority()));

        first.getComponent(0).setSizeFull();
        first.getComponent(1).setSizeFull();
        first.getComponent(2).setSizeFull();
        first.getComponent(3).setSizeFull();
        first.getComponent(4).setSizeFull();
        first.getComponent(5).setSizeFull();
        first.getComponent(6).setSizeFull();
        first.setExpandRatio(first.getComponent(0), 1);
        first.setExpandRatio(first.getComponent(1), 1);
        first.setExpandRatio(first.getComponent(2), 1);
        first.setExpandRatio(first.getComponent(3), 1);
        first.setExpandRatio(first.getComponent(4), 1);
        first.setExpandRatio(first.getComponent(5), 1);
        first.setExpandRatio(first.getComponent(6), 1);

        first.setSizeFull();

        HorizontalLayout edit = new HorizontalLayout();
        TextField r1 = new TextField("about");
        TextField r2 = new TextField("patient");
        TextField r3 = new TextField("doctor");
        TextField r4 = new TextField("createDate");
        TextField r5 = new TextField("duration");
        TextField r6 = new TextField("priority");
        edit.addComponent(new Label("Edit:"));
        edit.addComponent(r1);
        edit.addComponent(r2);
        edit.addComponent(r3);
        edit.addComponent(r4);
        edit.addComponent(r5);
        edit.addComponent(r6);
        Binder<String> b1 = new Binder<>();
        Binder<String> b2 = new Binder<>();
        Binder<String> b3 = new Binder<>();
        Binder<String> b4 = new Binder<>();
        Binder<String> b5 = new Binder<>();
        Binder<String> b6 = new Binder<>();
        b1.forField(r1).withValidator(s -> {
            if (s.length() > 0) {
                return true;
            }
            return false;
        }, "For example: text").bind(string -> string, null);
        b2.forField(r2).withValidator(s -> {
            try {// почему-то не работает...
                Long.parseLong(s);
                return true;
            } catch (NumberFormatException e) {
            }
            return false;
        }, "For example: 0 or 1").bind(string -> string, null);
        b3.forField(r3).withValidator(s -> {
            try {
                Long.parseLong(s);
                return true;
            } catch (NumberFormatException e) {
            }
            return false;
        }, "For example: 0 or 1").bind(string -> string, null);
        b4.forField(r4).withValidator(s -> {
            if (s.matches("^\\\\d{2}.\\\\d{2}.\\\\d{2}$")) {
                return true;
            }
            return false;
        }, "For example: 31.12.20").bind(string -> string, null);
        b5.forField(r5).withValidator(s -> {
            if (s.length() > 0) {
                return true;
            }
            return false;
        }, "For example: 12").bind(string -> string, null);
        b6.forField(r6).withValidator(s -> {
            if (s.equals(Priority.CITO) || s.equals(Priority.NORMAL)|| s.equals(Priority.STATIM)) {
                return true;
            }
            return false;
        }, "For example: Cito or Normal or Statim").bind(string -> string, null);
        b1.validate();
        b2.validate();
        b3.validate();
        b4.validate();
        b5.validate();
        b6.validate();
        r1.setSizeFull();
        r1.addValueChangeListener(valueChangeEvent -> editAbout = valueChangeEvent.getValue());
        r2.setSizeFull();
        r2.addValueChangeListener(valueChangeEvent -> editPatient = Long.parseLong(valueChangeEvent.getValue()));
        r3.setSizeFull();
        r3.addValueChangeListener(valueChangeEvent -> editDoctor = Long.parseLong(valueChangeEvent.getValue()));
        r4.setSizeFull();
        r4.addValueChangeListener(valueChangeEvent -> editCreateDate = valueChangeEvent.getValue());
        r5.setSizeFull();
        r5.addValueChangeListener(valueChangeEvent -> editDuration = valueChangeEvent.getValue());
        r6.setSizeFull();
        r6.addValueChangeListener(valueChangeEvent -> editPriority = valueChangeEvent.getValue());
        edit.setExpandRatio(r1, 1);
        edit.setExpandRatio(r2, 1);
        edit.setExpandRatio(r3, 1);
        edit.setExpandRatio(r4, 1);
        edit.setExpandRatio(r5, 1);
        edit.setExpandRatio(r6, 1);
        edit.setSizeFull();

        subContent.addComponent(first);
        subContent.addComponent(edit);
        subContent.addComponent(new Button("Edit", clickEvent -> {
            int i = 0;
            for (int a = 0; a < recipes.size(); a++) {
                if (recipes.get(a).getId() == selected) {
                    i = a;
                    break;
                }
            }
            Recipe buf = new Recipe(selected, editAbout, editPatient,
                    editDoctor, editCreateDate, editDuration, editPriority);
            if (db.updateRecipeByID(buf)) {
                recipes.remove(i);
                recipes.add(buf);
                table.clearSortOrder();
            } else {
                context.addWindow(recipeErrorWindow());
            }

            subWindow.close();
        }));
        subWindow.center();
        subWindow.setModal(true);
        return subWindow;
    }

    private Window recipeDeleteWindow() {
        Window subWindow = new Window("Delete");
        HorizontalLayout subContent = new HorizontalLayout();
        subWindow.setContent(subContent);

        subContent.addComponent(new Label("Really?"));
        subContent.addComponent(new Button("Yes", clickEvent -> {
            System.out.println("clickEvent " + clickEvent.getComponent().getId());
            System.out.println("selected = " + selected);
            if (db.deleteRecipeByID(selected)) {
                recipes.removeIf(p -> p.getId() == selected);
                table.clearSortOrder();
            } else {
                context.addWindow(recipeErrorWindow());
            }
            subWindow.close();
        }));
        subContent.addComponent(new Button("No", clickEvent -> subWindow.close()));
        subWindow.center();
        subWindow.setModal(true);
        return subWindow;
    }

    private Window recipeErrorWindow() {
        Window subWindow = new Window("Error");
        HorizontalLayout subContent = new HorizontalLayout();
        subWindow.setContent(subContent);

        subContent.addComponent(new Label("есть зависимости"));
        subContent.addComponent(new Button("Yes", clickEvent -> subWindow.close()));
        subWindow.center();
        subWindow.setModal(true);
        return subWindow;
    }

    private void creteFilter() {
        ldp = new ListDataProvider<>(recipes);

        table.setDataProvider(ldp);
        createFilter();
        ldp.setFilter(filter);
    }

    private void createFilter() {
        filter = (SerializablePredicate<Recipe>) r -> {
            if (!"".contains(textFilterAbout) && !"".equals(textFilterPriority)
                    && !"".equals(textFilterPatient)) {
                return r.getAbout().contains(textFilterAbout)
                        && r.getPriority().equals(textFilterPriority)
                        && ("" + r.getPatient()).equals(textFilterPatient);
            } else if (!"".contains(textFilterAbout) && !"".equals(textFilterPriority)) {
                return r.getAbout().contains(textFilterAbout)
                        && r.getPriority().equals(textFilterPriority);
            } else if (!"".contains(textFilterAbout) && !"".equals(textFilterPatient)) {
                return r.getAbout().contains(textFilterAbout)
                        && ("" + r.getPatient()).equals(textFilterPatient);
            } else if (!"".equals(textFilterPriority) && !"".equals(textFilterPatient)) {
                return r.getPriority().equals(textFilterPriority)
                        && ("" + r.getPatient()).equals(textFilterPatient);
            } else if (!"".contains(textFilterAbout)) {
                return r.getAbout().contains(textFilterAbout);
            } else if (!"".equals(textFilterPatient)) {
                return ("" + r.getPatient()).equals(textFilterPatient);
            } else if (!"".equals(textFilterPriority)) {
                return r.getPriority().equals(textFilterPriority);
            } else
                return true;
        };
    }

}
