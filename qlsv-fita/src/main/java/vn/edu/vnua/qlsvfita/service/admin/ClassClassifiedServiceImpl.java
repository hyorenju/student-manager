package vn.edu.vnua.qlsvfita.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.entity.*;
import vn.edu.vnua.qlsvfita.model.entity.Class;
import vn.edu.vnua.qlsvfita.repository.*;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.CreateClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.DeleteClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.GetClassClassifiedRequest;
import vn.edu.vnua.qlsvfita.request.admin.classification.classes.UpdateClassClassifiedRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassClassifiedServiceImpl implements ClassClassifiedService{
    private final ClassClassifiedRepository classClassifiedRepository;
    private final ClassRepository classRepository;
    private final TermRepository termRepository;
    private final StudentRepository studentRepository;
    private final PointRepository pointRepository;

    @Override
    public Page<ClassTerm> getClassClassified(GetClassClassifiedRequest request) {
        List<ClassTerm> classes = classClassifiedRepository.findAll();
        for (ClassTerm classTerm :
                classes) {
            Class classId = Class.builder().id(classTerm.getClassId()).build();
            List<Student> students = studentRepository.findAllByClasses(classId);

            long countExcellent = 0;
            long countGood = 0;
            long countFair = 0;
            long countMedium = 0;
            long countWeak = 0;
            long countWorst = 0;
            long numOfStu = 0;

            for (Student student :
                    students) {
                if (pointRepository.existsByStudentIdAndTermId(student.getId(), classTerm.getTermId())) {
                    StudentTerm pointCell = pointRepository.getByStudentIdAndTermId(student.getId(), classTerm.getTermId());
                    if (pointCell.getTrainingPoint() != null) {
                        int point = pointCell.getTrainingPoint();
                        if (point > 0) {
                            numOfStu++;
                            if (point >= 90) {
                                countExcellent++;
                            } else if (point >= 80) {
                                countGood++;
                            } else if (point >= 65) {
                                countFair++;
                            } else if (point >= 50) {
                                countMedium++;
                            } else if (point >= 35) {
                                countWeak++;
                            } else {
                                countWorst++;
                            }
                        }
                    }
                }
            }
            ClassTerm anotherClasses = classClassifiedRepository.findByClassIdAndTermId(classTerm.getClassId(), classTerm.getTermId());
            anotherClasses.setNumOfStu(numOfStu);
            anotherClasses.setExcellent(countExcellent);
            anotherClasses.setGood(countGood);
            anotherClasses.setFair(countFair);
            anotherClasses.setMedium(countMedium);
            anotherClasses.setWeak(countWeak);
            anotherClasses.setWorst(countWorst);
            classClassifiedRepository.saveAndFlush(anotherClasses);
        }
        Specification<ClassTerm> specification = CustomClassClassifiedRepository.filterClassClassified(
                request.getClassId(),
                request.getTermId()
        );
        return classClassifiedRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public ClassTerm createClassClassified(CreateClassClassifiedRequest request) {
        if(classClassifiedRepository.existsByClassIdAndTermId(request.getClassId(), request.getTermId())){
            throw new RuntimeException("Dữ liệu của lớp "+request.getClassId()+" trong học kỳ "+request.getTermId()+" đã tồn tại trong hệ thống");
        } else if(!classRepository.existsById(request.getClassId())){
            throw new RuntimeException("Không tồn tại lớp "+request.getClassId()+" trong hệ thống");
        } else if(!termRepository.existsById(request.getTermId())){
            throw new RuntimeException("Không tồn tại học kỳ "+request.getTermId()+" trong hệ thống");
        }
        ClassTerm classClassified = ClassTerm.builder()
                .classId(request.getClassId())
                .termId(request.getTermId())
                .build();
        classClassified = classClassifiedRepository.saveAndFlush(classClassified);
        return classClassified;
    }

    @Override
    public ClassTerm updateClassClassified(UpdateClassClassifiedRequest request) {
//        if(!classClassifiedRepository.existsByClassIdAndTermId(request.getClassId(), request.getTermId())){
//            throw new RuntimeException("Dữ liệu của lớp "+request.getClassId()+" trong học kỳ "+request.getTermId()+" không tồn tại trong hệ thống");
//        }
//        Class classId = Class.builder().id(request.getClassId()).build();
//        List<Student> students = studentRepository.findAllByClassesAndStatus(classId, "Còn đi học");
//        long countNum = students.size();
//        long countExcellent = 0;
//        long countGood = 0;
//        long countFair = 0;
//        long countMedium = 0;
//        long countWeak = 0;
//        long countWorst = 0;
//        for (Student student :
//                students) {
//            if (pointRepository.getByStudentIdAndTermId(student.getId(), request.getTermId()) != null) {
//                int point = pointRepository.getByStudentIdAndTermId(student.getId(), request.getTermId()).getTrainingPoint();
//                if (point >= 90) {
//                    countExcellent++;
//                } else if (point >= 80) {
//                    countGood++;
//                } else if (point >= 65) {
//                    countFair++;
//                } else if (point >= 50) {
//                    countMedium++;
//                } else if (point >= 35) {
//                    countWeak++;
//                } else {
//                    countWorst++;
//                }
//            }
//        }
//        ClassTerm classClassified = ClassTerm.builder()
//                .id(classClassifiedRepository.findByClassIdAndTermId(request.getClassId(), request.getTermId()).getId())
//                .classId(request.getClassId())
//                .termId(request.getTermId())
//                .numOfStu(countNum)
//                .excellent(countExcellent)
//                .good(countGood)
//                .fair(countFair)
//                .medium(countMedium)
//                .weak(countWeak)
//                .worst(countWorst)
//                .build();
//        classClassified = classClassifiedRepository.saveAndFlush(classClassified);
        return null;
    }

    @Override
    public void deleteClassClassified(DeleteClassClassifiedRequest request) {
        classClassifiedRepository.deleteById(classClassifiedRepository.findByClassIdAndTermId(request.getClassId(), request.getTermId()).getId());
    }
}
