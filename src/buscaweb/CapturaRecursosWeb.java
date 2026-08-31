package buscaweb;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

/**
 * Classe responsavel por capturar o codigo-fonte HTML de paginas Web.
 * Mesma interface esperada pelo Main (getListaRecursos / carregarRecursos).
 */
public class CapturaRecursosWeb {

    private ArrayList<String> listaRecursos = new ArrayList<String>();

    public ArrayList<String> getListaRecursos() {
        return listaRecursos;
    }

    public ArrayList<String> carregarRecursos() {
        ArrayList<String> codigos = new ArrayList<String>();
        for (String url : listaRecursos) {
            codigos.add(buscar(url));
        }
        return codigos;
    }

    private String buscar(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
            conn.setRequestProperty("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.9");
            conn.setRequestProperty("Accept-Encoding", "identity");
            conn.setInstanceFollowRedirects(true);

            int status = conn.getResponseCode();

            InputStream in = conn.getInputStream();
            String encoding = conn.getContentEncoding();
            if (encoding != null && encoding.toLowerCase().contains("gzip")) {
                in = new GZIPInputStream(in);
            }

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] chunk = new byte[8192];
            int lidos;
            while ((lidos = in.read(chunk)) != -1) {
                buffer.write(chunk, 0, lidos);
            }
            in.close();
            conn.disconnect();

            String html = new String(buffer.toByteArray(), "UTF-8");
            if (status != 200) {
                System.err.println("AVISO: HTTP " + status + " ao acessar " + urlString);
            }
            return html;
        } catch (Exception e) {
            System.err.println("ERRO ao buscar " + urlString + ": " + e.getMessage());
            return "";
        }
    }
}
