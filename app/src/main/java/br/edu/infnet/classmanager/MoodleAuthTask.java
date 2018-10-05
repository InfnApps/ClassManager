package br.edu.infnet.classmanager;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MoodleAuthTask extends AsyncTask<String, Void, Boolean> {

    public interface OnTaskCompleteListener {
        public void onTaskComplete(boolean success);

    }

    private final String MOODLE_VALIDATION_WORD = "token";

    private OnTaskCompleteListener listener;

    public void setOnTaskCompleteListener(
            OnTaskCompleteListener listener){
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    ///    progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    protected Boolean doInBackground(String... parameters) {
        String email = parameters[0];
        String password = parameters[1];
        String moodlePassword = parameters[2];

        return validateWithMoodle(email, moodlePassword);
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);

        listener.onTaskComplete(success);

    }


    private boolean validateWithMoodle(String username, String password){
        String baseurl = "https://lms.infnet.edu.br/moodle/login/token.php" +
                "?username=%s&password=%s&service=moodle_mobile_app";
        // TODO: discriminar cada exceção
        try {
            URL url = new URL(String.format(baseurl, username, password));
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            // pega um objeto que sabe ler dados da conexão (bytes)
            InputStream in = connection.getInputStream();
            // A partir de quem sabe ler bytes, construímos quem sabe ler caracteres
            InputStreamReader reader = new InputStreamReader(in);
            // A partir de quem sabe ler caracteres construímos quem sabe ler caracteres
            // de forma controlada
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            String texto = "";
            while (line != null) {
                texto = texto + line; //texto += line
                line = bufferedReader.readLine();
            }
            if (texto.contains(MOODLE_VALIDATION_WORD)){
                return true;
            }
        } catch (Exception exception){
            return false;
        }
        return false;
    }



}
