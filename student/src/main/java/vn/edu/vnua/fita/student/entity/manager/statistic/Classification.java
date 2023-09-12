package vn.edu.vnua.fita.student.entity.manager.statistic;

import lombok.Data;

@Data
public class Classification {
    private String termId;
    private Integer excellent;
    private Integer good;
    private Integer fair;
    private Integer medium;
    private Integer weak;
    private Integer worst;
}
