package vn.edu.vnua.fita.student.entity.manager.trash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.vnua.fita.student.entity.table.News;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trash_news")
public class TrashNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "news_id")
    private News news;

    @Column
    private Timestamp time;

    @Column(name = "by_whom", length = 100)
    private String byWhom;
}
