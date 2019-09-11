/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.retweet.web.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.BaseHttpStatus;
import org.iharu.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.http.HttpServletRequest;
import kcwiki.retweet.cache.inmem.RuntimeValue;
import org.iharu.exception.BaseException;
import org.iharu.type.error.ErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author iHaru
 */
@RestController
@RequestMapping("/file")
public class DownloadFile extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(DownloadFile.class);
    
    @Autowired
    RuntimeValue runtimeValue;
    
    /**
     * https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/
     * @param request
     * @param type
     * @param filename
     * @return
     * @throws IOException 
     */
    @GetMapping(value = "/{type}/{filename}")
    public ResponseEntity<Resource> download(HttpServletRequest request, @PathVariable("type") String type, @PathVariable("filename") String filename) throws IOException {
        // Load file as Resource
        Resource resource;
        try {
            File file = new File(String.format("%s/%s/%s", runtimeValue.WEBROOT_FOLDER, type, filename));
            if(!file.exists())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            resource = new UrlResource(file.toPath().toUri());
            if(!resource.exists()){
                throw new BaseException(ErrorType.OPERATION_ERROR, "File not found ");
            }
        } catch (MalformedURLException ex) {
            throw new BaseException(ErrorType.OPERATION_ERROR, ex);
        }

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            LOG.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
        
//        byte[] data = Files.readAllBytes(new File("/opt/iharu/girlsfrontline/gf-mitm.pem").toPath());
//        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(data);
    }
    
}
