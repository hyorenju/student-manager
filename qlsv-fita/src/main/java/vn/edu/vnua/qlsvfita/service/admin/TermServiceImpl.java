package vn.edu.vnua.qlsvfita.service.admin;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.entity.Class;
import vn.edu.vnua.qlsvfita.model.entity.Major;
import vn.edu.vnua.qlsvfita.model.entity.Student;
import vn.edu.vnua.qlsvfita.model.entity.StudentTerm;
import vn.edu.vnua.qlsvfita.model.entity.Term;
import vn.edu.vnua.qlsvfita.repository.*;
import vn.edu.vnua.qlsvfita.request.admin.term.CreateTermRequest;
import vn.edu.vnua.qlsvfita.request.admin.term.GetTermListRequest;
import vn.edu.vnua.qlsvfita.request.admin.term.UpdateTermRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TermServiceImpl implements TermService {
    private final TermRepository termRepository;
    private final StudentRepository studentRepository;
    private final PointRepository pointRepository;

    @Override
    public Page<Term> getTermList(GetTermListRequest request) {
//        List<Term> terms = termRepository.findAll();
//        for (Term term:
//                terms) {
//            String termId = term.getId();
//
//            List<Student> students = studentRepository.findAllByStatus("Còn đi học");
//            long countNum = students.size();
//            long countExcellent = 0;
//            long countGood = 0;
//            long countFair = 0;
//            long countMedium = 0;
//            long countWeak = 0;
//            long countWorst = 0;
//            for (Student student :
//                    students) {
//                if (pointRepository.getByStudentIdAndTermId(student.getId(), termId) != null) {
//                    int point = pointRepository.getByStudentIdAndTermId(student.getId(), termId).getTrainingPoint();
//                    if (point >= 90) {
//                        countExcellent++;
//                    } else if (point >= 80) {
//                        countGood++;
//                    } else if (point >= 65) {
//                        countFair++;
//                    } else if (point >= 50) {
//                        countMedium++;
//                    } else if (point >= 35) {
//                        countWeak++;
//                    } else {
//                        countWorst++;
//                    }
//                }
//            }
//            Term anotherTerm = termRepository.getById(termId);
//            anotherTerm.setNumOfStu(countNum);
//            anotherTerm.setExcellent(countExcellent);
//            anotherTerm.setGood(countGood);
//            anotherTerm.setFair(countFair);
//            anotherTerm.setMedium(countMedium);
//            anotherTerm.setWeak(countWeak);
//            anotherTerm.setWorst(countWorst);
//            termRepository.saveAndFlush(anotherTerm);
//        }
        List<Term> terms = termRepository.findAll();
        for (Term term :
                terms) {
            List<StudentTerm> points = pointRepository.findAllByTermId(term.getId());
            long countExcellent = 0;
            long countGood = 0;
            long countFair = 0;
            long countMedium = 0;
            long countWeak = 0;
            long countWorst = 0;
            long numOfStu = 0;

            for (StudentTerm pointList :
                    points) {
                if (pointList.getTrainingPoint() != null) {
                    int point = pointList.getTrainingPoint();
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
            Term anotherTerm = termRepository.getById(term.getId());
            anotherTerm.setNumOfStu(numOfStu);
            anotherTerm.setExcellent(countExcellent);
            anotherTerm.setGood(countGood);
            anotherTerm.setFair(countFair);
            anotherTerm.setMedium(countMedium);
            anotherTerm.setWeak(countWeak);
            anotherTerm.setWorst(countWorst);
            termRepository.saveAndFlush(anotherTerm);
        }
        Specification<Term> specification = CustomTermListRepository.filterTermList(
                request.getId()
        );
        return termRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public Term createTerm(CreateTermRequest request) {
        if (termRepository.existsById(request.getId())) {
            throw new RuntimeException("Mã học kỳ đã tồn tại trong hệ thống");
        } else {
            Term term = Term.builder()
                    .id(request.getId())
                    .termName("Học kỳ " + request.getId().charAt(4) + " - năm " + request.getId().substring(0, 4))
                    .build();

            term = termRepository.saveAndFlush(term);
            return term;
        }
    }

    @Override
    public Term updateTerm(UpdateTermRequest request, String termId) {
//        if (!pointRepository.existsByTermId(termId)) {
//            throw new RuntimeException("Không tồn tại mã học kỳ " + termId + " trong hệ thống");
//        }
//        List<Student> students = studentRepository.findAllByStatus("Còn đi học");
//        long countNum = students.size();
//        long countExcellent = 0;
//        long countGood = 0;
//        long countFair = 0;
//        long countMedium = 0;
//        long countWeak = 0;
//        long countWorst = 0;
//        for (Student student :
//                students) {
//            if (pointRepository.getByStudentIdAndTermId(student.getId(), termId) != null) {
//                int point = pointRepository.getByStudentIdAndTermId(student.getId(), termId).getTrainingPoint();
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
//
//        Term term = Term.builder()
//                .id(termId)
//                .termName(request.getName())
//                .numOfStu(countNum)
//                .excellent(countExcellent)
//                .good(countGood)
//                .fair(countFair)
//                .medium(countMedium)
//                .weak(countWeak)
//                .worst(countWorst)
//                .build();
//        term = termRepository.saveAndFlush(term);
//        return term;
        return null;
    }

    @Override
    public void deleteTerm(String id) {
        if (!termRepository.existsById(id)) {
            throw new RuntimeException("Không tồn tại mã học kỳ " + id + " trong hệ thống");
        }
        termRepository.deleteById(id);
    }
}
