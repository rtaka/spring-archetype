package org.example.ds.sport;

import org.example.domain.sport.Sport;
import org.example.ds.DBUnitSetup;
import org.example.ds.DBUnitTest;
import org.example.ds.MapperInject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

@DBUnitTest
class SportMapperTest {
    @MapperInject
    SportMapper mapper;

    @Test
    @DBUnitSetup(init = "sport/init-sport.xml")
    void findAll() {
        List<Sport> sports = mapper.findAll();
        Assertions.assertIterableEquals(
                sports,
                List.of(
                        new Sport(1, "サッカー", null, null),
                        new Sport(3, "バスケットボール", null, null),
                        new Sport(2, "空手", null, null),
                        new Sport(4, "野球", null, null)
                )
        );
    }

    @Test
    @DBUnitSetup(cleanup = true, expected = "sport/expected-insert.xml")
    void insert() {
        mapper.insert(new Sport(5, "陸上", null, null));
    }

    @Test
    @DBUnitSetup(
            cleanup = true,
            init = "sport/init-sport.xml",
            expected = "sport/expected-update.xml"
    )
    void update() {
        mapper.update(new Sport(3, "バスケ", null, null));
    }

    @Test
    @DBUnitSetup(
            cleanup = true,
            init = "sport/init-sport.xml",
            expected = "sport/expected-delete.xml"
    )
    void delete() {
        mapper.delete(new Sport(2, "空手", null, null));
    }
}