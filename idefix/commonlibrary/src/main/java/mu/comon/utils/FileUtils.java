package mu.comon.utils;

import android.content.Context;
import android.util.Log;
import javax.inject.Inject;

/**
 * Created on 10.4.15.
 */
public class FileUtils {

  private final Context context;

  @Inject public FileUtils(Context c) {
    context = c;
  }

  public void test() {
    Log.i("--->", "<----");
  }

  //    private void writeToFile(String data) {
  //        try {
  //            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput
  // ("config.txt", Context.MODE_PRIVATE));
  //            outputStreamWriter.write(data);
  //            outputStreamWriter.close();
  //        }
  //        catch (IOException e) {
  //            Log.e("Exception", "File write failed: " + e.toString());
  //        }
  //    }
  //
  //
  //    private String readFromFile() {
  //
  //        String ret = "";
  //
  //        try {
  //            InputStream inputStream = openFileInput("config.txt");
  //
  //            if ( inputStream != null ) {
  //                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
  //                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
  //                String receiveString = "";
  //                StringBuilder stringBuilder = new StringBuilder();
  //
  //                while ( (receiveString = bufferedReader.readLine()) != null ) {
  //                    stringBuilder.append(receiveString);
  //                }
  //
  //                inputStream.close();
  //                ret = stringBuilder.toString();
  //            }
  //        }
  //        catch (FileNotFoundException e) {
  //            Log.e("login activity", "File not found: " + e.toString());
  //        } catch (IOException e) {
  //            Log.e("login activity", "Can not read file: " + e.toString());
  //        }
  //
  //        return ret;
  //    }
}
