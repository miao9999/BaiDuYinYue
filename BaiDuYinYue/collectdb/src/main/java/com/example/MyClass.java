package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static void main (String [] args){
        Schema schema = new Schema(1,"com.example.dllo.baiduyinyue.mode.db");
        Entity entity = schema.addEntity("CollectionBean");
        entity.addIdProperty().autoincrement().primaryKey();
        entity.addStringProperty("userName");
        entity.addStringProperty("userPassword");
        entity.addStringProperty("songName");
        entity.addStringProperty("author");
        entity.addStringProperty("songUrl");
        entity.addByteArrayProperty("pic");
        entity.addStringProperty("picUrl");
        entity.addStringProperty("other");
        entity.addStringProperty("others");

        try {
            new DaoGenerator().generateAll(schema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
