package vn.edu.vnua.qlsvfita.controller.admin;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.vnua.qlsvfita.controller.BaseController;
import vn.edu.vnua.qlsvfita.model.entity.News;
import vn.edu.vnua.qlsvfita.request.admin.news.CreateNewsRequest;
import vn.edu.vnua.qlsvfita.request.admin.news.GetNewsListRequest;
import vn.edu.vnua.qlsvfita.request.admin.news.UpdateNewsRequest;
import vn.edu.vnua.qlsvfita.service.admin.NewsService;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("news")
public class NewsController extends BaseController {
    private final NewsService newsService;
    private final ModelMapper modelMapper = new ModelMapper();


    @PostMapping("list")
    public ResponseEntity<?> getNewsList(@Valid @RequestBody GetNewsListRequest request){
        Page<News> page = newsService.getNewsList(request);
        List<News> response = page.getContent();
        return buildPageItemResponse(request.getPage(), response.size(), page.getTotalElements(), response);
    }

    @PostMapping("create")
    public ResponseEntity<?> createNews(@Valid @RequestBody CreateNewsRequest request) throws ParseException {
        News response = newsService.createNews(request);
        return buildItemResponse(response);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<?> updateNews(@PathVariable Long id, @Valid @RequestBody UpdateNewsRequest request) throws ParseException {
        News response = newsService.updateNews(request, id);
        return buildItemResponse(response);
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable Long id){
        newsService.deleteNews(id);
        String message = "Xóa thành công";
        return buildItemResponse(message);
    }
}
