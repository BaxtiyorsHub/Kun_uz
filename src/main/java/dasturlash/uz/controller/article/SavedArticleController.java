package dasturlash.uz.controller.article;

import dasturlash.uz.service.article.SavedArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/saved")
public class SavedArticleController {
    @Autowired
    private SavedArticleService savedArticleService;
}
