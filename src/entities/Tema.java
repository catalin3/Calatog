package entities;

public class Tema {
    private int numar;
    private String descriere;
    private int deadline;

    public Tema(int numar, String descriere, int deadline){
        this.numar=numar;
        this.descriere=descriere;
        this.deadline=deadline;
    }

    public int getNumar() {
        return numar;
    }

    public String getDescriere() {
        return descriere;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }
}
