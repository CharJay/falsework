package com.charjay.falsework.util;


import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public  class CustomFileUtils {

    private static Logger logger = LoggerFactory.getLogger(CustomFileUtils.class);

    private CustomFileUtils() {
    }

    /**
     * 便捷的关闭流。对于可能抛出的异常，方法内部仅把异常信息输出日志，可根据具体需要修改。<br>
     * <br>
     * 通常只需关闭外层流，底层流会隐式的关闭。<br>
     * 你也可以显式的关闭所有流，注意传参顺序：外层流-->底层流。<br>
     * 若是非嵌套的多个流，则可忽略传参顺序。
     *
     * @param s
     *            需要关闭的流
     */
    public static void closeStreams( AutoCloseable... s ) {
        if (s == null)
            return;
        for (AutoCloseable _s : s) {
            if (_s != null)
                try {
                    _s.close();
                } catch (Exception e) {
                    logger.error(  "尝试关闭流时出错{}", e );
                }
        }
    }
    /**
     * {@link org.apache.commons.io.FileUtils#copyFile(File, File) }
     *
     * @param srcDir
     * @param destDir
     * @throws IOException
     */
    public static void copyDirectory( File srcDir, File destDir  ) throws IOException {
        FileUtils.copyDirectory( srcDir, destDir );
    }
    /**
     * 复制文件夹
     *
     * @param srcDir
     * @param destDir
     * @param fileFilter 选择器，定义文件夹或文件是否需要复制
     * @throws IOException
     */
    public static void copyDirectory( File srcDir, File destDir, FileFilter fileFilter ) throws IOException {
        if (srcDir == null) {
            throw new NullPointerException( "Source must not be null" );
        }
        if (destDir == null) {
            throw new NullPointerException( "Destination must not be null" );
        }
        if (srcDir.exists() == false) {
            throw new FileNotFoundException( "Source '" + srcDir + "' does not exist" );
        }
        if (srcDir.isDirectory() == false) {
            throw new IOException( "Source '" + srcDir + "' exists but is not a directory" );
        }
        if (srcDir.getCanonicalPath().equals( destDir.getCanonicalPath() )) {
            throw new IOException( "Source '" + srcDir + "' and destination '" + destDir + "' are the same" );
        }
        doCopyDirectory( srcDir, destDir, true, fileFilter );
    }

    private static void doCopyDirectory( File srcDir, File destDir, boolean preserveFileDate, FileFilter fileFilter ) throws IOException {
        if (destDir.exists()) {
            if (destDir.isDirectory() == false) {
                throw new IOException( "Destination '" + destDir + "' exists but is not a directory" );
            }
        } else {
            if (destDir.mkdirs() == false) {
                throw new IOException( "Destination '" + destDir + "' directory cannot be created" );
            }
            if (preserveFileDate) {
                destDir.setLastModified( srcDir.lastModified() );
            }
        }
        if (destDir.canWrite() == false) {
            throw new IOException( "Destination '" + destDir + "' cannot be written to" );
        }
        // recurse
        File[] files = srcDir.listFiles();
        if (files == null) { // null if security restricted
            throw new IOException( "Failed to list contents of " + srcDir );
        }
        for (int i = 0; i < files.length; i++) {
            File copiedFile = new File( destDir, files[i].getName() );
            if (files[i].isDirectory()) {
                if( fileFilter == null || fileFilter.accept( files[i] ) ){
                    doCopyDirectory( files[i], copiedFile, preserveFileDate, fileFilter );
                }
            } else {
                if( fileFilter == null || fileFilter.accept( files[i] ) ){
                    doCopyFile( files[i], copiedFile, preserveFileDate );
                }
            }
        }
    }

    private static void doCopyFile( File srcFile, File destFile, boolean preserveFileDate ) throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException( "Destination '" + destFile + "' exists but is a directory" );
        }

        FileInputStream input = new FileInputStream( srcFile );
        try {
            FileOutputStream output = new FileOutputStream( destFile );
            try {
                IOUtils.copy( input, output );
            } finally {
                IOUtils.closeQuietly( output );
            }
        } finally {
            IOUtils.closeQuietly( input );
        }

        if (srcFile.length() != destFile.length()) {
            throw new IOException( "Failed to copy full contents from '" + srcFile + "' to '" + destFile + "'" );
        }
        if (preserveFileDate) {
            destFile.setLastModified( srcFile.lastModified() );
        }
    }
}
