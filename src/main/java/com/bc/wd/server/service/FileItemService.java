package com.bc.wd.server.service;

import com.bc.wd.server.entity.FileItem;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 文件
 *
 * @author zhou
 */
public interface FileItemService {
    /**
     * 保存文件文档
     *
     * @param fileItem 文件文档
     */
    void save(FileItem fileItem);

    /**
     * 根据磁盘名删除文件文档
     *
     * @param diskName 磁盘名
     */
    void deleteFileItemByDiskName(String diskName);

    /**
     * 搜索文件
     *
     * @param queryBuilder 请求参数
     * @param pageable     分页参数
     * @return 搜索结果
     */
    Page<FileItem> search(QueryBuilder queryBuilder, Pageable pageable);
}
