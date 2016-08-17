package exercise.model;

import java.util.Arrays;

public class IssueType{
	private String id;
	private String name;
	private String[] issues;
	public IssueType(){}
	public IssueType(String id, String name, String[] issues) {
		super();
		this.id = id;
		this.name = name;
		this.issues = issues;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getIssues() {
		return issues;
	}
	public void setIssues(String[] issues) {
		this.issues = issues;
	}
	@Override
	public String toString() {
		return "issueType [id=" + id + ", name=" + name + ", issues=" + Arrays.toString(issues) + "]";
	}
}
