package com.ninggc.template.simpledemo.web.config;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StringToDate extends PropertyEditorSupport {
    @Override
    public void setValue(Object value) {
        try {
            if (value instanceof String) {
                super.setValue(new SimpleDateFormat("yyyy-MM-dd").parse((String) value));
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException();
        }
        super.setValue(value);
    }
}
