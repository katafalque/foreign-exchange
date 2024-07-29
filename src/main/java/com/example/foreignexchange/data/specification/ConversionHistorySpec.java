package com.example.foreignexchange.data.specification;

import com.example.foreignexchange.data.entity.ConversionHistory;
import com.example.foreignexchange.model.request.ConversionHistoryRequestModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;


public class ConversionHistorySpec {
    public static final String ID = "id";
    public static final String DATE = "date";
    private ConversionHistorySpec(){

    }

    public static Specification<ConversionHistory> filterBy(ConversionHistoryRequestModel requestModel) {
        return (Root<ConversionHistory> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            // List to hold the predicates
            Predicate predicate = cb.conjunction();

            // Add predicates based on the filter values
            if (requestModel.getId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("id"), requestModel.getId().toString()));
            }

            if (requestModel.getDate() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("date"), requestModel.getDate()));
            }

            if (requestModel.getSource() != null && !requestModel.getSource().isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("source"), requestModel.getSource()));
            }


            return predicate;
        };
    }
}
