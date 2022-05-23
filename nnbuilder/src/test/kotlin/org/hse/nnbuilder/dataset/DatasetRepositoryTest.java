package org.hse.nnbuilder.dataset;

import org.hse.nnbuilder.DatasetUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("test")
public class DatasetRepositoryTest {

    @Autowired
    private DatasetRepository datasetRepository;

    @Test
    void testAddDataset() throws IOException {
        DatasetStored dsStored = new DatasetStored(DatasetUtil.readDatasetFile());
        datasetRepository.save(dsStored);

        DatasetStored dsLoaded = datasetRepository.getById(dsStored.getDsId());

        assertThat(dsLoaded.getContent()).isNotEmpty();
    }
}
