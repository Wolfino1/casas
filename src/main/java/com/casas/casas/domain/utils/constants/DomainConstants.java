package com.casas.casas.domain.utils.constants;

public final class DomainConstants {
    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    /*
    Validation messages
     */
    public static final String FIELD_NAME_NULL_MESSAGE = "El campo 'nombre' no puede estar vacío";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "El campo 'descripción' no puede estar vacío";
}
