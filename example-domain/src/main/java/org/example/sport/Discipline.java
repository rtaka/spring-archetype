package org.example.sport;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Discipline {
    private int id;
    private String name;
    private List<Event> events;
}
