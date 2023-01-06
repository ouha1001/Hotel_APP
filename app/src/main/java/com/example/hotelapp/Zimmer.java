package com.example.hotelapp;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    public List<Integer> getRoomsbyType( String type) {
        List<Integer> roomsbytype = new ArrayList<>(rooms.size());

        if (type == null) {
            roomsbytype.addAll(rooms.keySet());
        } else {
            for (Integer list : rooms.keySet()) {

                if (Objects.equals(rooms.get(list), type)) {
                    roomsbytype.add(list);
                }
            }
        }
        return roomsbytype;
    }
}