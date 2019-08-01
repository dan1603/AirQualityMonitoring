package com.kalashnyk.denys.airqualitymonitoring.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root
public class FileModel  {

    @ElementList
    private ArrayList<AirQuality> list;
    @Attribute
    private String name;

    public FileModel(ArrayList<AirQuality> list, String name) {
        this.list = list;
        this.name = name;
    }

    public ArrayList<AirQuality> getList() {
        return list;
    }

    public void setList(ArrayList<AirQuality> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FileModel{" +
                "list=" + list +
                ", name='" + name + '\'' +
                '}';
    }
}
//@Root
//public class PropertyList {
//
//    @ElementList
//    private List<Entry> list;
//
//    @Attribute
//    private String name;
//
//    public String getName() {
//        return name;
//    }
//
//    public List getProperties() {
//        return list;
//    }
//}
//
//@Root
//public class Entry {
//
//    @Attribute
//    private String key;
//
//    @Element
//    private String value;
//
//    public String getName() {
//        return name;
//    }
//
//    public String getValue() {
//        return value;
//    }
//}