package com.example.newhealthcare.controller;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.itf.CrudInterface;
import com.example.newhealthcare.model.network.request.DandPApiRequest;
import com.example.newhealthcare.model.network.response.DandPApiResponse;
import com.example.newhealthcare.service.DandPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DandPController implements CrudInterface<DandPApiRequest, DandPApiResponse> {
    @Autowired
    private DandPService dandPService;

    @Override
    public Header<DandPApiResponse> create(Header<DandPApiRequest> request) {
        return null;
    }

    @Override
    public Header<DandPApiResponse> read(String id) {
        return null;
    }

    @Override
    public Header<DandPApiResponse> update(Header<DandPApiRequest> request) {
        return null;
    }

    @Override
    public Header delete(String id) {
        return null;
    }
}
