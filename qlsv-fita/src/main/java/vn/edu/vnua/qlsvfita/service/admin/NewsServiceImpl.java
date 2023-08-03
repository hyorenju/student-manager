package vn.edu.vnua.qlsvfita.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.vnua.qlsvfita.model.entity.News;
import vn.edu.vnua.qlsvfita.repository.CustomNewsListRepository;
import vn.edu.vnua.qlsvfita.repository.NewsRepository;
import vn.edu.vnua.qlsvfita.request.admin.news.CreateNewsRequest;
import vn.edu.vnua.qlsvfita.request.admin.news.GetNewsListRequest;
import vn.edu.vnua.qlsvfita.request.admin.news.UpdateNewsRequest;
import vn.edu.vnua.qlsvfita.util.MyUtils;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService{
    private final NewsRepository newsRepository;

    @Override
    public Page<News> getNewsList(GetNewsListRequest request) {
        Specification<News> specification = CustomNewsListRepository.filterNewsList(
                request.getFilter().getTitle()
        );
        return newsRepository.findAll(specification, PageRequest.of(request.getPage() - 1, request.getSize()));
    }

    @Override
    public News createNews(CreateNewsRequest request) throws ParseException {
        News news = News.builder()
                .title(request.getTitle())
                .postDate(MyUtils.convertTimestampFromString(request.getPostDate()))
                .description(request.getDescription())
                .content(request.getContent())
                .type(request.getType())
                .build();
        news = newsRepository.saveAndFlush(news);
        return news;
    }

    @Override
    public News updateNews(UpdateNewsRequest request, Long id) throws ParseException {
        if (!newsRepository.existsById(request.getId())) {
            throw new RuntimeException("Không tìm thấy mã tin.");
        }
        News news = newsRepository.getById(request.getId());
        news.setTitle(request.getTitle());
        news.setPostDate(MyUtils.convertTimestampFromString(request.getPostDate()));
        news.setDescription(request.getDescription());
        news.setContent(request.getContent());
        news.setType(request.getType());

        news = newsRepository.saveAndFlush(news);
        return news;
    }

    @Override
    public void deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new RuntimeException("Mã tin không tồn tại trong CSDL");
        }
        newsRepository.deleteById(id);
    }
}
