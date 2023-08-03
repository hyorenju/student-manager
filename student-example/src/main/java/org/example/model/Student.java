package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.util.MyUtils;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student{
    private String fullName;
    private float mathScore = 0;
    private float englishScore = 0;
    private float literatureScore = 0;
    private String className;
    private Date dob;
    private float averageScore = 0;
    private int age;
    private int type;

    public void calculateAverageScore() {
        this.averageScore = Math.round((englishScore + literatureScore + mathScore) / 3);
    }

    public void calculateAge() {
        age = new Date().getYear() - dob.getYear();
    }

//    @Override
//    public int compareTo(Student o) {
//        int criteria1 = MyUtils.getLastNameCharacter(this.fullName).compareTo(MyUtils.getLastNameCharacter(o.getFullName()));
//        int criteria2 = this.averageScore > o.getAverageScore() ? -2 : 2; //1 -= asc, -1 = desc
//        return criteria1 + criteria2;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        Student compareStudent = (Student) obj;
//        return compareStudent.getMathScore() == this.mathScore
//                && compareStudent.getClassName().equalsIgnoreCase(this.className)
//                && !compareStudent.getFullName().equalsIgnoreCase(this.fullName);
//    }

    public void validateInformation(AtomicInteger count){
        this.checkUserName(fullName, count);
        this.checkScore(mathScore, count);
        this.checkScore(englishScore, count);
        this.checkScore(literatureScore, count);
        this.checkType(type, count);
    }

    private void checkScore(float score, AtomicInteger count){
        if(score < 0 || score > 10) {
            System.out.println("Dòng "+ count + ": Điểm chỉ được trong khoảng 0-10.");
            throw new RuntimeException("Chương trình kết thúc!");
        }
    }

    private void checkType(int type, AtomicInteger count){
        if(type < 1 || type > 3){
            System.out.println("Dòng "+ count + ": Phân loại chỉ được trong khoảng 1-3.");
            throw new RuntimeException("Chương trình kết thúc!");
        }
        if(type == 3) {
            if(this.literatureScore < 7){
                System.out.println("Dòng "+ count + ": Điểm Văn của học sinh này không được dưới 7.");
                throw new RuntimeException("Chương trình kết thúc!");
            }
        }
    }

    private void checkUserName(String userName, AtomicInteger count){
        String regex = "^[A-Za-z]+$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userName);
        if(matcher.matches()) {
            System.out.println("Dòng " + count + ": Tên học sinh không được chứa kí tự đặc biệt!");
            throw new RuntimeException("Chương trình kết thúc!");
        }
    }

}
