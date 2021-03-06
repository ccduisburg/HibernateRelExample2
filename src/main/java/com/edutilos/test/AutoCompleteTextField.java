package com.edutilos.test;
import com.personTest.dao._PersonDAO;
import com.personTest.model.PersonStudent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class is a TextField which implements an "autocomplete" functionality, based on a supplied list of entries.
 * @author Caleb Brinkman
 */
@Getter
@Setter
public class AutoCompleteTextField extends TextField
{
    /** The existing autocomplete entries. */
    private final SortedSet<String> entries;
    /** The popup used to select an entry. */
    private ContextMenu entriesPopup;
    private String fieldName;
    private AutoCompleteTextField txtName, txtVorname,txtStrasse,txtHnummer,txtPLZ,txtSchule,txtLeite,txtKlasse;
    private _PersonDAO dao;

    /** Construct a new AutoCompleteTextField. */
    public AutoCompleteTextField() {
        super();
        dao = new _PersonDAO();
        entries = new TreeSet<>();
        entriesPopup = new ContextMenu();
        textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (getText().length() == 0)
                {
                    entriesPopup.hide();
                } else
                {
                    LinkedList<String> searchResult = new LinkedList<>();
                    searchResult.addAll(entries.subSet(getText(), getText() + Character.MAX_VALUE));
                    if (entries.size() > 0)
                    {
                        populatePopup(searchResult);
                        if (!entriesPopup.isShowing())
                        {
                            entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                        }
                    } else
                    {
                        entriesPopup.hide();
                    }
                }
            }
        });

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
                entriesPopup.hide();
            }
        });

    }


    private void populateFields(String fieldName) {
        String txt = getText();
        PersonStudent personStudent = dao.findByName(getText());
        if(personStudent == null) return;
//        clearField();
        switch(fieldName) {
            case "name":
                txtVorname.setText(personStudent.getVorname());
                txtStrasse.setText(personStudent.getAdress().getStrasse());
                txtHnummer.setText(personStudent.getAdress().getHnummer()+"");
                txtPLZ.setText(personStudent.getAdress().getPLZ());
                txtSchule.setText(personStudent.getSchule().getName());
                txtLeite.setText(personStudent.getSchule().getLeite());
                txtKlasse.setText(personStudent.getKlasse()+"");
                break;
            default:
                txtName.setText(personStudent.getName());
                txtVorname.setText(personStudent.getVorname());
                txtStrasse.setText(personStudent.getAdress().getStrasse());
                txtHnummer.setText(personStudent.getAdress().getHnummer()+"");
                txtPLZ.setText(personStudent.getAdress().getPLZ());
                txtSchule.setText(personStudent.getSchule().getName());
                txtLeite.setText(personStudent.getSchule().getLeite());
                txtKlasse.setText(personStudent.getKlasse()+"");

        }

    }

    /**
     * Get the existing set of autocomplete entries.
     * @return The existing autocomplete entries.
     */
    public SortedSet<String> getEntries() { return entries; }

    /**
     * Populate the entry set with the given search results.  Display is limited to 10 entries, for performance.
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<String> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        // If you'd like more entries, modify this line.
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++)
        {
            final String result = searchResult.get(i);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent actionEvent) {
                    setText(result);
                    entriesPopup.hide();
                    populateFields(fieldName);
                }
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);

    }
}