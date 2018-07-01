package validator;

import entities.Nota;

import java.util.List;

public class ValidatorNota implements Validator<Nota> {

    @Override
    public void validate(Nota nota) throws ValidateException {
        String msgs="";
        if(nota.getIdStudent()<1){
            msgs+="Nota nu poate avea id student negativ\n";
        }
        if(nota.getNumarTema()<1){
            msgs+="Nota nu poate avea numar negativ\n";
        }
        if(nota.getValoare()<1 || nota.getValoare()>10){
            msgs+="Valoarea notei trebuie sa fie de la 1 la 10\n";
        }
        if(msgs!=""){
            throw new ValidateException(msgs);
        }
    }

    @Override
    public void exist(List<Nota> list, Nota nota) throws ValidateException {
        for(Nota nt:list){
            if(nt.getIdStudent()==nota.getIdStudent()&&nt.getNumarTema()==nota.getNumarTema()) {
                throw new ValidateException("Exista deja o nota cu acelasi numar la acest student\n");
            }
        }
    }
}
