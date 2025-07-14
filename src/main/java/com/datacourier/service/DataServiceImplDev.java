package com.datacourier.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
@Profile("dev")
@Service
public class DataServiceImplDev implements DataService {
    @Override
    public String getData() {
        return "Development profile is active";
    }
}
