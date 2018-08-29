package com.mayweather.api.v1.mapper;

import com.mayweather.api.v1.model.CategoryDTO;
import com.mayweather.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);


    CategoryDTO categoryToCategoryDTO(Category category);
}
