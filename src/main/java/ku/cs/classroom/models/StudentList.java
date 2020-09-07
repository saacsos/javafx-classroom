package ku.cs.classroom.models;

import java.util.ArrayList;

public class StudentList {
    private ArrayList<Student> students;

    public StudentList() {
        students = new ArrayList<>();
    }

    public void add(Student student) {
        students.add(student);
    }

    public Student findById(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        throw new RuntimeException("Student with id " + id + " not found");
    }

    public double getStudentScore(String id, ScoreType type) {
        Student student = findById(id);
        return student.getScore().get(type);
    }

    public double getStudentTotalScore(String id) {
        Student student = findById(id);
        return student.getTotalScore();
    }

    public ArrayList<Student> toList() {
        return students;
    }
}
