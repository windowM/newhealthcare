package com.example.newhealthcare.controller;

import com.example.newhealthcare.Header;
import com.example.newhealthcare.model.network.request.doctor.DoctorPreApiRequest;
import com.example.newhealthcare.model.network.response.doctor.DoctorPreApiResponse;
import com.example.newhealthcare.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("{user}/{id}/prescription")
public class PrescriptionController {
    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping("")
    public Header<DoctorPreApiResponse> create(@PathVariable String user, @PathVariable String id, @RequestBody Header<DoctorPreApiRequest> request){
        return prescriptionService.create(user,id,request);
    }
}
