package repository.file;

import entities.Tema;
import repository.TemaRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TemaFileRepository extends TemaRepository{
    public TemaFileRepository(String file_name) {
        super();
        file_name = file_name;
        load();
    }

    private void load() {
        Path path = Paths.get("./Teme.txt");
        Stream<String> lines;
        try {
            lines = Files.lines(path);
            lines.forEach(s -> {
                        String[] l = s.split(",");
                Tema tema = new Tema(Integer.parseInt(l[0]),l[1],Integer.parseInt(l[2]));

                        super.add(tema);
                    }

            );

        } catch (IOException e) {
            System.out.println("Nu se poate citi din fisierul cu teme\n");
        }

    }

    private void writeFile() {
        try {
            PrintWriter bw = new PrintWriter(new FileWriter("Teme.txt"));
            for (Tema elem : getList())
                bw.println(elem.getNumar() + "," + elem.getDescriere() + "," + elem.getDeadline());
            bw.close();
        } catch (IOException e) {
            System.out.println("Nu se poate scrie in fisierul Teme.txt\n");
        }
    }

    public Tema add(Tema tema) {
        super.add(tema);
        writeFile();
        return tema;
    }

    public Tema update(int numar, Tema st) {
        super.update(numar, st);
        writeFile();
        return st;
    }

    public Tema delete(Tema tema) {
        super.delete(tema);
        writeFile();
        return tema;
    }
}
