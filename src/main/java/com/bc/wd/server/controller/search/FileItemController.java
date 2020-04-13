package com.bc.wd.server.controller.search;

import com.bc.wd.server.entity.FileItem;
import com.bc.wd.server.enums.ResponseMsg;
import com.bc.wd.server.service.FileItemService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/fileItems")
public class FileItemController {

    private static final Logger logger = LoggerFactory.getLogger(FileItemController.class);

    @Resource
    FileItemService fileItemService;

    @ApiOperation(value = "创建文件文档", notes = "创建文件文档")
    @PostMapping(value = "")
    public ResponseEntity<String> saveFileItem(
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
}
