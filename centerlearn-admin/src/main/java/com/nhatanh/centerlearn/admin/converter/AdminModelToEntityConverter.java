package com.nhatanh.centerlearn.admin.converter;

import com.nhatanh.centerlearn.admin.model.*;
import com.nhatanh.centerlearn.common.entity.*;
import com.nhatanh.centerlearn.common.enums.SubjectStatus;
import com.nhatanh.centerlearn.common.utils.Base64Util;
import com.nhatanh.centerlearn.common.utils.ClockProxy;
import com.nhatanh.centerlearn.common.utils.SlugGenerate;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class AdminModelToEntityConverter {
    protected final ClockProxy clock;
    private final Base64Util base64Util;

    public AccountRoleId toAccountRoleId(AccountRoleModel model) {
        AccountRoleId entityId = new AccountRoleId();
        this.mergeToEntityId(model, entityId);
        return entityId;
    }

    public Term toTermEntityConverter(SaveTermModel model) {
        Term entity = new Term();
        this.mergeToEntity(model, entity);
        return entity;
    }
    public Room toRoomEntityConverter(SaveRoomModel model) {
        Room entity = new Room();
        this.mergeToEntity(model, entity);
        return entity;
    }
    public Timeslot toTimeslotEntityConverter(SaveTimeslotModel model) {
        Timeslot entity = new Timeslot();
        this.mergeToEntity(model, entity);
        return entity;
    }
    public Role toRoleEntityConverter(SaveRoleModel model) {
        Role entity = new Role();
        this.mergeToEntity(model, entity);
        return entity;
    }

    public Permission toPermissionEntityConverter(SavePermissionModel model) {
        Permission entity = new Permission();
        this.mergeToEntity(model, entity);
        return entity;
    }

    public Account toAccountEntityConverter(SaveAccountModel model) {
        Account entity = new Account();
        this.mergeToEntity(model, entity);
        return entity;
    }

    public AccountRole toAccountRoleEntityConverter(AccountRoleModel model) {
        AccountRole entity = new AccountRole();
        this.mergeToEntity(model, entity);
        return entity;
    }

    public Subject toSubjectEntityConverter(AddSubjectModel model) {
        Subject entity = new Subject();
        this.mergeToEntity(entity, model);
        return entity;
    }

    public Textbook toTextbookEntityConverter(AddTextbookModel model) {
        Textbook entity = new Textbook();
        this.mergeToEntity(entity, model);
        return entity;
    }

    public SubjectTextbook toSubjectTextbookEntityConverter(SubjectTextbookModel model) {
        SubjectTextbook entity = new SubjectTextbook();
        this.mergeToEntity(entity, model);
        return entity;
    }

    public void mergeToEntity(SubjectTextbook entity, SubjectTextbookModel model) {
        entity.setSubjectId(model.getSubjectId());
        entity.setTextbookId(model.getTextbookId());
    }

    public void mergeToEntity(Textbook entity, AddTextbookModel model) {
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        entity.setAuthor(model.getAuthor());
        entity.setUrl(model.getUrl());
        entity.setStatus(model.getStatus());
        entity.setCreatedAt(this.clock.nowDateTime());
        entity.setUpdatedAt(this.clock.nowDateTime());
        entity.setSlug(model.getSlug());
    }

    public void mergeToEntity(AccountRoleModel model, AccountRole entity) {
        entity.setRoleId(model.getRoleId());
        entity.setAccountId(model.getAccountId());
    }

    public void mergeToEntityId(AccountRoleModel model, AccountRoleId entityId) {
        entityId.setAccountId(model.getAccountId());
        entityId.setRoleId(model.getRoleId());
    }

    public void mergeToEntity(SaveAccountModel model, Account entity) {
        entity.setUsername(model.getUsername());
        entity.setPassword(base64Util.encodePassword(model.getPassword()));
        entity.setDisplayName(model.getDisplayName());
        entity.setEmail(model.getEmail());
        entity.setPhone(model.getPhone());
        entity.setStatus(model.getStatus());
        entity.setCreatedAt(this.clock.nowDateTime());
        entity.setUpdatedAt(this.clock.nowDateTime());
        entity.setCreatorId(model.getCreatorId());
    }

    public void mergeToEntity(SavePermissionModel model, Permission entity) {
        entity.setRoleId(model.getRoleId());
        entity.setFeatureUri(model.getFeatureUri());
        entity.setFeatureMethod(model.getFeatureMethod());
        entity.setCreatedAt(this.clock.nowDateTime());
    }

    public void mergeToEntity(SaveRoleModel model, Role entity) {
        entity.setName(model.getName().toUpperCase());
        entity.setDisplayName(model.getDisplayName());
        entity.setCreatedAt(this.clock.nowDateTime());
    }
    public void mergeToEntity(SaveTimeslotModel model, Timeslot entity) {
        entity.setPeriod(model.getPeriod());
        entity.setTimeStart(model.getTimeStart());
        entity.setTimeEnd(model.getTimeEnd());
        entity.setDescription(model.getDescription());
        entity.setStatus(model.getStatus());
        entity.setCreatedAt(this.clock.nowDateTime());
        entity.setUpdatedAt(this.clock.nowDateTime());
    }

    public void mergeToSaveEntity(SaveRoomModel model, Room entity) {
        entity.setName(model.getName().toUpperCase());
        entity.setSlot(model.getSlot());
        entity.setTermId(model.getTermId());
        entity.setStatus(model.getStatus());
        entity.setDescription(model.getDescription());
        entity.setUpdatedAt(this.clock.nowDate());
    }

    public void mergeToEntity(SaveTermModel model, Term entity) {
        entity.setName(model.getName().toUpperCase());
        entity.setSlug(model.getSlug());
        entity.setTermType(model.getTermType());
        entity.setParentId(model.getParentId());
        entity.setDescription(model.getDescription());
    }

    public void mergeToEntity(SaveRoomModel model, Room entity) {
        entity.setName(model.getName().toUpperCase());
        entity.setSlot(model.getSlot());
        entity.setTermId(model.getTermId());
        entity.setDescription(model.getDescription());
        entity.setStatus(model.getStatus());
        entity.setCreatedAt(this.clock.nowDate());
        entity.setUpdatedAt(this.clock.nowDate());
    }

    public void mergeToSaveEntity(SaveTimeslotModel model, Timeslot entity) {
        entity.setPeriod(model.getPeriod());
        entity.setTimeStart(model.getTimeStart());
        entity.setTimeEnd(model.getTimeEnd());
        entity.setStatus(model.getStatus());
        entity.setDescription(model.getDescription());
        entity.setUpdatedAt(this.clock.nowDateTime());
    }

    public void mergeToSaveEntity(SaveRoleModel model, Role entity) {
        entity.setDisplayName(model.getDisplayName());
    }

    public void mergeToSaveEntity(String statusName, Account account) {
        account.setStatus(statusName.toUpperCase());
    }

    public void mergeToEntity(Subject entity, AddSubjectModel model) {
        entity.setName(model.getName().toUpperCase());
        entity.setDisplayName(model.getDisplayName());
        entity.setDescription(model.getDescription());
        entity.setStatus(model.getStatus());
        entity.setCreatedAt(this.clock.nowDateTime());
        entity.setUpdatedAt(this.clock.nowDateTime());
        entity.setImageId(model.getImageId());
        entity.setSlug(model.getSlug());
    }

    public void mergeToSaveEntity(Subject entity, SaveSubjectModel model) {
        if (model.getName() != null) {
            entity.setName(model.getName());
        }
        if (model.getDisplayName() != null) {
            entity.setDisplayName(model.getDisplayName());
            entity.setSlug(SlugGenerate.createSlugWithId(model.getDisplayName()));
        }
        if (model.getDescription() != null) {
            entity.setDescription(model.getDescription());
        }
        if (model.getImageId() > 0) {
            entity.setImageId(model.getImageId());
        }
        if (model.getStatus() != null) {
            entity.setStatus(model.getStatus().toUpperCase());
        }
        entity.setUpdatedAt(this.clock.nowDateTime());
    }

    public void mergeToSaveEntity(Textbook entity, SaveTextbookModel model) {
        if (model.getName() != null) {
            entity.setName(model.getName());
            entity.setSlug(SlugGenerate.createSlugWithId(model.getName()));
        }
        if (model.getDescription() != null) {
            entity.setDescription(model.getDescription());
        }
        if (model.getAuthor() != null) {
            entity.setAuthor(model.getAuthor());
        }
        if (model.getUrl() != null) {
            entity.setUrl(model.getUrl());
        }
        if (model.getStatus() != null) {
            entity.setStatus(model.getStatus().toUpperCase());
        }
        entity.setUpdatedAt(this.clock.nowDateTime());
    }
}
