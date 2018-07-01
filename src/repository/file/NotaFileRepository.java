package repository.file;

import entities.Nota;
import repository.NotaRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NotaFileRepository extends NotaRepository{
    public NotaFileRepository(String file_name) {
        super();
        file_name = file_name;
        load();
    }

    private void load() {
        Path path = Paths.get("./Note.txt");
        Stream<String> lines;
        try {
            lines = Files.lines(path);
            lines.forEach(s -> {
                        String[] l = s.split(",");
                        Nota nota = new Nota(Integer.parseInt(l[0]),Integer.parseInt(l[1]),Integer.parseInt(l[2]));

                        super.add(nota);
                    }

            );

        } catch (IOException e) {
            System.out.println("Nu se poate citi din fisierul cu note\n");
        }

    }

    private void writeFile() {
        try {
            PrintWriter bw = new PrintWriter(new FileWriter("Note.txt"));
            for (Nota elem : getList())
                bw.println(elem.getIdStudent() + "," + elem.getNumarTema() + "," + elem.getValoare());
            bw.close();
        } catch (IOException e) {
            System.out.println("Nu se poate scrie in fisierul Note.txt\n");
        }
    }

    public Nota add(Nota nota) {
        super.add(nota);
        writeFile();
        return nota;
    }

    public Nota delete(Nota nota) {
        super.delete(nota);
        writeFile();
        return  nota;
    }

    public Nota updateNota(int idStudent,int numar, Nota nota) {
        super.updateNota(idStudent,numar,nota);
        writeFile();
        return nota;
    }


}
