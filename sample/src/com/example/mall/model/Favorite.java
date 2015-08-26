package com.example.mall.model;

public class Favorite {
	private String id = "";
	private String title = "";
	private String url = "";
	private String createDate = "";
	
	public Favorite(String id ,String title, String url, String createDate){
		super();
		this.setId(id);
		this.setTitle(title);
		this.setUrl(url);
		this.setCreateDate(createDate);
	}
	
	public Favorite(){
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	@Override
	public String toString(){
		return "Favorite [id="+id+",title="+title+",url="+url+",createDate="+createDate+"]\n\n";
	}
	
}
