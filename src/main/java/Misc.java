import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Misc {
    public static void main(String[] args) throws IOException {

        List<String> appleIdList = getAppleIds();
//        System.out.println(appleIdList);
        System.out.println(appleIdList.size());

        String pathname = "/Users/DH20026552/Downloads/AAA CRT UPLOADS/Bulk Partner Upload (radar-97714323)/Distributor_Partner_Relation Table blank.sql";
//        String pathname = "/Users/DH20026552/Documents/Demo/src/main/resources/Distributor_Partner_Relation Table.sql";
        File file = new File(pathname);
        StringBuilder finalString = new StringBuilder();


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String str;
            int i = 0;

            while ((str = bufferedReader.readLine()) != null) {
                finalString.append(str.replace("''", appleIdList.get(i))).append("\n");
                i++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(finalString);
    }

    private static List<String> getAppleIds() {
        String appleIdPath = "/Users/DH20026552/Downloads/AAA CRT UPLOADS/Bulk Partner Upload (radar-97714323)/Ase_Partner Table.sql";
        List<String> appleIds = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(appleIdPath))) {
            String line;

            while((line = bufferedReader.readLine()) != null) {
                appleIds.add(line);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return appleIds;
    }
}
