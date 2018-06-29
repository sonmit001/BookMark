/*
 * @Project : DeepRoot
 * @FileName : G_MemberDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.dto;

/**
 * @Class : G_MemberDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
 */
public class G_MemberDTO {
	private String uid;
	private int gid;
	private int ccount;
	private int grid;
	private String nname;
	

	public G_MemberDTO() {}

	
	public G_MemberDTO(String uid, int gid, int ccount, int grid, String nname) {
		super();
		this.uid = uid;
		this.gid = gid;
		this.ccount = ccount;
		this.grid = grid;
		this.nname = nname;
	}
	
	public G_MemberDTO(String uid, int gid) {
		this.uid = uid;
		this.gid = gid;
	}

	//Getters and Setters
	public String getUid() {return uid;}
	public void setUid(String uid) {this.uid = uid;}
	public int getGid() {return gid;}
	public void setGid(int gid) {this.gid = gid;}
	public int getCcount() {return ccount;}
	public void setCcount(int ccount) {this.ccount = ccount;}
	public int getGrid() {return grid;}
	public void setGrid(int grid) {this.grid = grid;}
	public String getNname() {return nname;}
	public void setNname(String nname) {this.nname = nname;}

	@Override
	public String toString() {
		return "G_MemberDTO [uid=" + uid + ", gid=" + gid + ", ccount=" + ccount + ", grid=" + grid + ", nname=" + nname
				+ "]";
	}
	
}

