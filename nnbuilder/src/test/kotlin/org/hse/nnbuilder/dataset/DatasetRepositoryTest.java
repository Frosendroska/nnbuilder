package org.hse.nnbuilder.dataset;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import org.hse.nnbuilder.exception.DatasetNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
public class DatasetRepositoryTest {

    @Autowired
    private DatasetRepository datasetRepository;

    @Test
    void testAddDataset() throws DatasetNotFoundException, IOException {
        File ds = new File("../Resources/2022_forbes_billionaires.csv");
        DatasetStored dsStored = new DatasetStored(ds);
        datasetRepository.save(dsStored);

        DatasetStored dsLoaded = datasetRepository.getById(dsStored.getDsId());

        // Just to test that I can call them
        dsStored.getContent();
        dsLoaded.getContent();
    }

    @Test
    void testAdd() {
        File datasetFile = new File("../Resources/no_such_file.csv");
        assertThrows(
                DatasetNotFoundException.class,
                () -> datasetRepository.save(
                        new DatasetStored(datasetFile)
                )
        );
    }
}
