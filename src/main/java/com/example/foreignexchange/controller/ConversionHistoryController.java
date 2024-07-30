package com.example.foreignexchange.controller;

import com.example.foreignexchange.model.dto.ConversionHistoryDto;
import com.example.foreignexchange.model.pagination.ConversionHistoryPageResponse;
import com.example.foreignexchange.model.request.ConversionHistoryRequestModel;
import com.example.foreignexchange.model.response.ErrorResponse;
import com.example.foreignexchange.service.ConversionHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversionHistoryController {
    private final ConversionHistoryService conversionHistoryService;

    @Autowired
    public ConversionHistoryController(ConversionHistoryService conversionHistoryService){
        this.conversionHistoryService = conversionHistoryService;
    }

    @PostMapping("/conversion-history")
    public ResponseEntity getHistory(
            @Valid @RequestBody ConversionHistoryRequestModel conversionHistoryRequestModel,
            BindingResult validationResult
    ){
        try{
            if (validationResult.hasErrors()){
                return new ResponseEntity<>(createErrorResponse("invalid-request"), HttpStatus.BAD_REQUEST);
            }
            ConversionHistoryPageResponse<ConversionHistoryDto> pagedResponse = this.conversionHistoryService.getByFilter(conversionHistoryRequestModel);

            return new ResponseEntity<>(pagedResponse, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(createErrorResponse("server-error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ErrorResponse createErrorResponse(String message){
        return ErrorResponse.builder()
                .result("failed")
                .message(message)
                .build();
    }
}