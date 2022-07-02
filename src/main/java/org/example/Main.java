package org.example;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        String GOOGLE_APPLICATION_CREDENTIALS="/Users/ajaylee/Downloads/google-services.json";
        String EXCEL_FILE_PATH="/Users/ajaylee/Downloads/KISHORE-EL.xlsx";

        try {

            FileInputStream serviceAccount =
                    new FileInputStream(GOOGLE_APPLICATION_CREDENTIALS);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
            Firestore db = FirestoreClient.getFirestore();
            db.collection("products").listDocuments().forEach((documentReference ->System.out.println(documentReference.getId()) ));

            FileInputStream file = new FileInputStream(EXCEL_FILE_PATH);
            Workbook workbook = new XSSFWorkbook(file);


            Sheet sheet = workbook.getSheet("RICEMILL SPARE");

           Iterator iterator= sheet.iterator();
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Product product = new Product();

                product.setDepartment("RICE MILL SPAR");
                product.setCategory("RICE MILL");
                product.setColor("0xffef5350");
                Map<String,Object> map =  new HashMap<>();
                map.put("1",row.getCell(7).getNumericCellValue());
                product.setConfig(map);
                product.setDescription("All Parts are available");
                product.setImageUrl("https://firebasestorage.googleapis.com/v0/b/mstar-8a5d4.appspot.com/o/Productimages%2F2022-04-25T07%3A23%3A23.871081?alt=media&token=e1409ff2-8ff3-4b60-a3db-2520b3ae15fa");
                product.setAvaliable(true);
                product.setRating(5);
                product.setRequiredDocument(false);
                product.setSize("");
                product.setTitle((row.getCell(1).getStringCellValue()));
                ArrayList<String> urlList = new ArrayList<String>();
                urlList.add("http://www.mridulbearing.in/images/vbelt-fenner.jpg");
                product.setUrlList(urlList);
                db.collection("products").add(product);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}