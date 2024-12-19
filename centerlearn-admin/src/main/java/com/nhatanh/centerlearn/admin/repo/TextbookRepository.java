package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.admin.result.IdResult;
import com.nhatanh.centerlearn.common.entity.Textbook;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository
public interface TextbookRepository extends EzyDatabaseRepository<Long, Textbook> {

    @EzyQuery("SELECT t.id FROM Textbook t WHERE t.name = ?0")
    IdResult findTextbookIdByName(String name);

    @EzyQuery("SELECT t FROM Textbook t WHERE t.slug = ?0")
    Textbook findTextbookBySlug(String slug);
}
