package QBean;

import java.util.Locale;

public class QuestionBean {
    private int chapterNo = 1,
            questionNo = 1;
    private String title,
            output, 
            mode,
            selected;
    private String[] checked;
    DBManager dbManager;
    
    
    public QuestionBean(){
        chapterNo = 1; 
        questionNo = 1;
        title = " ";
        output = "";
        mode = "Display";
        dbManager = new DBManager();
    }
    
    public void displayPage(){
       output = "";
        Question question = dbManager.retrieveQuestion(chapterNo, questionNo);
        
        
        String hint = question.getHint();
        String key = question.getAnswerKey();
        
        
        if (question.getAnswerKey() == null){
            output = "<font color=\"red\"> Can not find requested question <font/>";
        } else {
            displayQuestion(question);
            displayChoices(question);
            
            //This will display "Check My Answer"
            if (mode.equals("Display")){
                output = output + "<br /><input type=\"submit\" name=\"mode\" value=\"Check My Answer\" style=\"background-color : green\">";
            }
            
            // This will grade the answer. Print if its correct or incorrect, explanation, and Get Statistics button
            else if (mode.equals("Check My Answer")){                              
                try{
                    if (checked.length > 0){
                    Answer answer = displayAnswer(question);
                    
                    //Correct answer output and display correct answer image 
                    if (answer.getIsCorrect() == 1){
                        output = output + "<br /><font color=\"green\"><p>Your answer " + key.toUpperCase() + " is correct</font>";
                        
                        output = output + "<br><span ><img src=\"correctanswer.PNG\" border=\"0\" width=\"42\" height=\"30\"</img></span></p>";
                        }
                    
                    //Incorrect answer output and display Incorrect answer image
                    if (answer.getIsCorrect() == 0){
                        String answers = "";
                        
                        for (String s : checked){
                            switch (s){
                                case "0" : answers += "A"; break;
                                case "1" : answers += "B"; break;
                                case "2" : answers += "C"; break;
                                case "3" : answers += "D"; break;
                                case "4" : answers += "E"; break;    
                            }
                        }
                        
                        output = output + "<br/><font color=\"red\"><p>Your answer " + answers + " is incorrect </font>";
                        
                        output = output + "<br><span ><img src=\"wronganswer.PNG\" border=\"0\" width=\"42\" height=\"30\"</img></span>";
                        
                        output = output + "<br/><font color=\"black\"><p>The correct answer is "+ key.toUpperCase(Locale.ITALY) + " </font></p>";
                        
                        if (hint.equals("NULL")){
                            
                        } else {
                            output = output + "<br /><font color=\"blue\">Explanation: "+ hint + " </font></p>";
                        }
                         //body = output + "<br /><font color=\"blue\"><p>Explanation: "+ hint + " </font>";
                       // output = output + "<font color=\"green\"><p>Click here to show the correct answer</p></font>";
                    }
                }
                } catch (Exception e){
                    //If the user did not select answer it will outout did not answer with image.
                    output = output + "<br /><p>You didn't answer this question";
                    output = output + "<br><span ><img src=\"noanswer.PNG\" border=\"0\" width=\"42\" height=\"30\"</img></span>";
                    output = output + "<br/><font color=\"black\"><p>The correct answer is "+ key.toUpperCase(Locale.ITALY) + " </font></p>";
                    
                    
                    //output = output + "<font color=\"green\"><p>Click here to show the correct answer</p></font>";
                    
                }
                
                //print Statistics button
                output = output + "<input type=\"button\" value=\"Get Statistics\" style=\"background-color : green\">";
            }
            
        }
    }
    
    
    //this will display question from the database
    private void displayQuestion(Question q){
        String line = q.getText();
        String first = line.split("\n")[0];
        String rest = line.substring(first.length());

        
        output = output  + first + "<br />";
        if (!rest.isEmpty()) output += "<pre><code class='java'>"+rest+"</code></pre>";
        
        
        //output = output + "<pre><code class='java'>"+q.getText()+"</code></pre>";
    }
    
    
    
    private void displayChoices(Question question){
        //Checkbox display
        char letter = 'A';
        
        if (question.getAnswerKey().length() > 1){
            String[] choices = {question.getChoiceA(), question.getChoiceB(), question.getChoiceC(), question.getChoiceD(), question.getChoiceE()};
            for (int i = 0; i < choices.length; i++){
                
                //If choice is not null, print the choice
                if (!choices[i].equals("NULL")){   // && !choices[i].equals(null)){
                    
                output = output +  "" + letter + ". " + "<input type=\"checkbox\" name=\"choices\" value=\"" + i +"\"> " + choices[i] + "<br />";
                
                
                
//                output = output + ("                            <div id = \"choicemargin\">");
//                              output = output +           ("                                <input name=\"choices\" type=\"checkbox\" + " + letter + ">");
//                                       output = output + ("                                <span id=\"choicelabel\">" + letter + ".</span> <span id=\"choicestatement\">" + choices[i] + "</span>");
//                                        output = output + ("                            </div>");
//                
                letter = (char) (((int) letter) + 1);
                }
            }
        }
        
        
        

        //Radio display
        if (question.getAnswerKey().length() == 1){
            String[] choices = {question.getChoiceA(), question.getChoiceB(), question.getChoiceC(), question.getChoiceD(), question.getChoiceE()};
            
            for (int i = 0; i < choices.length; i++){
                //If choice is not null, print the choice
                if (!choices[i].equals("NULL")){// && !s[i].equals(null)){
                    
              output = output +  "" + letter + ". " +   "<input type=\"radio\" name=\"choices\" value=\"" + i +"\"> " + choices[i] + "<br />";
                
//               output = output + ("                            <div id = \"choicemargin\">");
//               output = output +           ("                                <input name=\"choices\" type=\"radio\" + " + letter + ">");
//               output = output + ("                                <span id=\"choicelabel\">" + letter + ".</span> <span id=\"choicestatement\">" + choices[i] + "</span>");
//               output = output + ("                            </div>");
//                
               letter = (char) (((int) letter) + 1);
                }
            }
        }
        
    }
    
    
    
    
    private Answer displayAnswer(Question q){
        Answer answer = dbManager.retrieveAnswer(chapterNo, questionNo);
            answer.setChoiceA(0);
            answer.setChoiceB(0);
            answer.setChoiceC(0);
            answer.setChoiceD(0);
            answer.setChoiceE(0);
            answer.setChapterNo(chapterNo);
            answer.setQuestionNo(questionNo);
                
            //Set user answers
            for (String temp : checked){
                switch (temp){
                    case "0" : answer.setChoiceA(1); break;
                    case "1" : answer.setChoiceB(1); break;
                    case "2" : answer.setChoiceC(1); break;
                    case "3" : answer.setChoiceD(1); break;
                    case "4" : answer.setChoiceE(1); break;
                }
            }                   
                    
            //Check correctness
            String key = q.getAnswerKey();
            String answers = "";
            boolean correct = true;
            for (String temp1 : checked){
                answers += temp1;
            }
               
            //Different lengths means user answer is incorrect
            if (key.length() != answers.length()) correct = false;
            else {
                if (key.contains("a") && 
                        !answers.contains("0")) correct = false;
                if (key.contains("b") &&
                        !answers.contains("1")) correct = false;
                if (key.contains("c") && 
                        !answers.contains("2")) correct = false;
                if (key.contains("d") &&
                        !answers.contains("3")) correct = false;
                if (key.contains("e") &&
                        !answers.contains("4")) correct = false;
            }
                
            if (correct) answer.setIsCorrect(1);
            else answer.setIsCorrect(0);
                  
            dbManager.updateAnswer(answer);
            return answer;
    }

    
    
    
    public int getChapterNo() {
        return chapterNo;
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public String getTitle() {
        return title;
    }

    public String getOutput() {
        return output;
    }

    public String getMode() {
        return mode;
    }

    public String getSelected() {
        return selected;
    }

    public String[] getChecked() {
        return checked;
    }

    public DBManager getDbManager() {
        return dbManager;
    }

    public void setChapterNo(int chapterNo) {
        this.chapterNo = chapterNo;
    }

    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public void setChecked(String[] checked) {
        this.checked = checked;
    }

    public void setDbManager(DBManager dbManager) {
        this.dbManager = dbManager;
    }
}

//answeer class
class Answer {
    private int chapterNo, questionNo, isCorrect, choiceA, choiceB, choiceC, choiceD, choiceE;
    private String timeStamp, hostName, userName;
    
    Answer(){
        chapterNo = questionNo = isCorrect = choiceA = choiceB = choiceC = choiceD = choiceE = 0;
        timeStamp = hostName = userName = "";
    }

    public int getChapterNo() {
        return chapterNo;
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public int getIsCorrect() {
        return isCorrect;
    }

    public int getChoiceA() {
        return choiceA;
    }

    public int getChoiceB() {
        return choiceB;
    }

    public int getChoiceC() {
        return choiceC;
    }

    public int getChoiceD() {
        return choiceD;
    }

    public int getChoiceE() {
        return choiceE;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getHostName() {
        return hostName;
    }

    public String getUserName() {
        return userName;
    }

    public void setChapterNo(int chapterNo) {
        this.chapterNo = chapterNo;
    }

    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }

    public void setChoiceA(int choiceA) {
        this.choiceA = choiceA;
    }

    public void setChoiceB(int choiceB) {
        this.choiceB = choiceB;
    }

    public void setChoiceC(int choiceC) {
        this.choiceC = choiceC;
    }

    public void setChoiceD(int choiceD) {
        this.choiceD = choiceD;
    }

    public void setChoiceE(int choiceE) {
        this.choiceE = choiceE;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}





//Question class
class Question {
    private int chapter, question;
    private String text,
            choiceA,
            choiceB,
            choiceC, 
            choiceD, 
            choiceE,
            answerKey,
            hint;
    
    Question(){
        chapter = question = 0;
        text = choiceA = choiceB = choiceC = choiceD = choiceE = answerKey = hint = null;
    }

    public int getChapter() {
        return chapter;
    }

    public int getQuestion() {
        return question;
    }

    public String getText() {
        return text;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public String getChoiceE() {
        return choiceE;
    }

    public String getAnswerKey() {
        return answerKey;
    }

    public String getHint() {
        return hint;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    public void setChoiceE(String choiceE) {
        this.choiceE = choiceE;
    }

    public void setAnswerKey(String answerKey) {
        this.answerKey = answerKey;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}