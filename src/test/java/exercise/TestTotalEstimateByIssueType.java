package exercise;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TestTotalEstimateByIssueType {


	public static Logger logger=Logger.getLogger(TestTotalEstimateByIssueType.class);
	
	@Test
	public void testEstimateCountNoral(){
		try {
			List<String> issueTypes = new ArrayList<String>();
			issueTypes.add("task");
			issueTypes.add("bug");
			issueTypes.add("story");
			issueTypes.add("anyother");
			AtlassianExercise exer = new AtlassianExercise();
			Map<String, Integer> totalEstimateByIssueType = exer.testMethod("http://localhost:8080/api/v1", issueTypes);
			assertNotNull(totalEstimateByIssueType);
			assertEquals(4, totalEstimateByIssueType.size(),0);
			assertEquals(totalEstimateByIssueType.get("/issuetypes/task"), 0,0);
			assertEquals(totalEstimateByIssueType.get("/issuetypes/bug"), 10,0);
			assertEquals(totalEstimateByIssueType.get("/issuetypes/story"), 4,0);
			assertEquals(totalEstimateByIssueType.get("/issuetypes/anyother"), 0,0);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Test failed:"+e.getLocalizedMessage());
		}
	}
}
