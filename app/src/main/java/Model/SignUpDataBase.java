package Model;

public class SignUpDataBase {

    String id;
    String userName;
    String password;
    String firstName;
    String lastName;
    String firstQuestion;
    String secondQuestion;
    String thirdQuestion;
    String forthQuestion;
    String fifthQuestion;
    String sixthQuestion;
    String seventhQuestion;
    String eightQuestion;
    String ninthQuestion;
    String tenthQuestion;
    String score;


    public SignUpDataBase(){
    }

    public SignUpDataBase(String id,String userName,String password,String firstName,String lastName){
        this.id=id;
        this.userName=userName;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
    }

    public String score(String userName, String score) {
        this.score= score;
        this.userName=userName;
        return score;

    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void firstQuestion(String userName, String firstQuestion) {
        this.firstQuestion= firstQuestion;
        this.userName=userName;

    }

    public void thirdQuestion(String userName, String thirdQuestion) {
        this.thirdQuestion= thirdQuestion;
        this.userName=userName;
    }

    public void forthQuestion(String userName, String forthQuestion) {
        this.forthQuestion= forthQuestion;
        this.userName=userName;
    }
    public void secondQuestion(String userName, String secondQuestion) {
        this.secondQuestion= secondQuestion;
        this.userName=userName;
    }

    public void fifthQuestion(String userName, String fifthQuestion) {
        this.fifthQuestion= fifthQuestion;
        this.userName=userName;
    }
    public void sixthQuestion(String userName, String sixthQuestion) {
        this.sixthQuestion= sixthQuestion;
        this.userName=userName;
    }
    public void seventhQuestion(String userName, String seventhQuestion) {
        this.seventhQuestion= seventhQuestion;
        this.userName=userName;
    }
    public void eightQuestion(String userName, String eightQuestion) {
        this.eightQuestion= eightQuestion;
        this.userName=userName;
    }
    public void ninthQuestion(String userName, String ninthQuestion) {
        this.ninthQuestion= ninthQuestion;
        this.userName=userName;
    }
    public void tenthQuestion(String userName, String tenthQuestion) {
        this.tenthQuestion= tenthQuestion;
        this.userName=userName;
    }

    public String getSecondQuestion() {
        return secondQuestion;
    }

    public String getFirstQuestion() {
        return firstQuestion;
    }

    public void setFirstQuestion(String firstQuestion) {
        this.firstQuestion = firstQuestion;
    }

    public void setSecondQuestion(String secondQuestion) {
        this.secondQuestion = secondQuestion;
    }

    public String getEightQuestion() {
        return eightQuestion;
    }

    public String getFifthQuestion() {
        return fifthQuestion;
    }

    public String getForthQuestion() {
        return forthQuestion;
    }

    public String getNinthQuestion() {
        return ninthQuestion;
    }

    public String getSeventhQuestion() {
        return seventhQuestion;
    }

    public String getTenthQuestion() {
        return tenthQuestion;
    }

    public String getSixthQuestion() {
        return sixthQuestion;
    }

    public String getThirdQuestion() {
        return thirdQuestion;
    }

    public void setFifthQuestion(String fifthQuestion) {
        this.fifthQuestion = fifthQuestion;
    }

    public void setForthQuestion(String forthQuestion) {
        this.forthQuestion = forthQuestion;
    }

    public void setNinthQuestion(String ninthQuestion) {
        this.ninthQuestion = ninthQuestion;
    }

    public void setSeventhQuestion(String seventhQuestion) {
        this.seventhQuestion = seventhQuestion;
    }

    public void setSixthQuestion(String sixthQuestion) {
        this.sixthQuestion = sixthQuestion;
    }

    public void setTenthQuestion(String tenthQuestion) {
        this.tenthQuestion = tenthQuestion;
    }

    public void setThirdQuestion(String thirdQuestion) {
        this.thirdQuestion = thirdQuestion;
    }

    public void setEightQuestion(String eightQuestion) {
        this.eightQuestion = eightQuestion;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
