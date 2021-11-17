package post;

public class Post {
	private int id;
	private String title;
	private String content;
	public int getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", content=" + content + "]";
	}
	
	public Post() {
		
	}

	public Post(int id, String title, String content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
