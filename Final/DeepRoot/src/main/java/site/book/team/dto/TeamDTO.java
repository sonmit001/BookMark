/*
 * @Project : DeepRoot
 * @FileName : TeamDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.dto;

/**
 * @Class : TeamDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
 */
public class TeamDTO {
	private int gid;
	private String gname;
	private String htag;
	
	public TeamDTO() {}

	public TeamDTO(int gid, String gname, String htag) {
		super();
		this.gid = gid;
		this.gname = gname;
		this.htag = htag;
	}
	
	//Getters and Setters
	public int getGid() {return gid;}
	public void setGid(int gid) {this.gid = gid;}
	public String getGname() {return gname;}
	public void setGname(String gname) {this.gname = gname;}
	public String getHtag() {return htag;}
	public void setHtag(String htag) {this.htag = htag;}

	@Override
	public String toString() {
		return "TeamDTO [gid=" + gid + ", gname=" + gname + ", htag=" + htag + "]";
	}
	
}
