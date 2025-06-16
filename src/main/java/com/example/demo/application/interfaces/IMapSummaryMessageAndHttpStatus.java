package com.example.demo.application.interfaces;

import com.example.demo.domain.Summary;
import org.springframework.http.ResponseEntity;

public interface IMapSummaryMessageAndHttpStatus
{
    ResponseEntity<Summary> returnResponseWithSummary (String message, Enum httpStatus, Summary summary);
}
