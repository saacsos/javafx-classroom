package ku.cs.classroom.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ku.cs.classroom.models.ScoreType;
import ku.cs.classroom.models.Student;
import ku.cs.classroom.models.StudentList;
import ku.cs.classroom.services.StringConfiguration;
import ku.cs.classroom.services.StudentHardcodeDataSource;

import java.util.ArrayList;

public class ScoreController {

    private StudentList students;
    private StudentHardcodeDataSource dataSource;
    private Student selectedStudent;
    private ObservableList<Student> studentObservableList;

    @FXML private TableView<Student> studentsTable;
    @FXML private Label sidLabel;
    @FXML private Label nameLabel;
    @FXML private TextField assignmentScoreTextField;
    @FXML private TextField attendanceScoreTextField;
    @FXML private TextField midtermScoreTextField;
    @FXML private TextField finalScoreTextField;
    @FXML private Button updateScoreButton;

    @FXML
    public void initialize() {
        updateScoreButton.setDisable(true);
        dataSource = new StudentHardcodeDataSource();
        students = dataSource.getStudentsData();
        students.findById("6210400002").addScore(ScoreType.ASSIGNMENT, 10);
        showStudentData();

        studentsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedStudent(newValue);
            }
        });
    }

    private void showStudentData() {
        studentObservableList = FXCollections.observableArrayList(students.toList());
        studentsTable.setItems(studentObservableList);

        ArrayList<StringConfiguration> configs = new ArrayList<>();
        configs.add(new StringConfiguration("title:Student ID", "field:id"));
        configs.add(new StringConfiguration("title:Name", "field:name"));
        configs.add(new StringConfiguration("title:Total Score", "field:totalScore"));

        for (StringConfiguration conf: configs) {
            TableColumn col = new TableColumn(conf.get("title"));
            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
            studentsTable.getColumns().add(col);
        }
    }

    private void showSelectedStudent(Student student) {
        selectedStudent = student;
        sidLabel.setText(student.getId());
        nameLabel.setText(student.getName());
        assignmentScoreTextField.setText(String.valueOf(student.getScore().get(ScoreType.ASSIGNMENT)));
        attendanceScoreTextField.setText(String.valueOf(student.getScore().get(ScoreType.ATTENDANCE)));
        midtermScoreTextField.setText(String.valueOf(student.getScore().get(ScoreType.MIDTERM_EXAM)));
        finalScoreTextField.setText(String.valueOf(student.getScore().get(ScoreType.FINAL_EXAM)));
        updateScoreButton.setDisable(false);
    }

    private void clearSelectedStudent() {
        selectedStudent = null;
        sidLabel.setText("...");
        nameLabel.setText("...");
        assignmentScoreTextField.clear();
        attendanceScoreTextField.clear();
        midtermScoreTextField.clear();
        finalScoreTextField.clear();
        updateScoreButton.setDisable(true);
    }

    @FXML
    public void handleUpdateButton(ActionEvent event) {
        double inputAssignment = Double.parseDouble(assignmentScoreTextField.getText());
        double inputAttendance = Double.parseDouble(attendanceScoreTextField.getText());
        double inputMidterm = Double.parseDouble(midtermScoreTextField.getText());
        double inputFinal = Double.parseDouble(finalScoreTextField.getText());
        selectedStudent.updateAllScore(inputAssignment, inputAttendance, inputMidterm, inputFinal);
        clearSelectedStudent();
        studentsTable.refresh();
        studentsTable.getSelectionModel().clearSelection();
    }
}
