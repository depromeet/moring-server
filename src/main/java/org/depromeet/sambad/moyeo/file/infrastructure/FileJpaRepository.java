package org.depromeet.sambad.moyeo.file.infrastructure;

import org.depromeet.sambad.moyeo.file.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileJpaRepository extends JpaRepository<FileEntity, Long> {
	boolean existsByLogicalName(String logicalName);

	void deleteByLogicalName(String logicalName);
}
