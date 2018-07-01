package entities;

public class Nota {
    private int idStudent;
    private int numarTema;
    private int valoare;

    public Nota(int idStudent, int numarTema, int valoare){
        this.idStudent=idStudent;
        this.numarTema=numarTema;
        this.valoare=valoare;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public int getNumarTema() {
        return numarTema;
    }

    public int getValoare() {
        return valoare;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public void setNumarTema(int numarTema) {
        this.numarTema = numarTema;
    }

    public void setValoare(int valoare) {
        this.valoare = valoare;
    }
}
