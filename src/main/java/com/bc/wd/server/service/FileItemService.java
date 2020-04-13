package com.bc.wd.server.service;

import com.bc.wd.server.entity.FileItem;

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
}
