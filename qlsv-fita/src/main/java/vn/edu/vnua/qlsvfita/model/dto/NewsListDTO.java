package vn.edu.vnua.qlsvfita.model.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class NewsListDTO {
    private String title;
    private Timestamp postDate;
    private String description;
}
