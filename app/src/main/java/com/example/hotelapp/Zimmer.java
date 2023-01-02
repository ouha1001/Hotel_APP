package com.example.hotelapp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Zimmer {

    private final Map<Integer, String> rooms= new ConcurrentHashMap<>();

    public Map<Integer, String> getRooms() {
        return rooms;
    }

    public Zimmer() {
        rooms.put(101,"Double");
        rooms.put(102,"Single");
        rooms.put(103,"Double");
        rooms.put(104,"Single");
        rooms.put(105,"Double");
        rooms.put(106,"Single");
        rooms.put(107,"Double");
        rooms.put(108,"Single");
        rooms.put(109,"Double");
        rooms.put(110,"Single");
        rooms.put(111,"Double");
    }
}