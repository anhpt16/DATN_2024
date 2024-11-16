package com.nhatanh.centerlearn.admin.test.repository;


import com.nhatanh.centerlearn.admin.repo.TermRepository;
import com.nhatanh.centerlearn.admin.result.IdResult;
import com.nhatanh.centerlearn.admin.result.TermNameResult;
import com.nhatanh.centerlearn.admin.result.TermTypeResult;
import com.nhatanh.centerlearn.common.entity.Term;
import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationAfter;
import com.tvd12.ezyfox.util.Next;
import lombok.AllArgsConstructor;

import java.util.*;

@EzyConfigurationAfter
@AllArgsConstructor
public class TermRepositoryTest implements EzyBeanConfig {

    private final TermRepository termRepository;

    @Override
    public void config() {
        Set<Long> ids = new HashSet<>(Arrays.asList(1L, 2L, 3L));
        // when
        List<Term> terms = termRepository.findAllTerms(Next.fromPageSize(1, 10));
        List<TermTypeResult> termTypes = termRepository.findAllTermTypes();
        List<Term> termsByIds = termRepository.findListByIds(ids);
        List<TermNameResult> termNames = termRepository.findNamesContainingKeyword("ME");
        List<TermNameResult> typesByName = termRepository.findTypesByName("ADMIN_MENU");
        IdResult id = termRepository.findIdByTermNameAndType("Manage", "menu");
        Term termFind = termRepository.findById(1);
        // then
        System.out.println(terms.size());
    }
}
