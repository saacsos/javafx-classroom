package ku.cs.classroom.services;

import ku.cs.classroom.models.Student;
import ku.cs.classroom.models.StudentList;

public class StudentHardcodeDataSource {
    public StudentList getStudentsData() {
        StudentList students = new StudentList();
        students.add(new Student("6210400001", "Poirot"));
        students.add(new Student("6210400002", "Marple"));
        students.add(new Student("6210400003", "Maigret"));
        students.add(new Student("6210400004", "Morgan"));
        students.add(new Student("6210400005", "ชื่อไทย"));

        return students;
    }
}
