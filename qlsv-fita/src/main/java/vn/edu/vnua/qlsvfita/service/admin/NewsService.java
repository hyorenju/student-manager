package vn.edu.vnua.qlsvfita.service.admin;

import org.springframework.data.domain.Page;
import vn.edu.vnua.qlsvfita.model.entity.News;
import vn.edu.vnua.qlsvfita.request.admin.news.CreateNewsRequest;
import vn.edu.vnua.qlsvfita.request.admin.news.GetNewsListRequest;
import vn.edu.vnua.qlsvfita.request.admin.news.UpdateNewsRequest;

import java.text.ParseException;

public interface NewsService {
    Page<News> getNewsList(GetNewsListRequest request);

    News createNews(CreateNewsRequest request) throws ParseException;

    News updateNews(UpdateNewsRequest request, Long id) throws ParseException;

    void deleteNews(Long id);
}
