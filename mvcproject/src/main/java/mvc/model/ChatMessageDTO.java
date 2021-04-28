package mvc.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {
	private String send_date;
	private long id;
	private String login_id;
	private String message;
}
