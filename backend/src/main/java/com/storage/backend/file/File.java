package com.storage.backend.file;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table
public class File {

    @Id
    @SequenceGenerator(
            name="discord_storage_sequence",
            sequenceName = "discord_storage_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "discord_storage_sequence"
    )
    private Long id;
    private String fileName;

    @ElementCollection
    private List<Long> message_ids;

    public File() {}

    public File(String fileName, List<Long> message_ids) {
        this.fileName = fileName;
        this.message_ids = message_ids;
    }

    public Long getId() {
        return id;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<Long> getMessage_ids() {
        return message_ids;
    }

    public void setMessage_ids(List<Long> message_ids) {
        this.message_ids = message_ids;
    }
}
