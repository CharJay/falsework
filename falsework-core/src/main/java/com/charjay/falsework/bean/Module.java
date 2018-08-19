package com.charjay.falsework.bean;

import java.util.LinkedHashMap;

public class Module {

    private String name;

    private String comment = "";


    private LinkedHashMap<String, ModuleMethod> methods = new LinkedHashMap<>();


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getComment() {
        return comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }


    public LinkedHashMap<String, ModuleMethod> getMethods() {
        return methods;
    }

    public void setMethods(LinkedHashMap<String, ModuleMethod> methods) {
        this.methods = methods;
    }


}
