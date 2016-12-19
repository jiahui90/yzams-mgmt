/*
 * ApplicationConfig.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-12-01 12:13:19
 */
package com.yz.ams.server.ios.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import java.text.SimpleDateFormat;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Hu Qin<huqin@yzhtech.com>
 */
@javax.ws.rs.ApplicationPath("v1")
public class ApplicationConfig extends Application{
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    @Override
    public Set<Object> getSingletons() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JaxbAnnotationModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX"));
        Set<Object> resources = new java.util.HashSet<>();
        mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
        resources.add(new JacksonJaxbJsonProvider(mapper,
                JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS));

        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.yz.ams.server.ios.api.resource.ApplyResource.class);
        resources.add(com.yz.ams.server.ios.api.resource.AttendanceResource.class);
        resources.add(com.yz.ams.server.ios.api.resource.AuditResource.class);
        resources.add(com.yz.ams.server.ios.api.resource.TimeResource.class);
        resources.add(com.yz.ams.server.ios.api.resource.VacationResource.class);
    }
    
}
