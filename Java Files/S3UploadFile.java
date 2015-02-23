/* 
 * Samuel Benison Jeyaraj Victor
 * sambenison66@gmail.com
 */

import java.io.File;
import java.io.IOException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

//Simple Program to upload file to Amazon S3
public class S3UploadFile {
	private static String bucketName  = "XXXBucket"; // My Existing bucket
	// Two input file details
	private static String keyName1             = "cloudhd2013.csv";
	private static String uploadFileName1      = "C:/Users/XXX/Documents/YYY/hd2013.csv";
	private static String keyName2             = "cloudus-pci.xls";
	private static String uploadFileName2      = "C:/Users/XXX/Documents/YYY/us-pci.xls";
	
	public static void main(String[] args) throws IOException {
		// My AWS credentials
		AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials("Your-credentials", "Your-credentials"));
		long startTime = System.currentTimeMillis(); //Start Time
        try {
            System.out.println("Uploading a new object to S3 from a file\n");
            File file1 = new File(uploadFileName1); //Read the file
            // // Upload it to S3
            s3client.putObject(new PutObjectRequest(
            		                 bucketName, keyName1, file1));
            System.out.println("Uploaded " + keyName1 + " successfully to AWS S3..!!");
            System.out.println("Uploading a new object to S3 from a file\n");
            File file2 = new File(uploadFileName2);
            s3client.putObject(new PutObjectRequest(
            		                 bucketName, keyName2, file2));
            System.out.println("Uploaded " + keyName2 + " successfully to AWS S3..!!");

         } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        } finally {
        	// Time Calculation
        	long endTime = System.currentTimeMillis();
        	long timeTaken = endTime - startTime;
        	System.out.println("Time Taken for Uploading process : " + timeTaken + " ms");
        }
    }
}