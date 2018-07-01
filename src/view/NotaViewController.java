package view;


import entities.Nota;
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
import service.NotaService;
import service.StudentService;
import utils.ListEvent;
import utils.Observer;
import validator.ValidateException;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class NotaViewController implements Observer<Nota> {
    @FXML
    private TableView<Nota> tableViewNota;
    @FXML
    private TableColumn<Nota,String>idStudentColumn;
    @FXML
    private TableColumn<Nota,String> numarTemaColumn;
    @FXML
    private TableColumn<Nota,Integer> valoareColumn;
    @FXML
    private TextField textFieldIdStudent,textFieldNumarTema,textFieldValoare;
    @FXML
    private TextField textFieldFiltruStudent, textFieldFiltruTema;
    @FXML
    TextField textFieldNumeStudent;

    Stage dialogStage;
    NotaService service;
    StudentService studentService;

    ObservableList<Nota> model;
    public NotaViewController(){
    }
    @FXML
    private void initialize(){
        idStudentColumn.setCellValueFactory(new PropertyValueFactory<Nota,String>("IdStudent"));
        numarTemaColumn.setCellValueFactory(new PropertyValueFactory<Nota,String>("NumarTema"));
        valoareColumn.setCellValueFactory(new PropertyValueFactory<Nota,Integer>("Valoare"));

    }
    ;
    public  void setService(NotaService service){
        this.service=service;
        this.model= FXCollections.observableArrayList(service.getAllNota());
        tableViewNota.setItems(model);


        tableViewNota.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Nota>() {
            @Override
            public void changed(ObservableValue<? extends Nota> observable, Nota oldValue, Nota newValue) {
                if(tableViewNota.getSelectionModel().getSelectedItem()!=null){
                    Nota nota=tableViewNota.getSelectionModel().getSelectedItem();
                    setFields(nota);
                }
            }
        });
    }


    private void setFields(Nota nota)
    {
        //Student s=tableViewStudent.getSelectionModel().getSelectedItem();

        Student st=service.findStudent(nota.getIdStudent());
        String nume = st.getNume();
        textFieldNumeStudent.setText(nume);
        textFieldNumarTema.setText(Integer.toString(nota.getIdStudent()));
        textFieldIdStudent.setText(Integer.toString(nota.getNumarTema()));
        textFieldValoare.setText(Integer.toString(nota.getValoare()));

    }

    public TableView<Nota> getTableView() {
        tableViewNota.setItems(model);
        return tableViewNota;

    }



    @Override
    public void notyfyEvent(ListEvent<Nota> e) {
        tableViewNota=getTableView();
        model.setAll(StreamSupport.stream(e.getList().spliterator(),false).collect(Collectors.toList()));
    }
    @FXML
    private void saveNota(ActionEvent actionEvent){
        Nota s=extractNota();

        try {
            Nota saved=service.addNota(s);
            if(saved!=null){
                MessageAlert.showMessage(dialogStage, Alert.AlertType.INFORMATION,"Salvare cu succes","Studentul a fost adaugat!");
                // clearFields();
            }
            else{
                MessageAlert.showErrorMessage(dialogStage,"Exista deja un student cu acest id!");
            }
        }catch (ValidateException e) {
            MessageAlert.showErrorMessage(dialogStage, e.getMessage());
        }

    }

    @FXML
    private void updateNota(ActionEvent actionEvent) throws ValidateException {
        Nota nota=extractNota();


        Nota updated =  service.update(nota);
        if(updated!=null){
            MessageAlert.showMessage(dialogStage, Alert.AlertType.INFORMATION,"Modificare cu succes","Nota a fost adaugata!");
            // clearFields();
        }
        else{
            MessageAlert.showErrorMessage(dialogStage,"Exista deja o nota cu acest id si numar!");
        }

    }

    @FXML
    private void deleteNota(ActionEvent actionEvent) throws ValidateException {
        Nota nota=extractNota();

        Nota deleted=service.delete(nota);
        clearFields();
        if(deleted!=null){
            MessageAlert.showMessage(dialogStage, Alert.AlertType.INFORMATION,"Eliminare cu succes","Nota a fost eliminata!");
            // clearFields();
        }
        else{
            MessageAlert.showErrorMessage(dialogStage,"Nu exista un Nota cu acest id si numar!");
        }
    }

    @FXML
    private void clearNotaFields(ActionEvent actionEvent){
        clearFields();
        this.model= FXCollections.observableArrayList(service.getAllNota());
        tableViewNota.setItems(model);

    }

    private void clearFields() {
        textFieldNumeStudent.setText("");
        textFieldIdStudent.setText("");
        textFieldNumarTema.setText("");
        textFieldValoare.setText("");
    }
    private Nota extractNota() {

        String idS = textFieldIdStudent.getText();
        String idT = textFieldNumarTema.getText();
        String valoare= textFieldValoare.getText();
        int ids=Integer.valueOf(idS);
        int te=Integer.valueOf(idT);
        return  new Nota(ids,te,Integer.valueOf(valoare));
    }

    @FXML
    private void filtruNota10(ActionEvent actionEvent){
        this.model= FXCollections.observableArrayList(service.allTen());
        tableViewNota.setItems(model);
    }

    @FXML
    private void filtruNotaStudent(ActionEvent actionEvent){
        int id = Integer.parseInt(textFieldFiltruStudent.getText());
        this.model= FXCollections.observableArrayList(service.allId(id));
        tableViewNota.setItems(model);
    }

    @FXML
    private void filtruNotaTema(ActionEvent actionEvent){
        int nr = Integer.parseInt(textFieldFiltruTema.getText());
        this.model= FXCollections.observableArrayList(service.allTe(nr));
        tableViewNota.setItems(model);
    }
}
