package com.jing.magic.controller;

import com.jing.magic.service.CrawlingService;
import com.jing.magic.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author : jing
 * @projectName : magic
 * @packageName : com.jing.magic.controller
 * @date : 2021/11/11 16:48
 * @description :
 */
@RestController
@RequestMapping("novel")
public class NovelController {

    @Autowired
    private CrawlingService crawlingService;

    @Autowired
    private NovelService novelService;

    @GetMapping("crawling/chapter/{novelId}")
    public void crawlingChapter(@PathVariable("novelId") Long novelId) throws Exception {
        crawlingService.crawlingNovelChapter(novelId);
    }

    @GetMapping("crawling/content/{novelId}")
    public void crawlingContent(@PathVariable("novelId") Long novelId) throws Exception {
        crawlingService.crawlingNovelContent(novelId);
    }

    @GetMapping("download/{novelId}")
    public void download(@PathVariable("novelId") Long novelId, HttpServletResponse response) throws Exception {
        novelService.download(novelId, response);
    }

    @GetMapping("change/chapter/name/{novelId}")
    public void changeChapterName(@PathVariable("novelId") Long novelId, HttpServletResponse response) throws Exception {
        novelService.changeChapterName(novelId, response);
    }


}
