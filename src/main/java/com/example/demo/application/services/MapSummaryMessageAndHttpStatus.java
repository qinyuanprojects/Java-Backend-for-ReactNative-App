package com.example.demo.application.services;

import com.example.demo.application.interfaces.IMapSummaryMessageAndHttpStatus;
import com.example.demo.domain.Summary;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class MapSummaryMessageAndHttpStatus implements IMapSummaryMessageAndHttpStatus
{
    public ResponseEntity<Summary> returnResponseWithSummary (String message, Enum httpStatus, Summary summary)
    {
        Summary newSummary = new Summary(null, null, null);

        if (summary == null)
        {
            summary = newSummary;
        }

        summary.message = message;

        return new ResponseEntity<>(summary, (HttpStatusCode) httpStatus);
    }
}

