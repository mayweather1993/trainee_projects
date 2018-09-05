package com.mayweather.zipping.domain.models;

import com.mayweather.zipping.domain.Auditable;
import com.mayweather.zipping.repositories.listeners.AuditListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.file.Path;
import java.time.Instant;

@Entity
@Table(name = "File_metadata")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditListener.class)
public class FileMetaDataEntity implements Serializable {

    private static final long serialVersionUID = -1642769850931769897L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private Long size;

    @Column(name = "path")
    private Path path;

    @LastModifiedDate
    private Instant lastModified;

    @CreatedDate
    private Instant created;
}
