package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class StudentSpecialLiterature extends Student implements Comparable<StudentSpecialLiterature> {
    public StudentSpecialLiterature(String fullName, float mathScore, float englishScore, float literatureScore, String className, Date dob, float averageScore, int age, int type) {
        super(fullName, mathScore, englishScore, literatureScore, className, dob, averageScore, age, type);
    }

    @Override
    public void validateInformation(AtomicInteger count) {
        if(getLiteratureScore() < 7){
            System.out.println("Dòng "+ count + ": Điểm Văn của học sinh này không được dưới 7.");
            throw new RuntimeException("Chương trình kết thúc!");
        }
    }

    @Override
    public int compareTo(StudentSpecialLiterature o) {
        return this.getLiteratureScore() > o.getLiteratureScore() ? -1 : 1;
    }
}
