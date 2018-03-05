package QBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBManager {

    private String chapter,
            question,
            text,
            choiceA,
            choiceB,
            choiceC,
            choiceD,
            choiceE,
            answerKey,
            hint,
            message;

    PreparedStatement psmt;
    Connection conn = null;

    public DBManager() {
        chapter = question
                = text
                = choiceA = choiceB = choiceC = choiceD = choiceE
                = answerKey = hint = message = null;
        initDB();
        //populateDB();
    }

    public void initDB() {
        //Load Driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Error loading jdbc driver");
            System.exit(0);
        }
        System.out.println("Driver Loaded ");

        //Connect to Database
        conn = null;
        try {

            conn = DriverManager.getConnection("jdbc:mysql://35.185.94.191/patel", "patel", "tiger");

            //conn = DriverManager.getConnection("jdbc:mysql://localhost/javabook", "root", "root");
        } catch (Exception e) {
            System.out.println("Error connecting to server");
            System.exit(0);
        }
        System.out.println("Database Connected Successfully");

    }

    //retrieve requested questions
    public Question retrieveQuestion(int chapterNo, int questionNo) {
        Question question = new Question();

        String st = "Select * from intro11equiz where chapterNo = ? and questionNo = ?";

        try {
            psmt = conn.prepareCall(st);
        } catch (Exception e) {
            message = "Error in creating prepared statement";
            return question;
        }

        ResultSet result = null;

        try {
            psmt.setInt(1, chapterNo);
            psmt.setInt(2, questionNo);
            result = psmt.executeQuery();

            if (!result.isBeforeFirst()) {
                message = "Chapter number " + chapterNo + " Question number "
                        + questionNo + "cannot be found";
            } else {
                result.next();
                question.setChapter(result.getInt(1));
                question.setQuestion(result.getInt(2));
                question.setText(result.getString(3));
                question.setChoiceA(result.getString(4));
                question.setChoiceB(result.getString(5));
                question.setChoiceC(result.getString(6));
                question.setChoiceD(result.getString(7));
                question.setChoiceE(result.getString(8));;
                question.setAnswerKey(result.getString(9));
                question.setHint(result.getString(10));

                message = "Found Question";
            }
        } catch (Exception e) {
            message = "Error in retrieval";
            return question;
        }

        return question;
    }

    //retrieve Answer questions 
    public Answer retrieveAnswer(int chapterNo, int questionNo) {
        Answer ans = new Answer();

        String st = "Select * from intro11e where chapterNo = ?"
                + " and questionNo = ?";

        try {
            psmt = conn.prepareCall(st);
        } catch (Exception e) {
            message = "Error in creating prepared statement";
            return ans;
        }

        ResultSet res = null;
        try {
            psmt.setInt(1, chapterNo);
            psmt.setInt(2, questionNo);
            res = psmt.executeQuery();

            if (!res.isBeforeFirst()) {
                message = "Chapter " + chapterNo + " Question "
                        + questionNo + "not found";
            } else {
                res.next();
                ans.setChapterNo(res.getInt(1));
                ans.setQuestionNo(res.getInt(2));
                ans.setIsCorrect(res.getInt(3));
                ans.setTimeStamp(res.getString(4));
                ans.setHostName(res.getString(5));
                ans.setChoiceA(res.getInt(6));
                ans.setChoiceB(res.getInt(7));
                ans.setChoiceC(res.getInt(8));
                ans.setChoiceD(res.getInt(9));
                ans.setChoiceE(res.getInt(10));;
                ans.setUserName(res.getString(11));

                message = "Answer found";
            }
        } catch (Exception e) {
            message = "Error in retrieval";
            return ans;
        }

        return ans;
    }

    //this will update user's submission to database 
    public void updateAnswer(Answer a) {
        String db = "Insert into intro11e (isCorrect, hostname, answerA, "
                + "answerB, answerC, answerD, answerE, chapterNo, questionNo,"
                + " username) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        Statement stmt = null;
        ResultSet result = null;

        try {
            psmt = conn.prepareCall(db);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Error in preparing statement";
        }

        try {

            //This will check if chapter no and question no can be retrieve
            psmt = conn.prepareStatement(db);

            //Update the table with the user answer
            psmt.setInt(1, a.getIsCorrect());
            psmt.setString(2, a.getHostName());
            psmt.setInt(3, a.getChoiceA());
            psmt.setInt(4, a.getChoiceB());
            psmt.setInt(5, a.getChoiceC());
            psmt.setInt(6, a.getChoiceD());
            psmt.setInt(7, a.getChoiceE());
            psmt.setInt(8, a.getChapterNo());
            psmt.setInt(9, a.getQuestionNo());
            psmt.setString(10, "patel");

            psmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
            message = "an error occured in execution, try again";
        }
    }
}
