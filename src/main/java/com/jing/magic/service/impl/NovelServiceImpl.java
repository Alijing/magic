package com.jing.magic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jing.magic.entity.Novel;
import com.jing.magic.entity.NovelChapter;
import com.jing.magic.entity.NovelContent;
import com.jing.magic.mapper.NovelMapper;
import com.jing.magic.service.NovelChapterService;
import com.jing.magic.service.NovelContentService;
import com.jing.magic.service.NovelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author : jing
 * @projectName : magic
 * @packageName : com.jing.magic.service.impl
 * @date : 2021/11/11 16:50
 * @description :
 */
@Transactional(rollbackFor = Exception.class)
@Service("novelService")
public class NovelServiceImpl extends ServiceImpl<NovelMapper, Novel> implements NovelService {

    private final Logger logger = LoggerFactory.getLogger(NovelServiceImpl.class);

    @Autowired
    private NovelChapterService chapterService;

    @Autowired
    private NovelContentService contentService;

    @Override
    public void download(Long novelId, HttpServletResponse response) {
        Novel novel = getById(novelId);
        if (null == novel) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        QueryWrapper<NovelChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("novel_id", novelId);
        List<NovelChapter> chapters = chapterService.list(queryWrapper);
        for (NovelChapter ch : chapters) {
            builder.append(ch.getName()).append("\n");
            QueryWrapper<NovelContent> contentQueryWrapper = new QueryWrapper<>();
            contentQueryWrapper.eq("chapter_id", ch.getId());
            NovelContent one = contentService.getOne(contentQueryWrapper);
            builder.append(one.getContent()).append("\n\n");
        }
        try {
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(novel.getName() + ".txt", "UTF-8"));
            response.getOutputStream().write(builder.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            logger.error("小说下载失败", e);
        }
    }
}
