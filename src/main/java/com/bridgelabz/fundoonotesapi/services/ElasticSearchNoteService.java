package com.bridgelabz.fundoonotesapi.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotesapi.model.NoteEntity;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ElasticSearchNoteService {

	private RestHighLevelClient client;
	
	private ObjectMapper objectMapper;
	

	public ElasticSearchNoteService(RestHighLevelClient client, ObjectMapper objectMapper) {
		this.client = client;
		this.objectMapper = objectMapper;
	}
	
 private String INDEX = "elasticsearchnote";
 private String TYPE = "notedetails";
	@SuppressWarnings("unchecked")
	public String createNoteInElastic(NoteEntity noteEntity) throws IOException {
		
		Map<String, Object> documentMapper = objectMapper.convertValue(noteEntity, Map.class);
		System.out.println("i am in elastic search :"+ documentMapper);
		IndexRequest indexRequest = new IndexRequest(INDEX,TYPE,String.valueOf(noteEntity.getId())).source(documentMapper);
	System.out.println("i am after map : "+indexRequest);
    IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
		System.out.println("i am after indexRequest :"+indexResponse.getResult().name());	
		return indexResponse.getResult().name();
		

	}

	public List<NoteEntity> getAllNote() throws IOException{
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
		SearchHit[] searchHit = searchResponse.getHits().getHits();
		List<NoteEntity> listOfNote = new ArrayList<NoteEntity>();
		if(searchHit.length>0) {
			Arrays.stream(searchHit).forEach(hit -> listOfNote.add(objectMapper.convertValue(hit.getSourceAsMap(), NoteEntity.class)));
	
		}
	return listOfNote;
	}
	
	public NoteEntity findByIdByElasticSearch(int id) throws IOException {
		GetRequest getRequest = new GetRequest(INDEX,TYPE,String.valueOf(id));
		
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		Map<String,Object> resultMap = getResponse.getSource();
		return objectMapper.convertValue(resultMap, NoteEntity.class);
		
	}
	
	
	
}
