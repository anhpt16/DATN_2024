package com.nhatanh.centerlearn.admin.service;

import com.nhatanh.centerlearn.admin.converter.AdminEntityToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToModelConverter;
import com.nhatanh.centerlearn.admin.model.RoomModel;
import com.nhatanh.centerlearn.admin.model.SaveRoomModel;
import com.nhatanh.centerlearn.admin.model.TermModel;
import com.nhatanh.centerlearn.admin.repo.RoomRepository;
import com.nhatanh.centerlearn.admin.repo.TermRepository;
import com.nhatanh.centerlearn.admin.result.IdResult;
import com.nhatanh.centerlearn.common.entity.Room;
import com.nhatanh.centerlearn.common.entity.Term;
import com.nhatanh.centerlearn.common.enums.RoomStatus;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class RoomService {
    private final AdminModelToEntityConverter modelToEntityConverter;
    private final AdminEntityToModelConverter entityToModelConverter;
    private final AdminModelToModelConverter modelToModelConverter;
    private final RoomRepository roomRepository;

    public void addRoom(SaveRoomModel model) {
        this.roomRepository.save(this.modelToEntityConverter.toRoomEntityConverter(model));
    }

    public void updateRoomById(long id, SaveRoomModel model) {
        Room entity = this.roomRepository.findById(id);
        if (entity == null) {
            throw new ResourceNotFoundException("room");
        } else {
            this.modelToEntityConverter.mergeToSaveEntity(model, entity);
            this.roomRepository.save(entity);
        }
    }

    public void deleteRoomById(long id) {
        Room room = roomRepository.findById(id);
        if (room == null) {
            throw new ResourceNotFoundException("room");
        } else {
            this.roomRepository.delete(id);
        }
    }

    public List<String> getRoomStatus() {
        List<String> statuses = Arrays.stream(RoomStatus.values())
            .map(Enum::name)
            .collect(Collectors.toList());
        return statuses;
    }

    public PaginationModel<RoomModel> getRoomsByTermId(long id, int page, int size) {
        long totalPage = (long) Math.ceil((double) this.roomRepository.countByTermId(id) / size);
        if (page > totalPage) {
            throw new ResourceNotFoundException("page", "invalid");
        }
        List<RoomModel> roomModels = newArrayList(this.roomRepository.findByTermId(id, Next.fromPageSize(page, size)), entityToModelConverter::toModel);
        return this.modelToModelConverter.toRoomModelPagination(roomModels, totalPage, page);
    }

    public PaginationModel<RoomModel> getAllRoomPagination(int page, int size) {
        long totalPage = (long) Math.ceil((double) this.roomRepository.count() / size);
        if (page > totalPage) {
            throw new ResourceNotFoundException("page", "invalid");
        }
        List<RoomModel> roomModels = newArrayList(this.roomRepository.findAllRoom(Next.fromPageSize(page, size)), entityToModelConverter::toModel);
        return this.modelToModelConverter.toRoomModelPagination(roomModels, totalPage, page);
    }

    public RoomModel getRoomById(long id) {
        Room result = roomRepository.findById(id);
        return result == null ? null : this.entityToModelConverter.toModel(result);
    }

    public long getTermIdByRoomId(long id) {
        if (roomRepository.findById(id) == null) {
            throw new ResourceNotFoundException("RoomId", "invalid");
        }
        IdResult result = roomRepository.findTermIdByRoomId(id);
        return result != null ? result.getId() : 0L;
    }

}
