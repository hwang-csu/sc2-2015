/*
*	aidl file : frameworks/base/core/java/android/os/IMedicalDeviceInitService.aidl
*	This file contains definitions of functions which are exposed by service.
*	Modified: /frameworks/base/Android.mk to add IMedicalDeviceInitService.aidl to build
*
*	Created by: Ben Andow (benandow@gmail.com), Cleveland State University.
*	2/5/13
*/

package android.os;

/** @hide */
interface IMedicalDeviceInitService { 

	void addApp(String appName);
	void removeApp(String appName);
	boolean isMedicalApp(String appName);
	boolean isMedicalAppByUid();

	void addRestrictionPolicy(String appName, String permission);
	void removeRestrictionPolicy(String appName);
	boolean hasRestrictedPolicy(String appName, String permission);

	void addRequestedPermission(String appName, String permission);
	void removeRequestedPermissions(String appName);
	boolean hasRequestedPermission(String appName, String permission);
	boolean canStartComponent(String appName);

	boolean enforcePolicy(in byte[] policy);
	boolean restrictUserPermission(String permission);
	boolean unrestrictUserPermission(String permission);
	boolean isPermissionRestricted(String appName, String permission);	
}
