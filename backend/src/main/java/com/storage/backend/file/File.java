package com.storage.backend.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView({Views.ExternalView.class})
    private Long id;

    @JsonView({Views.ExternalView.class})
    private String fileName;
    @JsonView({Views.ExternalView.class})
    private long fileSize;

    @JsonIgnore
    @ElementCollection
    private List<Long> message_ids;

    public File() {}

    public File(String fileName, long fileSize, List<Long> message_ids) {
        this.fileName = fileName;
        this.fileSize = fileSize;
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

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public List<Long> getMessage_ids() {
        return message_ids;
    }

    public void setMessage_ids(List<Long> message_ids) {
        this.message_ids = message_ids;
    }
}
