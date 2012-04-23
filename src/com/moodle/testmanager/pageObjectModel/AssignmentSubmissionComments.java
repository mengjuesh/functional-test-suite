package com.moodle.testmanager.pageObjectModel;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.moodle.seleniumutils.PassFailCriteria;
/**
 * The page object model for Assignment Submission Comments.
 * @author Tim Barker 
 * @see <a href="http://www.gnu.org/copyleft/gpl.html">License: GNU GPL v3 or later</a>
 */
public class AssignmentSubmissionComments {
	//Internationalization file location
	public static String data = "properties/data/static/assignmentSubmissionComments.properties";
	private RemoteWebDriver driver;
	private Map<String, String> properties = new HashMap<String, String>();
/**
 * Constructor for the page object.	
 * @param driver The driver that is used for the test. There is no need to specify the value for the driver here as the driver
 * is instantiated in the test using one of the com.moodle.seleniumutils.SeleniumManager constructors.
 * loadObjectData constructs internationalization layer in the context of this page object.
 */
	public AssignmentSubmissionComments(RemoteWebDriver driver) {
		this.driver = driver;
		this.loadObjectData();
	}
/**
 * Loads data for the page object from the internationalization layer /properties/data/static/assignment.properties
 * where a selector requires a text string visible through the user interface e.g. value=button text, or link text.
 */
	public void loadObjectData() {
		Properties dataLoad = new Properties();
		try {
			dataLoad.load(new FileInputStream(data));
		} catch (Exception e) {}
		//put values from the properties file into hashmap
		this.properties.put("linkComments", dataLoad.getProperty("linkComments"));
		this.properties.put("linkSaveComment", dataLoad.getProperty("linkSaveComment"));
		this.properties.put("linkCancelComment", dataLoad.getProperty("linkCancelComment"));
		this.properties.put("linkYes", dataLoad.getProperty("linkYes"));
		this.properties.put("exceptionMessageCommentPresentShouldNotBe", dataLoad.getProperty("exceptionMessageCommentPresentShouldNotBe"));
		this.properties.put("exceptionMessageCommentNotPresent", dataLoad.getProperty("exceptionMessageCommentNotPresent"));
	}
/**
 * Clicks the comments link to display submission comments.
 */
	public void clickLinkSubmissionComments() {
		WebElement link = driver.findElementByPartialLinkText(this.properties.get("linkComments"));
		link.click();
	}
/**
 * Enters text in the submission comments text box.
 * @param desiredComment The text that you want to enter in the comments box. Pass from the test.
 */
	public void enterTextSubmissionComments(String desiredComment) {
		WebElement textArea = driver.findElementByXPath("//div[@class='comment-area']/div/textarea");
		textArea.sendKeys(desiredComment);
	}
/**
 * Clicks the link to save a submission comment.
 */
	public void clickLinkSaveComment() {
		WebElement link = driver.findElementByLinkText(this.properties.get("linkSaveComment"));
		link.click();
	}
/**
 * Clicks the link to cancel a submission comment.
 */
	public void clickLinkCancelComment() {
		WebElement link = driver.findElementByLinkText(this.properties.get("linkCancelComment"));
		link.click();
	}
/**
 * Makes the test fail if a given submission comment has been saved.	
 * @param commentText The text that was entered when the user entered the comment. This is the text that you are verifying is not there.
 * @throws Exception Throws an exception if the comment was saved.
 */
	public void assertCommentNotSaved(String commentText) throws Exception {
		PassFailCriteria passFail = new PassFailCriteria(driver);
		passFail.assertElementIsNotPresentByXpath("//div[contains(.,'" + commentText + "')]", this.properties.get("exceptionMessageCommentPresentShouldNotBe"), 2);
	}
/**
 * Makes the test fail if a given submission comment has not been saved.
 * @param commentText The text that was entered when the user entered the comment. This is the text that you are verifying is there.
 * @throws Exception Throws an exception if the comment was not saved.
 */
	public void assertCommentSaved(String commentText) throws Exception {
		PassFailCriteria passFail = new PassFailCriteria(driver);
		passFail.assertElementIsPresentByXpath("//div[contains(.,'" + commentText + "')]", this.properties.get("exceptionMessageCommentNotPresent"), 2);
	}
/**
 * Deletes a comment and confirms the deletion.
 * @param commentText the text of the comment.
 */
	public void clickLinkDeleteSubmissionCommentAndConfirm(String commentText) {
		WebElement link = driver.findElementByXPath("//div[contains(.," + commentText + ")]/div/a[@title='Delete this comment']");
		link.click();
		WebElement yesLink = driver.findElementByLinkText(this.properties.get("linkYes"));
		yesLink.click();
	}
}