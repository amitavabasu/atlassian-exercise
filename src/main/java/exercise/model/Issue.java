package exercise.model;

public class Issue{
	private String id;
	private String issuetype;
	private String description;
	private Integer estimate;
	public Issue(){}
	public Issue(String id, String issuetype, String description, Integer estimate) {
		super();
		this.id = id;
		this.issuetype = issuetype;
		this.description = description;
		this.estimate = estimate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIssuetype() {
		return issuetype;
	}
	public void setIssuetype(String issuetype) {
		this.issuetype = issuetype;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getEstimate() {
		return estimate;
	}
	public void setEstimate(Integer estimate) {
		this.estimate = estimate;
	}
	@Override
	public String toString() {
		return "issue [id=" + id + ", issuetype=" + issuetype + ", description=" + description + ", estimate="
				+ estimate + "]";
	}
}
