package com.project3.api.entities.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class PostTime {
    private Timestamp time;

    private String operation;

    public PostTime(Timestamp time, String operation) {
        this.time = time;
        this.operation = operation;
    }

    public PostTime() {
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "PostTime{" +
                "time=" + time +
                ", operation='" + operation + '\'' +
                '}';
    }

    public boolean isGreaterOperation(){
        return this.operation.contains("greater");
    }

    public boolean isLessOperation(){
        return this.operation.contains("less");
    }


}

