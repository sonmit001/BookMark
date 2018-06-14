package site.book.user.dto;

public class EmailAuthDTO {
	private String uid;
	private String authcode;
	
	public EmailAuthDTO() {}
	
	public EmailAuthDTO(String uid, String authcode) {
		this.uid = uid;
		this.authcode = authcode;
	}
	public String getUid() {return uid;}
	public void setUid(String uid) {this.uid = uid;}
	public String getAuthcode() {return authcode;}
	public void setAuthcode(String authcode) {this.authcode = authcode;}

	@Override
	public String toString() {
		return "EmailAuthDTO [uid=" + uid + ", authcode=" + authcode + "]";
	}
}
