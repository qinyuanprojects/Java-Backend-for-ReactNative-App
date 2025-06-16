package com.example.demo.application.services;

import com.example.demo.application.interfaces.IMappingUsersAndId;
import com.example.demo.domain.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;


@Repository
public class MappingUsersAndId implements IMappingUsersAndId
{
    public Transaction mapIdAndSoOn(Map<String, Object> objectMap)
    {
        Transaction mapId = new Transaction(null, null, null, null, null, null);

        mapId.id = (String) objectMap.get("Id");
        mapId.user_id = (String) objectMap.get("User Id");
        mapId.title = (String) objectMap.get("Title");
        mapId.amount = convertToBigDecimal(objectMap);
        mapId.category = (String) objectMap.get("Category");

        return mapId;
    }

    public BigDecimal convertToBigDecimal(Map<String, Object> objectMap)
    {
        try
        {
            return BigDecimal.valueOf((Integer) objectMap.get("Amount"));
        }
        catch (ClassCastException e)
        {
            return BigDecimal.valueOf((Double) objectMap.get("Amount"));
        }
    }
}
