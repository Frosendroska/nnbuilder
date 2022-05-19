package org.hse.nnbuilder.dataset;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hse.nnbuilder.queue.TaskQueued;

@Entity
@Table(name = "datasets")
public class DatasetStored {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long datasetId;

    @Column
    private byte[] data;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<TaskQueued> tasks = new ArrayList<>();
}
