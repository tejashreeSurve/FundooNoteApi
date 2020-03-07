package com.bridgelabz.fundoonotesapi.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotesapi.model.NoteEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchNoteServiceImpl {

	private RestHighLevelClient client;
	
	private ObjectMapper objectMapper;

	public ElasticSearchNoteServiceImpl(RestHighLevelClient client, ObjectMapper objectMapper) {
		this.client = client;
		this.objectMapper = objectMapper;
	}
	
 private String INDEX = "elasticSearchNote";
 private String TYPE = "notedetails";
	public String createNoteInElastic(NoteEntity noteEntity) throws IOException {
		Map<String, Object> documentMapper = objectMapper.convertValue(noteEntity, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX,TYPE,String.valueOf(noteEntity.getId())).source(documentMapper);
		IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
		return indexResponse.getResult().name();
	}
	
	public List<NoteEntity> getAllNotes(){
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
		return getSearchResult(searchResponse);
	}
	
}
