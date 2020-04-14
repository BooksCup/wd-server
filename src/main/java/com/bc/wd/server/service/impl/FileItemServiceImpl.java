package com.bc.wd.server.service.impl;

import com.bc.wd.server.entity.FileItem;
import com.bc.wd.server.es.repository.FileItemRepository;
import com.bc.wd.server.service.FileItemService;
import com.bc.wd.server.util.CommonUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Override
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
     * 删除文件索引
     */
    @Override
    public void deleteFileItemIndex() {
        elasticsearchTemplate.deleteIndex(FileItem.class);
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

    /**
     * 高亮查询
     *
     * @param searchQuery        请求参数
     * @param highLightFieldList 高亮字段
     * @return 搜索结果
     */
    @Override
    public Page<FileItem> highLightSearch(SearchQuery searchQuery, List<String> highLightFieldList) {
        return elasticsearchTemplate.queryForPage(searchQuery, FileItem.class, new SearchResultMapper() {
            @SuppressWarnings("unchecked")
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                SearchHits searchHits = response.getHits();
                long totalHits = searchHits.getTotalHits();
                List<T> results = new ArrayList<>();
                for (SearchHit searchHit : searchHits) {
                    Map<String, Object> entityMap = searchHit.getSourceAsMap();
                    for (String highLightField : highLightFieldList) {
                        String highLightValue = searchHit.getHighlightFields().get(highLightField).fragments()[0].toString();
                        entityMap.put(highLightField, highLightValue);
                    }
                    results.add((T) CommonUtil.map2Object(entityMap, FileItem.class));
                }
                return new AggregatedPageImpl<>(results, pageable, totalHits, response.getAggregations(), response.getScrollId());
            }
        });
    }
}
