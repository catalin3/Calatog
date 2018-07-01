package service;

import entities.Student;
import entities.Tema;
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

public class TemaService implements Observable<Tema>{
    private AbstractCRUDRepository<Tema> temaRepository;
    private Validator<Tema> temaValidator;
    ArrayList<Observer<Tema>> eObservers=new ArrayList<>();
    public TemaService(AbstractCRUDRepository<Tema> temaRepository, Validator<Tema> temaValidator){
        this.temaRepository=temaRepository;
        this.temaValidator=temaValidator;
    }

    public List<Tema> getAllTema(){
        return temaRepository.getList();
    }

    public Tema addStudent(Tema tema) throws ValidateException {
        temaValidator.exist(getAllTema(),tema);
        temaValidator.validate(tema);

        Tema s =temaRepository.add(tema);
        ListEvent<Tema> ev=createEvent(ListEventType.ADD,s,temaRepository.getList());
        notifyObserver(ev);
        return tema;
    }

    public Tema update(Tema tema){

        Tema updated = temaRepository.update(tema.getNumar(),tema);
        ListEvent<Tema> ev=createEvent(ListEventType.UPDATE,tema,temaRepository.getList());
        notifyObserver(ev);
        return  updated;
    }

    public Tema delete(Tema s){
        Tema nota = temaRepository.delete(s);
        ListEvent<Tema> ev = createEvent(ListEventType.REMOVE,s,temaRepository.getList());
        notifyObserver(ev);
        return nota;
    }

    @Override
    public void addObserver(Observer<Tema> o) {
        eObservers.add(o);
    }

    @Override
    public void removeObserver(Observer<Tema> o) {
        eObservers.remove(o);
    }

    @Override
    public void notifyObserver(ListEvent<Tema> event) {
        eObservers.forEach(x->x.notyfyEvent(event));
    }
    private ListEvent<Tema> createEvent(ListEventType type, final Tema el, final Iterable<Tema> l) {
        return new ListEvent<Tema>(type) {
            @Override
            public Iterable<Tema> getList() {
                return l;
            }

            @Override
            public Tema getElement() {
                return el;
            }
        };
    }

    public <E> List<E> filterAndSorter(List<E> lista, Predicate<E> p,
                                       Comparator<E> c){
        return lista.stream().filter(p).sorted(c).collect(Collectors.toList());

    }

    public List<Tema> allDeadlineAsc(int saptamana){
        Predicate<Tema> predicate= x->x.getDeadline()>saptamana;
        Comparator<Tema> comparator= (x, y)->{
            if(x.getDeadline()>y.getDeadline()){
                return 1;
            }
            else if(x.getDeadline()<y.getDeadline()){
                return -1;
            }
            else return 0;
        };
        return filterAndSorter((List)getAllTema(),predicate,comparator);
    }
    public List<Tema> allDeadlineDesc(int saptamana){
        Predicate<Tema> predicate= x->x.getDeadline()<saptamana;
        Comparator<Tema> comparator= (x,y)->{
            if(x.getDeadline()<y.getDeadline()){
                return 1;
            }
            else if(x.getDeadline()>y.getDeadline()){
                return -1;
            }
            else return 0;
        };
        return filterAndSorter((List)getAllTema(),predicate,comparator);
    }
}
