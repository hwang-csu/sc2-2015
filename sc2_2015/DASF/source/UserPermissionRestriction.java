package android.util;

import android.os.IMedicalDeviceInitService;
import android.content.Context;
import android.os.ServiceManager;

/*
 * Methods for restricting/unrestricting user permissions. (DASF)
 * Created by: Ben Andow <benandow@gmail.com>
*/

public final class UserPermissionRestriction{

	public static boolean restrictPermission(String permission){
		final IMedicalDeviceInitService medDevServ = IMedicalDeviceInitService.Stub.asInterface(ServiceManager.getService("med_dev_init"));
		try {
			return medDevServ.restrictUserPermission(permission);
		}catch(Exception e){}//catch
		return false;
	}//restrictPermission

	public static boolean unrestrictPermission(String permission){
		final IMedicalDeviceInitService medDevServ = IMedicalDeviceInitService.Stub.asInterface(ServiceManager.getService("med_dev_init"));
		try {
			return medDevServ.unrestrictUserPermission(permission);
		}catch(Exception e){}//catch
		return false;
	}//unrestrictPermission

	public static boolean isPermissionRestricted(Context mContext, String permission){
		String packageName = (mContext != null && permission !=null) ? getPackageName(mContext) : null;
		if(packageName != null){
			final IMedicalDeviceInitService medDevServ = IMedicalDeviceInitService.Stub.asInterface(ServiceManager.getService("med_dev_init"));
			try {
				return medDevServ.isPermissionRestricted(packageName, permission);
			}catch(Exception e){}//catch
		}//if
		return false;
	}//isPermissionRestricted

	private static String getPackageName(Context mContext){
		String[] pkgs = mContext.getPackageManager().getPackagesForUid(android.os.Process.myUid());
		return (pkgs == null || pkgs.length != 1) ? null : pkgs[0];
	}//getPackageName
}//class
