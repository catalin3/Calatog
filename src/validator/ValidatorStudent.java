package validator;

import entities.Student;

import java.util.List;

public class ValidatorStudent implements Validator<Student>{

    @Override
    public void validate(Student student) throws ValidateException {
        String msgs="";

        if(student.getId()<1){
            msgs+="Studentul nu poate avea id < 0\n";
        }
        if(student.getNume().equals("")){
            msgs+="Studentul nu poate avea numele vid\n";
        }
        if(student.getGrupa()<1){
            msgs+="Studentul poate avea grupa < 0\n";
        }
        if(student.getEmail().equals("")){
            msgs+="Studentul nu poate avea emailul vid\n";
        }
        if(student.getProfesor().equals("")){
            msgs+="Studentul nu poate avea profesor vid\n";
        }

        if(msgs!=""){
            throw new ValidateException(msgs);
        }
    }

    @Override
    public void exist(List<Student>list, Student student) throws ValidateException {
        for(Student st:list){
            if(st.getId()==student.getId()){
                throw new ValidateException("Exista deja un Student cu acest id!");
            }
        }
    }
}
