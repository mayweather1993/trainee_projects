package com.mayweather.zipping.repositories;

import com.mayweather.zipping.domain.models.FileMetaDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetaDataRepository extends JpaRepository<FileMetaDataEntity, Long> {
}
