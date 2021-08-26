package org.example.domain.sport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sport {
    private int id;
    private String name;
    private List<Discipline> disciplines;
    private List<Event> events;
}
