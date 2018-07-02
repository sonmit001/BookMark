/*
 * @Project : DeepRoot
 * @FileName : U_BookDTO.java
 * @Date : 2018. 6. 11.
 * @Author : 김태웅
*/

package site.book.user.dto;

public class UserDTO {
	private String uid;
	private String nname;
	private String pwd;
	private int enabled;
	private String regdate;
	private String profile;
	
	public UserDTO() {}
	
	public UserDTO(String uid, String nname, String pwd, int enabled, String regdate, String profile) {
		this.uid = uid;
		this.nname = nname;
		this.pwd = pwd;
		this.enabled = enabled;
		this.regdate = regdate;
		this.profile = profile;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getNname() {
		return nname;
	}
	public void setNname(String nname) {
		this.nname = nname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "UserDTO [uid=" + uid + ", nname=" + nname + ", pwd=" + pwd + ", enabled=" + enabled + ", regdate="
				+ regdate + ", profile=" + profile + "]";
	}
	
}
