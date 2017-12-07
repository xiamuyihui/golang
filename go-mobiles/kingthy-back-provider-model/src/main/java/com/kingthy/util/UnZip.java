
package com.kingthy.util;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipInputStream;

/**
 * UnZip(描述其作用)
 * <p>
 * 赵生辉 2017年11月23日 17:37
 *
 * @version 1.0.0
 */

public class UnZip {

    /**
     * 解压7z压缩文件到指定路径
     * @param orgPath 压缩文件路径
     * @param tarpath 解压的路径
     */
    public static void apache7ZDecomp(String orgPath, String tarpath) {

        try {
            SevenZFile sevenZFile = new SevenZFile(new File(orgPath));
            SevenZArchiveEntry entry = sevenZFile.getNextEntry();
            while (entry != null) {

                // System.out.println(entry.getName());
                if (entry.isDirectory()) {

                    new File(tarpath + entry.getName()).mkdirs();
                    entry = sevenZFile.getNextEntry();
                    continue;
                }
                FileOutputStream out = new FileOutputStream(tarpath
                    + File.separator + entry.getName());
                byte[] content = new byte[(int) entry.getSize()];
                sevenZFile.read(content, 0, content.length);
                out.write(content);
                out.close();
                entry = sevenZFile.getNextEntry();
            }
            sevenZFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  解压zip文件到指定路径
     * @param archive 压缩文件路径
     * @param decompressDir 解压路径
     * @throws IOException
     */
    public static void readByZipInputStream(String archive, String decompressDir)
        throws IOException
    {
        BufferedInputStream bi = null;
        // ----解压文件(ZIP文件的解压缩实质上就是从输入流中读取数据):
        System.out.println("开始读压缩文件");

        CheckedInputStream csumi = null;
        try
        {
            FileInputStream fi = new FileInputStream(archive);
            csumi = new CheckedInputStream(fi, new CRC32());
            //java.lang.
            ZipInputStream in2 = new ZipInputStream(csumi);
            bi = new BufferedInputStream(in2);
            java.util.zip.ZipEntry ze;// 压缩文件条目
            // 遍历压缩包中的文件条目
            while ((ze = in2.getNextEntry()) != null)
            {
                String entryName = ze.getName();
                if (ze.isDirectory())
                {
                    System.out.println("正在创建解压目录 - " + entryName);
                    File decompressDirFile = new File(decompressDir + "/" + entryName);
                    if (!decompressDirFile.exists())
                    {
                        decompressDirFile.mkdirs();
                    }
                }
                else
                {
                    BufferedOutputStream bos = null;
                    try
                    {
                        System.out.println("正在创建解压文件 - " + entryName);
                        bos = new BufferedOutputStream(new FileOutputStream(decompressDir + "/" + entryName));
                        byte[] buffer = new byte[1024];
                        int readCount = bi.read(buffer);

                        while (readCount != -1)
                        {
                            bos.write(buffer, 0, readCount);
                            readCount = bi.read(buffer);
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        if (null != bos)
                        {
                            bos.close();
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != bi)
            {
                bi.close();
            }
        }
        System.out.println("Checksum: " + csumi.getChecksum().getValue());
    }
}