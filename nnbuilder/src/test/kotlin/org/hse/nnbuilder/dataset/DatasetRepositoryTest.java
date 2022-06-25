package org.hse.nnbuilder.dataset;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.hse.nnbuilder.DatasetUtil;
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
    void testAddDataset() throws IOException {
        DatasetStored dsStored = new DatasetStored(DatasetUtil.readDatasetFile(), null);
        datasetRepository.save(dsStored);

        DatasetStored dsLoaded = datasetRepository.getById(dsStored.getDatasetId());

        assertThat(dsLoaded.getContent()).isNotEmpty();
    }
}
