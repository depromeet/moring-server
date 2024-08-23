package org.depromeet.sambad.moring.common.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	protected LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(nullable = false)
	protected LocalDateTime updatedAt;

	public Long getCreatedAtWithEpochMilli() {
		return this.createdAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public Long getUpdatedAtWithEpochMilli() {
		return this.updatedAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
}

