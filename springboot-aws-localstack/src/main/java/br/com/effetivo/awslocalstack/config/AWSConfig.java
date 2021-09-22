package br.com.effetivo.awslocalstack.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.endpoint}")
    private String endpoint;

    @Bean
    public AmazonS3 s3() {
        AWSCredentials awsCredentials =
                new BasicAWSCredentials("", "");
        return AmazonS3ClientBuilder
                .standard()
                //.withRegion("ap-south-1")
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}