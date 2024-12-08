package com.nhatanh.centerlearn.admin.controller.service;

import com.nhatanh.centerlearn.common.constant.Constants;
import com.nhatanh.centerlearn.common.converter.RequestToModelConverter;
import com.nhatanh.centerlearn.common.response.GalleryResponse;
import com.nhatanh.centerlearn.common.service.MediaService;
import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import com.tvd12.ezyhttp.server.core.resources.FileUploader;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@EzySingleton
@AllArgsConstructor
public class MediaServiceController {

    private final FileUploader fileUploader;
    private final MediaService mediaService;
    private final RequestToModelConverter requestToModelConverter;

    public List<GalleryResponse> getGalleryByAccountId(long id) {

        return Collections.emptyList();
    }

    public void upload(HttpServletRequest request) throws Exception {
        // Lấy mã người gửi
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        // Lấy phần từ request tương ứng với file đã gửi lên
        Part filePart = request.getPart("file"); // "file" là tên field trong FormData của client
        // Lấy tên file
        String fileName = filePart.getSubmittedFileName();
        System.out.println(fileName);
        // Lấy dung lượng của file
        long size = filePart.getSize();
        // Lấy định dạng
        String mediaType = filePart.getContentType();
        // Lấy đường dẫn của ảnh
        String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        File targetFile = new File(Constants.uploadDir + uniqueFileName); // Đường dẫn thư mục lưu trữ file
        String url = Constants.uploadDir + uniqueFileName;
        // Tạo đường dẫn file và lưu file lên server
        try (InputStream inputStream = filePart.getInputStream();
             OutputStream outputStream = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            // Ghi file
        } catch (Exception e) {
            throw new IOException("Write File Fail");
        }
        // Ghi thông tin file vào database
        this.mediaService.uploadImage(this.requestToModelConverter.toImageUploadModel(
            fileName, url, mediaType, accountId, size
        ));
    }
}
