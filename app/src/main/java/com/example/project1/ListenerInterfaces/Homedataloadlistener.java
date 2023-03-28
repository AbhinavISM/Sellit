package com.example.project1.ListenerInterfaces;

import com.example.project1.data_classes.Property_model_class;

import java.util.List;

public interface Homedataloadlistener {
    void onHomedataloaded(List<Property_model_class> fullyloadeddata);
}
