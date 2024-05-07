package br.com.devsouza.desafioanotaai.publishers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.devsouza.desafioanotaai.publishers.dtos.CatalogEventBody;
import lombok.SneakyThrows;

@Service
public class AwsSnsPublisher {
    
    private final Logger log = LoggerFactory.getLogger(AwsSnsPublisher.class);
    private final AmazonSNS snsClient;
    private final Topic catalogTopic;
    private final ObjectMapper mapper;

    public AwsSnsPublisher(AmazonSNS snsClient, @Qualifier("catalogEventsTopic") Topic catalogTopic, ObjectMapper mapper) {
        this.snsClient = snsClient;
        this.catalogTopic = catalogTopic;
        this.mapper = mapper;
    }

    @SneakyThrows
    public void publish(CatalogEventBody message) {
        String json = mapper.writeValueAsString(message);
        
        log.info("Publicando mensagem SNS: ", message.getEvent().toString());
        log.debug("Publicando Mensagem SNS: ", json);
        
        this.snsClient.publish(catalogTopic.getTopicArn(), json);
    }
}
