package com.bc.wd.server.controller.search;

import com.bc.wd.server.cons.Constant;
import com.bc.wd.server.entity.FileItem;
import com.bc.wd.server.enums.ResponseMsg;
import com.bc.wd.server.service.FileItemService;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/fileItems")
public class FileItemController {

    private static final Logger logger = LoggerFactory.getLogger(FileItemController.class);

    private static final int THREAD_NUM = 10;
    @Resource
    FileItemService fileItemService;

    /**
     * 创建文件文档
     *
     * @param fileName 文件名
     * @param filePath 文件路径
     * @param diskName 磁盘名
     * @param fileSize 文件大小
     * @return ResponseEntity<String>
     */
    @ApiOperation(value = "创建文件文档", notes = "创建文件文档")
    @PostMapping(value = "")
    public ResponseEntity<String> createFileItemDocument(
            @RequestParam String fileName,
            @RequestParam String filePath,
            @RequestParam String diskName,
            @RequestParam String fileSize) {
        ResponseEntity<String> responseEntity;
        FileItem fileItem = new FileItem(fileName, filePath, diskName, fileSize);
        try {
            fileItemService.save(fileItem);
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.FILE_ITEM_DOCUMENT_ADD_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.FILE_ITEM_DOCUMENT_ADD_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 根据磁盘名删除文件文档
     *
     * @param diskName 磁盘名
     */
    @ApiOperation(value = "根据磁盘名删除文件文档", notes = "根据磁盘名删除文件文档")
    @DeleteMapping(value = "")
    public ResponseEntity<String> deleteFileItemByDiskName(
            @RequestParam String diskName) {
        ResponseEntity<String> responseEntity;
        try {
            fileItemService.deleteFileItemByDiskName(diskName);
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.FILE_ITEM_DOCUMENT_DELETE_SUCCESS.getResponseCode(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity<>(
                    ResponseMsg.FILE_ITEM_DOCUMENT_DELETE_ERROR.getResponseCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    /**
     * 搜索文件(版本号v1: 简单查询)
     *
     * @param searchKey     关键字
     * @param page          页数(默认第1页)
     * @param pageSize      分页大小(默认单页10条)
     * @param sortField     排序字段
     * @param sortDirection ASC: "升序"  DESC:"降序"
     * @return Page<FileItem>
     */
    @ApiOperation(value = "搜索文件(版本号v1: 简单查询)", notes = "搜索文件(版本号v1: 简单查询)")
    @GetMapping(value = "/v1")
    public Page<FileItem> searchV1(@RequestParam String searchKey,
                                   @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "sortField", required = false) String sortField,
                                   @RequestParam(value = "sortDirection", required = false) String sortDirection) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (!StringUtils.isEmpty(searchKey)) {
            // must
            // 根据关键字匹配若干字段,文件名、文件路径
            MultiMatchQueryBuilder keywordMmqb = QueryBuilders.multiMatchQuery(searchKey,
                    "fileName", "filePath");
            boolQuery = boolQuery.must(keywordMmqb);
        }
        Pageable pageable = generatePageable(page, pageSize, sortField, sortDirection);
        return fileItemService.search(boolQuery, pageable);
    }

    /**
     * 生成分页参数
     *
     * @param page          页数(默认第1页)
     * @param pageSize      分页大小(默认单页10条)
     * @param sortField     排序字段
     * @param sortDirection ASC: "升序"  DESC:"降序"
     * @return 分页参数
     */
    private Pageable generatePageable(Integer page, Integer pageSize, String sortField, String sortDirection) {
        page = page < 1 ? 0 : page - 1;
        Pageable pageable;
        if (!StringUtils.isEmpty(sortField)) {
            // 需要排序
            Sort.Direction direction;
            if (Constant.SORT_DIRECTION_ASC.equalsIgnoreCase(sortDirection)) {
                direction = Sort.Direction.ASC;
            } else {
                direction = Sort.Direction.DESC;
            }
            Sort sort = new Sort(direction, sortField);
            pageable = PageRequest.of(page, pageSize, sort);
        } else {
            pageable = PageRequest.of(page, pageSize);
        }
        return pageable;
    }
}
