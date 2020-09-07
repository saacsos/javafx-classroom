package ku.cs.classroom.models;

public class Student {
    private String id;
    private String name;
    private Score score;
    
    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        score = new Score();
    }
    
    public void addScore(ScoreType type, double score) {
        this.score.add(type, score);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Score getScore() {
        return score;
    }

    public double getTotalScore() {
        return score.totolScore();
    }

    public void updateAllScore(double assignment, double attendance, double midtermExam, double finalExam) {
        score.set(ScoreType.ASSIGNMENT, assignment);
        score.set(ScoreType.ATTENDANCE, attendance);
        score.set(ScoreType.MIDTERM_EXAM, midtermExam);
        score.set(ScoreType.FINAL_EXAM, finalExam);
    }
}
