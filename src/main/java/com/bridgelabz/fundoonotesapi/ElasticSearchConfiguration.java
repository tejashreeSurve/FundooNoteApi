package com.bridgelabz.fundoonotesapi;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tejashree Surve
 * @Purpose : This contains configuration of Elastic Search.
 */
@Configuration
public class ElasticSearchConfiguration {
	
	@Bean(destroyMethod = "close")
	public RestHighLevelClient client() {
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200,"http")));
	return client;
	}
}
