package macc.paxsz.com.myapplication.Javatool;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Base64;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DataFprmatChangeApi {
	  public DataFprmatChangeApi() {
	    }

	    public static byte[] asciiByteArray2BcdArray(byte[] var0) {
	        return hexString2ByteArray(asciiByteArray2String1(var0));
	    }
       //asciiByteArrayתstring
	    public static String asciiByteArray2String(byte[] var0) { 
	        if(var0 != null && var0.length != 0) {
	            StringBuffer var1 = new StringBuffer();
	            char[] var2 = new char[var0.length];
	            int var3 = 0;

	            for(int var4 = 0; var4 < var0.length; ++var4) {
	                if(var0[var4] == 32) {
	                    var3 = var4;
	                    break;
	                }

	                var3 = var0.length;
	                var2[var4] = (char)var0[var4];
	            }

	            var1.append(var2, 0, var3);
	            return var1.toString();
	        } else {
	            return "";
	        }
	    }
	  //asciiByteArray转string
	    public static String asciiByteArray2String1(byte[] var0) {
	        StringBuffer var1 = new StringBuffer();
	        char[] var2 = new char[var0.length];
	        int var3 = 0;

	        for(int var4 = 0; var4 < var0.length && var0[var4] != 0; ++var4) {
	            var3 = var0.length;
	            var2[var4] = (char)var0[var4];
	        }

	        var1.append(var2, 0, var3);
	        return var1.toString();
	    }

	    public static byte[] string2ASCIIByteArray(String var0) {
	        byte[] var1 = null;

	        try {
	            var1 = var0.getBytes("US-ASCII");
	        } catch (UnsupportedEncodingException var3) {

	            var3.printStackTrace();
	        }

	        return var1;
	    }

	    public static byte[] int2BCDByteArray(int var0) {
	        if(var0 <= 9999 && var0 >= 0) {
	            StringBuffer var1 = new StringBuffer(var0 + "");
	            int var2 = var1.length();
	            if(var2 != 4) {
	                for(int var3 = 0; var3 < 4 - var2; ++var3) {
	                    var1.insert(0, '0');
	                }
	            }

	            return hexString2ByteArray(var1.toString());
	        } else {
	            return new byte[]{(byte)0, (byte)0};
	        }
	    }

	    public static byte[] hexString2ByteArray(String var0) {
	        if(var0 != null && !var0.equals("")) {
	            if(var0.length() % 2 != 0) {
	                var0 = var0 + "0";
	            }

	            byte[] var1 = new byte[var0.length() / 2];

	            for(int var2 = 0; var2 < var0.length() / 2; ++var2) {
	                char var3 = var0.charAt(2 * var2);
	                char var4 = var0.charAt(2 * var2 + 1);
	                byte var5 = hexChar2Byte(var3);
	                byte var6 = hexChar2Byte(var4);
	                if(var5 < 0 || var6 < 0) {
	                    return null;
	                }

	                int var7 = var5 << 4;
	                var1[var2] = (byte)(var7 + var6);
	            }

	            return var1;
	        } else {
	            return null;
	        }
	    }

	    public static byte hexString2Byte(String var0) {
	        if(TextUtils.isEmpty(var0)) {
	            return (byte)0;
	        } else {
	            char var2 = var0.charAt(0);
	            char var3 = var0.charAt(1);
	            byte var4 = hexChar2Byte(var2);
	            byte var5 = hexChar2Byte(var3);
	            if(var4 >= 0 && var5 >= 0) {
	                int var6 = var4 << 4;
	                byte var1 = (byte)(var6 + var5);
	                return var1;
	            } else {
	                return (byte)0;
	            }
	        }
	    }

	    public static short[] StringAndShorttoShortArray(short[] var0, String var1) {
	        byte[] var4 = hexString2ByteArray(var1.replaceAll(" ", ""));
	        short[] var2;
	        int var3;
	        if(var0 != null) {
	            var3 = var0.length;
	            var2 = new short[var3 + var4.length];
	            System.arraycopy(var0, 0, var2, 0, var3);
	        } else {
	            var3 = 0;
	            var2 = new short[var4.length];
	        }

	        for(int var5 = 0; var5 < var4.length; ++var5) {
	            var2[var3 + var5] = (short)var4[var5];
	        }

	        return var2;
	    }

	    public static short[] Bytes2ShortArray(byte[] var0) {
	        short[] var1 = null;
	        if(var0 != null) {
	            var1 = new short[var0.length];

	            for(int var2 = 0; var2 < var0.length; ++var2) {
	                var1[var2] = (short)var0[var2];
	            }
	        }

	        return var1;
	    }

	    public static byte hexChar2Byte(char var0) {
	        return var0 >= 48 && var0 <= 57?(byte)(var0 - 48):(var0 >= 97 && var0 <= 102?(byte)(var0 - 97 + 10):(var0 >= 65 && var0 <= 70?(byte)(var0 - 65 + 10):-1));
	    }

	    public static String byteArray2HexString(byte[] var0) {
	        if(var0 != null && var0.length != 0) {
	            StringBuilder var1 = new StringBuilder();
	            byte[] var2 = var0;
	            int var3 = var0.length;

	            for(int var4 = 0; var4 < var3; ++var4) {
	                byte var5 = var2[var4];
	                String var6 = Integer.toHexString(255 & var5);
	                if(var6.length() < 2) {
	                    var6 = "0" + var6;
	                }

	                var1.append(var6);
	            }

	            return var1.toString();
	        } else {
	            return "";
	        }
	    }

	    public static String byteArray2HexStringNotAppendZero(byte[] var0) {
	        if(var0 != null && var0.length != 0) {
	            StringBuilder var1 = new StringBuilder();
	            byte[] var2 = var0;
	            int var3 = var0.length;

	            for(int var4 = 0; var4 < var3; ++var4) {
	                byte var5 = var2[var4];
	                String var6 = Integer.toHexString(255 & var5);
	                if(var6.length() < 2) {
	                    var1.append(var6);
	                }
	            }

	            return var1.toString();
	        } else {
	            return "";
	        }
	    }

	    public static String byteArray2HexStringWithSpace(byte[] var0) {
	        if(var0 != null && var0.length != 0) {
	            StringBuilder var1 = new StringBuilder();
	            byte[] var2 = var0;
	            int var3 = var0.length;

	            for(int var4 = 0; var4 < var3; ++var4) {
	                byte var5 = var2[var4];
	                String var6 = Integer.toHexString(255 & var5);
	                if(var6.length() < 2) {
	                    var6 = "0" + var6;
	                }

	                var1.append(var6);
	                var1.append(" ");
	            }

	            return var1.toString();
	        } else {
	            return "";
	        }
	    }

	    public static String getBCDString(byte[] var0, int var1, int var2) {
	        byte[] var3 = new byte[var2 - var1 + 1];
	        System.arraycopy(var0, var1, var3, 0, var3.length);
	        return byteArray2HexString(var3);
	    }

	    public static String getBCDString(String var0) {
	        byte[] var1 = hexString2ByteArray(var0);
	        return getBCDString(var1, 0, var1.length);
	    }

	    public static String getHexString(byte[] var0, int var1, int var2) {
	        byte[] var3 = new byte[var2 - var1 + 1];
	        System.arraycopy(var0, var1, var3, 0, var3.length);
	        return byteArray2HexStringWithSpace(var3);
	    }

	    public static String shortArray2HexStringWithSpace(short[] var0) {
	        StringBuilder var1 = new StringBuilder();
	        short[] var2 = var0;
	        int var3 = var0.length;

	        for(int var4 = 0; var4 < var3; ++var4) {
	            short var5 = var2[var4];
	            String var6 = Integer.toHexString(255 & var5);
	            if(var6.length() < 2) {
	                var6 = "0" + var6;
	            }

	            var1.append(var6);
	        }

	        return var1.toString();
	    }

	    public static byte[] shortArray2bytes(short[] var0) {
	        byte[] var1 = new byte[var0.length];

	        for(int var2 = 0; var2 < var0.length; ++var2) {
	            var1[var2] = (byte)(255 & var0[var2]);
	        }

	        return var1;
	    }

	    public static byte[] shortArray2bytes2(short[] var0) {
	        byte[] var1 = new byte[var0.length * 2];

	        for(int var2 = 0; var2 < var0.length; ++var2) {
	            var1[var2 * 2 + 1] = (byte)(var0[var2] / 256);
	            var1[var2 * 2] = (byte)(255 & var0[var2]);
	        }

	        return var1;
	    }

	    public static byte[] short2ByteArrayLow(short var0) {
	        byte[] var1 = new byte[]{(byte)(255 & var0), (byte)(var0 / 256)};
	        return var1;
	    }

	    public static byte[] short2ByteArrayHigh(short var0) {
	        byte[] var1 = new byte[]{(byte)(var0 / 256), (byte)(255 & var0)};
	        return var1;
	    }

	    public static byte[] short2BcdByteArray(short var0) {
	        byte var3 = 2;
	        byte[] var4 = new byte[2];

	        for(int var5 = 0; var5 < var3; ++var5) {
	            byte var6 = (byte)(var0 >> 8 * (var3 - var5 - 1) & 255);
	            byte var1 = (byte)(var6 / 10);
	            byte var2 = (byte)(var6 % 10);
	            var4[var5] = (byte)((var1 << 4) + var2);
	        }

	        return var4;
	    }

	    public static int bcdByteArray2Int(byte var0, byte var1) {
	        boolean var4 = false;
	        byte var5 = 0;
	        int var6;
	        if((var0 & 128) == 128) {
	            var6 = var0 + 256;
	        } else {
	            var6 = var0;
	        }

	        byte var2 = (byte)(var6 / 16);
	        byte var3 = (byte)(var6 % 16);
	        int var7 = var5 + var2 * 1000 + var3 * 100;
	        if((var1 & 128) == 128) {
	            var6 = var1 + 256;
	        } else {
	            var6 = var1;
	        }

	        var2 = (byte)(var6 / 16);
	        var3 = (byte)(var6 % 16);
	        var7 += var2 * 10 + var3;
	        return var7;
	    }

	    public static int bcdByteArray2Int(byte[] var0) {
	        boolean var3 = false;
	        boolean var4 = false;
	        int var5;
	        if((var0[0] & 128) == 128) {
	            var5 = var0[0] + 256;
	        } else {
	            var5 = var0[0];
	        }

	        byte var1 = (byte)(var5 / 16);
	        byte var2 = (byte)(var5 % 16);
	        int var6 = var1 * 1000 + var2 * 100;
	        if((var0[1] & 128) == 128) {
	            var5 = var0[1] + 256;
	        } else {
	            var5 = var0[1];
	        }

	        var1 = (byte)(var5 / 16);
	        var2 = (byte)(var5 % 16);
	        var6 += var1 * 10 + var2;
	        return var6;
	    }

	    public static int byte2int(byte[] var0, int var1, int var2) {
	        return var0[1] & 255 | var0[0] << 8 & '\uff00';
	    }

	    public static int byte2int(byte var0, byte var1) {
	        return var1 & 255 | var0 << 8 & '\uff00';
	    }

	    public static byte[] int2ByteArray(int var0) {
	        byte[] var1 = new byte[]{(byte)(var0 >> 24 & 255), (byte)(var0 >> 16 & 255), (byte)(var0 >> 8 & 255), (byte)(var0 & 255)};
	        return var1;
	    }

	    public static String byte2BinaryString(byte var0) {
	        String var1 = "00000000";
	        String var2 = Integer.toBinaryString(var0);
	        if(var2.length() > 8) {
	            var2 = var2.substring(var2.length() - 8);
	        } else if(var2.length() < 8) {
	            var2 = "00000000".substring(var2.length()) + var2;
	        }

	        return var2;
	    }

	    public static byte getCrc(byte[] var0, int var1, int var2) {
	        byte var3 = 0;

	        for(int var4 = var1; var4 <= var2; ++var4) {
	            var3 ^= var0[var4];
	        }

	        return var3;
	    }

	    public static byte[] getTLVData(String var0, int var1, byte[] var2) {
	        byte[] var3 = new byte[1024];
	        byte var4 = 0;
	        byte[] var5 = hexString2ByteArray(var0);
	        System.arraycopy(var5, 0, var3, var4, var5.length);
	        int var7 = var4 + var5.length;
	        if(var1 > 127 && var1 < 255) {
	            var3[var7++] = -127;
	        }

	        var3[var7++] = (byte)var1;
	        System.arraycopy(var2, 0, var3, var7, var1);
	        var7 += var1;
	        byte[] var6 = new byte[var7];
	        System.arraycopy(var3, 0, var6, 0, var7);
	        return var6;
	    }

	    public static byte[] mergeByteArray(byte[] var0, byte[] var1) {
	        if(null == var0 && null != var1) {
	            return var1;
	        } else if(null == var0 && null == var1) {
	            return new byte[0];
	        } else if(null != var0 && null == var1) {
	            return var0;
	        } else {
	            byte[] var2 = new byte[var0.length + var1.length];
	            System.arraycopy(var0, 0, var2, 0, var0.length);
	            System.arraycopy(var1, 0, var2, var0.length, var1.length);
	            return var2;
	        }
	    }

	    public static byte[] getSubByteArray(byte[] var0, int var1, int var2) {
	        byte[] var3 = new byte[var2];

	        try {
	            System.arraycopy(var0, var1, var3, 0, var3.length);
	        } catch (Exception var5) {
	            ;
	        }

	        return var3;
	    }

	    public static byte[] byteAppendOne(byte[] var0, byte var1) {
	        byte[] var2 = new byte[]{var1};
	        byte[] var3 = mergeByteArray(var0, var2);
	        return var3;
	    }

	    public static byte[] mergeByteArray(byte[][] var0) {
	        byte[] var1 = new byte[0];

	        for(int var2 = 0; var2 < var0.length; ++var2) {
	            var1 = mergeByteArray(var1, var0[var2]);
	        }

	        return var1;
	    }

	    public static byte[] getTLVData(String var0, String var1) {
	        byte[] var2 = string2ASCIIByteArray(var1);
	        byte[] var3 = null;

	        try {
	            var3 = getTLVData(var0, var2.length, var2);
	        } catch (Exception var5) {
	            var5.printStackTrace();
	        }

	        return var3;
	    }

	    public static byte[] getTLVData(String var0, byte[] var1) {
	        byte[] var2 = null;

	        try {
	            var2 = getTLVData(var0, var1.length, var1);
	        } catch (Exception var4) {
	            var4.printStackTrace();
	        }

	        return var2;
	    }

	    public static byte[] addLL2ByteArr(byte[] var0) {
	        if(null != var0 && 0 != var0.length) {
	            byte[] var1 = int2BCDByteArray(var0.length);
	            byte[] var2 = mergeByteArray(var1, var0);
	            return var2;
	        } else {
	            return new byte[0];
	        }
	    }

	    public static byte[] DoubleList2ByteArray(ArrayList<Double> var0) {
	        if(var0 == null) {
	            return new byte[0];
	        } else {
	            byte[] var1 = new byte[var0.size()];

	            for(int var2 = 0; var2 < var0.size(); ++var2) {
	                var1[var2] = (byte)(new Double(((Double)var0.get(var2)).doubleValue())).intValue();
	            }

	            return var1;
	        }
	    }

	    public static byte[] ByteArrayList2ByteArray(List<byte[]> var0) {
	        ArrayList var1 = new ArrayList();
	        Iterator var2 = var0.iterator();

	        while(var2.hasNext()) {
	            byte[] var3 = (byte[])var2.next();

	            for(int var4 = 0; var4 < var3.length; ++var4) {
	                var1.add(Byte.valueOf(var3[var4]));
	            }
	        }

	        byte[] var5 = new byte[var1.size()];

	        for(int var6 = 0; var6 < var1.size(); ++var6) {
	            var5[var6] = ((Byte)var1.get(var6)).byteValue();
	        }

	        return var5;
	    }

	    public static String decodingTLV(String var0, String var1) {
	        if(var0 != null && var0.length() % 2 == 0) {
	            String var2 = "";
	            int var3 = 0;

	            while(var3 < var0.length()) {
	                try {
	                    String var4 = var0.substring(var3, var3 += 2);
	                    if((Integer.parseInt(var4, 16) & 31) == 31) {
	                        var4 = var4 + var0.substring(var3, var3 += 2);
	                    }

	                    String var5 = var0.substring(var3, var3 += 2);
	                    int var6 = Integer.parseInt(var5, 16);
	                    if(var6 > 128) {
	                        int var7 = var6 - 128;
	                        var5 = var0.substring(var3, var3 += var7 * 2);
	                        var6 = Integer.parseInt(var5, 16);
	                    }

	                    var6 *= 2;
	                    String var10 = var0.substring(var3, var3 += var6);
	                    if(var1.equalsIgnoreCase(var4)) {
	                        var2 = var10;
	                        break;
	                    }
	                } catch (NumberFormatException var8) {
	                    throw new RuntimeException("Error parsing number", var8);
	                } catch (IndexOutOfBoundsException var9) {
	                    throw new RuntimeException("Error processing field", var9);
	                }
	            }

	            return var2;
	        } else {
	            throw new RuntimeException("Invalid tlv, null or odd length");
	        }
	    }

	    public static byte[] byteArrayBase64Encode(byte[] var0) {
	        return Base64.encode(var0, 1);
	    }

	    public static byte[] byteArrayBase64Decode(byte[] var0) {
	        return Base64.decode(var0, 1);
	    }

	    public static byte[] bcd2Ascii(byte[] var0) {
	        byte[] var1 = new byte[2];

	        for(int var2 = 0; var2 < var0.length; ++var2) {
	            if((var0[var2] & 240) >> 4 <= 9) {
	                var1[var2 * 2] = (byte)((var0[var2] >> 4 & 15) + 48);
	            } else {
	                var1[var2 * 2] = (byte)(var0[var2] >> 4 & 70);
	            }

	            var1[var2 * 2 + 1] = (byte)((var0[var2] & 15) + 48);
	        }

	        return var1;
	    }

	    public static String hexByteArray2BinaryStr(byte[] var0) {
	        String[] var1 = new String[]{"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
	        String var2 = "";
	        boolean var3 = false;
	        byte[] var4 = var0;
	        int var5 = var0.length;

	        for(int var6 = 0; var6 < var5; ++var6) {
	            byte var7 = var4[var6];
	            int var8 = (var7 & 240) >> 4;
	            var2 = var2 + var1[var8];
	            var8 = var7 & 15;
	            var2 = var2 + var1[var8];
	        }

	        return var2;
	    }

	    public static String binaryString2hexString(String var0) {
	        if(var0 != null && !var0.equals("") && var0.length() % 8 == 0) {
	            StringBuffer var1 = new StringBuffer();
	            boolean var2 = false;

	            for(int var3 = 0; var3 < var0.length(); var3 += 4) {
	                int var5 = 0;

	                for(int var4 = 0; var4 < 4; ++var4) {
	                    var5 += Integer.parseInt(var0.substring(var3 + var4, var3 + var4 + 1)) << 4 - var4 - 1;
	                }

	                var1.append(Integer.toHexString(var5));
	            }

	            return var1.toString();
	        } else {
	            return null;
	        }
	    }

	    public static byte[] str2Bcd(String var0) {
	        int var1 = var0.length();
	        int var2 = var1 % 2;
	        if(var2 != 0) {
	            var0 = "0" + var0;
	            var1 = var0.length();
	        }

	        byte[] var3 = new byte[var1];
	        if(var1 >= 2) {
	            var1 /= 2;
	        }

	        byte[] var4 = new byte[var1];
	        var3 = var0.getBytes();

	        for(int var7 = 0; var7 < var0.length() / 2; ++var7) {
	            int var5;
	            if(var3[2 * var7] >= 48 && var3[2 * var7] <= 57) {
	                var5 = var3[2 * var7] - 48;
	            } else if(var3[2 * var7] >= 97 && var3[2 * var7] <= 122) {
	                var5 = var3[2 * var7] - 97 + 10;
	            } else {
	                var5 = var3[2 * var7] - 65 + 10;
	            }

	            int var6;
	            if(var3[2 * var7 + 1] >= 48 && var3[2 * var7 + 1] <= 57) {
	                var6 = var3[2 * var7 + 1] - 48;
	            } else if(var3[2 * var7 + 1] >= 97 && var3[2 * var7 + 1] <= 122) {
	                var6 = var3[2 * var7 + 1] - 97 + 10;
	            } else {
	                var6 = var3[2 * var7 + 1] - 65 + 10;
	            }

	            int var8 = (var5 << 4) + var6;
	            byte var9 = (byte)var8;
	            var4[var7] = var9;
	        }

	        return var4;
	    }

	    public static byte[] intToBytes(int var0) {
	        byte[] var1 = new byte[]{(byte)(var0 & 255), (byte)(var0 >> 8 & 255), (byte)(var0 >> 16 & 255), (byte)(var0 >> 24 & 255)};
	        return var1;
	    }
	    //��int����ȫ�����int����
	public static int intarr2int(int[] arr) {
		String a = null;
		int b = 0;
		for (int i = 0; i < arr.length - 1; i++) {

			a = a + arr[i];
			try {
//							String bString=a.substring(4, 4+6);
				String bString = a.substring(0, a.length());
				b = Integer.valueOf(bString).intValue();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

		}
		return b;
	}
	//��int������int����
	public static int intarr2int1(int[] arr) {
		String a = null;
		int b = 0;
		for (int i = 0; i < arr.length - 1; i++) {

			a = a + arr[i];
			try {
				String bString = a.substring(4, 4 + 6);
//	    			String bString=a.substring(0, a.length());
				b = Integer.valueOf(bString).intValue();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

		}
		return b;
	}
			

	    public static byte[] intToBytes2(int var0) {
	        byte[] var1 = new byte[]{(byte)(var0 >> 24 & 255), (byte)(var0 >> 16 & 255), (byte)(var0 >> 8 & 255), (byte)(var0 & 255)};
	        return var1;
	    }

	    public static int bytesToInt(byte[] var0, int var1) {
	        int var2 = var0[var1] & 255 | (var0[var1 + 1] & 255) << 8 | (var0[var1 + 2] & 255) << 16 | (var0[var1 + 3] & 255) << 24;
	        return var2;
	    }

	    public static int bytesToInt2(byte[] var0, int var1) {
	        int var2 = (var0[var1] & 255) << 24 | (var0[var1 + 1] & 255) << 16 | (var0[var1 + 2] & 255) << 8 | var0[var1 + 3] & 255;
	        return var2;
	    }

	    public static boolean endOfBytesIsOneGBK(byte[] var0) {
	        if(var0 != null && var0.length != 0) {
	            boolean var1 = true;
	            if(var0[var0.length - 1] < -1) {
	                int var2;
	                for(var2 = var0.length - 1; var2 >= 0 && var0[var2] < 0; --var2) {
	                    ;
	                }

	                if(var2 == 0) {
	                    ;
	                }

	                int var3 = var0.length - var2 - 1;
	                if(var3 % 2 == 0) {
	                    var1 = false;
	                }
	            } else {
	                var1 = false;
	            }

	            return var1;
	        } else {
	            return false;
	        }
	    }

	    public static byte[] xor8(byte[] var0, byte[] var1) {
	        byte[] var2 = new byte[8];
	        if(var0 != null && var1 != null && var0.length >= 8 && var1.length >= 8) {
	            for(int var3 = 0; var3 < 8; ++var3) {
	                var2[var3] = (byte)(var0[var3] ^ var1[var3]);
	            }

	            return var2;
	        } else {
	            return null;
	        }
	    }

	    public static void main(String[] var0) {
	        byte[] var1 = new byte[]{(byte)-76, (byte)-14, (byte)-45, (byte)-95, (byte)-41, (byte)-42, (byte)-73, (byte)-5, (byte)-76, (byte)-82, (byte)-54, (byte)-79, (byte)-93, (byte)-70, (byte)-44, (byte)-38, (byte)-46, (byte)-86, (byte)-45, (byte)-48, (byte)-46, (byte)-69, (byte)-72, (byte)-10, (byte)53, (byte)-41, (byte)-42, (byte)-67, (byte)-38, (byte)-75, (byte)-60, (byte)-54, (byte)-3, (byte)-66, (byte)-35, (byte)-51, (byte)-73, (byte)-93};
	        byte[] var2 = new byte[]{(byte)-76, (byte)-14, (byte)-45, (byte)-95, (byte)-41, (byte)-42, (byte)-73, (byte)-5, (byte)-76, (byte)-82, (byte)-54, (byte)-79, (byte)-93, (byte)-70, (byte)-44, (byte)-38, (byte)-46, (byte)-86, (byte)-45, (byte)-48, (byte)-46, (byte)-69, (byte)-72, (byte)-10, (byte)53, (byte)-41, (byte)-42, (byte)-67, (byte)-38, (byte)-75, (byte)-60, (byte)-54, (byte)-3, (byte)-66, (byte)-35, (byte)-51, (byte)-73, (byte)-93, (byte)-88, (byte)-41, (byte)-42, (byte)-52, (byte)-27, (byte)-76, (byte)-13, (byte)-48, (byte)-95};
	        byte[] var3 = new byte[]{(byte)-76, (byte)-14, (byte)-45, (byte)-95, (byte)-41, (byte)-42, (byte)-73, (byte)-5, (byte)-76, (byte)-82, (byte)-54, (byte)-79, (byte)-93, (byte)-70, (byte)-44, (byte)-38, (byte)-46, (byte)-86, (byte)-45, (byte)-48, (byte)-46, (byte)-69, (byte)-72, (byte)-10};
	        byte[] var4 = var1;
	        int var5 = var1.length;

	        int var6;
	        byte var7;
	        for(var6 = 0; var6 < var5; ++var6) {
	            var7 = var4[var6];
	            System.out.print(var7 + "\t");
	        }

	        System.out.println();
	        var4 = var2;
	        var5 = var2.length;

	        for(var6 = 0; var6 < var5; ++var6) {
	            var7 = var4[var6];
	            System.out.print(var7 + "\t");
	        }

	        System.out.println();
	        boolean var8 = endOfBytesIsOneGBK(var1);
	        System.out.println("bool = " + var8);
	        boolean var9 = endOfBytesIsOneGBK(var2);
	        System.out.println("bool2 = " + var9);
	        boolean var10 = endOfBytesIsOneGBK(var3);
	        System.out.println("bool2 = " + var10);
	    }

	    public static String toStringHex(String var0) {
	        byte[] var1 = new byte[var0.length() / 2];

	        for(int var2 = 0; var2 < var1.length; ++var2) {
	            try {
	                var1[var2] = (byte)(255 & Integer.parseInt(var0.substring(var2 * 2, var2 * 2 + 2), 16));
	            } catch (Exception var5) {
	                var5.printStackTrace();
	            }
	        }

	        try {
	            var0 = new String(var1, "utf-8");
	        } catch (Exception var4) {
	            var4.printStackTrace();
	        }

	        return var0;
	    }

	    public static byte[] commonString2Bcd(String var0) {
	        int var1 = var0.length();
	        int var2 = var1 % 2;
	        if(var2 != 0) {
	            var0 = "0" + var0;
	            var1 = var0.length();
	        }

	        byte[] var3 = new byte[var1];
	        if(var1 >= 2) {
	            var1 /= 2;
	        }

	        byte[] var4 = new byte[var1];
	        var3 = var0.getBytes();

	        for(int var7 = 0; var7 < var0.length() / 2; ++var7) {
	            int var5;
	            if(var3[2 * var7] >= 48 && var3[2 * var7] <= 57) {
	                var5 = var3[2 * var7] - 48;
	            } else if(var3[2 * var7] >= 97 && var3[2 * var7] <= 122) {
	                var5 = var3[2 * var7] - 97 + 10;
	            } else {
	                var5 = var3[2 * var7] - 65 + 10;
	            }

	            int var6;
	            if(var3[2 * var7 + 1] >= 48 && var3[2 * var7 + 1] <= 57) {
	                var6 = var3[2 * var7 + 1] - 48;
	            } else if(var3[2 * var7 + 1] >= 97 && var3[2 * var7 + 1] <= 122) {
	                var6 = var3[2 * var7 + 1] - 97 + 10;
	            } else {
	                var6 = var3[2 * var7 + 1] - 65 + 10;
	            }

	            int var8 = (var5 << 4) + var6;
	            byte var9 = (byte)var8;
	            var4[var7] = var9;
	        }

	        return var4;
	    }

	    public static String hexStringXOR(String var0) {
	        if(!TextUtils.isEmpty(var0) && var0.length() != 0) {
	            String var1 = hexByteArray2BinaryStr(hexString2ByteArray(var0));
	            char[] var2 = var1.toCharArray();

	            for(int var3 = 0; var3 < var2.length; ++var3) {
	                if(var2[var3] == 48) {
	                    var2[var3] = 49;
	                } else if(var2[var3] == 49) {
	                    var2[var3] = 48;
	                }
	            }

	            new String();
	            var1 = binaryString2hexString(String.valueOf(var2));
	            return var1;
	        } else {
	            return "";
	        }
	    }
	    
	    /**  
	     * �ַ���ת����ʮ�������ַ��� 
	     * @param String str ��ת����ASCII�ַ��� 
	     * @return String ÿ��Byte֮��ո�ָ�����: [61 6C 6B] 
	     */    
	    public static String str2HexStr(String str)  
	    {    
	  
	        char[] chars = "0123456789ABCDEF".toCharArray();    
	        StringBuilder sb = new StringBuilder("");  
	        byte[] bs = str.getBytes();    
	        int bit;    
	          
	        for (int i = 0; i < bs.length; i++)  
	        {    
	            bit = (bs[i] & 0x0f0) >> 4;    
	            sb.append(chars[bit]);    
	            bit = bs[i] & 0x0f;    
	            sb.append(chars[bit]);   
	        }    
	        return sb.toString().trim();    
	    }  
	    
	    
	    
	    
	    
	    
	    
	    
	   /*
	    * ͼƬ���� 
	    */
	    
		public static int saveImage(Bitmap bmp) {
			File appDir = new File("/sdcard/", "bitmap");
			if (!appDir.exists()) {
				appDir.mkdir();
			}
			String fileName = "test" + ".jpg";
			File file = new File(appDir, fileName);
			try {
				FileOutputStream fos = new FileOutputStream(file);
				bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 0;
		}
		
		public static byte[] toBmpBytes(Bitmap bitmap) {

			if (bitmap == null)
				return null;

			int nBmpWidth = bitmap.getWidth();
			int nBmpHeight = bitmap.getHeight();
			int nFixBmpHeight = ((nBmpHeight + 7) >> 3) << 3;
			int biBitCount = 1;
			int wWidth = ((((nBmpWidth * biBitCount) + 31) & ~31) >> 3);
			int bufferSize = wWidth * nFixBmpHeight;
			try {
				//bmp�ļ�ͷ
				int bfType = 0x4d42;
				long bfOffBits = 14 + 40 + 8;
				long bfSize = bfOffBits + bufferSize;
				int bfReserved1 = 0;
				int bfReserved2 = 0;
				ByteBuffer byteBuffer = ByteBuffer.allocate((int) bfSize);
				// ����bmp�ļ�ͷ
				writeWord(byteBuffer, bfType);
				writeDword(byteBuffer, bfSize);
				writeWord(byteBuffer, bfReserved1);
				writeWord(byteBuffer, bfReserved2);
				writeDword(byteBuffer, bfOffBits);

				//bmp��Ϣͷ
				long biSize = 40L;
				long biWidth = nBmpWidth;
				long biHeight = nFixBmpHeight;
				int biPlanes = 1;
				//	            int biBitCount = 1;
				long biCompression = 0L;
				long biSizeImage = bufferSize;
				long biXpelsPerMeter = 0L;
				long biYPelsPerMeter = 0L;
				long biClrUsed = 0L;
				long biClrImportant = 0L;
				// ����bmp��Ϣͷ
				writeDword(byteBuffer, biSize);
				writeLong(byteBuffer, biWidth);
				writeLong(byteBuffer, biHeight);
				writeWord(byteBuffer, biPlanes);
				writeWord(byteBuffer, biBitCount);
				writeDword(byteBuffer, biCompression);
				writeDword(byteBuffer, biSizeImage);
				writeLong(byteBuffer, biXpelsPerMeter);
				writeLong(byteBuffer, biYPelsPerMeter);
				writeDword(byteBuffer, biClrUsed);
				writeDword(byteBuffer, biClrImportant);
				writeLong(byteBuffer, 0xff000000L);
				writeLong(byteBuffer, 0xffffffffL);

				byte[] fix = new byte[(nFixBmpHeight - nBmpHeight) * wWidth];
				Arrays.fill(fix, (byte) 0xff);
				byteBuffer.put(fix);
				byte data[] = byteBuffer.array();
				int position = byteBuffer.position();
				int binary;
				int nCol, nRealCol;
				int wRow;
				int clr;
				int tmp;
				for (nCol = 0, nRealCol = nBmpHeight - 1; nCol < nBmpHeight; ++nCol, --nRealCol) {
					binary = 0;
					for (wRow = 0; wRow < (wWidth << 3); wRow++) {
						if (wRow < nBmpWidth) {
							clr = bitmap.getPixel(wRow, nCol);
							tmp = (Color.red(clr) + Color.green(clr) + Color.blue(clr)) / 3 > 200 ? 1 : 0;
							//	                        tmp = (Color.red(clr) > 180 && Color.green(clr) > 180 && Color.blue(clr) > 180) ? 1 : 0;
							binary <<= 1;
							binary |= tmp;
						} else {
							binary <<= 1;
						}

						if ((wRow + 1) % 8 == 0) {
							data[position + wWidth * nRealCol + (((wRow + 1) >> 3) - 1)] = (byte) binary;
							binary = 0;
						}
					}
				}
				return data;

			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		private static void writeWord(ByteBuffer buffer, int value) throws IOException {
			buffer.put((byte) (value & 0xff));
			buffer.put((byte) (value >> 8 & 0xff));
		}

		private static void writeDword(ByteBuffer buffer, long value) throws IOException {
			buffer.put((byte) (value & 0xff));
			buffer.put((byte) (value >> 8 & 0xff));
			buffer.put((byte) (value >> 16 & 0xff));
			buffer.put((byte) (value >> 24 & 0xff));
		}

		private static void writeLong(ByteBuffer buffer, long value) throws IOException {
			buffer.put((byte) (value & 0xff));
			buffer.put((byte) (value >> 8 & 0xff));
			buffer.put((byte) (value >> 16 & 0xff));
			buffer.put((byte) (value >> 24 & 0xff));
		}
}
