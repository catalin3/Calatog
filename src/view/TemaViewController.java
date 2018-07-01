package view;

import entities.Student;
import entities.Tema;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.TemaService;
import utils.ListEvent;
import utils.Observer;
import validator.ValidateException;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TemaViewController implements Observer<Tema> {
    @FXML
    private TableView<Tema> tableViewTema=new TableView<Tema>();
    @FXML
    private TableColumn<Tema,String> numarColumn;
    @FXML
    private TableColumn<Tema,String> descriereColumn;
    @FXML
    private TableColumn<Tema,String> deadlineColumn;

    @FXML
    TextField textFieldNumar;
    @FXML
    TextField textFieldDescriere;
    @FXML
    TextField textFieldDeadline;
    @FXML
    TextField textFieldDeadlineFilter;

    TemaService service;
    ObservableList<Tema> model;
    Stage dialogStage;
    Tema Tema;

    public TemaViewController(){

    }
    @FXML
    private void initialize(){
        numarColumn.setCellValueFactory(new PropertyValueFactory<Tema,String>("Numar"));
        descriereColumn.setCellValueFactory(new PropertyValueFactory<Tema,String>("Descriere"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<Tema,String>("Deadline"));

    }

    public void setService(TemaService service){
        this.service=service;
        this.model= FXCollections.observableArrayList(service.getAllTema());
        tableViewTema.setItems(model);

        tableViewTema.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tema>() {
            @Override
            public void changed(ObservableValue<? extends Tema> observable, Tema oldValue, Tema newValue) {
                if(tableViewTema.getSelectionModel().getSelectedItem()!=null){
                    Tema tema=tableViewTema.getSelectionModel().getSelectedItem();
                    setFields(tema);
                }
            }
        });
    }


    private void setFields(Tema s)
    {
        //Student s=tableViewStudent.getSelectionModel().getSelectedItem();
        textFieldNumar.setText(Integer.toString(s.getNumar()));
        textFieldDescriere.setText(s.getDescriere());
        textFieldDeadline.setText(Integer.toString(s.getDeadline()));

    }

    public TableView<Tema> getTableView() {
        tableViewTema.setItems(model);
        return tableViewTema;

    }



    @Override
    public void notyfyEvent(ListEvent<Tema> e) {
        tableViewTema=getTableView();
        model.setAll(StreamSupport.stream(e.getList().spliterator(),false).collect(Collectors.toList()));
    }
    @FXML
    private void saveTema(ActionEvent actionEvent){
        Tema s=extractTema();
        try {
            Tema saved=service.addStudent(s);
            if(saved!=null){
                MessageAlert.showMessage(dialogStage, Alert.AlertType.INFORMATION,"Salvare cu succes","Studentul a fost adaugat!");
                // clearFields();
            }
            else{
                MessageAlert.showErrorMessage(dialogStage,"Exista deja un student cu acest id!");
            }
        }catch (ValidateException e){
            MessageAlert.showErrorMessage(dialogStage,e.getMessage());
        }

    }

    @FXML
    private void update(ActionEvent actionEvent) throws ValidateException {
        Tema nota=extractTema();


        Tema updated =  service.update(nota);
        if(updated!=null){
            MessageAlert.showMessage(dialogStage, Alert.AlertType.INFORMATION,"Modificare cu succes","Tema a fost adaugata!");
            // clearFields();
        }
        else{
            MessageAlert.showErrorMessage(dialogStage,"Exista deja o tema cu acest id si numar!");
        }

    }

    @FXML
    private void deleteTema(ActionEvent actionEvent) throws ValidateException {
        Tema nota=extractTema();

        Tema deleted=service.delete(nota);
        clearFields();
        if(deleted!=null){
            MessageAlert.showMessage(dialogStage, Alert.AlertType.INFORMATION,"Eliminare cu succes","Tema a fost eliminata!");
            // clearFields();
        }
        else{
            MessageAlert.showErrorMessage(dialogStage,"Nu exista Tema cu acest numar!");
        }
    }

    @FXML
    private void clearTemaFields(ActionEvent actionEvent){
        clearFields();
        this.model= FXCollections.observableArrayList(service.getAllTema());
        tableViewTema.setItems(model);
    }



    private void clearFields(){
        textFieldNumar.setText("");
        textFieldDescriere.setText("");
        textFieldDeadline.setText("");
    }

    private Tema extractTema() {

        String nume = textFieldNumar.getText();
        String profesor=textFieldDescriere.getText();
        String id= textFieldDeadline.getText();
        int ids=Integer.valueOf(nume);
        int dead=Integer.valueOf(id);
        return  new Tema(ids,profesor,dead);
    }

    @FXML
    private void filtruDeadlineAsc(ActionEvent actionEvent){
        int saptamana = Integer.parseInt(textFieldDeadlineFilter.getText());
        this.model= FXCollections.observableArrayList(service.allDeadlineAsc(saptamana));
        tableViewTema.setItems(model);
    }

    @FXML
    private void filtruDeadlineDesc(ActionEvent actionEvent){
        int saptamana = Integer.parseInt(textFieldDeadlineFilter.getText());
        this.model= FXCollections.observableArrayList(service.allDeadlineDesc(saptamana));
        tableViewTema.setItems(model);
    }


}
