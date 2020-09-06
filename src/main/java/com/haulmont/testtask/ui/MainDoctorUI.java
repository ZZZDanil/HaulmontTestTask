package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.DaoDoctor;
import com.haulmont.testtask.db.DoctorHSQL;
import com.haulmont.testtask.pojo.Doctor;
import com.vaadin.data.Binder;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class MainDoctorUI {

    private final VerticalLayout doctorUI;

    private UI context;
    private long selected = 2;
    private Grid<Doctor> table;

    private String createName = "";
    private String createSurname = "";
    private String createPatronymic = "";
    private String createSpecialisation = "";
    private String editName = "";
    private String editSurname = "";
    private String editPatronymic = "";
    private String editSpecialisation = "";

    private DaoDoctor db;

    private List<Doctor> doctors;

    public MainDoctorUI(UI context) {
        try {
            db = new DoctorHSQL();
            doctors = db.getAllDoctor();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.context = context;

        doctorUI = new VerticalLayout();

        table = new Grid<>();
        table.addColumn(Doctor::getName).setCaption("name")
                .setResizable(true).setExpandRatio(1)
                .setMinimumWidth(0);
        table.addColumn(Doctor::getSurname).setCaption("surname")
                .setResizable(true).setExpandRatio(1)
                .setMinimumWidth(0);
        table.addColumn(Doctor::getPatronymic).setCaption("patronymic")
                .setResizable(true).setExpandRatio(1)
                .setMinimumWidth(0);
        table.addColumn(Doctor::getSpecialisation).setCaption("specialisation")
                .setResizable(true).setExpandRatio(1)
                .setMinimumWidth(0);
        assert doctors != null;
        table.setItems(doctors);

        HorizontalLayout toolBar = new HorizontalLayout();

        Button add = doctorAddButton();
        Button edit = doctorEditButton();
        Button del = doctorDeleteButton();
        Button stat = doctorStatisticButton();
        toolBar.addComponent(add);
        toolBar.addComponent(edit);
        toolBar.addComponent(del);
        toolBar.addComponent(stat);
        add.setWidth("100%");
        edit.setWidth("100%");
        del.setWidth("100%");
        stat.setWidth("100%");
        toolBar.setExpandRatio(add, 1);
        toolBar.setExpandRatio(edit, 1);
        toolBar.setExpandRatio(del, 1);
        toolBar.setExpandRatio(stat, 1);

        toolBar.setComponentAlignment(add, Alignment.TOP_RIGHT);
        toolBar.setComponentAlignment(edit, Alignment.TOP_RIGHT);
        toolBar.setComponentAlignment(del, Alignment.TOP_RIGHT);
        toolBar.setComponentAlignment(stat, Alignment.TOP_RIGHT);

        table.setItems(doctors);
        table.addSelectionListener(e -> {
            System.out.println("table select doctors by id: " + table.getSelectionModel().getFirstSelectedItem().get().getId());
            Doctor d = e.getFirstSelectedItem().get();
            selected = d.getId();
        });
        Label l = new Label("Doctors");
        l.setWidth("100%");
        doctorUI.addComponent(l);
        doctorUI.addComponent(toolBar);
        doctorUI.addComponent(table);
        doctorUI.setComponentAlignment(l, Alignment.TOP_CENTER);
        doctorUI.setComponentAlignment(toolBar, Alignment.TOP_RIGHT);
        doctorUI.setComponentAlignment(table, Alignment.BOTTOM_CENTER);
        doctorUI.setExpandRatio(toolBar, 1);
        doctorUI.setExpandRatio(table, 8);
        table.setSizeFull();
        toolBar.setSpacing(false);
        toolBar.setSizeUndefined();
        doctorUI.setSizeFull();
        doctorUI.setSpacing(false);
        doctorUI.setMargin(false);
    }

    public Layout getDoctorUI() {
        return doctorUI;
    }

    private Button doctorAddButton() {
        Button b = new Button("Add");
        b.setId("test id, it's just work? ");
        b.addClickListener(clickEvent -> context.addWindow(doctorCreateWindow()));
        b.setSizeUndefined();
        return b;
    }

    private Button doctorEditButton() {
        Button b = new Button("Edit");
        b.addClickListener(clickEvent -> context.addWindow(Objects.requireNonNull(doctorEditWindow())));
        b.setSizeUndefined();
        return b;
    }

    private Button doctorDeleteButton() {
        Button b = new Button("Delete");
        b.addClickListener(clickEvent -> context.addWindow(doctorDeleteWindow()));
        b.setSizeUndefined();
        return b;
    }

    private Button doctorStatisticButton() {
        Button b = new Button("Statistic");
        b.addClickListener(clickEvent -> context.addWindow(doctorStatisticWindow()));
        b.setSizeUndefined();
        return b;
    }

    private Window doctorCreateWindow() {
        Window subWindow = new Window("Create");
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);
        HorizontalLayout param = new HorizontalLayout();
        TextField dName = new TextField("name");
        TextField dSurname = new TextField("surname");
        TextField dPatronymic = new TextField("patronymic");
        TextField dSpecialisation = new TextField("specialisation");

        param.addComponent(dName);
        param.addComponent(dSurname);
        param.addComponent(dPatronymic);
        param.addComponent(dSpecialisation);

        Binder<String> bn = new Binder<>();
        Binder<String> bs = new Binder<>();
        Binder<String> bp = new Binder<>();
        Binder<String> bph = new Binder<>();
        bn.forField(dName).withValidator(name -> {
            if (name.length() > 0) {
                return true;
            }
            return false;
        }, "For example: Ivan").bind(string -> string, null);
        bs.forField(dSurname).withValidator(surname -> {
            if (surname.length() > 0) {
                return true;
            }
            return false;
        }, "For example: Ivanov").bind(string -> string, null);
        bp.forField(dPatronymic).withValidator(patronymic -> {
            if (patronymic.length() > 0) {
                return true;
            }
            return false;
        }, "For example: Ivanovich").bind(string -> string, null);
        bph.forField(dSpecialisation).withValidator(s -> {
            if (s.length() > 0) {
                return true;
            }
            return false;
        }, "For example: Doctor").bind(string -> string, null);
        bn.validate();
        bp.validate();
        bs.validate();
        bph.validate();

        param.getComponent(0).setSizeFull();
        dName.addValueChangeListener(valueChangeEvent -> createName = valueChangeEvent.getValue());
        param.getComponent(1).setSizeFull();
        dSurname.addValueChangeListener(valueChangeEvent -> createSurname = valueChangeEvent.getValue());
        param.getComponent(2).setSizeFull();
        dPatronymic.addValueChangeListener(valueChangeEvent -> createPatronymic = valueChangeEvent.getValue());
        param.getComponent(3).setSizeFull();
        dSpecialisation.addValueChangeListener(valueChangeEvent -> createSpecialisation = valueChangeEvent.getValue());
        param.setExpandRatio(dName, 1);
        param.setExpandRatio(dSurname, 1);
        param.setExpandRatio(dPatronymic, 1);
        param.setExpandRatio(dSpecialisation, 1);
        param.setSizeFull();

        subContent.addComponent(param);
        subContent.addComponent(new Button("Create",
                clickEvent -> {
                    Doctor addD = new Doctor(100L, createName,
                            createSurname, createPatronymic, createSpecialisation);
                    if (db.setDoctor(addD)) {
                        doctors.add(addD);
                        table.clearSortOrder();
                    } else {
                        context.addWindow(doctorErrorWindow());
                    }
                    subWindow.close();
                }));
        subWindow.center();
        subWindow.setModal(true);
        return subWindow;
    }

    private Window doctorEditWindow() {
        Window subWindow = new Window("Edit");
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);

        Doctor d = doctors.stream().filter(f -> selected == f.getId()).findFirst().get();
        HorizontalLayout first = new HorizontalLayout();
        first.addComponent(new Label("First:"));
        first.addComponent(new Label(d.getName()));
        first.addComponent(new Label(d.getSurname()));
        first.addComponent(new Label(d.getPatronymic()));
        first.addComponent(new Label(d.getSpecialisation()));

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
        TextField dName = new TextField("name");
        TextField dSurname = new TextField("surname");
        TextField dPatronymic = new TextField("patronymic");
        TextField dSpecialisation = new TextField("specialisation");
        edit.addComponent(new Label("Edit:"));
        edit.addComponent(dName);
        edit.addComponent(dSurname);
        edit.addComponent(dPatronymic);
        edit.addComponent(dSpecialisation);


        Binder<String> bn = new Binder<>();
        Binder<String> bs = new Binder<>();
        Binder<String> bp = new Binder<>();
        Binder<String> bph = new Binder<>();
        bn.forField(dName).withValidator(name -> {
            if (name.length() > 0) {
                return true;
            }
            return false;
        }, "For example: Ivan").bind(string -> string, null);
        bs.forField(dSurname).withValidator(surname -> {
            if (surname.length() > 0) {
                return true;
            }
            return false;
        }, "For example: Ivanov").bind(string -> string, null);
        bp.forField(dPatronymic).withValidator(patronymic -> {
            if (patronymic.length() > 0) {
                return true;
            }
            return false;
        }, "For example: Ivanovich").bind(string -> string, null);
        bph.forField(dSpecialisation).withValidator(s -> {
            if (s.length() > 0) {
                return true;
            }
            return false;
        }, "For example: Doctor").bind(string -> string, null);
        bn.validate();
        bp.validate();
        bs.validate();
        bph.validate();


        dName.setSizeFull();
        dName.addValueChangeListener(valueChangeEvent -> editName = valueChangeEvent.getValue());
        dSurname.setSizeFull();
        dSurname.addValueChangeListener(valueChangeEvent -> editSurname = valueChangeEvent.getValue());
        dPatronymic.setSizeFull();
        dPatronymic.addValueChangeListener(valueChangeEvent -> editPatronymic = valueChangeEvent.getValue());
        dSpecialisation.setSizeFull();
        dSpecialisation.addValueChangeListener(valueChangeEvent -> editSpecialisation = valueChangeEvent.getValue());
        edit.setExpandRatio(dName, 1);
        edit.setExpandRatio(dSurname, 1);
        edit.setExpandRatio(dPatronymic, 1);
        edit.setExpandRatio(dSpecialisation, 1);
        edit.setSizeFull();

        subContent.addComponent(first);
        subContent.addComponent(edit);
        subContent.addComponent(new Button("Edit", clickEvent -> {
            int i = 0;
            for (int a = 0; a < doctors.size(); a++) {
                if (doctors.get(a).getId() == selected) {
                    i = a;
                    break;
                }
            }
            Doctor buf = new Doctor(selected, editName, editSurname, editPatronymic, editSpecialisation);
            if (db.updateDoctorByID(buf)) {
                doctors.remove(i);
                doctors.add(buf);
                table.clearSortOrder();
            } else {
                context.addWindow(doctorErrorWindow());
            }
            subWindow.close();
        }));
        subWindow.center();
        subWindow.setModal(true);
        return subWindow;
    }

    private Window doctorDeleteWindow() {
        Window subWindow = new Window("Delete");
        HorizontalLayout subContent = new HorizontalLayout();
        subWindow.setContent(subContent);

        subContent.addComponent(new Label("Really?"));
        subContent.addComponent(new Button("Yes", clickEvent -> {
            System.out.println("clickEvent " + clickEvent.getComponent().getId());
            System.out.println("selected = " + selected);
            if (db.deleteDoctorByID(selected)) {
                doctors.removeIf(p -> p.getId() == selected);
                table.clearSortOrder();
            } else {
                context.addWindow(doctorErrorWindow());
            }
            subWindow.close();
        }));
        subContent.addComponent(new Button("No", clickEvent -> subWindow.close()));
        subWindow.center();
        subWindow.setModal(true);
        return subWindow;
    }

    private Window doctorStatisticWindow() {
        Window subWindow = new Window("Statistic");
        VerticalLayout subContent = new VerticalLayout();
        subWindow.setContent(subContent);
        List<List<String>> list = db.getStatisticOfDoctors();
        for (int a = 0; a< list.size(); a++){
            subContent.addComponent(new Label(list.get(a).get(0) + " - " + list.get(a).get(1) + " recipes"));
        }
        subContent.addComponent(new Button("Close", clickEvent -> subWindow.close()));
        subWindow.center();
//        subWindow.setWidth("50%");
        subWindow.setHeight("50%");
        subWindow.setModal(true);
        return subWindow;
    }

    private Window doctorErrorWindow() {
        Window subWindow = new Window("Error");
        HorizontalLayout subContent = new HorizontalLayout();
        subWindow.setContent(subContent);

        subContent.addComponent(new Label("есть зависимости"));
        subContent.addComponent(new Button("Ok", clickEvent -> subWindow.close()));
        subWindow.center();
        subWindow.setModal(true);
        return subWindow;
    }
}
