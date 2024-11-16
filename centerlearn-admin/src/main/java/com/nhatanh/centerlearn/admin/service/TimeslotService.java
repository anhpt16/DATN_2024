package com.nhatanh.centerlearn.admin.service;

import com.nhatanh.centerlearn.admin.converter.AdminEntityToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToModelConverter;
import com.nhatanh.centerlearn.admin.model.SaveTimeslotModel;
import com.nhatanh.centerlearn.admin.model.TimeslotModel;
import com.nhatanh.centerlearn.admin.repo.TimeslotRepository;
import com.nhatanh.centerlearn.common.entity.Timeslot;
import com.nhatanh.centerlearn.common.enums.RoomStatus;
import com.nhatanh.centerlearn.common.enums.TimeslotStatus;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class TimeslotService {
    private final AdminModelToEntityConverter modelToEntityConverter;
    private final AdminModelToModelConverter modelToModelConverter;
    private final AdminEntityToModelConverter entityToModelConverter;
    private final TimeslotRepository timeslotRepository;

    public void addTimeslot(SaveTimeslotModel model) {
        this.timeslotRepository.save(this.modelToEntityConverter.toTimeslotEntityConverter(model));
    }

    public void updateTimeslotById(long id, SaveTimeslotModel model) {
        Timeslot entity = timeslotRepository.findById(id);
        if (entity == null) {
            throw new ResourceNotFoundException("TimeslotId");
        } else {
            this.modelToEntityConverter.mergeToSaveEntity(model, entity);
            this.timeslotRepository.save(entity);
        }
    }

    public void deleteTimeslotById(long id) {
        Timeslot result = timeslotRepository.findById(id);
        if (result == null) {
            throw new ResourceNotFoundException("TimeslotId");
        } else {
            this.timeslotRepository.delete(id);
        }
    }

    public List<String> getTimeslotStatus() {
        List<String> statuses = Arrays.stream(TimeslotStatus.values())
            .map(Enum::name)
            .collect(Collectors.toList());
        return statuses;
    }

    public TimeslotModel getTimeslotById(long id) {
        Timeslot result = timeslotRepository.findById(id);
        return result == null ? null : this.entityToModelConverter.toModel(result);
    }

    public PaginationModel<TimeslotModel> getAllTimeSlot(int page, int size) {
        long totalPage = (long) Math.ceil((double) this.timeslotRepository.count() / size);
        if (page > totalPage) {
            throw new ResourceNotFoundException("page", "invalid");
        }
        List<TimeslotModel> models = newArrayList(this.timeslotRepository.getAllTimeslot(Next.fromPageSize(page, size)), entityToModelConverter::toModel);
        return this.modelToModelConverter.toTimeslotModelPagination(models, totalPage, page);
    }
}
