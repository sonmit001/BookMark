/*
 * @Project : DeepRoot
 * @FileName : TopDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.social.dto;

/**
 * @Class : TopDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
 */
public class TopDTO {
	private String url;
	private String name;
	private int ucount;
	
	public TopDTO() {}

	public TopDTO(String url, String name, int ucount) {
		this.url = url;
		this.name = name;
		this.ucount = ucount;
	}
	
	//Getters and Setters
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public int getUcount() {return ucount;}
	public void setUcount(int ucount) {this.ucount = ucount;}

	@Override
	public String toString() {
		return "TopDTO [url=" + url + ", name=" + name + ", ucount=" + ucount + "]";
	}
	
}
