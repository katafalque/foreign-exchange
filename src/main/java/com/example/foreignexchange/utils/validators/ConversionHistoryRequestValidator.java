package com.example.foreignexchange.utils.validators;

import com.example.foreignexchange.model.request.ConversionHistoryRequestModel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConversionHistoryRequestValidator implements ConstraintValidator<ValidateConversionHistoryRequestModel, ConversionHistoryRequestModel> {
    @Override
    public boolean isValid(ConversionHistoryRequestModel requestModel, ConstraintValidatorContext context) {
        if (requestModel == null)
            return false;

        return (requestModel.getId() != null || requestModel.getDate() != null) &&
                requestModel.getPage() >= 0 && requestModel.getSize() > 0;
    }
}
