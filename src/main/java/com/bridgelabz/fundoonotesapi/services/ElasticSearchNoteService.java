package com.bridgelabz.fundoonotesapi.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotesapi.model.NoteEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Tejashree Surve
 * @Purpose : This is Service Method for Elastic Search.
 */
@Component
@Service
public class ElasticSearchNoteService {

	private RestHighLevelClient client;

	private ObjectMapper objectMapper;

	public ElasticSearchNoteService(RestHighLevelClient client, ObjectMapper objectMapper) {
		this.client = client;
		this.objectMapper = objectMapper;
	}

	// set index name
	private final String INDEX = "elasticcontroller";
	// set type name
	private final String TYPE = "createNote";

	// create Note using elastic Search
	@SuppressWarnings("unchecked")
	public void createNoteInElastic(NoteEntity noteEntity) throws IOException {
		Map<String, Object> documentMapper = objectMapper.convertValue(noteEntity, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, UUID.randomUUID().toString()).source(documentMapper);
		client.index(indexRequest, RequestOptions.DEFAULT);
	}

	// get all note using elastic search
	public List<NoteEntity> getAllNote() throws IOException {
		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHit[] searchHit = searchResponse.getHits().getHits();
		List<NoteEntity> listOfNote = new ArrayList<NoteEntity>();
		if (searchHit.length > 0) {
			Arrays.stream(searchHit)
					.forEach(hit -> listOfNote.add(objectMapper.convertValue(hit.getSourceAsMap(), NoteEntity.class)));
		}
		return listOfNote;
	}

	// find By Note id using elastic search
	public NoteEntity findByIdByElasticSearch(String id) throws IOException {
		GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		Map<String, Object> resultMap = getResponse.getSource();
		return objectMapper.convertValue(resultMap, NoteEntity.class);
	}

	// delete Note using elastic search
	public String deletNote(String id) throws IOException {
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
		DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
		return deleteResponse.getResult().name();
	}

	// update Note using elastic search 
	public void updateNote(NoteEntity noteEntity) throws IOException {
		NoteEntity resultNote = findByIdByElasticSearch(String.valueOf(noteEntity.getId()));
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, String.valueOf(resultNote.getId()));
		@SuppressWarnings("unchecked")
		Map<String, Object> documentMapper = objectMapper.convertValue(noteEntity, Map.class);
		updateRequest.doc(documentMapper);
		client.update(updateRequest, RequestOptions.DEFAULT);
	}
}
