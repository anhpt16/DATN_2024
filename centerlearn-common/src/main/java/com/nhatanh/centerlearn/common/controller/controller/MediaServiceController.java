package com.nhatanh.centerlearn.common.controller.controller;

import com.nhatanh.centerlearn.common.constant.Constants;
import com.nhatanh.centerlearn.common.controller.decorator.MediaModelDecorator;
import com.nhatanh.centerlearn.common.converter.ModelToResponseConverter;
import com.nhatanh.centerlearn.common.converter.RequestToModelConverter;
import com.nhatanh.centerlearn.common.filter.MediaFilterCriteria;
import com.nhatanh.centerlearn.common.model.GalleryModel;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.response.GalleryResponse;
import com.nhatanh.centerlearn.common.service.MediaService;
import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
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
    private final MediaService mediaService;
    private final RequestToModelConverter requestToModelConverter;
    private final ModelToResponseConverter modelToResponseConverter;
    private final MediaModelDecorator mediaModelDecorator;

    public PaginationModel<GalleryResponse> getGalleryPaginationByAccountId(
        MediaFilterCriteria criteria,
        int page,
        int size)
    {
        PaginationModel<GalleryModel> galleryPaginationModel = this.mediaService.getGalleryPaginationByAccountId(criteria, page, size);
        return this.mediaModelDecorator.toGalleryPaginationResponse(galleryPaginationModel);
    }

    public List<GalleryResponse> getGalleryByAccountId(long id) {
        List<GalleryModel> models = this.mediaService.getGalleryByAccountId(id);
        if (models.isEmpty()) {
            return Collections.emptyList();
        }
        return this.modelToResponseConverter.toGalleryListResponse(models);
    }

    public GalleryResponse getGalleryById(long id) {
        GalleryModel model = this.mediaService.getGalleryById(id);
        if (model == null) {
            return null;
        }
        return this.modelToResponseConverter.toGalleryResponse(model);
    }

    public void updateImageById(long id, String name) {
        this.mediaService.updateImageById(id, name);
    }

    public void upload(HttpServletRequest request) throws Exception {
        // Lấy mã người gửi
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        // Lấy phần từ request tương ứng với file đã gửi lên
        Part filePart = request.getPart("file"); // "file" là tên field trong FormData của client
        // Lấy tên file
        String fileNameWithExt = filePart.getSubmittedFileName();
        String fileName = fileNameWithExt.substring(0, fileNameWithExt.lastIndexOf('.'));
        System.out.println(fileName);
        // Lấy dung lượng của file
        long size = filePart.getSize();
        // Lấy định dạng
        String mediaType = filePart.getContentType();
        // Lấy đường dẫn của ảnh
        String fileExtension = fileNameWithExt.substring(fileNameWithExt.lastIndexOf('.'));
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        File targetFile = new File(Constants.uploadDir + uniqueFileName); // Đường dẫn thư mục lưu trữ file
        String url = uniqueFileName;
        // Tạo đường dẫn file và lưu file lên server
        try (InputStream inputStream = filePart.getInputStream();
             OutputStream outputStream = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            // Ghi file
            // Ghi thông tin file vào database
            this.mediaService.uploadImage(this.requestToModelConverter.toImageUploadModel(
                fileName, url, mediaType, accountId, size
            ));
        } catch (Exception e) {
            throw new IOException("Write File Fail");
        }
    }

    public void remove(HttpServletRequest request, long imageId) throws Exception {
        // Lấy id của người xóa ảnh
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        // Tìm ảnh của người muốn xóa
        GalleryModel galleryModel = this.mediaService.getGalleryByAccountIdAndId(accountId, imageId);
        if (galleryModel == null) {
            throw new HttpNotFoundException("User with id: " + accountId + " and Image with id: " + imageId + " invalid");
        }
        // Xóa ảnh khỏi thư mục
        String filePath = Constants.uploadDir + galleryModel.getUrl();
        File imageFile = new File(filePath);
        if (imageFile.exists() && imageFile.isFile()) {
            boolean deleted = imageFile.delete();
            if (!deleted) {
                throw new IOException("Failed to delete image from file system.");
            }
        } else {
            throw new HttpNotFoundException("Image with id: " + imageId + " not found on server");
        }
        // Xóa dữ liệu của ảnh khỏi CSDL
        this.mediaService.deleteGalleryById(imageId);
        System.out.println("Delete Image With ID: " + imageId);
    }
}
