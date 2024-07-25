package org.depromeet.sambad.moring.file.infrastructure;

import java.util.List;

import org.depromeet.sambad.moring.file.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileJpaRepository extends JpaRepository<FileEntity, Long> {
	List<FileEntity> findAllByIsDefaultTrue();
}
