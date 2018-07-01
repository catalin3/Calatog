package view;

import entities.Nota;
import entities.Student;
import entities.Tema;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import repository.AbstractCRUDRepository;
import repository.StudentRepository;
import repository.file.NotaFileRepository;
import repository.file.StudentFileRepository;
import repository.file.TemaFileRepository;
import sample.Main;
import service.NotaService;
import service.StudentService;
import service.TemaService;
import validator.Validator;
import validator.ValidatorNota;
import validator.ValidatorStudent;
import validator.ValidatorTema;

import java.io.IOException;

public class PrincipalTabPaneController {
    @FXML
    TabPane tabPanePrincipal;
    @FXML
    Tab tabStudent;
    @FXML
    Tab tabTema;
    @FXML
    Tab tabNota;

    public void initialize(){
       initStudentViewLayout();
       initTemaViewLayout();
       initNotaViewLayout();
    }

    private void initNotaViewLayout() {
        try {
            Validator<Nota> notaValidator = new ValidatorNota();
            AbstractCRUDRepository<Nota> notaRepository = new NotaFileRepository("Note.txt");
            AbstractCRUDRepository<Student> studentRepository = new StudentFileRepository("Studenti.txt");

            NotaService notaService = new NotaService(notaRepository,notaValidator,studentRepository);

            //Load student view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/NotaView.fxml"));
            AnchorPane centerLayout = (AnchorPane) loader.load();

            //set the service and the model for controller class
            NotaViewController notaViewController = loader.getController();
            notaViewController.setService(notaService);
            notaService.addObserver(notaViewController);
            tabNota.setContent(centerLayout);
            tabPanePrincipal.getTabs().set(2,tabNota);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    /*
    @FXML
    public void handleStudentCRUD(){
        initStudentViewLayout();
    }*/

    public void initStudentViewLayout(){
        try {
            Validator<Student> studentValidator = new ValidatorStudent();
            AbstractCRUDRepository<Student> studentRepository = new StudentFileRepository("Studenti.txt");
            //AbstractCRUDRepository<Student> studrepo=new StudentRepository();
            StudentService studentService = new StudentService(studentRepository, studentValidator);

            //Load student view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/StudentView.fxml"));
            AnchorPane centerLayout = (AnchorPane) loader.load();

            //set the service and the model for controller class
            StudentViewController studentViewController = loader.getController();
            studentViewController.setService(studentService);
            studentService.addObserver(studentViewController);
            tabStudent.setContent(centerLayout);
            tabPanePrincipal.getTabs().set(0,tabStudent);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void initTemaViewLayout(){
        try {
            Validator<Tema> temaValidator = new ValidatorTema();
            AbstractCRUDRepository<Tema> temaRepository = new TemaFileRepository("Teme.txt");
            TemaService temaService = new TemaService(temaRepository, temaValidator);

            //Load student view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/TemaView.fxml"));
            AnchorPane centerLayout = (AnchorPane) loader.load();

            //set the service and the model for controller class
            TemaViewController temaViewController = loader.getController();
            temaViewController.setService(temaService);
            temaService.addObserver(temaViewController);
            tabTema.setContent(centerLayout);
            tabPanePrincipal.getTabs().set(1,tabTema);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
