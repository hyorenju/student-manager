package vn.edu.vnua.qlsvfita.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.entity.StudentTerm;
import vn.edu.vnua.qlsvfita.repository.PointRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService{
    private final PointRepository pointRepository;

    @Override
    public List<StudentTerm> StudyProcess(String id) {
        return pointRepository.findAllByStudentId(id);
    }
}
