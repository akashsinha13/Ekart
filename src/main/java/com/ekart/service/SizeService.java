package com.ekart.service;

import com.ekart.dao.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SizeService {

    @Autowired
    private SizeRepository sizeRepository;
}
