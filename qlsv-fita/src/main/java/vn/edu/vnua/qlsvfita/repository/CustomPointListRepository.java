package vn.edu.vnua.qlsvfita.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.edu.vnua.qlsvfita.model.entity.StudentTerm;
import vn.edu.vnua.qlsvfita.request.admin.point.GetPointListRequest;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomPointListRepository {
    public static Specification<StudentTerm> filterPointList(String studentId,
                                                             String termId,
                                                             Integer point,
                                                             Integer accPoint,
                                                             Integer trainingPoint
                                                             /*Float pointMin, Float pointMax,
                                                             Float accPointMin, Float accPointMax,
                                                             Integer trainingPointMin, Integer trainingPointMax*/) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(studentId)) {
                predicates.add(criteriaBuilder.like(root.get("studentId"), studentId));
            }
            if (StringUtils.hasText(termId)) {
                predicates.add(criteriaBuilder.like(root.get("termId"), termId));
            }
            if(point!=null) {
                if(point==1){
                    predicates.add(criteriaBuilder.between(root.get("medScore4"), 3.6, 4.0));
                } else if(point==2){
                    predicates.add(criteriaBuilder.between(root.get("medScore4"), 3.2, 3.6));
                } else if(point==3){
                    predicates.add(criteriaBuilder.between(root.get("medScore4"), 2.5, 3.2));
                } else if(point==4){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("medScore4"), 2.5));
                } else if(point==5){
                    predicates.add(criteriaBuilder.between(root.get("medScore4"), 2.0, 2.5));
                } else if(point==6){
                    predicates.add(criteriaBuilder.between(root.get("medScore4"), 1.0, 2.0));
                } else if(point==7){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("medScore4"), 1.0));
                }
            }
            if(trainingPoint!=null) {
                if(trainingPoint==1){
                    predicates.add(criteriaBuilder.between(root.get("trainingPoint"), 90, 100));
                } else if(trainingPoint==2){
                    predicates.add(criteriaBuilder.between(root.get("trainingPoint"), 80, 89));
                } else if(trainingPoint==3){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("trainingPoint"), 80));
                } else if(trainingPoint==4){
                    predicates.add(criteriaBuilder.between(root.get("trainingPoint"), 65, 79));
                } else if(trainingPoint==5){
                    predicates.add(criteriaBuilder.between(root.get("trainingPoint"), 50, 64));
                } else if(trainingPoint==6){
                    predicates.add(criteriaBuilder.between(root.get("trainingPoint"), 35, 49));
                } else if(trainingPoint==7){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("trainingPoint"), 35));
                }
            }
            if(accPoint!=null) {
                if(accPoint==1){
                    predicates.add(criteriaBuilder.between(root.get("scoreAccumulated4"), 3.6, 4.0));
                } else if(accPoint==2){
                    predicates.add(criteriaBuilder.between(root.get("scoreAccumulated4"), 3.2, 3.6));
                } else if(accPoint==3){
                    predicates.add(criteriaBuilder.between(root.get("scoreAccumulated4"), 2.5, 3.2));
                } else if(accPoint==4){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("scoreAccumulated4"), 2.5));
                } else if(accPoint==5){
                    predicates.add(criteriaBuilder.between(root.get("scoreAccumulated4"), 2.0, 2.5));
                } else if(accPoint==6){
                    predicates.add(criteriaBuilder.between(root.get("scoreAccumulated4"), 1.0, 2.0));
                } else if(accPoint==7){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("scoreAccumulated4"), 1.0));
                }
            }
//            if (pointMin != null) {
//                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("medScore4"), pointMin));
//            }
//            if (pointMax != null) {
//                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("medScore4"), pointMax));
//            }
//            if (accPointMin != null) {
//                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("scoreAccumulated4"), accPointMin));
//            }
//            if (accPointMax != null) {
//                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("scoreAccumulated4"), accPointMax));
//            }
//            if (trainingPointMin != null) {
//                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("trainingPoint"), trainingPointMin));
//            }
//            if (trainingPointMax != null) {
//                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("trainingPoint"), trainingPointMax));
//            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
