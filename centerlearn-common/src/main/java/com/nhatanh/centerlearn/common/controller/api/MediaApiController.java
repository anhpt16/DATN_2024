package com.nhatanh.centerlearn.common.controller.api;

import com.nhatanh.centerlearn.common.controller.controller.MediaServiceController;
import com.nhatanh.centerlearn.common.filter.MediaFilterCriteria;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.request.ImageNameRequest;
import com.nhatanh.centerlearn.common.response.GalleryResponse;
import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import com.tvd12.ezyhttp.server.core.request.RequestArguments;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Optional;

@Api
@Controller("/api/v1/media")
@AllArgsConstructor
public class MediaApiController {
    private final MediaServiceController mediaServiceController;

    @DoGet("/image")
    public PaginationModel<GalleryResponse> getGalleryPaginationByAccountId(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam (value = "size", defaultValue = "12") int size,
        @RequestParam (value = "sortOrder", defaultValue = "2") int sortOrder,
        @RequestParam (value = "keyword") String keyword
    ) {
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        MediaFilterCriteria criteria = MediaFilterCriteria.builder()
            .ownerAccountId(accountId)
            .name(keyword)
            .sortOrder(sortOrder)
            .build();
        PaginationModel<GalleryResponse> galleryPaginationResponse = this.mediaServiceController.getGalleryPaginationByAccountId(
            criteria,
            page,
            size
        );
        return galleryPaginationResponse;
    }

    @DoPost("/image")
    public ResponseEntity uploadImage(
        RequestArguments requestArguments
    ) throws Exception {
        this.mediaServiceController.upload(requestArguments.getRequest());
        return  ResponseEntity.noContent();
    }

    @DoPut("/image/{id}")
    public ResponseEntity updateImageById(
        @PathVariable long id,
        @RequestBody ImageNameRequest request
        ) {
        //Validate

        //Process
        this.mediaServiceController.updateImageById(id, request.getName());
        return ResponseEntity.noContent();
    }

    @DoDelete("/image/{id}")
    public ResponseEntity deleteGalleryById(
        RequestArguments arguments,
        @PathVariable long id
    ) throws Exception {
        this.mediaServiceController.remove(arguments.getRequest(), id);
        return ResponseEntity.noContent();
    }

    @DoGet("/image/{id}")
    public ResponseEntity getGalleryById(
        @PathVariable long id
    ) {
        GalleryResponse galleryResponse = this.mediaServiceController.getGalleryById(id);
        if (galleryResponse == null) {
            return ResponseEntity.notFound("Image with id: " + id + " invalid");
        }
        return ResponseEntity.ok(galleryResponse);
    }

    @DoGet("/{imageName}")
    public void getImage(HttpServletResponse response, @PathVariable("imageName") String imageName) {
        String imagePath = "G:/media/" + imageName;
        File file = new File(imagePath);

        // Kiểm tra sự tồn tại của tệp
        if (!file.exists()) {
            throw new HttpNotFoundException("Image file does not exist.");
        }

        // Kiểm tra xem file có phải là tệp hình ảnh
        if (!file.isFile()) {
            throw new HttpNotFoundException("The path is not a valid image file.");
        }

        // Xác định loại MIME dựa trên phần mở rộng tệp
        String mimeType = "image/jpeg"; // Mặc định là JPEG
        String extension = getExtension(imageName);
        if ("png".equalsIgnoreCase(extension)) {
            mimeType = "image/png";
        } else if ("gif".equalsIgnoreCase(extension)) {
            mimeType = "image/gif";
        } else if ("webp".equalsIgnoreCase(extension)) {
            mimeType = "image/webp";
        }

        // Thiết lập các headers
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", "inline; filename=\"" + imageName + "\"");

        // Đọc tệp và ghi dữ liệu vào OutputStream của response
        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new HttpNotFoundException("Failed to read image file: " + imageName);
        }
    }

    private String getExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot != -1 && lastDot < fileName.length() - 1) {
            return fileName.substring(lastDot + 1);
        }
        return "bin"; // mặc định nếu không có phần mở rộng
    }
}
