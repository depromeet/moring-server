package org.depromeet.sambad.moring.common.config;

import org.depromeet.sambad.moring.file.infrastructure.ObjectStorageProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ObjectStorageConfig {

	private final ObjectStorageProperties objectStorageProperties;

	@Bean
	public AmazonS3 amazonS3() {
		BasicAWSCredentials credentials = new BasicAWSCredentials(
			objectStorageProperties.objectStorage().credentials().accessKey(),
			objectStorageProperties.objectStorage().credentials().secretKey());

		return AmazonS3ClientBuilder
			.standard()
			.withEndpointConfiguration(
				new AwsClientBuilder.EndpointConfiguration(
					objectStorageProperties.objectStorage().endpoint(),
					objectStorageProperties.region().staticRegion()))
			.withCredentials(new AWSStaticCredentialsProvider(credentials))
			.build();
	}
}
