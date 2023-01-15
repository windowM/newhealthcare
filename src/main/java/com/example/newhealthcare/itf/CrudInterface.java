package com.example.newhealthcare.itf;

import com.example.newhealthcare.Header;


public interface CrudInterface<Req,Res> {
    Header<Res> create(Header<Req> request);
    Header<Res> read(String id);
    Header<Res> update(Header<Req> request);
    Header delete(String id);
}
