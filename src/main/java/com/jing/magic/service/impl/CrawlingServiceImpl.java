package com.jing.magic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jing.magic.entity.Novel;
import com.jing.magic.entity.NovelChapter;
import com.jing.magic.pipeline.NovelChapterPipeline;
import com.jing.magic.pipeline.NovelContentPipeline;
import com.jing.magic.processor.NovelChapterProcessor;
import com.jing.magic.processor.NovelContentProcessor;
import com.jing.magic.service.CrawlingService;
import com.jing.magic.service.NovelChapterService;
import com.jing.magic.service.NovelService;
import com.jing.magic.spider.MagicSpider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * @author : jing
 * @projectName : magic
 * @packageName : com.jing.magic.service.impl
 * @date : 2021/11/12 10:19
 * @description :
 */
@Service("crawlingService")
public class CrawlingServiceImpl implements CrawlingService {

    private final Logger logger = LoggerFactory.getLogger(NovelServiceImpl.class);

    @Autowired
    private NovelChapterProcessor chapterProcessor;

    @Autowired
    private NovelContentProcessor contentProcessor;

    @Autowired
    private NovelChapterPipeline chapterPipeline;
    @Autowired
    private NovelContentPipeline contentPipeline;

    @Autowired
    private NovelService novelService;

    @Autowired
    private NovelChapterService chapterService;

    @Override
    public void crawlingNovelChapter(Long novelId) {
        Novel novel = novelService.getById(novelId);
        if (null == novel) {
            return;
        }
        MagicSpider.create(chapterProcessor)
                //从https://qd.anjuke.com/community/开始爬取
                .addUrl(novel.getPath())
                .setTarget(novelId)
                // 使用自定义的Pipeline
                .addPipeline(chapterPipeline)
                .thread(1)
                .run();
    }

    @Override
    public void crawlingNovelContent(Long novelId) {
        QueryWrapper<NovelChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("novel_id", novelId);
        queryWrapper.eq("status", 0);
        List<NovelChapter> chapters = chapterService.list(queryWrapper);
        if (null == chapters) {
            return;
        }
        for (NovelChapter ch : chapters) {
            Spider.create(contentProcessor)
                    //从https://qd.anjuke.com/community/开始爬取
                    .addUrl(ch.getPath())
                    // 使用自定义的Pipeline
                    .addPipeline(contentPipeline)
                    .thread(1)
                    .run();
        }

    }


}
