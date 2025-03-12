package com.casas.casas.domain.model;

import com.casas.casas.domain.exceptions.DescriptionMaxSizeExceededException;
import com.casas.casas.domain.exceptions.NameMaxSizeExceededException;
import com.casas.casas.domain.utils.constants.DomainConstants;
import com.casas.casas.infrastructure.entities.CategoryEntity;

import java.util.Objects;

    public class HomeModel {
        private Long id;
        private String name;
        private String description;
        private CategoryEntity category;

        public HomeModel(Long id, String name, String description, CategoryEntity category) {

            if (name.length() > 50) throw new NameMaxSizeExceededException();
            if (description.length() > 90) throw new DescriptionMaxSizeExceededException();
            this.id = id;
            this.name = Objects.requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
            this.description = Objects.requireNonNull(description,  DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
            this.category = Objects.requireNonNull(category,  DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            if (name.length() > 50) throw new NameMaxSizeExceededException();
            this.name = Objects.requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            if (description.length() > 90) throw new DescriptionMaxSizeExceededException();
            this.description = Objects.requireNonNull(description,  DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        }

        public CategoryEntity getCategory() {
            return category;
        }

        public void setCategory(CategoryEntity category) {
            this.category = category;}
}
