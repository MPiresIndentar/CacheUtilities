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
public class FileJsonCacheUtils {

    /**
     * Armazena cache
     *
     * @param name_key
     * @param folderTokens
     * @param object_to_json
     * @throws IOException
     */
    public static void saveCache(String name_key, String folderTokens, Object object_to_json) throws IOException {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                String jsonToSave = JsonSimpleUtil.toJson(object_to_json);
                saveToken(name_key, folderTokens, jsonToSave);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public static void saveToken(String name_key, String folderTokens, String jsonToken) throws IOException {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                File createFileToken = getFileJsonCache(name_key, folderTokens);
                FileUtils.write(createFileToken, jsonToken, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    /**
     * user.home/pastaTokens/chave.json
     *
     * @param name_key
     * @param pastaTokens
     * @return
     */
    private static File getFileJsonCache(String name_key, String pastaTokens) {
        String property = System.getProperty("user.home");
        String directory = property.concat(File.separator).concat(pastaTokens).concat(File.separator)
                .concat(name_key).concat(".json");
        File file = new File(directory);
        return file;
    }

    /**
     * Recupera o objeto salvo em arquivo json.
     *
     *
     * @param <T>
     * @param key
     * @param folderTokens
     * @param clazz classe do objeto
     * @return
     */
    public static <T> T getObject(String key, String folderTokens, Class clazz) {
        try {
            File fileToken = getFileJsonCache(key, folderTokens);
            if (fileToken.exists()) {
                String jsonToken = FileUtils.readFileToString(fileToken, "UTF-8");
                T token = (T) JsonSimpleUtil.toObject(jsonToken, clazz);
                return token;
            }
            return null;
        } catch (IOException ex) {
            Logger.getLogger(FileJsonCacheUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
