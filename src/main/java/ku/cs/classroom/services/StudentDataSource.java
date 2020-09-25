package ku.cs.classroom.services;

import ku.cs.classroom.models.StudentList;

public interface StudentDataSource {
    StudentList getStudentsData();

    void setStudentsData(StudentList students);
}
