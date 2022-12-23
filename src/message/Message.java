package message;
import service.Process;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor @NoArgsConstructor
public class Message {
	
	private Process sender;
	private int h;
	private TypeMessage type;

}
