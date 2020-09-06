package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.DaoPatient;
import com.haulmont.testtask.db.PatientHSQL;
import com.haulmont.testtask.pojo.Patient;
import com.vaadin.data.Binder;
import com.vaadin.ui.*;

import java.util.List;
import java.util.Objects;

public class MainPatientUI {

    private final VerticalLayout patientUI;
    private UI context;
    private Grid<Patient> table;
    private long selected = 2;

    private DaoPatient db;

    private List<Patient> people;

    private String createName = "";
    private String createSurname = "";
    private String createPatronymic = "";
    private String createPhone = "";
    private String editName = "";
    private String editSurname = "";
    private String editPatronymic = "";
    private String editPhone = "";

    //    private String allCountryRegex = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$";
    private String allCountryRegex = "^(\\\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$";

    public MainPatientUI(UI context) {

        db = new PatientHSQL();
        people = db.getAllPatient();

        this.context = context;

        patientUI = new VerticalLayout();


        HorizontalLayout toolBar = new HorizontalLayout();
        Button add = patientAddButton();
        Button edit = patientEditButton();
        Button del = patientDeleteButton();
        toolBar.addComponent(add);
        toolBar.addComponent(edit);
        toolBar.addComponent(del);
        add.setWidth("100%");
        edit.setWidth("100%");
        del.setWidth("100%");
        toolBar.setExpandRatio(add, 1);
        toolBar.setExpandRatio(edit, 1);
        toolBar.setExpandRatio(del, 1);


        toolBar.setComponentAlignment(add, Alignment.TOP_RIGHT);
        toolBar.setComponentAlignment(edit, Alignment.TOP_RIGHT);
        toolBar.setComponentAlignment(del, Alignment.TOP_RIGHT);

        table = new Grid<>();
        table.setSelectionMode(Grid.SelectionMode.SINGLE);
        table.addColumn(Patient::getName).setCaption("name")
                .setResizable(true).setExpandRatio(1)
                .setMinimumWidth(0);
        table.addColumn(Patient::getSurname).setCaption("surname")
                .setResizable(true).setExpandRatio(1)
                .setMinimumWidth(0);
        table.addColumn(Patient::getPatronymic)
                .setCaption("patronymic").setResizable(true)
                .setExpandRatio(1).setMinimumWidth(0);
        table.addColumn(Patient::getPhone).setCaption("phone")
                .setResizable(true).setExpandRatio(1)
                .setMinimumWidth(0);

        table.setItems(people);
        table.addSelectionListener(e -> {
            if (e.getFirstSelectedItem().isPresent()) {
                Patient p = e.getFirstSelectedItem().get();
                selected = p.getId();
            }
        });

        Label l = new Label("Patients");
        l.setWidth("100%");
        patientUI.addComponent(l);
        patientUI.addComponent(toolBar);
        patientUI.addComponent(table);
        patientUI.setComponentAlignment(l, Alignment.TOP_CENTER);
        patientUI.setComponentAlignment(toolBar, Alignment.TOP_RIGHT);
        patientUI.setComponentAlignment(table, Alignment.BOTTOM_CENTER);
        patientUI.setExpandRatio(toolBar, 1);
        patientUI.setExpandRatio(table, 8);
        table.setSizeFull();
        toolBar.setSpacing(false);
        toolBar.setSizeUndefined();
        patientUI.setSizeFull();
        patientUI.setSpacing(false);
        patientUI.setMargin(false);
    }

    public Layout getPatientUI() {
        return patientUI;
    }

    private Button patientAddButton() {
        Button b = new Button("Add");
        b.setId("test id, it's just work? ");
        b.addClickListener(clickEvent -> context.addWindow(patientCreate()));
        b.setSizeUndefined();
        return b;
    }

    private Button patientEditButton() {
        Button b = new Button("Edit");
        b.addClickListener(clickEvent -> context.addWindow(Objects.requireNonNull(patientEdit())));
        b.setSizeUndefined();
        return b;
    }

    private Button patientDeleteButton() {
        Button b = new Button("Delete");
        b.addClickListener(clickEvent -> context.addWindow(patientDelete()));
        b.setSizeUndefined();
        return b;
    }

    private Window patientCreate() {
        boolean block = true;
        Window subWindow = new Window("Create");
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);
        HorizontalLayout param = new HorizontalLayout();
        TextField pName = new TextField("name");
        TextField pSurname = new TextField("surname");
        TextField pPatronymic = new TextField("patronymic");
        TextField pPhone = new TextField("phone");
        Binder<String> bn = new Binder<>();
        Binder<String> bs = new Binder<>();
        Binder<String> bp = new Binder<>();
        Binder<String> bph = new Binder<>();
        bn.forField(pName).withValidator(name -> {
            if (name.length() > 0) {
                return true;
            }
            return false;
        }, "For example: Ivan").bind(string -> string, null);
        bs.forField(pSurname).withValidator(surname -> {
            if (surname.length() > 0) {
                return true;
            }
            return false;
        }, "For example: Ivanov").bind(string -> string, null);
        bp.forField(pPatronymic).withValidator(patronymic -> {
            if (patronymic.length() > 0) {
                return true;
            }
            return false;
        }, "For example: Ivanovich").bind(string -> string, null);
        bph.forField(pPhone).withValidator(phone -> {
            if (phone.matches(allCountryRegex)) {
                return true;
            }
            return false;
        }, "For example: 0(000)000-0000").bind(string -> string, null);
        bn.validate();
        bp.validate();
        bs.validate();
        bph.validate();
        param.addComponent(pName);
        param.addComponent(pSurname);
        param.addComponent(pPatronymic);
        param.addComponent(pPhone);
        pName.addValueChangeListener(valueChangeEvent ->
                createName = valueChangeEvent.getValue());
        pSurname.addValueChangeListener(valueChangeEvent ->
                createSurname = valueChangeEvent.getValue());
        pPatronymic.addValueChangeListener(valueChangeEvent ->
                createPatronymic = valueChangeEvent.getValue());
        pPhone.addValueChangeListener(valueChangeEvent ->
                createPhone = valueChangeEvent.getValue());
        pName.setSizeFull();
        pSurname.setSizeFull();
        pPatronymic.setSizeFull();
        pPhone.setSizeFull();
        param.setExpandRatio(pName, 1);
        param.setExpandRatio(pSurname, 1);
        param.setExpandRatio(pPatronymic, 1);
        param.setExpandRatio(pPhone, 1);
        param.setSizeFull();
        subContent.addComponent(param);
        subContent.addComponent(new Button("Create",
                clickEvent -> {
                    if (bn.isValid() && bs.isValid() && bp.isValid() && bph.isValid()) {
                        Patient createPatient = new Patient(100L, createName,
                                createSurname, createPatronymic, createPhone);
                        if (db.setPatient(createPatient)) {
                            people.add(createPatient);
                            table.clearSortOrder();
                        } else {
                            context.addWindow(patientError());
                        }
                        subWindow.close();
                    }
                }));
        subWindow.center();
        subWindow.setModal(true);
        return subWindow;
    }

    private Window patientEdit() {
        Window subWindow = new Window("Edit");
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);

        Patient p = people.stream().filter(f -> selected == f.getId()).findFirst().get();
        HorizontalLayout first = new HorizontalLayout();
        first.addComponent(new Label("First:"));
        first.addComponent(new Label(p.getName()));
        first.addComponent(new Label(p.getSurname()));
        first.addComponent(new Label(p.getPatronymic()));
        first.addComponent(new Label(p.getPhone()));

        first.getComponent(0).setSizeFull();
        first.getComponent(1).setSizeFull();
        first.getComponent(2).setSizeFull();
        first.getComponent(3).setSizeFull();
        first.getComponent(4).setSizeFull();
        first.setExpandRatio(first.getComponent(0), 1);
        first.setExpandRatio(first.getComponent(1), 1);
        first.setExpandRatio(first.getComponent(2), 1);
        first.setExpandRatio(first.getComponent(3), 1);
        first.setExpandRatio(first.getComponent(4), 1);

        first.setSizeFull();

        HorizontalLayout edit = new HorizontalLayout();
        TextField pName = new TextField("name");
        TextField pSurname = new TextField("surname");
        TextField pPatronymic = new TextField("patronymic");
        TextField pPhone = new TextField("phone");

        edit.addComponent(new Label("Edit:"));
        edit.addComponent(pName);
        edit.addComponent(pSurname);
        edit.addComponent(pPatronymic);
        edit.addComponent(pPhone);

        Binder<String> bn = new Binder<>();
        Binder<String> bs = new Binder<>();
        Binder<String> bp = new Binder<>();
        Binder<String> bph = new Binder<>();
        bn.forField(pName).withValidator(name -> {
            if (name.length() > 0) {
                return true;
            }
            return false;
        }, "For example: Ivan").bind(string -> string, null);
        bs.forField(pSurname).withValidator(surname -> {
            if (surname.length() > 0) {
                return true;
            }
            return false;
        }, "For example: Ivanov").bind(string -> string, null);
        bp.forField(pPatronymic).withValidator(patronymic -> {
            if (patronymic.length() > 0) {
                return true;
            }
            return false;
        }, "For example: Ivanovich").bind(string -> string, null);
        bph.forField(pPhone).withValidator(phone -> {
            if (phone.matches(allCountryRegex)) {
                return true;
            }
            return false;
        }, "For example: 0(000)000-0000").bind(string -> string, null);
        bn.validate();
        bp.validate();
        bs.validate();
        bph.validate();

        edit.getComponent(1).setSizeFull();
        pName.addValueChangeListener(valueChangeEvent ->
                editName = valueChangeEvent.getValue());
        edit.getComponent(2).setSizeFull();
        pSurname.addValueChangeListener(valueChangeEvent ->
                editSurname = valueChangeEvent.getValue());
        edit.getComponent(3).setSizeFull();
        pPatronymic.addValueChangeListener(valueChangeEvent ->
                editPatronymic = valueChangeEvent.getValue());
        edit.getComponent(4).setSizeFull();
        pPhone.addValueChangeListener(valueChangeEvent ->
                editPhone = valueChangeEvent.getValue());
        edit.setExpandRatio(edit.getComponent(0), 1);
        edit.setExpandRatio(edit.getComponent(1), 1);
        edit.setExpandRatio(edit.getComponent(2), 1);
        edit.setExpandRatio(edit.getComponent(3), 1);
        edit.setExpandRatio(edit.getComponent(4), 1);
        edit.setSizeFull();

        subContent.addComponent(first);
        subContent.addComponent(edit);
        subContent.addComponent(new Button("Edit", clickEvent -> {
            int i = 0;
            for (int a = 0; a < people.size(); a++) {
                if (people.get(a).getId() == selected) {
                    i = a;
                    break;
                }
            }
            Patient buf = new Patient(selected, editName, editSurname, editPatronymic, editPhone);
            if (db.updatePatientByID(buf)) {
                people.remove(i);
                people.add(buf);
                table.clearSortOrder();
            } else {
                context.addWindow(patientError());
            }
            subWindow.close();
        }));

        subWindow.center();
        subWindow.setModal(true);
        return subWindow;
    }

    private Window patientDelete() {
        Window subWindow = new Window("Delete");
        HorizontalLayout subContent = new HorizontalLayout();
        subWindow.setContent(subContent);

        subContent.addComponent(new Label("Really?"));
        subContent.addComponent(new Button("Yes", clickEvent -> {
            System.out.println("clickEvent " + clickEvent.getComponent().getId());
            System.out.println("selected = " + selected);
            if (db.deletePatientByID(selected)) {
                people.removeIf(p -> p.getId() == selected);
                table.clearSortOrder();
            } else {
                context.addWindow(patientError());
            }
            subWindow.close();
        }));
        subContent.addComponent(new Button("No", clickEvent -> subWindow.close()));

        subWindow.center();
        subWindow.setModal(true);
        return subWindow;
    }

    private Window patientError() {
        Window subWindow = new Window("Error");
        HorizontalLayout subContent = new HorizontalLayout();
        subWindow.setContent(subContent);

        subContent.addComponent(new Label("есть зависимости"));
        subContent.addComponent(new Button("Yes", clickEvent -> subWindow.close()));
        subWindow.center();
        subWindow.setModal(true);
        return subWindow;
    }
}
