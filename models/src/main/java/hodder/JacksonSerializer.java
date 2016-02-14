package hodder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class JacksonSerializer implements Serializer<Message>, Deserializer<Message> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Message deserialize(String topic, byte[] data) {
        try {
            return OBJECT_MAPPER.readValue(data, Message.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] serialize(String topic, Message message) {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {

    }
}
