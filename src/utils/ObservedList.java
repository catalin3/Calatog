package utils;

import java.util.ArrayList;
import java.util.List;

public class ObservedList<E> extends ArrayList<E> implements Observable<E>{
    protected List<Observer<E>> observers=new ArrayList<Observer<E>>();

    @Override
    public void addObserver(Observer<E> o) {
    observers.add(o);
    }

    @Override
    public void removeObserver(Observer<E> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObserver(ListEvent<E> event) {
        for(Observer o:observers)
            o.notyfyEvent(event);
    }

    @Override
    public boolean add(E e){
        boolean ret=super.add(e);
        ListEvent<E> event =createEvent(ListEventType.ADD,e);
        notifyObserver(event);
        return ret;
    }

    @Override
    public E set(int index,E e){
        E ret =super.set(index,e);
        ListEvent<E> event=createEvent(ListEventType.UPDATE,e);
        notifyObserver(event);
        return ret;
    }

    @Override
    public E remove(int index){
        E ret=super.remove(index);
        ListEvent<E> event=createEvent(ListEventType.REMOVE,ret);
        notifyObserver(event);
        return  ret;
    }
    private ListEvent<E> createEvent(ListEventType type, final E e) {
        return new ListEvent<E>(type) {
            @Override
            public ObservedList<E> getList() {
                return ObservedList.this;

            }
            @Override
            public E getElement(){
                return e;
            }
        };
    }

   /* @Override
    public void notifyObservers(ListEvent<E> event) {

    }*/
}
