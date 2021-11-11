package util;

	public enum ProtocolMessage {

	    MESSAGE_SEPARATOR("#"),
	    OK("AUTH OK"),
	    NOT_OK("AUTH NOK"),
	    INVALID_REQUEST("INVALID REQUEST"),
	    UNKNOWN_TOWN("UNKNOWN TOWN"),
	    AUTH_REQUEST("AUTH" + MESSAGE_SEPARATOR.getMessage()),
	    INFO_REQUEST("INFO" + MESSAGE_SEPARATOR.getMessage()),
		ERROR("ERR");

	    private final String value;

	    private ProtocolMessage(String value) {
	        this.value = value;
	    }

	    public String getMessage() {
	        return value;
	    }
}
