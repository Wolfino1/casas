package com.casas.casas.domain.utils.constants;

public final class DomainConstants {
    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String FIELD_NAME_NULL_MESSAGE = "Field name cannot be empty";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "Field description cannot be empty";
    public static final String LISTING_DATE_IS_IN_MORE_THAN_ONE_MONTH = "listing date must be in less than a month";
    public static final String CATEGORY_DOES_NOT_EXIST = "Category not found";
    public static final String LOCATION_DOES_NOT_EXIST = "Location not found";
    public static final String PAGE_NUMBER_INVALID = "Page number cannot be less than 0";
    public static final String PAGE_SIZE_INVALID = "Page size cannot be less than 0";
    public static final String FIELD_ADDRESS_NULL_MESSAGE ="Field address cannot be null";
    public static final String ADDRESS_ALREADY_EXISTS = "That address is already in use";
    public static final String HOUSE_DOES_NOT_EXIST = "The house does not exist";
    public static final String DEPARTMENT_DOES_NOT_EXIST = "Department not found";
    public static final Long PUB_STATUS_IS_ACTIVE = 2L;
    public static final Long PUB_STATUS_IS_DRAFT = 1L;
    public static final Integer DAYS_TO_ADD = 1;
    public static final Integer MONTHS_TO_ADD = 1;


}
