package vn.edu.vnua.qlsvfita.service.admin;

import vn.edu.vnua.qlsvfita.model.entity.StudentTerm;

import java.util.List;

public interface StatisticsService {
    List<StudentTerm> StudyProcess(String id);
}
