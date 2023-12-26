package scott.steps;

import io.cucumber.java.en.And;
import io.cucumber.messages.types.Hook;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
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
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.NoSuchElementException;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.WebDriver;
import scott.utils.SingletonInteger;


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


    @When("I download the pdf files to my folder with {string}")
    public void i_download_the_pdf_files_to_my_folder_with(String caseNumber) throws InterruptedException {
        List<WebElement> elements = downloadPage.pdfIcons;
        SoftAssert softAssert = new SoftAssert();


        // Specify the name of the folder you want to create
        String folderName = "Case_" + caseNumber;

        // Specify the path to the folder, including the folder name
        String folderPath = "/Users/kursatdogan/Desktop/downloaded files/" + folderName;

        // Create a File object with the folder path
        File folder = new File(folderPath);

        // Perform the folder creation operation
        boolean created = folder.mkdir(); // Use mkdirs() for creating parent directories if they don't exist

        // Iterate through the elements and download PDFs
        for (int index = 1; index <= elements.size(); index++) {
            WebElement element = elements.get(index - 1);
            element.click();

            Hooks.counterExpected++;

            // Optional: Clear cookies and switch back to the main window
            Driver.get().manage().deleteAllCookies();
            Driver.get().switchTo().window(Driver.get().getWindowHandles().iterator().next());

            // Optional: Add a delay to wait before proceeding to the next iteration
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Specify the path to the file you want to rename
            String filePath = "/Users/kursatdogan/IdeaProjects/git-github.com-miamidade/miamidade/C:\\Users\\Downloads\\test\\/Document.PDF";

            // Create a File object with the current file path
            File file = new File(filePath);

            String newFileName = "";

            // Specify the new name for the file
            newFileName = folderName + "_file_" + index + ".pdf";


            // Create a File object with the new file path
            File newFile = new File(file.getParent(), newFileName);

            // Perform the rename operation
            boolean renamed = file.renameTo(newFile);


            // Assert that exactly one PDF file is downloaded after each iteration
            //int actualNumberOfFiles = countPdfFilesInFolder("/Users/kursatdogan/IdeaProjects/git-github.com-miamidade/miamidade/C:\\Users\\Downloads\\test\\");


            sourceFolderPath = "/Users/kursatdogan/IdeaProjects/git-github.com-miamidade/miamidade/C:\\Users\\Downloads\\test\\";
            destinationFolderPath = "/Users/kursatdogan/Desktop/downloaded files/Case_" + caseNumber;
            movePDFFilesToDestination(sourceFolderPath, destinationFolderPath);
            int actualNumberOfFiles = countPdfFilesInFolder("/Users/kursatdogan/Desktop/downloaded files/Case_" + caseNumber);
            Assert.assertEquals("Number of PDF files after download does not match", index, actualNumberOfFiles);
            Hooks.counterActual++;
            //softAssert.assertEquals(actualNumberOfFiles, index, "Number of PDF files after download does not match");
            Thread.sleep(3000);

        }

        int expectedFiles = Hooks.counterExpected;
        int actualFiles = Hooks.counterActual;
        System.out.println("Expected number of files to download= " + expectedFiles);
        System.out.println("Actual number of files to download= " + actualFiles);

    }


    private void downloadInPdfFormat() {
        // Insert the code for downloading in PDF format
        // You can use the code provided in the previous response
        // Make sure to replace placeholders like "path/to/chromedriver" and "your_page_url_here"
        // with your actual values
    }





    private String sourceFolderPath;
    private String destinationFolderPath;
    private List<WebElement> pdfIcons;  // Assuming this represents PDF download links

    @Given("there are PDF files in the source folder {string}")
    public void setupFolders(String caseNumber) {
        // Set the paths to your source and destination folders
        sourceFolderPath = "/Users/kursatdogan/IdeaProjects/git-github.com-miamidade/miamidade/C:\\Users\\Downloads\\test\\";
        destinationFolderPath = "/Users/kursatdogan/Desktop/downloaded files/Case_" + caseNumber;
    }

    @When("I move PDF files from the source folder to the destination folder")
    public void movePDFFiles() {

        // Move PDF files to a folder
        movePDFFilesToDestination(sourceFolderPath, destinationFolderPath);
    }

    @Then("the PDF files should be moved successfully to the destination folder")
    public void verifyFilesMoved() {
        // Add assertions or verifications if needed
    }

    private void movePDFFilesToDestination(String sourceFolderPath, String destinationFolderPath) {
        Path sourcePath = Paths.get(sourceFolderPath);
        Path destinationPath = Paths.get(destinationFolderPath);

        try {
            Files.walkFileTree(sourcePath, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // Move the file to the destination folder
                    Path destinationFile = destinationPath.resolve(sourcePath.relativize(file));
                    Files.move(file, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to count PDF files in a folder
    private int countPdfFilesInFolder(String folderPath) {
        Path path = Paths.get(folderPath);
        int count = 0;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*.pdf")) {
            for (Path entry : stream) {
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }







}


