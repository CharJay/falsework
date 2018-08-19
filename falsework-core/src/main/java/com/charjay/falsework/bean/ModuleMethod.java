package com.charjay.falsework.bean;

import java.util.ArrayList;
import java.util.List;

public class ModuleMethod {

    private String name;
    private String nameUpperFirst;
    private String comment = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameUpperFirst() {
        return nameUpperFirst;
    }

    public void setNameUpperFirst(String nameUpperFirst) {
        this.nameUpperFirst = nameUpperFirst;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
