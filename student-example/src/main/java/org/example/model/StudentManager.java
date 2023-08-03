package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.util.MyUtils;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentManager {
    List<Student> students = new ArrayList<>();
    List<StudentSpecialMath> studentSpecialMaths = new ArrayList<>();
    List<StudentSpecialLiterature> studentSpecialLiteratures = new ArrayList<>();

    public void readDataFromFile(String studentStr, AtomicInteger count) {
        if (!studentStr.isEmpty()) {
            try {
                String[] infoList = studentStr.trim().split(",");
                if (infoList.length != 7) {
                    throw new RuntimeException("Dòng "+ count + ": Định dạng thông tin của 1 học sinh không đúng!");
                }
                String fullName = infoList[0].trim();
                float mathScore = MyUtils.parseFloat(infoList[1].trim());
                float englishScore = MyUtils.parseFloat(infoList[2].trim());
                float literatureScore = MyUtils.parseFloat(infoList[3].trim());
                Date dob = MyUtils.parseDateFromString(infoList[4], "dd/MM/yyyy");
                String className = infoList[5].trim();
                int type = Integer.parseInt(infoList[6].trim());

                Student student = new Student();
                student.setFullName(fullName);
                student.setClassName(className);
                student.setMathScore(mathScore);
                student.setEnglishScore(englishScore);
                student.setLiteratureScore(literatureScore);
                student.setDob(dob);
                student.setClassName(className);
                student.setType(type);

                student.validateInformation(count);
                student.calculateAge();
                student.calculateAverageScore();

                if(type==2){
                    StudentSpecialMath studentSpecialMath = new StudentSpecialMath(fullName,mathScore,englishScore,literatureScore,className,dob, student.getAverageScore(), student.getAge(), type);
                    studentSpecialMath.validateInformation(count);
                    studentSpecialMaths.add(studentSpecialMath);
                } else if(type==3){
                    StudentSpecialLiterature studentSpecialLiterature = new StudentSpecialLiterature(fullName,mathScore,englishScore,literatureScore,className,dob, student.getAverageScore(), student.getAge(), type);
                    studentSpecialLiterature.validateInformation(count);
                    studentSpecialLiteratures.add(studentSpecialLiterature);
                }

                students.add(student);
            }  catch (ParseException e){
                System.out.println("Dòng "+ count + ": Ngày phải đúng định dạng dd/MM/yyyy.");
                throw new RuntimeException("Chương trình kết thúc!");
            } catch (NumberFormatException e){
                System.out.println("Dòng "+ count + ": Trường điểm và phân loại phải là ký tự số.");
                throw new RuntimeException("Chương trình kết thúc!");
            }
        }
    }

    public void showMenu(){
        Scanner sc =new Scanner(System.in);
        int choice;
        boolean check = true;
        while (check) {
            System.out.println("""
                    
                    ********************************************************
                    Menu:
                    1. In tất cả thông tin học sinh.
                    2. In danh sách học sinh theo bảng chữ cái, điểm trung bình giảm dần.
                    3. In danh sách học sinh cùng lớp và cùng điểm môn toán.
                    4. In danh sách học sinh lớp chuyên.
                    5. Thoát.""");
            System.out.print("Nhập lựa chọn: ");
            try{
                choice = sc.nextInt(); sc.nextLine();
                switch (choice){
                    case 1:
                        System.out.println("-----Danh sách sinh viên:");
                        showAllStudentInfo(getStudents());
                        break;
                    case 2:
                        System.out.println("-----Sắp xếp theo tên và đtb giảm:");
                        showAllStudentInfo(this.sortByNameAndScoreDesc());
                        break;
                    case 3:
                        System.out.println("-----Tìm kiếm những sinh viên cùng lớp và cùng điểm toán:");
                        showAllStudentInfo(this.searchBySameClassAndMathScore());
                        break;
                    case 4:
                        System.out.println("-----Danh sách học sinh lớp chuyên theo điểm giảm dần");
                        System.out.println("---Chuyên Toán:");
                        showAllStudentInfo(sortSpecialMath(studentSpecialMaths));
                        System.out.println("---Chuyên Văn:");
                        showAllStudentInfo(sortSpecialLiterature(studentSpecialLiteratures));
                        break;
                    case 5:
                        System.out.println("Kết thúc chương trình.");
                        check = false;
                        break;
                    default:
                        System.out.println("**** Dữ liệu nhập không hợp lệ, mời nhập lại.");
                        break;
                }
            } catch (InputMismatchException e){
                System.out.println("**** Dữ liệu nhập không hợp lệ, mời nhập lại.");
                sc.nextLine();
            }
        }
    }

    public void showAllStudentInfo(List<? extends Student> students) {
        students.forEach(student -> {
            String info = String.format("Sinh viên: tên: %s |   Toán: %.2f  |   Văn: %.2f   |   Anh: %.2f   |   ĐTB: %.2f   |   Ngày Sinh: %s   |   Tuổi: %d    |   Lớp: %s |   Loại: %s",
                    student.getFullName(),
                    student.getMathScore(),
                    student.getLiteratureScore(),
                    student.getEnglishScore(),
                    student.getAverageScore(),
                    MyUtils.parseDateToString(student.getDob(), "dd-MM-yyyy"),
                    student.getAge(),
                    student.getClassName(),
                    student.getType()==1 ? "Học sinh lớp thường" : student.getType()==2 ? "Học sinh chuyên Toán" : "Học sinh chuyên Văn"
            );
            System.out.println(info);
        });
    }

    public List<Student> sortByNameAndScoreDesc() {
        List<Student> students = this.getStudents();
        Comparator<Student> lastNameComparator = Comparator.comparing(student -> MyUtils.getLastNameCharacter(student.getFullName()));
        Comparator<Student> avgScoreComparator = Comparator.comparing(Student::getAverageScore).reversed();

        return students.stream()
                .sorted(lastNameComparator.thenComparing(avgScoreComparator))
                .toList();
    }

    public List<Student> searchBySameClassAndMathScore() {
//        List<Student> duplicate = new ArrayList<>();
//        students.forEach(student -> {
//            List<Student> filter = students.stream().filter(student1 -> student1.equals(student)).collect(Collectors.toList());
//            if (!filter.isEmpty()) {
//                duplicate.addAll(filter);
//            }
//        });
        List<Student> students = this.students.stream()
                .collect(Collectors.groupingBy(Student::getMathScore))
                .values().stream()
                .filter(list -> list.size()>1)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Student::getClassName))
                .values().stream()
                .filter(i -> i.size()>1)
                .flatMap(List::stream)
                .toList();
        return students;
    }

    public List<StudentSpecialMath> sortSpecialMath(List<StudentSpecialMath> students){
        Collections.sort(students);
        return students;
    }

    public List<StudentSpecialLiterature> sortSpecialLiterature(List<StudentSpecialLiterature> students){
        Collections.sort(students);
        return students;
    }

}
