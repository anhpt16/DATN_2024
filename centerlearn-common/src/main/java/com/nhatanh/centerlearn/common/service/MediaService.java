package com.nhatanh.centerlearn.common.service;

import com.nhatanh.centerlearn.common.converter.EntityToModelConverter;
import com.nhatanh.centerlearn.common.converter.ModelToEntityConverter;
import com.nhatanh.centerlearn.common.entity.Media;
import com.nhatanh.centerlearn.common.entity.Term;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.filter.MediaFilterCriteria;
import com.nhatanh.centerlearn.common.model.AccountModel;
import com.nhatanh.centerlearn.common.model.GalleryModel;
import com.nhatanh.centerlearn.common.model.ImageUploadModel;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.repository.MediaRepository;
import com.nhatanh.centerlearn.common.repository.MediaRepositoryCustom;
import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class MediaService {
    private final MediaRepository mediaRepository;
    private final ModelToEntityConverter modelToEntityConverter;
    private final EntityToModelConverter entityToModelConverter;
    private final MediaRepositoryCustom mediaRepositoryCustom;

    public long uploadImage(ImageUploadModel model) {
        Media media = this.modelToEntityConverter.toMediaEntityConverter(model);
        this.mediaRepository.save(media);
        return media.getId();
    }

    public List<GalleryModel> getGalleryByAccountId(long accountId) {
        List<Media> medias = this.mediaRepository.findGalleryByAccountId(accountId);
        if (medias.isEmpty()) {
            return Collections.emptyList();
        }
        return this.entityToModelConverter.toGalleryListModel(medias);
    }

    public void updateImageById(long id, String name) {
        Media media = this.mediaRepository.findById(id);
        if (media == null) {
            throw new HttpNotFoundException("Image with id " + id + " invalid");
        } else {
            this.modelToEntityConverter.mergeToSaveEntity(media, name);
            this.mediaRepository.save(media);
        }
    }

    public GalleryModel getGalleryById(long id) {
        Media media = this.mediaRepository.findById(id);
        if (media == null) {
            return null;
        }
        return this.entityToModelConverter.toGalleryModel(media);
    }

    public GalleryModel getGalleryByAccountIdAndId(long accountId, long imageId) {
        Media media = this.mediaRepository.findGallaryByAccountIdAndId(accountId, imageId);
        if (media == null) {
            return null;
        }
        return this.entityToModelConverter.toGalleryModel(media);
    }

    public void deleteGalleryById(long imageId) {
        Media media = this.mediaRepository.findById(imageId);
        if (media == null) {
            throw new HttpNotFoundException("Image with id: " + imageId + " invalid");
        }
        this.mediaRepository.delete(imageId);
    }

    public PaginationModel<GalleryModel> getGalleryPaginationByAccountId(MediaFilterCriteria criteria, int page, int size) {
        long totalPage = (long) Math.ceil((double) this.mediaRepositoryCustom.countMediaByCriteria(criteria) / size);
        if (page > totalPage) {
            throw new ResourceNotFoundException("page", "invalid");
        }
        List<GalleryModel> galleryModels = newArrayList(this.mediaRepositoryCustom.findGalleryByCriteria(criteria, Next.fromPageSize(page, size)), entityToModelConverter::toGalleryModel);
        return PaginationModel.<GalleryModel>builder()
            .items(galleryModels)
            .totalPage(totalPage)
            .currentPage(page)
            .build();
    }

    public Map<Long, String> getImageUrlMapByIds(Collection<Long> ids) {
        return EzyMaps.newHashMapNewValues(this.getGalleriesMapByIds(ids), GalleryModel::getUrl);
    }

    public Map<Long, GalleryModel> getGalleriesMapByIds(Collection<Long> ids) {
        return ids.isEmpty() ? Collections.emptyMap() : this.mediaRepository.findListByIds(ids).stream()
            .collect(Collectors.toMap(Media::getId, this.entityToModelConverter::toGalleryModel, (o, n) -> n));
    }
}
