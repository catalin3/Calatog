package repository;

import entities.Student;

public class StudentRepository extends AbstractCRUDRepository<Student> {

    @Override
    public Student update(int id, Student student) {
        for(int i=0;i<list.size();i++){
            if(list.get(i).getId()==id){
                list.set(i,student);
            }
        }
        return student;
    }

    @Override
    public Student delete(Student st) {
        for (int i=0;i<list.size();i++){
            if(list.get(i).getId()==st.getId()){
                list.remove(i);
            }
        }
        return st;
    }

    public Student findOne(int id){
        for(Student st:getList()){
            if(st.getId()==id)
                return st;
        }
        return null;
    }
}
