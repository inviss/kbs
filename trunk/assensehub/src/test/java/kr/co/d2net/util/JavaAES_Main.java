package kr.co.d2net.util;

import com.malgn.aesDemo.AESUtil;

public class JavaAES_Main 
{
	  public static void main(String[] args)
	  {
		  AESUtil aesUtil = new AESUtil();
		  String[] KeyIVArray;
		  String strKey;
		  String strIV;
		  String strCipherText;
		  String strPlanText;
		  KeyIVArray = aesUtil.GenerateKeyandIVbyPassword("KDAS_AES_DEMO");
		  strKey = KeyIVArray[0];
		  strIV = KeyIVArray[1];
		  System.out.println("KEY :" + strKey + "\nIV  :" + strIV);
		  
		  
		  //strPlanText = "Kdas_Aes_Demo 1234 가나다라 hellow";
		  System.out.println("Before PlanText : Kdas_Aes_Demo 1234 가나다라 hellow");
		  
		  strCipherText = aesUtil.doEncrypt("KdasTest", "CD24D68261E7CFEDD53FC1017759EA32", "054737DD6D5F452F50FE9572D9600BB9");
		  System.out.println("CipherText : " + strCipherText);
		 
		  strPlanText = aesUtil.doDecrypt(strCipherText, "CD24D68261E7CFEDD53FC1017759EA32", "054737DD6D5F452F50FE9572D9600BB9");
		  System.out.println("PlanText   : " + strPlanText);
	  }
}


