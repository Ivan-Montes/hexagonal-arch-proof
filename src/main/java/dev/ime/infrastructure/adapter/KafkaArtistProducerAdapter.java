package dev.ime.infrastructure.adapter;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import dev.ime.config.ArtistMapper;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.outbound.ArtistProducerPort;
import dev.ime.infrastructure.config.InfrastructureConstant;

@Service
public class KafkaArtistProducerAdapter implements ArtistProducerPort{

	private final KafkaTemplate<String, Object> kafkaTemplate;	
	private final Logger logger;
	private final ArtistMapper artistMapper;
	
	public KafkaArtistProducerAdapter(KafkaTemplate<String, Object> kafkaTemplate, Logger logger, ArtistMapper artistMapper) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.logger = logger;
		this.artistMapper = artistMapper;
	}

	@Override
	public void sendArtistCreatedMessage(Artist artist) {
			
		send(new ProducerRecord<>(InfrastructureConstant.ARTIST_CREATED, artistMapper.fromDomainToDto(artist)));
	}

	@Override
	public void sendArtistUpdatedMessage(Artist artist) {
		
		send(new ProducerRecord<>(InfrastructureConstant.ARTIST_UPDATED, artistMapper.fromDomainToDto(artist)));
	}

	@Override
	public void sendArtistDeletedMessage(Artist artist) {
		
		send(new ProducerRecord<>(InfrastructureConstant.ARTIST_DELETED, artistMapper.fromDomainToDto(artist)));		
	}
    
	private void send(ProducerRecord<String, Object> producerRecord) {
			
		CompletableFuture<SendResult<String, Object>> completableFuture = kafkaTemplate.send(producerRecord);
		completableFuture.whenComplete( (result, ex) -> {
			if (ex == null) {
	            handleSuccess(result);
	        }
	        else {
	            handleFailure(result, ex);
	        }
		});	
	}

	private void handleSuccess(SendResult<String, Object> result) {
		logger.info("### Message sent to Kafka successfully: " + result.getProducerRecord().topic() + " <==> " + result.getProducerRecord().value());
    }

    private void handleFailure(SendResult<String, Object> result, Throwable ex) {
    	logger.info("### Error " + ex.getMessage() + " sending message to Kafka: " + result.getProducerRecord().topic() + " <==> " + result.getProducerRecord().value());
    }
    
}
