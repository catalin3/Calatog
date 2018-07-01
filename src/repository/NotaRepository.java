package repository;

import entities.Nota;

public class NotaRepository extends AbstractCRUDRepository<Nota>{


    public Nota updateNota(int id,int numar, Nota nota) {
        for(int i=0;i<list.size();i++){
            if(list.get(i).getIdStudent()==id && list.get(i).getNumarTema()==numar){
                list.set(i,nota);
            }
        }
        return nota;
    }

    @Override
    public Nota update(int id, Nota nota) {
        return super.update(id, nota);
    }

    public void deleteNota(int id, int numar) {
        for(int i=0;i<list.size();i++){
            if(list.get(i).getIdStudent()==id && list.get(i).getNumarTema()==numar){
                list.remove(i);
            }
        }
    }

    @Override
    public Nota delete(Nota nota) {
        for (int i=0;i<list.size();i++){
            if(list.get(i).getIdStudent()==nota.getIdStudent() && list.get(i).getNumarTema()==nota.getNumarTema()){
                list.remove(i);
            }
        }
        return nota;
    }
}
