package br.com.indentar.cacheutilities;

import br.com.indentar.jsonsimpleutil.JsonSimpleUtil;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 * Classe utilitária para manipulação de tokens de autenticação em arquivos.
 */
public class FileTokenCacheUtils {

    /**
     * Armazena token
     *
     * @param key
     * @param folderTokens
     * @param token
     * @throws IOException
     */
    public static void saveToken(String key, String folderTokens, Object token) throws IOException {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                String jsonToSave = JsonSimpleUtil.toJson(token);
                File createFileToken = createFileToken(key, folderTokens);
                FileUtils.write(createFileToken, jsonToSave, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * user.home/pastaTokens/chave.json
     *
     * @param chave
     * @param pastaTokens
     * @return
     */
    private static File createFileToken(String chave, String pastaTokens) {
        String property = System.getProperty("user.home");
        String directory = property.concat(File.separator).concat(pastaTokens).concat(File.separator)
                .concat(chave).concat(".json");
        File file = new File(directory);
        return file;
    }

    /**
     * Recupera o token salvo em arquivo.
     *
     *
     * @param <T>
     * @param key
     * @param folderTokens
     * @param clazz
     * @return
     */
    public static <T> T getToken(String key, String folderTokens, Class clazz) {
        try {
            File fileToken = createFileToken(key, folderTokens);
            if (fileToken.exists()) {
                String jsonToken = FileUtils.readFileToString(fileToken, "UTF-8");
                T token = JsonSimpleUtil.toObject(jsonToken, clazz);
                return token;
            }
            return null;
        } catch (IOException ex) {
            Logger.getLogger(FileTokenCacheUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
