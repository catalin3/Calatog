package repository;

import entities.Nota;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCRUDRepository<T> {
    public List<T> list = new ArrayList<>();

    public int size(){
        return list.size();
    }

    public List<T> getList() {
        return list;
    }

    public T add(T t){
        list.add(t);
        return t;
    }

    public T update(int id, T t){

        return t;
    }

    public T delete(T t){
        return t;
    }



    public T updateNota(int id,int nr, T t){

        return t;
    }

}

