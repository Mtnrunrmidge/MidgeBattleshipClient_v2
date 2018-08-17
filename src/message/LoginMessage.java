package message;

public class LoginMessage extends Message {
    @Override
    public MessageType getMessageType() {
        return MessageType.LOGIN;
    }

    public LoginMessage(String username) {
        super(username);
    }
}
