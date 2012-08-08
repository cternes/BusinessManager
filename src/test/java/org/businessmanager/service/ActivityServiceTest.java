package org.businessmanager.service;

import java.util.List;

import junit.framework.Assert;

import org.businessmanager.domain.Activity;
import org.businessmanager.domain.Activity.ActivityType;
import org.businessmanager.domain.ModificationType;
import org.businessmanager.dto.ActivityDto;
import org.businessmanager.web.bean.ContactActivityBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class ActivityServiceTest {

	@Autowired
	private ActivityService activityService;
	
	@Test
	public void testSaveActivity() {
		//check initial, expected size=0
		List<ActivityDto> recentActivities = activityService.getRecentActivities(10);
		Assert.assertEquals(0, recentActivities.size());
		
		saveActivity(1L);
		
		//check after, expected size=1
		recentActivities = activityService.getRecentActivities(10);
		Assert.assertEquals(1, recentActivities.size());
	}

	private void saveActivity(Long userId) {
		Activity activity1 = new Activity(userId, ActivityType.CONTACT);
		activityService.saveActivity(activity1);
	}
	
	@Test
	public void testGetRecentActivity() {
		//check initial, expected size=0
		List<ActivityDto> recentActivities = activityService.getRecentActivities(10);
		Assert.assertEquals(0, recentActivities.size());
		
		//add 20 activities
		for (int i = 0; i < 20; i++) {
			saveActivity(1L);
		}
		
		//check after, expected size=10
		recentActivities = activityService.getRecentActivities(10);
		Assert.assertEquals(10, recentActivities.size());
		
		//check after, expected size=20
		recentActivities = activityService.getRecentActivities(20);
		Assert.assertEquals(20, recentActivities.size());
	}
	
	@Test
	public void testGetRecentActivityByUser() {
		//check initial, expected size=0
		List<ActivityDto> recentActivities = activityService.getRecentActivities(10);
		Assert.assertEquals(0, recentActivities.size());
		
		//add 10 activities
		long userId = 0L;
		for (int i = 0; i < 10; i++) {
			if(i % 5 == 0) {
				userId++;
			}
			saveActivity(userId);
		}
		
		//check all activities, expected total size=10
		recentActivities = activityService.getRecentActivities(10);
		Assert.assertEquals(10, recentActivities.size());
		
		//check activities for userId=1, expected size=5
		recentActivities = activityService.getRecentActivitiesByUser(1L, 10);
		Assert.assertEquals(5, recentActivities.size());
	}
	
	@Test
	public void testWithData() {
		//check initial, expected size=0
		List<ActivityDto> recentActivities = activityService.getRecentActivities(10);
		Assert.assertEquals(0, recentActivities.size());
		
		//add 1 activity
		Activity activity1 = new Activity(5L, ActivityType.CONTACT);
		
		String json = new ContactActivityBean("Test user", ModificationType.CREATE, "Erich Gamma").toJson();
		activity1.setData(json);
		activityService.saveActivity(activity1);
		
		//check activities, expected size=1
		recentActivities = activityService.getRecentActivities(1);
		Assert.assertEquals(1, recentActivities.size());
		
		ActivityDto dto = recentActivities.get(0);
		Assert.assertEquals("Test user CREATE Erich Gamma", dto.getUsername() + " " + dto.getActivity() + " " + dto.getObjectName());
	}
}
