package com.nhatanh.centerlearn.admin.repo;


import com.nhatanh.centerlearn.admin.result.IdResult;
import com.nhatanh.centerlearn.admin.result.NumberResult;
import com.nhatanh.centerlearn.admin.result.TermNameResult;
import com.nhatanh.centerlearn.admin.result.TermTypeResult;
import com.nhatanh.centerlearn.common.entity.Term;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;

import java.util.Collection;
import java.util.List;

@EzyRepository
public interface TermRepository extends EzyDatabaseRepository<Long, Term>{
    List<Term> findAllTerms(Next next);
    List<Term> findListByIds(Collection<Long> ids);
    List<Term> findByTermType(String termType, Next next);
    List<Term> findByTermType(String termType);
    Term findById(long id);
    long countByTermType(String termType);

    @EzyQuery("SELECT t.id FROM Term t WHERE t.name = ?0 AND t.termType = ?1")
    IdResult findIdByTermNameAndType(String name, String type);

    @EzyQuery("SELECT t.termType FROM Term t WHERE t.name = ?0")
    List<TermNameResult> findTypesByName(String keyword);

    @EzyQuery("SELECT DISTINCT e.termType as termType FROM Term e")
    List<TermTypeResult> findAllTermTypes();

    @EzyQuery("SELECT DISTINCT t.name FROM Term t WHERE t.name LIKE CONCAT('%', ?0, '%')")
    List<TermNameResult> findNamesContainingKeyword(String keyword);

}
