package bigant.monche.common;


import java.io.ObjectInputStream.GetField;
import java.security.MessageDigest;

public class EncodePass
{
    /**
     * @param args the command line arguments
     */
	
	public static String getMd5(String passwd) throws Exception
    {
    	
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(passwd.getBytes("UTF8"));
        byte s[] = m.digest();
        String result = "";
        for (int i = 0; i < s.length; i++) {
          result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
        }
        return result;
    }
	
	public static String enCodePass(String pass) throws Exception
	
	{
		return Base64Coder.encodeString(getMd5(pass));
		 
	}
	

}
