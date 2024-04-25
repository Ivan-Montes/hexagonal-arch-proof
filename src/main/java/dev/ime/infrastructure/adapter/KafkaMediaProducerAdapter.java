package dev.ime.infrastructure.adapter;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import dev.ime.config.MediaMapper;
import dev.ime.domain.model.Media;
import dev.ime.domain.port.outbound.MediaProducerPort;
import dev.ime.infrastructure.config.InfrastructureConstant;

@Service
public class KafkaMediaProducerAdapter implements MediaProducerPort{

	private final KafkaTemplate<String, Object> kafkaTemplate;	
	private final Logger logger;
	private final MediaMapper mediaMapper;	
	
	public KafkaMediaProducerAdapter(KafkaTemplate<String, Object> kafkaTemplate, Logger logger,
			MediaMapper mediaMapper) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.logger = logger;
		this.mediaMapper = mediaMapper;
	}

	@Override
	public void sendMediaCreatedMessage(Media media) {
		
		send(new ProducerRecord<>(InfrastructureConstant.MEDIA_CREATED, mediaMapper.fromDomainToDto(media)));
	}

	@Override
	public void sendMediaUpdatedMessage(Media media) {
		
		send(new ProducerRecord<>(InfrastructureConstant.MEDIA_UPDATED, mediaMapper.fromDomainToDto(media)));
	}

	@Override
	public void sendMediaDeletedMessage(Media media) {
		
		send(new ProducerRecord<>(InfrastructureConstant.MEDIA_DELETED, mediaMapper.fromDomainToDto(media)));
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
