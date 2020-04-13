package com.bc.wd.server.service.impl;

import com.bc.wd.server.entity.FileItem;
import com.bc.wd.server.es.repository.FileItemRepository;
import com.bc.wd.server.service.FileItemService;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 保存文件文档
     *
     * @param fileItem 文件文档
     */
    @Override
    public void save(FileItem fileItem) {
        fileItemRepository.save(fileItem);
    }
}
