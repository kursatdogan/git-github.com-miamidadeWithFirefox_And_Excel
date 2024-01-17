package scott.steps;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import scott.pages.DownloadPage;
import scott.pages.DropBoxPage;
import scott.utils.ConfigurationReader;
import scott.utils.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.util.*;
import java.util.List;


public class download {
    DownloadPage downloadPage = new DownloadPage();
    DropBoxPage dropBoxPage = new DropBoxPage();
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

    @When("I perform the end-to-end process using Excel data")
    public void performEndToEndProcessWithExcelData() throws InterruptedException, IOException {
        // Load the Excel file
        FileInputStream inputStream = new FileInputStream(new File("/Users/kursatdogan/Desktop/autoDownloadExcel.xlsx")); //location of the Excel file in local
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet("Sheet1");

        // Create a CellStyle with green font color (for PASS)
        CellStyle passStyle = workbook.createCellStyle();
        Font passFont = workbook.createFont();
        passFont.setColor(IndexedColors.GREEN.getIndex());
        passStyle.setFont(passFont);

        // Create a CellStyle with red font color (for FAIL)
        CellStyle failStyle = workbook.createCellStyle();
        Font failFont = workbook.createFont();
        failFont.setColor(IndexedColors.RED.getIndex());
        failStyle.setFont(failFont);

        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);


            // Check if the row is null (empty)
            if (row == null) {
                System.out.println("Empty or incomplete row found. Stopping the iteration. Row index: " + rowIndex);
                break;  // Exit the loop when the row is null
            }

            Cell cell = row.getCell(0);

            // Check if the cell is null
            if (cell == null) {
                System.out.println("Empty cell found. Stopping the iteration. Row index: " + rowIndex);
                break;  // Exit the loop when the cell is null
            }

            String combinedValue;

            try {
                combinedValue = cell.getStringCellValue();
            } catch (NullPointerException e) {
                System.out.println("NullPointerException when accessing cell value. Row index: " + rowIndex);
                e.printStackTrace();
                break;
            }

            // Check if the cell value is empty or null
            if (combinedValue == null || combinedValue.isEmpty()) {
                System.out.println("Empty or null cell value found. Stopping the iteration. Row index: " + rowIndex);
                break;  // Exit the loop when the first empty or null cell value is encountered
            }
            String[] parts = combinedValue.split("-");

            String year = parts[0];
            String caseNumber = parts[1];

            // Step 1: Enter case details
            downloadPage.yearInputBox.sendKeys(year);
            downloadPage.focusedYear.click();
            downloadPage.caseNumberInputBox.sendKeys(caseNumber);
            Select dropdown = new Select(downloadPage.caseCodeInputBox);
            dropdown.selectByValue(ConfigurationReader.get("caseCode"));

            // Step 2: Click the search button
            WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(10));
            downloadPage.dockets = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[@class='btn btn-lg coc-button--primary '])[1]")));
            downloadPage.searchButton.click();

            // Step 3: get the filing date and case type
            downloadPage.filingDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_lblFilingDate_Parties")));

            String filingDate = downloadPage.filingDate.getText();
            String caseType = downloadPage.caseType.getText();
            // Assuming 'driver' is your WebDriver instance
            System.out.println("filingDate = " + filingDate);
            System.out.println("caseType = " + caseType);

            // Ensure the row is not null before proceeding
            if (row == null) {
                // If the row doesn't exist, you can create it
                row = sheet.createRow(rowIndex);
            }

            Cell cell1 = row.createCell(1, CellType.STRING);
            Cell cell2 = row.createCell(2, CellType.STRING);
            Cell cell3 = row.createCell(3, CellType.STRING);
            Cell cell4 = row.createCell(4, CellType.STRING);

            cell1.setCellValue(filingDate);
            cell2.setCellValue(caseType);

            // Save the changes to the workbook
            try (FileOutputStream fileOut = new FileOutputStream("/Users/kursatdogan/Desktop/autoDownloadExcel.xlsx")) {
                workbook.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Step 4: Click on dockets
            wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(30));
            downloadPage.dockets = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class=' card-header TSDcardHeader'])[3]")));
            JavascriptExecutor js = (JavascriptExecutor) Driver.get();
            js.executeScript("arguments[0].scrollIntoView();", downloadPage.dockets);
            Thread.sleep(3000);
            ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].click();", downloadPage.dockets);

            // Step 5: Download PDF files
            List<WebElement> elements = downloadPage.pdfIcons;

            // Specify the name of the folder you want to create
            String folderName = "Case_" + caseNumber;

            // Specify the path to the folder, including the folder name
            String folderPath = "/Users/kursatdogan/Desktop/downloaded files/" + folderName;

            // Create a File object with the folder path
            File folder = new File(folderPath);

            // Perform the folder creation operation
            boolean created = folder.mkdir(); // Use mkdirs() for creating parent directories if they don't exist

            for (int index = 1; index <= elements.size(); index++) {
                WebElement element = elements.get(index - 1);

                WebDriverWait pdfWait = new WebDriverWait(Driver.get(), Duration.ofSeconds(20));
                downloadPage.dockets = pdfWait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Hooks.counterExpected++;

                // Specify the path to the file you want to rename
                String filePath = "/Users/kursatdogan/IdeaProjects/git-github.com-miamidade/miamidade/C:\\Users\\Downloads\\test\\/Document.PDF";

                // Create a File object with the current file path
                File file = new File(filePath);

                String newFileName = "";

                // Specify the new name for the file
                newFileName = "Case_" + caseNumber + "_file_" + index + ".pdf";

                // Create a File object with the new file path
                File newFile = new File(file.getParent(), newFileName);

                // Perform the rename operation
                boolean renamed = file.renameTo(newFile);

                // Move PDF files to a folder
                sourceFolderPath = "/Users/kursatdogan/IdeaProjects/git-github.com-miamidade/miamidade/C:\\Users\\Downloads\\test\\";
                destinationFolderPath = "/Users/kursatdogan/Desktop/downloaded files/Case_" + caseNumber;
                movePDFFilesToDestination(sourceFolderPath, destinationFolderPath);

                int actualNumberOfFiles = countPdfFilesInFolder("/Users/kursatdogan/Desktop/downloaded files/Case_" + caseNumber);

                cell3.setCellValue("The expected number of files is: " + index + " The actual number of files is: " + actualNumberOfFiles);
                cell4.setCellValue("PASS");
                cell4.setCellStyle(passStyle);
                // Save the changes to the workbook
                try (FileOutputStream fileOut = new FileOutputStream("/Users/kursatdogan/Desktop/autoDownloadExcel.xlsx")) {
                    workbook.write(fileOut);
                } catch (IOException e) {
                    e.printStackTrace();
                }



                try {
                    Assert.assertEquals("Number of PDF files after download does not match", index, actualNumberOfFiles);
                } catch (AssertionError e) {
                    // Print the error message to the console
                    cell4.setCellValue("FAIL");
                    cell4.setCellStyle(failStyle);
                    try (FileOutputStream fileOut = new FileOutputStream("/Users/kursatdogan/Desktop/autoDownloadExcel.xlsx")) {
                        workbook.write(fileOut);
                    } catch (IOException a) {
                        a.printStackTrace();
                    }
                    System.err.println("Assertion failed: " + e.getMessage());

                }
                Hooks.counterActual++;
            }

            // Get the current window handle
            String parentWindowHandle = Driver.get().getWindowHandle();

            // Get all window handles
            Set<String> windowHandles = Driver.get().getWindowHandles();

            // Iterate through all handles
            for (String handle : windowHandles) {
                // Check if the handle is not the parent window handle
                if (!handle.equals(parentWindowHandle)) {
                    // Switch to the new window
                    Driver.get().switchTo().window(handle);

                    // Perform actions in the new window as needed

                    // Close the new window or switch back to the parent window if required
                    // Driver.get().close();  // Uncomment this line if you want to close the new window
                    Driver.get().switchTo().window(parentWindowHandle); // Uncomment this line to switch back to the parent window
                }
            }

            Driver.get().get("https://www2.miamidadeclerk.gov/ocs/Search.aspx");
            downloadPage.localCaseNumberTab.click();

        }

        // Close the workbook and input stream
        workbook.close();
        inputStream.close();
    }

    @When("I move documents to dropbox")
    public void i_move_documents_to_dropbox() throws InterruptedException, AWTException {

            // Specify the path to the folder you want to zip
            String folderPath = "/Users/kursatdogan/Desktop/downloaded files";

            // Specify the output zip file path
            String zipFilePath = "/Users/kursatdogan/Desktop";

            // Zip the folder
            try {
                zipFolder(folderPath, zipFilePath);
                System.out.println("Folder zipped successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }

        Driver.get().get("https://www.dropbox.com/login");
        Thread.sleep(3000);
        dropBoxPage.dropBoxEmailInputBox.sendKeys("kursatdogan2790@gmail.com");
        dropBoxPage.dropBoxSubmitBtn.click();
        WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(10));
        dropBoxPage.dropBoxPasswordInputBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type=\"password\"]")));
        dropBoxPage.dropBoxPasswordInputBox.sendKeys("0512860kursat");
        dropBoxPage.dropBoxSubmitBtn.click();
        Thread.sleep(15000);
        dropBoxPage.courtDocumentsFolder = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, '_rc-hl-truncated-string__container_1fh0k_36') and contains(., 'court documents')]")));
        dropBoxPage.courtDocumentsFolder.click();
        dropBoxPage.uploadBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='dig-Text dig-Text--variant-label dig-Text--size-small dig-Text--color-inherit dig-Text--isBold _browseActionBarSimpleButton-label_1y8yi_26' and text()='Upload']")));
        dropBoxPage.uploadBtn.click();
        dropBoxPage.folderUpload = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h2[contains(@class, 'dig-Title') and contains(., 'Folder')]")));
        dropBoxPage.folderUpload.click();

        // Delay to give time for the file dialog to open
        Thread.sleep(2000);

        // Path to the folder you want to select
        //String folderPath = "/Users/kursatdogan/Desktop/downloaded files";

        // Set the folder path to the clipboard
        StringSelection stringSelection = new StringSelection(folderPath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

        // Use Robot to paste the folder path into the file dialog
        Robot robot = new Robot();
        robot.delay(1000);  // Optional delay for better reliability
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        // Press Enter to confirm the folder selection
        Thread.sleep(5000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(5000);
    }

    private void downloadInPdfFormat() {
    }
    private String sourceFolderPath;
    private String destinationFolderPath;
    private List<WebElement> pdfIcons;  // Assuming this represents PDF download links


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

    private static void zipFolder(String sourceFolderPath, String zipFilePath) throws IOException {
        try (FileSystem fs = FileSystems.newFileSystem(URI.create("jar:file:" + zipFilePath),
                Map.of("create", "true"))) {
            Path sourcePath = Paths.get(sourceFolderPath);
            Files.walk(sourcePath)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        Path relativePath = sourcePath.relativize(path);
                        Path zipEntryPath = fs.getPath("/", relativePath.toString());
                        try {
                            Files.copy(path, zipEntryPath, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }







}


