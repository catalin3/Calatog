package entities;

public class Student {
    private int id;
    private String nume;
    private int grupa;
    private String email;
    private String profesor;

    public Student(int id, String nume, int grupa, String email, String profesor){
        this.id=id;
        this.nume=nume;
        this.grupa=grupa;
        this.email=email;
        this.profesor=profesor;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public int getGrupa() {
        return grupa;
    }

    public String getEmail() {
        return email;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    @Override
    public String toString() {
        return ""+getId()+" "+getNume()+" "+getGrupa()+" "+getEmail()+" "+getProfesor()+"\n";
    }
}
