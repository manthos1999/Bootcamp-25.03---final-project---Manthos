package bootcamp_2025_03_manthos.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatCompletionRequest {
    private String model;
    private List<ChatMessage> messages;


}