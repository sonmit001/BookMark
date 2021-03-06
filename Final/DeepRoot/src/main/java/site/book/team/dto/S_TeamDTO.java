/*
 * @Project : DeepRoot
 * @FileName : S_TeamDTO.java
 * @Date : 2018. 6. 8.
 * @Author : 김희준
*/


package site.book.team.dto;

/**
 * @Class : S_TeamDTO.java
 * @Date : 2018. 6. 8.
 * @Author : 김희준
 */
public class S_TeamDTO {
	private int gid;
	private String gname;
	private String nname;
	private String htag;
	private String regdate;
	private String duedate;
	
	public S_TeamDTO() {}
	
	public S_TeamDTO(int gid, String gname, String nname, String htag, String regdate, String duedate) {
		super();
		this.gid = gid;
		this.gname = gname;
		this.nname = nname;
		this.htag = htag;
		this.regdate = regdate;
		this.duedate = duedate;
	}
	//Getters and Setters
	public int getGid() {return gid;}
	public void setGid(int gid) {this.gid = gid;}
	public String getGname() {return gname;}
	public void setGname(String gname) {this.gname = gname;}
	public String getNname() {return nname;}
	public void setNname(String nname) {this.nname = nname;}
	public String getHtag() {return htag;}
	public void setHtag(String htag) {this.htag = htag;}
	public String getRegdate() {return regdate;}
	public void setRegdate(String regdate) {this.regdate = regdate;}
	public String getDuedate() {return duedate;}
	public void setDuedate(String duedate) {this.duedate = duedate;}

	@Override
	public String toString() {
		return "S_TeamDTO [gid=" + gid + ", gname=" + gname + ", nname=" + nname + ", htag=" + htag + ", regdate="
				+ regdate + ", duedate=" + duedate + "]";
	}
	
}
