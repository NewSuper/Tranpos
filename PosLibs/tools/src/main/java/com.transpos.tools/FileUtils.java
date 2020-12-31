package com.transpos.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import static com.transpos.tools.UtilsConstants.NEW_LINE;


/**
 * Created by youthyj on 2018/9/5.
 */
public class FileUtils {
    private static final String Tag = FileUtils.class.getSimpleName();
    private static final int BUFFER_SIZE_DEFAULT = 8 * 1024;

    private FileUtils() throws Exception {
        throw new Exception();
    }

    // 读
    public static String read(String path) {
        if (TextUtils.isEmpty(path)) {

            return "";
        }
        return read(new File(path));
    }

    public static String read(File file) {
        return read(file, null, null, BUFFER_SIZE_DEFAULT);
    }

    public static String read(String path, String encoding, String separator, int bufferLength) {
        if (TextUtils.isEmpty(path)) {

            return "";
        }
        return read(new File(path), encoding, separator, bufferLength);
    }

    public static String read(File file, String encoding, String separator, int bufferLength) {
        if (separator == null || separator.equals("")) {
            separator = NEW_LINE;
        }
        if (!file.exists()) {
            return "";
        }
        StringBuffer str = new StringBuffer();
        FileInputStream fs = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fs = new FileInputStream(file);
            if (encoding == null || encoding.trim().equals("")) {
                isr = new InputStreamReader(fs);
            } else {
                isr = new InputStreamReader(fs, encoding.trim());
            }
            br = new BufferedReader(isr, bufferLength);

            String data;
            while ((data = br.readLine()) != null) {
                str.append(data).append(separator);
            }
            if (str.length() > 0) {
                return str.substring(0, str.lastIndexOf(separator));
            } else {
                return str.toString();
            }
        } catch (IOException e) {

        } finally {
            close(br);
            close(isr);
            close(fs);
        }
        return "";
    }

    // 写(覆盖)
    public static boolean writeCover(String content, String path) {
        if (TextUtils.isEmpty(path)) {

            return false;
        }
        return writeCover(content, new File(path));
    }

    public static boolean writeCover(String content, File target) {
        return write(content, target, false);
    }

    public static boolean writeCover(List<String> contents, String path) {
        if (TextUtils.isEmpty(path)) {

            return false;
        }
        return writeCover(contents, new File(path));
    }

    public static boolean writeCover(List<String> contents, File target) {
        return write(contents, target, false);
    }

    public static boolean writeCover(InputStream is, String path) {
        if (TextUtils.isEmpty(path)) {

            return false;
        }
        return writeCover(is, new File(path));
    }

    public static boolean writeCover(InputStream is, File target) {
        return write(is, target, false);
    }

    // 写(追加)
    public static boolean writeAppend(String content, String path) {
        if (TextUtils.isEmpty(path)) {

            return false;
        }
        return writeAppend(content, new File(path));
    }

    public static boolean writeAppend(String content, File target) {
        return write(content, target, true);
    }

    public static boolean writeAppend(List<String> contents, String path) {
        if (TextUtils.isEmpty(path)) {

            return false;
        }
        return writeAppend(contents, new File(path));
    }

    public static boolean writeAppend(List<String> contents, File target) {
        return write(contents, target, true);
    }

    public static boolean writeAppend(InputStream is, String path) {
        if (TextUtils.isEmpty(path)) {

            return false;
        }
        return writeAppend(is, new File(path));
    }

    public static boolean writeAppend(InputStream is, File target) {
        return write(is, target, true);
    }

    // 写
    public static boolean write(String content, File target, boolean append) {
        if (TextUtils.isEmpty(content)) {

            return false;
        }
        if (target.exists() && target.isDirectory()) {

            return false;
        }
        if (parentNotExists(target)) {

            return false;
        }

        FileWriter fw = null;
        try {
            fw = new FileWriter(target, append);
            fw.write(content);
            return true;
        } catch (IOException e) {

        } finally {
            close(fw);
        }
        return false;
    }

    public static boolean write(List<String> contents, File target, boolean append) {
        if (contents == null || contents.isEmpty()) {

            return false;
        }
        if (target.exists() && target.isDirectory()) {

            return false;
        }
        if (parentNotExists(target)) {

            return false;
        }

        FileWriter fw = null;
        try {
            fw = new FileWriter(target, append);
            for (int i = 0; i < contents.size(); i++) {
                if (i > 0) {
                    fw.write(NEW_LINE);
                }
                fw.write(contents.get(i));
            }
            return true;
        } catch (IOException e) {

        } finally {
            close(fw);
        }
        return false;
    }

    public static boolean write(InputStream is, File target, boolean append) {
        if (is == null) {

            return false;
        }
        if (target.exists() && target.isDirectory()) {

            return false;
        }
        if (parentNotExists(target)) {

            return false;
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(target, append);
            byte data[] = new byte[BUFFER_SIZE_DEFAULT];
            int length;
            while ((length = is.read(data)) != -1) {
                fos.write(data, 0, length);
            }
            fos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(is);
            close(fos);
        }
        return false;
    }

    private static boolean parentNotExists(File target) {
        if (target != null) {
            return !target.exists() && !target.getParentFile().exists() && !target.getParentFile().mkdirs();
        }
        return true;
    }

    // 复制
    public static boolean copy(File source, File dest) {
        if (source == null || dest == null) {

            return false;
        }
        if (!source.exists() && source.isDirectory()) {

            return false;
        }
        if (dest.exists()) {

            return false;
        }
        dest.getParentFile().mkdirs();

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel ifc = null;
        FileChannel ofc = null;
        try {
            fis = new FileInputStream(source);
            fos = new FileOutputStream(dest);
            ifc = fis.getChannel();
            ofc = fos.getChannel();
            MappedByteBuffer mbb = ifc.map(
                    FileChannel.MapMode.READ_ONLY,
                    0,
                    ifc.size()
            );
            ofc.write(mbb);
            return true;
        } catch (IOException e) {

            return false;
        } finally {
            close(ifc);
            close(ofc);
            close(fis);
            close(fos);
        }
    }

    public static void copy(InputStream inputStream, File destFile) throws IOException {
        if (destFile.exists()) {
            destFile.delete();
        }
        final FileOutputStream out = new FileOutputStream(destFile);
        try {
            final byte[] buffer = new byte[4096];
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } finally {
            out.flush();
            try {
                out.getFD().sync();
            } catch (IOException ignored) {
            }
            out.close();
        }
    }

    // 删除
    public static boolean delete(String path) {
        if (TextUtils.isEmpty(path)) {

            return false;
        }
        return delete(new File(path));
    }

    public static boolean delete(File file) {
        if (file == null) {

            return false;
        }
        if (!file.exists()) {

            return false;
        }
        if (file.isDirectory()) {
            String[] children = file.list();
            for (int i = 0; i < children.length; i++) {
                File child = new File(file, children[i]);
                boolean success = delete(child);
                if (!success) {
                    return false;
                }
            }
        }
        boolean success = false;
        try {
            success = file.delete();
//            KLog.d("hzx","文件路径: " + file.getAbsolutePath() + ", 结果: " + success);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!success) {

        }
        return success;
    }

    // 移动
    public static boolean move(File source, File dest) {
        if (source == null || dest == null) {

            return false;
        }
        if (!source.exists() && source.isDirectory()) {

            return false;
        }
        if (dest.exists()) {

            return false;
        }
        return source.renameTo(dest) || (copy(source, dest) && delete(source));
    }

    // 关闭
    public static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {

        }
    }

    public static void close(Channel channel) {
        if (channel == null) {
            return;
        }
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean adCardExit = Environment.getExternalStorageState()
                .endsWith(Environment.MEDIA_MOUNTED);//判断SD卡是否挂载
        if (adCardExit) {
            sdDir = Environment.getExternalStorageDirectory();//获取根目录
        }
        if (sdDir != null) {
            return sdDir.toString();
        } else {
            return "";
        }
    }

    public static void saveAsFile(String data, File file) {
        if (TextUtils.isEmpty(data) || file == null)
            return;
        if (file.exists()) {
            safelyDelete(file);
        }
        FileOutputStream fos = null;
        try {
            file.createNewFile();
            fos = new FileOutputStream(file);
            fos.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean safelyDelete(File file) {
        try {
            return file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getStringFromFile(String fileName) {
        return getStringFromFile(new File(fileName));
    }

    public static String getStringFromFile(File file) {
        if (file == null || !file.exists())
            return "";
        StringBuilder sb = new StringBuilder();
        FileReader fr = null;
        try {
            fr = new FileReader(file);
            char[] buffer = new char[BUFFER_SIZE_DEFAULT];
            int len;
            while ((len = fr.read(buffer)) > 0) {
                sb.append(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }











    /*
     * 文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }


    /**
     * @param filePath 文件路径，like XXX/XXX/XX.mp3
     * @return 专辑封面bitmap
     * @Description 获取专辑封面
     */
    public static Bitmap getMP3AlbumArt(Context context, final String filePath) {
        //能够获取多媒体文件元数据的类
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            byte[] embedPic = retriever.getEmbeddedPicture(); //得到字节型数据
            bitmap = BitmapFactory.decodeByteArray(embedPic, 0, embedPic.length); //转换为图片
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return bitmap;
    }


    public static boolean copyDir(final File srcFile,
                                  final File destFile,
                                  final OnCopyProgressListener listener) {
        listener.onTotal(getDirLength(srcFile));
        return copyOrMoveDir(srcFile, destFile, listener);
    }

    private static boolean copyOrMoveDir(final File srcDir,
                                         final File destDir,
                                         final OnCopyProgressListener listener) {
        if (srcDir == null || destDir == null) return false;
        // destDir's path locate in srcDir's path then return false
        String srcPath = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath)) return false;
        if (!srcDir.exists() || !srcDir.isDirectory()) return false;
        if (!createOrExistsDir(destDir)) return false;
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File oneDestFile = new File(destPath + file.getName());
            if (file.isFile()) {
                if (!copyOrMoveFile(file, oneDestFile, listener)) return false;
            } else if (file.isDirectory()) {
                if (!copyOrMoveDir(file, oneDestFile, listener)) return false;
            }
        }
        return true;
    }

    public static boolean copyOrMoveFile(final File srcFile,
                                          final File destFile,
                                          final OnCopyProgressListener listener) {
        if (srcFile == null || destFile == null) return false;
        // srcFile equals destFile then return false
        if (srcFile.equals(destFile)) return false;
        // srcFile doesn't exist or isn't a file then return false
        if (!srcFile.exists() || !srcFile.isFile()) return false;
        if (destFile.exists()) {
            if (!destFile.delete()) return false;
        }
        if (!createOrExistsDir(destFile.getParentFile())) return false;
        try {
            return writeFileFromIS(destFile, new FileInputStream(srcFile), listener);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean writeFileFromIS(final File file,
                                           final InputStream is,
                                           final OnCopyProgressListener listener) {
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            byte[] data = new byte[8192];
            int len;
            while ((len = is.read(data, 0, 8192)) != -1) {
                os.write(data, 0, len);
                listener.onProgress(len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static long getDirLength(File dir) {
        //1,定义一个求和变量
        long len = 0;
        //2,获取该文件夹下所有的文件和文件夹listFiles();
        File[] subFiles = dir.listFiles();//Demo1_Student.class Demo1_Student.java
        //3,遍历数组
        for (File subFile : subFiles) {
            //4,判断是文件就计算大小并累加
            if (subFile.isFile()) {
                len = len + subFile.length();
                //5,判断是文件夹,递归调用
            } else {
                len = len + getDirLength(subFile);
            }
        }
        return len;
    }

    public static boolean rename(final File file, final String newName) {
        // file is null then return false
        if (file == null) return false;
        // file doesn't exist then return false
        if (!file.exists()) return false;
        // the new name is space then return false
        if (isSpace(newName)) return false;
        // the new name equals old name then return true
        if (newName.equals(file.getName())) return true;
        File newFile = new File(file.getParent() + File.separator + newName);
        // the new name of file exists then return false
        return !newFile.exists()
                && file.renameTo(newFile);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public interface OnCopyProgressListener {
        void onProgress(long progress);

        void onTotal(long total);
    }
}
