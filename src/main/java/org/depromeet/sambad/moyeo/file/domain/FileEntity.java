package org.depromeet.sambad.moyeo.file.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.depromeet.sambad.moyeo.common.domain.BaseTimeEntity;

import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Entity
@Table(name = "file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String originalName;

	private String directory;

	private String generatedName;

	public static FileEntity of(String filePath, String originalName) {
		Path path = Paths.get(filePath);
		String directory = path.getParent().toString();
		String generatedName = path.getFileName().toString();

		return new FileEntity(originalName, directory, generatedName);
	}

	private FileEntity(String originalName, String directory, String generatedName) {
		this.originalName = originalName;
		this.directory = directory;
		this.generatedName = generatedName;
	}

	public String getPath() {
		return directory + "/" + generatedName;
	}
}
