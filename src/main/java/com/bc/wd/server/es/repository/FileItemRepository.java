package com.bc.wd.server.es.repository;

import com.bc.wd.server.entity.FileItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 文件es组件
 *
 * @author zhou
 */
public interface FileItemRepository extends ElasticsearchRepository<FileItem, String> {
}
