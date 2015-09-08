package com.malgn.aesDemo;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AESUtil 
{
	Cipher aesCipher;
	byte[] salt = {(byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,(byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99};
	final int keyLengthInBits = 128;
	
	public String[] GenerateKeyandIVbyPassword(String strPassword)
	{
		String[] retStringArray = new String[2];
		String strKey = "";
		String strIV = "";
		
		try
		{
			byte[] keyBytes = PBKDF2.deriveKey(strPassword.getBytes(), salt, 20, keyLengthInBits/8);
			strKey = ByteArrayToHexString(keyBytes);
			retStringArray[0] = strKey;
			
			StringBuffer sb = new StringBuffer();
			sb.append(strPassword);
			String revPassword = sb.reverse().toString();
			
			byte[] ivBytes = PBKDF2.deriveKey(revPassword.getBytes(), salt, 20, keyLengthInBits/8);
			strIV = ByteArrayToHexString(ivBytes);
			retStringArray[1] = strIV;
		}
		catch (Exception ex)
		{
			return retStringArray;
		}
		
		return retStringArray;
	}
	
	private boolean setupAESCipher(String  strKey, String strIV, int mode)
	{
		boolean bRtn = true;
		try
		{
			aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] keyBytes = HexStringToByteArray(strKey);
			byte[] iv = HexStringToByteArray(strIV);
			
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			
			aesCipher.init(mode, keySpec, ivSpec);
		}
		catch (Exception ex)
		{
			bRtn = false;
			return bRtn;
		}
		
		return bRtn;
	}
	
	public String doEncrypt(String strPlanText, String strKey, String strIV)
	{
		String strCipherText = "";
		
		if(setupAESCipher(strKey, strIV, Cipher.ENCRYPT_MODE))
		{
			byte[] planData = strPlanText.getBytes();
			try
			{
				byte[] cipherData = aesCipher.doFinal(planData);
				strCipherText = URLEncoder.encode( Base64.encode(cipherData), "UTF-8" );
			}
			catch (Exception ex)
			{
				return strCipherText;
			}
		}
		
		return strCipherText;
	}
	
	public String doDecrypt(String strCipherText, String strKey, String strIV)
	{
		String strPlanText = "";
		
		if(setupAESCipher(strKey, strIV, Cipher.DECRYPT_MODE))
		{
			try
			{
				byte[] cipherData = Base64.decode( URLDecoder.decode( strCipherText, "UTF-8" ) );
				byte[] planData = aesCipher.doFinal(cipherData);
				strPlanText = new String(planData);
			}
			catch (Exception ex)
			{
				return strPlanText;
			}
		}
		
		return strPlanText;
	}
	
    public static byte[] HexStringToByteArray(String s)
    {
        byte[] r= new byte[s.length()/2];
        for (int i = 0; i < s.length(); i+=2)
        {
            r[i/2] = (byte) java.lang.Integer.parseInt(s.substring(i,2+i), 16);
        }
        return r;
    }

    public static String ByteArrayToHexString(byte[] b) 
    {
        StringBuffer sb1= new StringBuffer();
        for (int i = 0 ; i < b.length; i++) {
            if (((int) b[i] & 0xff) < 0x10) sb1.append("0");
            sb1.append(Long.toString((int) b[i] & 0xff, 16));
        }
        return sb1.toString().toUpperCase();
    }
}
