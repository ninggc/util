package com.ninggc.template.simpledemo.web;

import com.ninggc.template.simpledemo.config.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Future;

/**
 * @author ninggc
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    AsyncTask asyncTask;

    @GetMapping("/1")
    public Date test(Date param, HttpServletRequest request) {
        System.out.println("controller" + new SimpleDateFormat("yyyy-MM-dd").format(param));
        Future<String> stringFuture = asyncTask.asyncTask();
        System.out.println("after");
        return new Date();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        // binder.registerCustomEditor(String.class, new StringToDate());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
