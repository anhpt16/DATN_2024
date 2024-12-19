package com.nhatanh.centerlearn.common.service;

import com.nhatanh.centerlearn.common.converter.EntityToModelConverter;
import com.nhatanh.centerlearn.common.converter.ModelToEntityConverter;
import com.nhatanh.centerlearn.common.entity.UserTerm;
import com.nhatanh.centerlearn.common.model.UserTermModel;
import com.nhatanh.centerlearn.common.repository.UserTermRepository;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserTermService {
    private final UserTermRepository userTermRepository;
    private final EntityToModelConverter entityToModelConverter;

    public UserTermModel getUserTermByIdAndCreatorId(long userTermId, long accountId) {
        UserTerm userTerm = this.userTermRepository.findUserTermByIdAndCreatorId(userTermId, accountId);
        if (userTerm == null) {
            throw new HttpNotFoundException("UserTermId - AccountId Not Found");
        }
        return this.entityToModelConverter.toUserTermModel(userTerm);
    }

    public String getUserTermNameById(long userTermId) {
        UserTerm userTerm = this.userTermRepository.findById(userTermId);
        if (userTerm == null) {
            return null;
        }
        return userTerm.getTerm();
    }
}
