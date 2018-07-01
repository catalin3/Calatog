package repository;

import entities.Tema;

public class TemaRepository extends AbstractCRUDRepository<Tema> {
    @Override
    public Tema update(int numar,Tema tema){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getNumar()==tema.getNumar()){
                list.set(i,tema);
            }
        }
        return tema;
    }
    @Override
    public Tema delete(Tema tema){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getNumar()==tema.getNumar()){
                list.remove(i);
            }
        }
        return tema;
    }
}
