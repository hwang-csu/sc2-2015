package android.util;

import java.nio.ByteBuffer;
import dalvik.system.Taint;

/**
 * Data classification interface with overloaded methods for tainting 
 *	data (DASF)
 * 
 *	Created by: Ben Andow <benandow@gmail.com>
 */

public final class DataClassification {

	public static final int TAINT_CLASSIFIED	= Taint.TAINT_CLASSIFIED;
    public static final int TAINT_SECRET		= Taint.TAINT_SECRET;
	public static final int TAINT_TOPSECRET		= Taint.TAINT_TOPSECRET;
	
	//Classified Taint
	public static void addClassifiedTaint(String str){
		Taint.addTaintString(str, Taint.TAINT_CLASSIFIED);
	}
	
	public static void addClassifiedTaint(Object[] array){
		Taint.addTaintObjectArray(array, Taint.TAINT_CLASSIFIED);
	}
	
	public static void addClassifiedTaint(boolean[] array){
		Taint.addTaintBooleanArray(array, Taint.TAINT_CLASSIFIED);
	}
	
	public static void addClassifiedTaint(char[] array){
		Taint.addTaintCharArray(array, Taint.TAINT_CLASSIFIED);
	}
	
	public static void addClassifiedTaint(byte[] array){
		Taint.addTaintByteArray(array, Taint.TAINT_CLASSIFIED);
	}
	
	public static void addClassifiedTaint(ByteBuffer dByteBuffer) {
		Taint.addTaintDirectByteBuffer(dByteBuffer, Taint.TAINT_CLASSIFIED);
	 }
	
	public static void addClassifiedTaint(int[] array){
		Taint.addTaintIntArray(array, Taint.TAINT_CLASSIFIED);
	}
	
	public static void addClassifiedTaint(short[] array){
		Taint.addTaintShortArray(array, Taint.TAINT_CLASSIFIED);
	}

	public static void addClassifiedTaint(long[] array){
		Taint.addTaintLongArray(array, Taint.TAINT_CLASSIFIED);
	}
	
	public static void addClassifiedTaint(float[] array){
		Taint.addTaintFloatArray(array, Taint.TAINT_CLASSIFIED);
	}
	
	public static void addClassifiedTaint(double[] array){
		Taint.addTaintDoubleArray(array, Taint.TAINT_CLASSIFIED);
	}
	
    public static boolean addClassifiedTaint(boolean val){
    	return Taint.addTaintBoolean(val, Taint.TAINT_CLASSIFIED);
    }
    
    public static char addClassifiedTaint(char val){
    	return Taint.addTaintChar(val, Taint.TAINT_CLASSIFIED);
    }
    
    public static byte addClassifiedTaint(byte val){
    	return Taint.addTaintByte(val, Taint.TAINT_CLASSIFIED);
    }
    
    public static int addClassifiedTaint(int val){
    	return Taint.addTaintInt(val, Taint.TAINT_CLASSIFIED);
    }

    public static short addClassifiedTaint(short val){
    	return Taint.addTaintShort(val, Taint.TAINT_CLASSIFIED);
    }

 
    public static long addClassifiedTaint(long val){
    	return Taint.addTaintLong(val, Taint.TAINT_CLASSIFIED);
    }

    public static float addClassifiedTaint(float val){
    	return Taint.addTaintFloat(val, Taint.TAINT_CLASSIFIED);
    }

    public static double addClassifiedTaint(double val){
    	return Taint.addTaintDouble(val, Taint.TAINT_CLASSIFIED);
    }
	
    public static void addClassifiedTaintFile(int fd){
    	Taint.addTaintFile(fd, Taint.TAINT_CLASSIFIED);
    }
	
	//Secret Taint

	public static void addSecretTaint(String str){
		Taint.addTaintString(str, Taint.TAINT_SECRET);
	}
	
	public static void addSecretTaint(Object[] array){
		Taint.addTaintObjectArray(array, Taint.TAINT_SECRET);
	}
	
	public static void addSecretTaint(boolean[] array){
		Taint.addTaintBooleanArray(array, Taint.TAINT_SECRET);
	}
	
	public static void addSecretTaint(char[] array){
		Taint.addTaintCharArray(array, Taint.TAINT_SECRET);
	}
	
	public static void addSecretTaint(byte[] array){
		Taint.addTaintByteArray(array, Taint.TAINT_SECRET);
	}
	
	public static void addSecretTaint(ByteBuffer dByteBuffer) {
		Taint.addTaintDirectByteBuffer(dByteBuffer, Taint.TAINT_SECRET);
	 }
	
	public static void addSecretTaint(int[] array){
		Taint.addTaintIntArray(array, Taint.TAINT_SECRET);
	}
	
	public static void addSecretTaint(short[] array){
		Taint.addTaintShortArray(array,Taint.TAINT_SECRET);
	}
	
	public static void addSecretTaint(long[] array){
		Taint.addTaintLongArray(array, Taint.TAINT_SECRET);
	}
	
	public static void addSecretTaint(float[] array){
		Taint.addTaintFloatArray(array, Taint.TAINT_SECRET);
	}
	
	public static void addSecretTaint(double[] array){
		Taint.addTaintDoubleArray(array, Taint.TAINT_SECRET);
	}
	
    public static boolean addSecretTaint(boolean val){
    	return Taint.addTaintBoolean(val, Taint.TAINT_SECRET);
    }
    
    public static char addSecretTaint(char val){
    	return Taint.addTaintChar(val, Taint.TAINT_SECRET);
    }
    
    public static byte addSecretTaint(byte val){
    	return Taint.addTaintByte(val, Taint.TAINT_SECRET);
    }
    
    public static int addSecretTaint(int val){
    	return Taint.addTaintInt(val, Taint.TAINT_SECRET);
    }

    public static short addSecretTaint(short val){
    	return Taint.addTaintShort(val, Taint.TAINT_SECRET);
    }
 
    public static long addSecretTaint(long val){
    	return Taint.addTaintLong(val, Taint.TAINT_SECRET);
    }
    
    public static float addSecretTaint(float val){
    	return Taint.addTaintFloat(val, Taint.TAINT_SECRET);
    }

    public static double addSecretTaint(double val){
    	return Taint.addTaintDouble(val, Taint.TAINT_SECRET);
    }
    
    public static void addSecretTaintFile(int fd){
    	Taint.addTaintFile(fd, Taint.TAINT_SECRET);
    }
    
	//Top Secret Taint
	
	public static void addTopSecretTaint(String str){
		Taint.addTaintString(str, Taint.TAINT_TOPSECRET);
	}
	
	public static void addTopSecretTaint(Object[] array){
		Taint.addTaintObjectArray(array, Taint.TAINT_TOPSECRET);
	}
	
	public static void addTopSecretTaint(boolean[] array){
		Taint.addTaintBooleanArray(array, Taint.TAINT_TOPSECRET);
	}

	public static void addTopSecretTaint(char[] array){
		Taint.addTaintCharArray(array, Taint.TAINT_TOPSECRET);
	}
	
	public static void addTopSecretTaint(byte[] array){
		Taint.addTaintByteArray(array, Taint.TAINT_TOPSECRET);
	}
	
	public static void addTopSecretTaint(ByteBuffer dByteBuffer) {
		Taint.addTaintDirectByteBuffer(dByteBuffer, Taint.TAINT_TOPSECRET);
	 }
	
	public static void addTopSecretTaint(int[] array){
		Taint.addTaintIntArray(array, Taint.TAINT_TOPSECRET);
	}
	
	public static void addTopSecretTaint(short[] array){
		Taint.addTaintShortArray(array, Taint.TAINT_TOPSECRET);
	}
	
	public static void addTopSecretTaint(long[] array){
		Taint.addTaintLongArray(array, Taint.TAINT_TOPSECRET);
	}
	
	public static void addTopSecretTaint(float[] array){
		Taint.addTaintFloatArray(array, Taint.TAINT_TOPSECRET);
	}
	
	public static void addTopSecretTaint(double[] array){
		Taint.addTaintDoubleArray(array, Taint.TAINT_TOPSECRET);
	}
	
    public static boolean addTopSecretTaint(boolean val){
    	return Taint.addTaintBoolean(val, Taint.TAINT_TOPSECRET);
    }
    
    public static char addTopSecretTaint(char val){
    	return Taint.addTaintChar(val, Taint.TAINT_TOPSECRET);
    }
    
    public static byte addTopSecretTaint(byte val){
    	return Taint.addTaintByte(val, Taint.TAINT_TOPSECRET);
    }
    
    public static int addTopSecretTaint(int val){
    	return Taint.addTaintInt(val, Taint.TAINT_TOPSECRET);
    }
    
    public static short addTopSecretTaint(short val){
    	return Taint.addTaintShort(val, Taint.TAINT_TOPSECRET);
    }
 
    public static long addTopSecretTaint(long val){
    	return Taint.addTaintLong(val, Taint.TAINT_TOPSECRET);
    }
    
    public static float addTopSecretTaint(float val){
    	return Taint.addTaintFloat(val, Taint.TAINT_TOPSECRET);
    }

    public static double addTopSecretTaint(double val){
    	return Taint.addTaintDouble(val, Taint.TAINT_TOPSECRET);
    }
    
    public static void addTopSecretTaintFile(int fd){
    	Taint.addTaintFile(fd, Taint.TAINT_TOPSECRET);
    }
    

    //Get Classification Taints
    public static int getTaint(String str){
    	return Taint.getTaintString(str);
    }

    public static int getTaint(Object[] array){
    	return Taint.getTaintObjectArray(array);
    }

    public static int getTaint(boolean[] array){
    	return Taint.getTaintBooleanArray(array);
    }

    public static int getTaint(char[] array){
    	return Taint.getTaintCharArray(array);
    }

    public static int getTaint(byte[] array){
    	return Taint.getTaintByteArray(array);
    }
    
    public static int getTaint(ByteBuffer dByteBuffer) {
       return Taint.getTaintDirectByteBuffer(dByteBuffer);
    }

    public static int getTaint(int[] array){
    	return Taint.getTaintIntArray(array);
    }

    public static int getTaint(short[] array){
    	return Taint.getTaintShortArray(array);
    }

    public static int getTaint(long[] array){
    	return Taint.getTaintLongArray(array);
    }

    public static int getTaint(float[] array){
    	return Taint.getTaintFloatArray(array);
    }
    
    public static int getTaint(double[] array){
    	return Taint.getTaintDoubleArray(array);
    }
    
    public static int getTaint(boolean val){
    	return Taint.getTaintBoolean(val);
    }

    public static int getTaint(char val){
    	return Taint.getTaintChar(val);
    }

    public static int getTaint(byte val){
    	return Taint.getTaintByte(val);
    }

    public static int getTaint(int val){
    	return Taint.getTaintInt(val);
    }
    
    public static int getTaint(short val){
    	return Taint.getTaintShort(val);
    }

    public static int getTaint(long val){
    	return Taint.getTaintLong(val);
    }

    public static int getTaint(float val){
    	return Taint.getTaintFloat(val);
    }

    public static int getTaint(double val){
    	return Taint.getTaintDouble(val);
    }

    public static int getTaint(Object obj){
    	return Taint.getTaintRef(obj);
    }
    
    public static int getTaintFD(int fd){
    	return Taint.getTaintFile(fd);
    }
    
}//DataClassification
