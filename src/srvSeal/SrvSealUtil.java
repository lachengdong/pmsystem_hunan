package srvSeal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class SrvSealUtil
{
  private static String soname = "AutoServerSeal";
  private static String os;
  private static SrvSealUtil obj = new SrvSealUtil();

  static
  {
    if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
      init();
      os = "windows";
      soname = "AutoServerSeal";
    }
    else
    {
      os = "";
      soname = "autoseal";
    }
    System.loadLibrary(soname);
  }

  static void init()
  {
    String path = System.getProperty("java.library.path");

    String dir = path.substring(0, path.indexOf(";"));
    System.out.println("dll´æ·ÅÂ·¾¶£º" + dir);

    File dll = new File(dir + "/" + soname + ".dll");
    try
    {
      InputStream stream = new SrvSealUtil().getClass()
        .getResourceAsStream("/" + soname + ".dll");
      if (stream != null) {
        FileOutputStream fos = new FileOutputStream(dll);
        byte[] b = new byte[1000];
        int n;
        while ((n = stream.read(b)) != -1)
        {
          fos.write(b, 0, n);
        }stream.close();
        fos.close();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public native int openOffice(int paramInt);

  public native int officeToPdf(int paramInt, String paramString1, String paramString2);

  public native int closeOffice(int paramInt);

  public native int openObj(String paramString, int paramInt1, int paramInt2);

  public native int setCtrlPath(String paramString);

  public native int PrintDoc(int paramInt, String paramString);

  public native int setValue(int paramInt, String paramString1, String paramString2);

  public native int login(int paramInt1, int paramInt2, String paramString1, String paramString2);

  public native int addPage(int paramInt, String paramString1, String paramString2);

  public native int addSeal(int paramInt, String paramString1, String paramString2, String paramString3);

  public native int getPageCount(int paramInt);

  public native int saveFile(int paramInt, String paramString1, String paramString2);

  public native int saveFile(int paramInt1, String paramString1, String paramString2, int paramInt2);

  public native int openObj(String paramString, int paramInt);

  public native int openData(byte[] paramArrayOfByte);

  public native int saveFile(int paramInt, String paramString);

  public native String verify(int paramInt);

  public native byte[] getData(int paramInt);

  public native byte[] getSignSHAData(int paramInt);

  public native int getSignPos(int paramInt);

  public native int insertPicture(int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public native int getDocType(String paramString);

  public native int PrintDoc(int paramInt1, String paramString1, String paramString2, int paramInt2);

  public native String getPrinterList();

  public native String getPrinterStatusByStr(String paramString);

  public native String getJobInfoByStr(String paramString, int paramInt);

  public native int resetPrinterByStr(String paramString);

  public native byte[] getSealBmpData(String paramString);

  public native int setSealBmpData(int paramInt, String paramString);

  public native String getNextSeal(int paramInt, String paramString);

  public native int getSealPos(int paramInt, String paramString);

  public native byte[] getSealP7(int paramInt, String paramString);

  public native byte[] getSealSignSHAData(int paramInt, String paramString);

  public static String change(String str)
  {
    String[] strs = str.split("/");
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < strs.length; i++) {
      sb.append(strs[i]).append("\\");
    }
    sb.delete(sb.length() - 1, sb.length());
    return sb.toString();
  }

  public static void main(String[] args)
  {
    String filePath = "D:\\20150530\\a.doc";
    SrvSealUtil s1 = new SrvSealUtil();
    int nObjID = s1.openObj(filePath, 0, 0);
    System.out.println("nObjID" + nObjID);
    String v = s1.verify(nObjID);
    System.out.println("v:" + v);
  }
}