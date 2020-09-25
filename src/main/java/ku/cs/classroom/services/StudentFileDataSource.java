package ku.cs.classroom.services;

import ku.cs.classroom.models.ScoreType;
import ku.cs.classroom.models.Student;
import ku.cs.classroom.models.StudentList;

import java.io.*;

public class StudentFileDataSource implements StudentDataSource{

    private String fileDirectoryName;
    private String fileName;
    private StudentList students;

    public StudentFileDataSource(String fileDirectoryName, String fileName) {
        this.fileDirectoryName = fileDirectoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(fileDirectoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = fileDirectoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create " + filePath);
            }
        }
    }

    private void readData() throws IOException {
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line = "";
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            Student student = new Student(data[0].trim(), data[1].trim());
            student.addScore(ScoreType.ASSIGNMENT, Double.parseDouble(data[2]));
            student.addScore(ScoreType.ATTENDANCE, Double.parseDouble(data[3]));
            student.addScore(ScoreType.MIDTERM_EXAM, Double.parseDouble(data[4]));
            student.addScore(ScoreType.FINAL_EXAM, Double.parseDouble(data[5]));
            students.add(student);
        }
        reader.close();
    }

    @Override
    public StudentList getStudentsData() {
        try {
            students = new StudentList();
            readData();
        } catch (FileNotFoundException e) {
            System.err.println(this.fileName + " not found");
        } catch (IOException e) {
            System.err.println("IOException from reading " + this.fileName);
        }
        return students;
    }

    @Override
    public void setStudentsData(StudentList students) {
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (Student student: students.toList()) {
                String line = student.getId() + ","
                        + student.getName() + ","
                        + student.getScore().get(ScoreType.ASSIGNMENT) + ","
                        + student.getScore().get(ScoreType.ATTENDANCE) + ","
                        + student.getScore().get(ScoreType.MIDTERM_EXAM) + ","
                        + student.getScore().get(ScoreType.FINAL_EXAM);
                writer.append(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot write " + filePath);
        }
    }
}
