package com.dynelm.robotdarttest.entity;

import com.jilk.ros.rosbridge.operation.Operation;

/**
 * Created by dynelm
 */

public class PublishEvent {
    public String msg;
    public String id;
    public String name;
    public String op;


    public PublishEvent(Operation operation, String name, String content) {
        if(operation != null) {
            id = operation.id;
            op = operation.op;
        }
        this.name = name;
        msg = content;
    }
}
