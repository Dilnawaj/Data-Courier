package com.datacourier.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
@Profile("prod")
@Service
public class DataServiceImplProd implements DataService{
    @Override
    public String getData() {
        return "Production profile is active";
    }
}
