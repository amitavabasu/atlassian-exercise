package exercise.web;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import exercise.model.Issue;
import exercise.model.IssueType;

@RestController
@RequestMapping(value = "/api/v1/**")

public class ServiceController {
	@RequestMapping(value="/issuetypes/{issue_type}", method=RequestMethod.GET)
    public @ResponseBody Object  getIssueType(@PathVariable("issue_type") String issueType) throws Exception {
		IssueType issueTypeObj = null;
		 switch(issueType){
		 
		 case "bug" :
			 issueTypeObj = new IssueType("/issuetypes/bug", "bug", new String[]{"/issues/1","/issues/2","/issues/3"});
			 break;
			 
		 case "story":
			 issueTypeObj = new IssueType("/issuetypes/story", "story", new String[]{"/issues/4","/issues/5"});
			 break;
			 
		 case "task":
		 default:
			 break;
		 }
		 
		 return issueTypeObj;
	}
	
	@RequestMapping(value="/issues/{id}", method=RequestMethod.GET)
    public @ResponseBody Object  getIssue(@PathVariable("id") String id) throws Exception {
		
		Issue issueObj = null;
		
		 switch(id){
		 
		 case "1" :
			 issueObj = new Issue("/issues/1", "/issuetypes/bug", "Issue #1", 3);
			 break;
			 
		 case "2":
			 issueObj = new Issue("/issues/2", "/issuetypes/bug", "Issue #2", 5);
			 break;
			 
		 case "3":
			 issueObj = new Issue("/issues/3", "/issuetypes/bug", "Issue #3", 2);
			 break;
			 
		 case "4":
			 issueObj = new Issue("/issues/4", "/issuetypes/story", "Issue #4", 3);
			 break;
			 
		 case "5":
			 issueObj = new Issue("/issues/5", "/issuetypes/story", "Issue #5", 1);
			 break;
			 
		 default:
			 break;
		 }
		 return issueObj;
	}
	
	@RequestMapping(value="/hello", method=RequestMethod.GET)
    public @ResponseBody String  hello() throws Exception {
   		return "Hello World!";
	}
}
