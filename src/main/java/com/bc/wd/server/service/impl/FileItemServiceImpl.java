package com.bc.wd.server.service.impl;

import com.bc.wd.server.entity.FileItem;
import com.bc.wd.server.es.repository.FileItemRepository;
import com.bc.wd.server.service.FileItemService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.stereotype.Service;

/**
 * 文件
 *
 * @author zhou
 */
@Service("fileItemService")
public class FileItemServiceImpl implements FileItemService {
    @Autowired
    FileItemRepository fileItemRepository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 保存文件文档
     *
     * @param fileItem 文件文档
     */
    @Override
    public void save(FileItem fileItem) {
        fileItemRepository.save(fileItem);
    }

    /**
     * 根据磁盘名删除文件文档
     *
     * @param diskName 磁盘名
     */
    public void deleteFileItemByDiskName(String diskName) {
        DeleteQuery deleteQuery = new DeleteQuery();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchQuery("diskName", diskName));

        deleteQuery.setQuery(queryBuilder);
        elasticsearchTemplate.delete(deleteQuery, FileItem.class);

        // 彻底删除
        elasticsearchTemplate.refresh(FileItem.class);
    }

    /**
     * 搜索文件
     *
     * @param queryBuilder 请求参数
     * @param pageable     分页参数
     * @return 搜索结果
     */
    @Override
    public Page<FileItem> search(QueryBuilder queryBuilder, Pageable pageable) {
        return fileItemRepository.search(queryBuilder, pageable);
    }
}
