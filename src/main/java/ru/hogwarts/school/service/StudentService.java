package ru.hogwarts.school.service;

import com.sun.tools.javac.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student findStudent(long id) {
        logger.info("Was invoked method for find student {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student {}", student);
        return studentRepository.save(student);
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student {}", student);
        return studentRepository.save(student);
    }

    public void removeStudent(long id) {
        logger.info("Was invoked method for remove student {}", id);
        studentRepository.deleteById(id);
    }

    public void removeAllStudent() {
        logger.info("Was invoked method for remove student {}");
        studentRepository.deleteAll();
    }

    public Collection<Student> filterAge(Integer age) {
        logger.info("Was invoked method for filter student by age {}", age);
        ArrayList<Student> listStudentByAge = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            if (student.getAge() == age) {
                listStudentByAge.add(student);
            }
        }
        return listStudentByAge;
    }

    public Collection<Student> findByAgeBetween(Integer minAge, Integer maxAge) {
        logger.info("Was invoked method for filter student by between {} and {}", minAge, maxAge);
        ArrayList<Student> listStudentByAge = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            if (student.getAge() > minAge && student.getAge() < maxAge) {
                listStudentByAge.add(student);
            }
        }
        return listStudentByAge;
    }

    public Integer getAmountOfAllStudents() {
        logger.info("Was invoked method for get all student");
        return studentRepository.getAmountOfAllStudents();
    }

    public Integer getAverageAgeByStudent() {
        logger.info("Was invoked method for get average age student");
        return studentRepository.getAverageAgeByStudent();
    }

    public List<Student> getLastStudent() {
        logger.info("Was invoked method for get last student");
        return studentRepository.getLastStudent();
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public List<Student> findAllStudentWithTheLetterA() {
        List<Student> allStudentWithLetterA = findAll().stream()
                .parallel()
                .filter(a -> a.getName().toUpperCase().startsWith("A"))
                .sorted(Comparator.comparing(s -> s.getName()))
                .collect(Collectors.toList());
        return allStudentWithLetterA;
    }

    public double findAverageAgeByStudent() {
        return findAll().stream()
                .mapToInt(a -> a.getAge())
                .average().orElse(0);
    }

    @Transactional
    public void printParallelAllStudent() {
        List<Student> all = studentRepository.findAll().stream()
                .sorted(Comparator.comparing(Student::getId))
                .limit(6)
                .toList();
        printStudent(all.get(0));
        printStudent(all.get(1));
        new Thread(() -> {

            printStudent(all.get(2));
            printStudent(all.get(3));

        }).start();
        new Thread(() -> {
            printStudent(all.get(4));
            printStudent(all.get(5));
        }).start();
    }

    @Transactional
    public void printSynchronized() {
        Queue<Student> all = new LinkedList<>(studentRepository.findAll().stream()
                .sorted(Comparator.comparing(Student::getId))
                .limit(6)
                .toList());
        printStudentSynchronized(all.poll());
        printStudentSynchronized(all.poll());
        new Thread(() -> {

            printStudentSynchronized(all.poll());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            printStudentSynchronized(all.poll());

        }).start();
        new Thread(() -> {
            printStudentSynchronized(all.poll());
            printStudentSynchronized(all.poll());
        }).start();
    }

    private synchronized void printStudentSynchronized(Student student) {
        System.out.println(student);
    }

    private void printStudent(Student student) {
        System.out.println(student);
    }
}
