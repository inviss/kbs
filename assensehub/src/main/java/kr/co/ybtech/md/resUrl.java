package kr.co.ybtech.md;

public class resUrl {
	private String url_type;
	private String url;

	public resUrl() {
	}

	public resUrl(String url_type, String url) {
		this.url_type = url_type;
		this.url = url;
	}

	public String getUrl_type() {
		return url_type;
	}

	public void setUrl_type(String url_type) {
		this.url_type = url_type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
