package com.jing.magic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jing.magic.entity.NovelContent;
import com.jing.magic.mapper.NovelContentMapper;
import com.jing.magic.service.NovelContentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : jing
 * @projectName : magic
 * @packageName : com.jing.magic.service.impl
 * @date : 2021/11/12 10:26
 * @description :
 */
@Transactional(rollbackFor = Exception.class)
@Service("novelContentService")
public class NovelContentServiceImpl extends ServiceImpl<NovelContentMapper, NovelContent> implements NovelContentService {


}
