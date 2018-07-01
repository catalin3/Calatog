package service;

import entities.Nota;
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

public class NotaService implements Observable<Nota> {
    private AbstractCRUDRepository<Nota> notaRepository;
    private AbstractCRUDRepository<Student> studentRepository;
    private Validator<Nota> notaValidator;
    private ArrayList<Observer<Nota>> eObservers=new ArrayList<>();
    public NotaService(AbstractCRUDRepository<Nota> notaRepository, Validator<Nota> notaValidator,AbstractCRUDRepository<Student> studentRepository){
        this.notaRepository=notaRepository;
        this.notaValidator=notaValidator;
        this.studentRepository=studentRepository;
    }

    public List<Nota> getAllNota(){
        return notaRepository.getList();
    }

    public Nota addNota(Nota nota) throws ValidateException {
        notaValidator.exist(getAllNota(),nota);
        notaValidator.validate(nota);
        notaRepository.add(nota);
        ListEvent<Nota> ev=createEvent(ListEventType.ADD,nota,notaRepository.getList());
        notifyObserver(ev);
        return nota;
    }

    public Nota update(Nota nota){
        Nota notaupdated = notaRepository.updateNota(nota.getIdStudent(),nota.getNumarTema(),nota);
        ListEvent<Nota> ev=createEvent(ListEventType.UPDATE,nota,notaRepository.getList());
        notifyObserver(ev);
        return notaupdated;
    }

    public Nota delete(Nota s){
        Nota nota = notaRepository.delete(s);
        ListEvent<Nota> ev = createEvent(ListEventType.REMOVE,s,notaRepository.getList());
        notifyObserver(ev);
        return nota;
    }

    @Override
    public void addObserver(Observer<Nota> o) {
        eObservers.add(o);
    }

    @Override
    public void removeObserver(Observer<Nota> o) {
        eObservers.remove(o);
    }

    @Override
    public void notifyObserver(ListEvent<Nota> event) {
        eObservers.forEach(x->x.notyfyEvent(event));
    }
    private ListEvent<Nota> createEvent(ListEventType type, final Nota el, final Iterable<Nota> l) {
        return new ListEvent<Nota>(type) {
            @Override
            public Iterable<Nota> getList() {
                return l;
            }

            @Override
            public Nota getElement() {
                return el;
            }
        };
    }

    public <E> List<E> filterAndSorter(List<E> lista, Predicate<E> p,
                                       Comparator<E> c){
        return lista.stream().filter(p).sorted(c).collect(Collectors.toList());

    }

    public List<Nota> allId(int id){
        Predicate<Nota> predicate= x->x.getIdStudent()==id;
        Comparator<Nota> comparator= (x, y)->{
            if(x.getValoare()<y.getValoare()){
                return 1;
            }
            else if(x.getValoare()>y.getValoare()){
                return -1;
            }
            else return 0;
        };
        return filterAndSorter((List)getAllNota(),predicate,comparator);
    }
    public List<Nota> allTe(int nrTema){
        Predicate<Nota> predicate= x->x.getNumarTema()==nrTema;
        Comparator<Nota> comparator= (x,y)->{
            if(x.getValoare()<y.getValoare()){
                return 1;
            }
            else if(x.getValoare()>y.getValoare()){
                return -1;
            }
            else return 0;
        };
        return filterAndSorter((List)getAllNota(),predicate,comparator);
    }
    public List<Nota> allTen(){
        Predicate<Nota> predicate= x->x.getValoare()==10;
        Comparator<Nota> comparator= (x,y)->{
            if(x.getNumarTema()<y.getNumarTema()){
                return 1;
            }
            else if(x.getNumarTema()>y.getNumarTema()){
                return -1;
            }
            else return 0;
        };
        return filterAndSorter((List)getAllNota(),predicate,comparator);
    }

    public Student findStudent(int id){
        for(Student st:studentRepository.getList()){
            if(st.getId()==id)
                return st;
        }
        return null;
    }
}
