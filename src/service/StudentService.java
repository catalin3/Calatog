package service;

import entities.Student;
import repository.AbstractCRUDRepository;
import utils.ListEvent;
import utils.ListEventType;
import utils.Observable;
import utils.Observer;
import validator.ValidateException;
import validator.Validator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentService implements Observable<Student> {
    private AbstractCRUDRepository<Student> studentRepository;
    private Validator<Student> studentValidator;
    ArrayList<Observer<Student>> eObservers=new ArrayList<>();
    public StudentService(AbstractCRUDRepository<Student> studentRepository, Validator<Student> studentValidator){
        this.studentRepository=studentRepository;
        this.studentValidator=studentValidator;
    }

    public List<Student> getAllStudent(){
        return studentRepository.getList();
    }

    public Student addStudent(Student student) throws ValidateException {
        studentValidator.exist(getAllStudent(),student);
        studentValidator.validate(student);
        Student s =studentRepository.add(student);
        ListEvent<Student> ev=createEvent(ListEventType.ADD,s,studentRepository.getList());
        notifyObserver(ev);
        return student;
    }

    public Student update( Student s){

        Student student = studentRepository.update(s.getId(),s);
        ListEvent<Student> ev=createEvent(ListEventType.UPDATE,s,studentRepository.getList());
        notifyObserver(ev);
        return  student;
    }

    public Student delete(Student s){
        Student student = studentRepository.delete(s);
        ListEvent<Student> ev = createEvent(ListEventType.REMOVE,s,studentRepository.getList());
        notifyObserver(ev);
        return student;
    }

    @Override
    public void addObserver(Observer<Student> o) {
    eObservers.add(o);
    }

    @Override
    public void removeObserver(Observer<Student> o) {
        eObservers.remove(o);
    }

    @Override
    public void notifyObserver(ListEvent<Student> event) {
    eObservers.forEach(x->x.notyfyEvent(event));
    }
    private ListEvent<Student> createEvent(ListEventType type, final Student el, final Iterable<Student> l) {
        return new ListEvent<Student>(type) {
            @Override
            public Iterable<Student> getList() {
                return l;
            }

            @Override
            public Student getElement() {
                return el;
            }
        };
    }

    public <E> List<E> filterAndSorter(List<E> lista, Predicate<E> p,
                                       Comparator<E> c){
        return lista.stream().filter(p).sorted(c).collect(Collectors.toList());

    }

    public List<Student> allNameAsc(String nume){
        Predicate<Student> predicate= x->x.getNume().equals(nume);
        Comparator<Student> comparator= (x,y)->{
            if(x.getGrupa()>y.getGrupa()){
                return 1;
            }
            else if(x.getGrupa()<y.getGrupa()){
                return -1;
            }
            else return 0;
        };
        return filterAndSorter((List)getAllStudent(),predicate,comparator);
    }
    public List<Student> allGrupaAsc(int gr){
        Predicate<Student> predicate= x->x.getGrupa()==gr;
        Comparator<Student> comparator= (x,y)->{
            return  x.getNume().compareTo(y.getNume());
        };
        return filterAndSorter((List)getAllStudent(),predicate,comparator);
    }
    public List<Student> allCadruAsc(String nume){
        Predicate<Student> predicate= x->x.getProfesor().equals(nume);
        Comparator<Student> comparator= (x,y)->{
            return  x.getProfesor().compareTo(y.getProfesor());
        };
        return filterAndSorter((List)getAllStudent(),predicate,comparator);
    }


}
