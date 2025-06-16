package com.example.demo.application.interfaces;

import com.example.demo.domain.Summary;

public interface ISummaryRepository
{
    Summary fetchSummary(String user_id);
}
