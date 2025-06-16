package com.example.demo.application.interfaces;

import com.example.demo.domain.Transaction;

import java.util.Map;

public interface IMappingUsersAndId
{
    Transaction mapIdAndSoOn(Map<String, Object> objectMap);
}
