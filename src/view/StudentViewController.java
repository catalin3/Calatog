package view;

import entities.Student;
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
import service.StudentService;
import utils.ListEvent;
import utils.Observer;
import validator.ValidateException;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class StudentViewController implements Observer<Student> {
    @FXML
    private TableView<Student> tableViewStudent=new TableView<Student>();;
    @FXML
    private TableColumn<Student,String> idColumn;
    @FXML
    private TableColumn<Student,String> numeColumn;
    @FXML
    private TableColumn<Student,String> grupaColumn;
    @FXML
    private TableColumn<Student,String> emailColumn;
    @FXML
    private TableColumn<Student,String> profesorColumn;

    @FXML
    TextField textFieldId;
    @FXML
    TextField textFieldNume;
    @FXML
    TextField textFieldGrupa;
    @FXML
    TextField textFieldEmail;
    @FXML
    TextField textFieldProfesor;
    @FXML
    TextField textFieldFiltruNume;
    @FXML
    TextField textFieldFiltruGrupa;
    @FXML
    TextField textFieldFiltruProfesor;



    StudentService service;
    ObservableList<Student> model;
    Stage dialogStage;
    Student student;

    public StudentViewController(){

    }

    public void setService(StudentService service){
        this.service=service;
        this.model= FXCollections.observableArrayList(service.getAllStudent());
        tableViewStudent.setItems(model);
        tableViewStudent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                if(tableViewStudent.getSelectionModel().getSelectedItem()!=null){
                    Student s=tableViewStudent.getSelectionModel().getSelectedItem();
                    setFields(s);
                }
            }
        });
    }



    @FXML
    private void initialize(){
        idColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("Id"));
        numeColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("Nume"));
        grupaColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("Grupa"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("Email"));
        profesorColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("Profesor"));

    }


    @FXML
    private void saveStudent(ActionEvent actionEvent){
        Student s=extractStudent();
        try {
            Student saved=service.addStudent(s);
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
    private void updateStudent(ActionEvent actionEvent) throws ValidateException {
        Student s=extractStudent();

        Student updated=service.update(s);
        if(updated!=null){
            MessageAlert.showMessage(dialogStage, Alert.AlertType.INFORMATION,"Modificare cu succes","Studentul a fost modificat!");
            // clearFields();
        }
        else{
            MessageAlert.showErrorMessage(dialogStage,"Nu exista un Student cu acest id!");
        }

    }

    @FXML
    private void deleteStudent(ActionEvent actionEvent) throws ValidateException {
        Student student=extractStudent();

        Student deleted=service.delete(student);
        clearFields();
        if(deleted!=null){
            MessageAlert.showMessage(dialogStage, Alert.AlertType.INFORMATION,"Eliminare cu succes","Studentul a fost eliminat!");
            // clearFields();
        }
        else{
            MessageAlert.showErrorMessage(dialogStage,"Nu exista un Student cu acest id!");
        }
    }

    @FXML
    private void clearStudentFields(ActionEvent actionEvent){
        clearFields();
        this.model= FXCollections.observableArrayList(service.getAllStudent());
        tableViewStudent.setItems(model);
    }

    private void clearFields(){
        textFieldId.setText("");
        textFieldNume.setText("");
        textFieldGrupa.setText("");
        textFieldEmail.setText("");
        textFieldProfesor.setText("");
    }

    private Student extractStudent() {

        String nume = textFieldNume.getText();
        String grupa=textFieldGrupa.getText();
        String email=textFieldEmail.getText();
        String profesor=textFieldProfesor.getText();
        String id= textFieldId.getText();
        int ids=Integer.valueOf(id);
        int grupas=Integer.valueOf(grupa);
        return  new Student(ids,nume,grupas,email,profesor);
    }


    private void setFields(Student s)
    {
        //Student s=tableViewStudent.getSelectionModel().getSelectedItem();
        textFieldId.setText(Integer.toString(s.getId()));
        textFieldNume.setText(s.getNume());
        textFieldGrupa.setText(Integer.toString(s.getGrupa()));
        textFieldEmail.setText(s.getEmail());
        textFieldProfesor.setText(s.getProfesor());

    }

    public TableView<Student> getTableView() {
        tableViewStudent.setItems(model);
        return tableViewStudent;


    }

    @FXML
    private void filterNume(ActionEvent actionEvent){
        String nume = textFieldFiltruNume.getText();
        this.model= FXCollections.observableArrayList(service.allNameAsc(nume));
        tableViewStudent.setItems(model);
    }

    @FXML
    private void filterGrupa(ActionEvent actionEvent){
        Integer grupa = Integer.parseInt(textFieldFiltruGrupa.getText());
        this.model= FXCollections.observableArrayList(service.allGrupaAsc(grupa));
        tableViewStudent.setItems(model);
    }

    @FXML
    private void filterProfesor(ActionEvent actionEvent){
        String profesor = textFieldFiltruProfesor.getText();
        this.model= FXCollections.observableArrayList(service.allCadruAsc(profesor));
        tableViewStudent.setItems(model);
    }



    @Override
    public void notyfyEvent(ListEvent<Student> e) {
    tableViewStudent=getTableView();
    model.setAll(StreamSupport.stream(e.getList().spliterator(),false).collect(Collectors.toList()));
    }
}
