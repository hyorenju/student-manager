package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class StudentSpecialMath extends Student implements Comparable<StudentSpecialMath> {
    public StudentSpecialMath(String fullName, float mathScore, float englishScore, float literatureScore, String className, Date dob, float averageScore, int age, int type) {
        super(fullName, mathScore, englishScore, literatureScore, className, dob, averageScore, age, type);
    }

    @Override
    public void validateInformation(AtomicInteger count) {
        if(getMathScore() < 7){
            System.out.println("Dòng "+ count + ": Điểm Toán của học sinh này không được dưới 7.");
            throw new RuntimeException("Chương trình kết thúc!");
        }
    }

    @Override
    public int compareTo(StudentSpecialMath o) {
        return this.getMathScore() > o.getMathScore() ? -1 : 1;
    }
}
