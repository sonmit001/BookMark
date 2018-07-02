/*
 * @Project : DeepRoot
 * @FileName : ChatMessage.java
 * @Date : 2018. 6. 27.
 * @Author : 김희준
*/


package site.book.socket;

public class JstreeAlarm {
	private String nname;
	private String doing;
	private String target;
	private String location;
	private String type;
	private String newnameorplace;
	
	public String getNname() {
		return nname;
	}
	public void setNname(String nname) {
		this.nname = nname;
	}
	public String getDoing() {
		return doing;
	}
	public void setDoing(String doing) {
		this.doing = doing;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNewnameorplace() {
		return newnameorplace;
	}
	public void setNewnameorplace(String newnameorplace) {
		this.newnameorplace = newnameorplace;
	}
	
	@Override
	public String toString() {
		return "JstreeAlarm [nname=" + nname + ", doing=" + doing + ", target=" + target + ", location=" + location
				+ ", type=" + type + ", newnameorplace=" + newnameorplace + "]";
	}
	
	
	
}

