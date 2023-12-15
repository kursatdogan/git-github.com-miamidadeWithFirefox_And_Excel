package scott.steps;

import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import scott.pages.DownloadPage;
import scott.utils.ConfigurationReader;
import scott.utils.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import javax.lang.model.SourceVersion;
import javax.swing.*;
import java.io.File;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Set;

public class download {
    DownloadPage downloadPage = new DownloadPage();
    Driver driver;


    @Given("user is on the login page")
    public void user_is_on_the_login_page(){
        Driver.get().get(ConfigurationReader.get("url"));
    }

    @When("user enters valid {string} and {string}")
    public void userEntersWithAnd(String arg0, String arg1) {
        downloadPage.usernameInput.sendKeys(arg0);
        downloadPage.passwordInput.sendKeys(arg1);
        downloadPage.submitButton.click();
    }

    @When("user click login button")
    public void user_click_login_button() {
        downloadPage.loginButton.click();
    }
    @Then("the user should be able to login")
    public void the_user_should_be_able_to_login() {
        Assert.assertTrue(downloadPage.standardSearchButton.isDisplayed());
    }
    @When("I click StandardSearchButton")
    public void i_click_standard_search_button() {
        downloadPage.standardSearchButton.click();
    }
    @When("I click localCaseNumberTab")
    public void i_click_local_case_number_tab() {
        downloadPage.localCaseNumberTab.click();
    }

    @When("I enter {string} {string} {string}")
    public void i_enter(String year, String caseNumber, String caseCode) throws InterruptedException {
        downloadPage.yearInputBox.sendKeys(year);
        downloadPage.focusedYear.click();
        downloadPage.caseNumberInputBox.sendKeys(caseNumber);
        Select dropdown = new Select(downloadPage.caseCodeInputBox);
        dropdown.selectByIndex(4); // Assuming "CA - Circuit Civil" is the 5th option (index starts from 0)
    }
    @When("I click searchButton")
    public void i_click_search_button() {
        downloadPage.searchButton.click();
    }

    @Then("the title should be {string}")
    public void the_title_should_be(String title) {
        String actualTitle = downloadPage.title.getText();
        Assert.assertEquals(actualTitle,title);
    }
    @When("I click dockets")
    public void i_click_dockets() {
        downloadPage.dockets.click();
    }

    @When("I download the pdf files to my folder")
    public void i_download_the_pdf_files_to_my_folder() {
        // Set the path to the desktop folder where you want to create the foldercasenumber
        String desktopFolder = "/Users/kursatdogan/Desktop/autoDownloadCourtDocuments";

        List<WebElement> elements = downloadPage.pdfIcons;

        Actions actions = new Actions(Driver.get());

        // Create a foldercasenumber under the desktop folder
        String pdfFolder = desktopFolder + File.separator + "caseNumber";
        new File(pdfFolder).mkdirs();

        // Iterate through the elements and download PDFs
        for (int index = 1; index <= elements.size(); index++) {
            WebElement element = elements.get(index - 1);
            element.click();



            // Wait for the download to complete (you might need to adjust the time or use a better strategy)
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Assuming the downloaded file is a PDF
            String pdfFileName = "Case_" + index + ".pdf";
            String pdfFilePath = pdfFolder + File.separator + pdfFileName;

            // Rename the downloaded file to move it to the foldercasenumber
            new File(desktopFolder + File.separator + "downloaded.pdf").renameTo(new File(pdfFilePath));

            Driver.get().switchTo().window(Driver.get().getWindowHandles().iterator().next());

            /*Set<String> list = Driver.get().getWindowHandles();

            for (String handle : list) {
                Driver.get().switchTo().window(handle);

                // Perform actions on the current window
                // For example, you can check the title of the window
                String currentWindowTitle = Driver.get().getTitle();
                System.out.println("Window Title: " + currentWindowTitle);

                // Locate elements, perform actions, etc. on the current window

                // If you find the correct window, you can break out of the loop
                if (currentWindowTitle.contains("Civil, Family and Probate Courts Online System")) {
                    break;
                }
            }*/

// Switch back to the original window if needed
            //Driver.get().switchTo().window(originalWindowHandle);

            actions.sendKeys(Keys.COMMAND+"s");


            // Close the current tab or window

            //Driver.get().close();

            // Switch back to the main window (assuming it's the first window handle)

        }
    }



}


