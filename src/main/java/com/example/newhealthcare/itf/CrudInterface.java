package com.example.newhealthcare.itf;

import com.example.newhealthcare.Header;
import org.codehaus.groovy.runtime.metaclass.MetaMethodIndex;

public interface CrudInterface<Req,Res> {
    Header<Res> create(Header<Req> request);
    Header<Res> read(String id);
    Header<Res> update(Header<Req> request);
    Header delete(String id);
}
