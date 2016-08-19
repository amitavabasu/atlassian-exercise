package exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Phaser;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import exercise.model.Issue;
import exercise.model.IssueType;

public class AtlassianExercise {
	
	
	private String HOST = null;
	//Used for calling REST API Asynchronously
	private AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
	//Used for storing the final result <issue type>:<total estimate> as key value. Needs concurrent hash map as will be modified 
	//and read concurrently within multiple rest call processing at different times.  
	private Map<String, Integer> totalEstimateByIssueType = new ConcurrentHashMap<String, Integer>();
	//will be using to keep track of total number of anync. request/thread started and ended in calling sequence to traverse rest api data.  
	private Phaser phaser = new Phaser();  
	
	//Issue type listener - will execute on call back of issue type rest request. 
	private ListenableFutureCallback<ResponseEntity<IssueType>> issueTypeListner = new ListenableFutureCallback<ResponseEntity<IssueType>>() {
		@Override
	    public void onSuccess(ResponseEntity<IssueType> entity) {
			IssueType issueType = entity.getBody();
			String[] issues = issueType.getIssues();
			if(issues!=null && issues.length > 0){
				//register total number of calls to be made asynchronously to get all issue details
				phaser.bulkRegister(issues.length);
				for(String issueUri:issues){
					asyncRestTemplate.getForEntity(HOST+issueUri, Issue.class).addCallback(issueListner);
				}
			}
			//unregister as one rest issue type call completed processed successfully 
			phaser.arriveAndDeregister();
	    }

	    @Override
	    public void onFailure(Throwable t) {
	        System.out.println("Exception calling rest service: " +t.getLocalizedMessage());
	        //unregister as one rest call completed but failed (due to any reason, not trying to track errors) 
	        phaser.arriveAndDeregister();
	    }
	};
	
	//Issue listener - will execute on call back of issue details...? rest request.
	private ListenableFutureCallback<ResponseEntity<Issue>> issueListner = new ListenableFutureCallback<ResponseEntity<Issue>>() {
		@Override
	    public void onSuccess(ResponseEntity<Issue> entity) {
			Issue issue = entity.getBody();
			incrementTotalEstimate(issue.getIssuetype(), issue.getEstimate());
			//unregister as one rest issue details call completed - processed successfully
			phaser.arriveAndDeregister();
	    }

	    @Override
	    public void onFailure(Throwable t) {
	        System.out.println("Exception calling rest service: " +t.getLocalizedMessage());
	        //unregister as one rest call completed but failed (NO ERROR tracking at this point)
	        phaser.arriveAndDeregister();
	    }
	};
	
	/*
	 * Method to read update store totalEstimateByIssueType Synchronously by blocking threads who want to increment totalEstimate-By-IssueType at the same time.  
	 * 
	 */
	public void incrementTotalEstimate(String issueType, Integer estimate){
		synchronized(totalEstimateByIssueType){
			Integer currentTotalEstimate = totalEstimateByIssueType.get(issueType);
			//Calculate and store total estimate
			if(estimate==null){
				totalEstimateByIssueType.put(issueType, estimate);
			}else{
				totalEstimateByIssueType.put(issueType, currentTotalEstimate+estimate);
			}
		}
	}
	/*
	 * Method to print final result from the map - totalEstimateByIssueType
	 * 
	 */
	private void printResult(){
		if(totalEstimateByIssueType.size()==0){
			System.out.println("No result to print");
		}else{
			Set<String> keys = totalEstimateByIssueType.keySet();
			for(String key:keys){
				Integer totalEstimate = totalEstimateByIssueType.get(key);
				System.out.println(key.substring(key.lastIndexOf('/')+1)+":"+totalEstimate);
			}
		}
	}
	
	//TEST METHOD
	public Map<String, Integer> testMethod(String host, List<String> issueTypes){
		HOST = host;
		phaser.bulkRegister(issueTypes.size()+1);
		for(String issueType:issueTypes){
			totalEstimateByIssueType.put("/issuetypes/"+issueType, 0);
			asyncRestTemplate.getForEntity(HOST+"/issuetypes/"+issueType, IssueType.class).addCallback(issueTypeListner);
		}
		phaser.arriveAndAwaitAdvance();
		printResult();
		return totalEstimateByIssueType;
	}
	
	//MAIN
//	public static void main(String[] args){
//		// Process provided input for correctness and invoke test method on an instance
//		if(args.length == 0)
//			System.exit(0);
//		String host = args[0].trim();
//		List<String> issueTypes = new ArrayList<String>();
//		for(int i=1; i<args.length; i++){
//			issueTypes.add(args[i]);
//		}
//		if(issueTypes.size()==0)
//			System.exit(0);
//		new AtlassianExercise().testMethod(host, issueTypes);
//	}
}
