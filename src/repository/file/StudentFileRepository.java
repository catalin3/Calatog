package repository.file;

import entities.Student;
import repository.StudentRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class StudentFileRepository extends StudentRepository {
    public StudentFileRepository(String file_name) {
        super();
        file_name = file_name;
        load();
    }

    private void load() {
        Path path = Paths.get("./Studenti.txt");
        Stream<String> lines;
        try {
            lines = Files.lines(path);
            lines.forEach(s -> {
                        String[] l = s.split(",");
                        Student studenti = new Student(Integer.parseInt(l[0]),l[1],Integer.parseInt(l[2]),l[3],l[4]);

                        super.add(studenti);
                    }

            );

        } catch (IOException e) {
            System.out.println("Nu se poate citi din fisierul cu studenti\n");
        }
    }

    private void writeFile() {
        try {
            PrintWriter bw = new PrintWriter(new FileWriter("Studenti.txt"));
            for (Student elem : getList())
                bw.println(elem.getId() + "," + elem.getNume() + "," + elem.getGrupa() + "," + elem.getEmail() + "," + elem.getProfesor());
            bw.close();
        } catch (IOException e) {
            System.out.println("Nu se poate scrie in fisierul Studenti.txt\n");
        }
    }

    public Student add(Student student) {
        super.add(student);
        writeFile();
        return student;
    }

    public Student update(int idStudent, Student st) {
        super.update(idStudent, st);
        writeFile();
        return st;
    }

    public Student delete(Student st) {
        super.delete(st);
        writeFile();
        return st;
    }
}
