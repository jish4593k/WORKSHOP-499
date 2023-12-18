import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class WorksheetContent {
    private int id;
    private String title;
    private String company;
    private String person;
    private String content;
    private Date creationDate;
    private String imageId;

   
    private void deleteFilesAndRecord(Connection connection) throws SQLException {
        String bookPdf = "ap" + imageId + ".pdf";
        String bookPptx = "a/" + imageId + ".pptx";

        
        File pdfFile = new File(bookPdf);
        if (pdfFile.exists()) {
            pdfFile.delete();
        }

        File pptxFile = new File(bookPptx);
        if (pptxFile.exists()) {
            pptxFile.delete();
        }

      
        String deleteRecordQuery = "?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteRecordQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

   
    public static void deleteOldRecords(Connection connection) throws SQLException {
        LocalDate cutoffDate = LocalDate.now().minusDays(30);
        String selectOldRecordsQuery = "S?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectOldRecordsQuery)) {
            preparedStatement.setDate(1, Date.valueOf(cutoffDate));

            List<WorksheetContent> oldRecords =

            
            for (WorksheetContent item : oldRecords) {
                item.deleteFilesAndRecord(connection);
            }
        }
    }

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("y")) {
            deleteOldRecords(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
