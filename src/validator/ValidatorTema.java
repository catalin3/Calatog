package validator;

import entities.Tema;

import java.util.List;

public class ValidatorTema implements Validator<Tema> {
    @Override
    public void validate(Tema tema) throws ValidateException {
        String msgs="";
        if(tema.getNumar()<=0){
            msgs+="Numarul temei nu poate fi <= 0\n";
        }
        if(tema.getDescriere().equals("")){
            msgs+="Descrierea temei nu poate fi vida\n";
        }
        if(tema.getDeadline()<1){
            msgs+="Deadlineul nu poate fi < saptamana 1\n";
        }
        if(msgs!=""){
            throw new ValidateException(msgs);
        }
    }

    @Override
    public void exist(List<Tema> list, Tema tema) throws ValidateException {
        for (Tema tm:list){
            if(tm.getNumar()==tema.getNumar()){
                throw new ValidateException("Exista deja o tema cu acest numar");
            }
        }
    }
}
