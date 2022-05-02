package com.LSJ.Blog.service;

import com.LSJ.Blog.dao.CategoryRepository;
import com.LSJ.Blog.domain.Category;
import com.LSJ.Blog.domain.Member;
import com.LSJ.Blog.dto.category.CategorySaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void save(CategorySaveForm categorySaveForm, Member findMember) {
        Category category = Category.createCategory(
                categorySaveForm.getId(),
                categorySaveForm.getName()
        );
        category.setMember(findMember);

        categoryRepository.save(category);
    }
}
