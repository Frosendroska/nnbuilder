package org.hse.nnbuilder.dataset;

import java.io.File;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
public class DatasetRepositoryTest {

    @Test
    void testAdd(){
        File datasetFile = new File("Resourses/2022_forbes_billionaires.csv");
        DatasetStored datasetStored = new DatasetStored(datasetFile);

    }
}
