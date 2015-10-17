/*
*	/frameworks/base/services/java/com/android/server/MedicalDeviceInitService.java
*
*	This service starts when the system is booted and is responsible for managing the
*	permission restrictions
*
*	Created by: Ben Andow (benandow@gmail.com), Cleveland State University. 2/5/13
*/
package com.android.server;

import android.content.Context;
import android.os.IMedicalDeviceInitService;
import android.util.Log;
import android.os.Binder;
import android.os.Process;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import android.os.ServiceManager;
import android.app.ActivityManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public final class MedicalDeviceInitService extends IMedicalDeviceInitService.Stub {
	private static final String TAG = "MedicalDeviceInitService";

	private static final HashSet<String> medAppSet = new HashSet<String>();//packageName
	private static final HashMap<String, AppPermission> restrictPolicyMap = new HashMap<String, AppPermission>();//packageName, permission
	private static final HashMap<String, AppPermission> requestedPermissionMap = new HashMap<String, AppPermission>();//packageName, permission
	private static final HashMap<String, RestrictedAppPermission> restrictedPermissionMap = new HashMap<String, RestrictedAppPermission>();//permission, package

//TODO: Read SERVER_IP and SERVER_PORT from configuration file.
	private static final String SERVER_IP = "137.148.128.81";
	private static final int SERVER_PORT = 9998;
	private static final int keySize = 16;
//TODO: Handle retrieving the initial key (THIS IS TERRIBLE)
	private static byte[] currKey = {(byte)0x00,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04,(byte)0x05,(byte)0x06,(byte)0x07,(byte)0x08,(byte)0x09,(byte)0x0A,(byte)0x0B,(byte)0x0C,(byte)0x0D,(byte)0x0E,(byte)0x0F};
	private static int n = 0;

	private Context mContext;

    public MedicalDeviceInitService(Context context) {
        super();
		this.mContext = context;
		//Get initial key, device starting
		
		
		Log.i(TAG, "DASF Medical Device System Configured");
    }//Constructor

//	private void getInitialKey(){
//
//	}//getInitialKey

	/*
	 * Application Table Functions
	 */
	public void addApp(String appName){
		if(!isSystemOrRoot()){
			Log.i(TAG, "DASF Could not add app, is not system or root");
			return;
		}//if
		synchronized(medAppSet){		
			medAppSet.add(appName);
		}//synchronized
	}//insertApp

	public void removeApp(String appName){
		if(!isSystemOrRoot()){
			Log.i(TAG, "DASF Could not remove app, is not system or root");
			return;
		}//if
		synchronized(medAppSet){
			medAppSet.remove(appName);
		}//synchronized
		cleanUpPackage(appName);
	}//removeApp

	public boolean isMedicalApp(String appName){		
		synchronized(medAppSet){
			return medAppSet.contains(appName);
		}//synchronized
	}//isAppRegistered

	public boolean isMedicalAppByUid(){
		int uid = Binder.getOrigCallingUid();
		String[] pkgs = mContext.getPackageManager().getPackagesForUid(uid);
        if(pkgs == null || pkgs.length != 1)
			return false;
		Log.i(TAG, "DASF isMedAppByUID "+pkgs[0]);
		return isMedicalApp(pkgs[0]);
	}//isMedicalAppByUid

	private void cleanUpPackage(String appName){
		if(!isSystemOrRoot()){
			Log.i(TAG, "DASF Could not clean package, is not system or root");
			return;	
		}//if
		//Remove requested restriction policy
		synchronized(restrictPolicyMap){
			restrictPolicyMap.remove(appName);
		}//synchronized
		//Remove requested permissions
		synchronized(requestedPermissionMap){
			requestedPermissionMap.remove(appName);
		}//synchronized
		//Remove permission restrictions enforced from app
		synchronized(restrictedPermissionMap){
			Map<String, RestrictedAppPermission> m = restrictedPermissionMap;
			for (Map.Entry<String, RestrictedAppPermission> entry : m.entrySet()) {
				RestrictedAppPermission value = entry.getValue();
				if(value.restrictorAppName.equals(appName))
					restrictedPermissionMap.remove(entry.getKey());
			}//for
		}//synchronized
	}//cleanUpPackage

	/*
	 * Restriction Policy Functions
	 */
	public void addRestrictionPolicy(String appName, String permission){
		if(!isSystemOrRoot()){	
			Log.i(TAG, "DASF Could not add restriction policy, is not system or root");
			return;
		}//if
		synchronized(restrictPolicyMap){
			if(restrictPolicyMap.containsKey(appName)){
				//Entry exists, add to map
				final AppPermission ap = restrictPolicyMap.get(appName);
				ap.appPermissions.add(permission);
			}else{
				//No entry, create and add to map
				final AppPermission ap = new AppPermission(appName);
				ap.addPermission(permission);
 				restrictPolicyMap.put(appName, ap);
			}//else
		}//synchronized
	}//insertPermission
    	
	public void removeRestrictionPolicy(String appName){
		if(!isSystemOrRoot()){	
			Log.i(TAG, "DASF Could not remove restriction policy, is not system or root");
			return;
		}//if
		synchronized(restrictPolicyMap){
			restrictPolicyMap.remove(appName);
		}//synchronized
	}//removeRestrictionPolicy
    	
	public boolean hasRestrictedPolicy(String appName, String permission){
		synchronized(restrictPolicyMap){
			final AppPermission ap = restrictPolicyMap.get(appName);
			return ap == null ? false : ap.appPermissions.contains(permission);
		}//synchronized
	}//hasRestrictedPolicy

	/*
	 * Requested Permissions Methods
	 */
	public void addRequestedPermission(String appName, String permission){
		if(!isSystemOrRoot()){
			Log.i(TAG, "DASF Could not add requested permission, is not system or root");
			return;
		}//if
		synchronized(requestedPermissionMap){
			if(requestedPermissionMap.containsKey(appName)){
				final AppPermission ap = requestedPermissionMap.get(appName);
				ap.appPermissions.add(permission);
			}else{//No entry, create and add to map
				final AppPermission ap = new AppPermission(appName);
				ap.addPermission(permission);
				requestedPermissionMap.put(appName, ap);
			}//else
		}//synchronized
	}//addPermission
	
	public void removeRequestedPermissions(String appName){
		if(!isSystemOrRoot()){
			Log.i(TAG, "DASF Could not remove requested permission, is not system or root");	
			return;
		}//if
		synchronized(requestedPermissionMap){
			requestedPermissionMap.remove(appName);
		}//synchronized	
	}//removeRequestedPermissions

    	
	public boolean hasRequestedPermission(String appName, String permission){
		synchronized(requestedPermissionMap){
			final AppPermission ap = requestedPermissionMap.get(appName);
			return ap == null ? false : ap.containsPermission(permission);
		}//synchronized
	}//hasRequestedPermission

	public boolean canStartComponent(String appName){
		int uid = -1;
		PackageManager pm =  mContext.getPackageManager();
		try{
			ApplicationInfo ai = pm.getPackageInfo(appName, 0).applicationInfo;
			uid = ai.uid;
			if(uid == -1) return false;
			if(uid== Process.SYSTEM_UID || uid == 0 || (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0){
				Log.i(TAG, "DASF MEDDEVICEINITSERVICE canStartComponent = System or Root "+appName+" "+uid);
				return true;
			}//if
		}catch(Exception e){
			return false;
		}//catch

		synchronized(requestedPermissionMap){
			final String[] requestedPerms = requestedPermissionMap.containsKey(appName) ?
											 requestedPermissionMap.get(appName).getPermissions() : null;
			if(requestedPerms == null) return true;
			for(int i = 0; i < requestedPerms.length; i++){
				synchronized(restrictedPermissionMap){
					final RestrictedAppPermission rap = restrictedPermissionMap.get(requestedPerms[i]);
					if(rap != null && !rap.getRestrictorAppName().equals(appName)){
						Log.i(TAG, "DASF can not start component (internal) "+ appName+" "+uid);					
						return false;
					}
				}//synchronized
			}//for
		}//synchronized
		Log.i(TAG, "DASF can start component(internal) "+appName+" "+uid);
		return true;
	}//canStartComponent
	

	/*
	 * Restriction Table Functions
	 */

	public boolean enforcePolicy(byte[] policy){
		long t1 = System.nanoTime();
		final int uid = Binder.getOrigCallingUid();
		Log.i(TAG, "DASF uid "+uid);
		String[] pkgs = mContext.getPackageManager().getPackagesForUid(uid);
        if(pkgs == null || pkgs.length != 1)
			return false;
		if(!isMedicalApp(pkgs[0])) return false;
//Check current key to ensure not null

//
		try{
			policy = decrypt(this.currKey, policy);
			byte[] newKey = new byte[keySize];
			//Verify new Key (hash^{n-1}(newKey))

			//
			this.currKey = newKey;
			System.arraycopy(policy, policy.length-keySize, newKey, 0, keySize);
			byte[] actualPolicy = new byte[policy.length-keySize];
			System.arraycopy(policy, 0, actualPolicy, 0, actualPolicy.length);
			String strPolicy = new String(actualPolicy);
			//Get restrictions in one String and unrestrictions in another
			String[][] res = new String[][]{getRestrictions(strPolicy),getUnrestrictions(strPolicy)};
			
			if(res[0] != null){
				for(int i = 0; i < res[0].length; i++){
					if(!isPermissionRestricted(res[0][i])){
						synchronized(restrictedPermissionMap){
							restrictedPermissionMap.put(res[0][i], new RestrictedAppPermission(pkgs[0], uid, true, res[0][i]));
						}
						killProcesses(pkgs[0], res[0][i]);
					}//if
				}//for
			}//if
			if(res[1] != null){
				for(int i = 0; i < res[1].length; i++){
					if(isPermissionRestricted(res[1][i])){
						synchronized(restrictedPermissionMap){	
							final RestrictedAppPermission rap = restrictedPermissionMap.get(res[1][i]);
							if(rap != null && rap.getRestrictorUID() == uid && rap.setByServer()){
								restrictedPermissionMap.remove(res[1][i]);
							}//if
						}//synch
					}//if
				}//for
			}//if
			long t2 = System.nanoTime();
			Log.w(TAG, "DASF TIME (ENFORCE POLICY) "+(t2-t1));
			return true;
		}catch(Exception e){
			Log.w(TAG,"DASF error enforcing server policy");
		}
		return false;
	}

	public boolean restrictUserPermission(String permission){			
		final int uid = Binder.getOrigCallingUid();
		Log.i(TAG, "DASF uid "+uid);
		String[] pkgs = mContext.getPackageManager().getPackagesForUid(uid);
		Log.i(TAG, "DASF "+((pkgs==null) ? "null" : pkgs.length+" "+pkgs[0]+";"));
        if(pkgs == null || pkgs.length != 1)
			return false;
		
		Log.i(TAG, "DASF declared? "+hasRestrictedPolicy(pkgs[0], permission) + " is already res? "+isPermissionRestricted(permission));
		if(hasRestrictedPolicy(pkgs[0], permission) && !isPermissionRestricted(permission)){
			Log.i(TAG, "DASF putting res");
			synchronized(restrictedPermissionMap){
				restrictedPermissionMap.put(permission, new RestrictedAppPermission(pkgs[0], uid, false, permission));
			}//synchronized
			killProcesses(pkgs[0], permission);
			return true;
		}//if
		return false;
	}//restrictUserPermission
    	
	public boolean unrestrictUserPermission(String permission){
		final int uid = Binder.getOrigCallingUid();
		Log.i(TAG, "DASF uid "+uid);
		String[] pkgs = mContext.getPackageManager().getPackagesForUid(uid);

		Log.i(TAG, "DASF "+((pkgs==null) ? "null" : pkgs.length+" "+pkgs[0]+";"));
        if(pkgs == null || pkgs.length != 1)
			return false;
		
		Log.i(TAG, "DASF UNRESTRICT"+isPermissionRestricted(permission)+" ");
		if(isPermissionRestricted(permission)){
			synchronized(restrictedPermissionMap){
				final RestrictedAppPermission rap = restrictedPermissionMap.get(permission);
				if(rap != null && rap.getRestrictorUID() == uid && !rap.setByServer()){
					restrictedPermissionMap.remove(permission);
					return true;
				}//if
			}//synchronized
		}//if
		return false;
	}//unrestrictUserPermission

	public boolean isPermissionRestricted(String appName, String permission){
		synchronized(restrictedPermissionMap){
			return restrictedPermissionMap.containsKey(permission) ?
					!restrictedPermissionMap.get(permission).getRestrictorAppName().equals(appName) :
					false;
		}//synchronized
	}//isPermissionRestricted


	private boolean isPermissionRestricted(String permission){
		synchronized(restrictedPermissionMap){
			return restrictedPermissionMap.containsKey(permission);
		}//synchronized
	}//isPermissionRestricted
    	
	//Helper Functions
	private boolean isSystemOrRoot(){
		final int uid = Binder.getOrigCallingUid();
		return (uid == Process.SYSTEM_UID || uid == 0);
	}//isSystemOrRoot

	private void killProcesses(String appName, String permission){
		long ident = Binder.clearCallingIdentity();
		final PackageManager pm = mContext.getPackageManager();
		final ActivityManager am = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
		try{
			List<ApplicationInfo> packages = pm.getInstalledApplications(0);
			for(ApplicationInfo packageInfo : packages){
				final String packageName = packageInfo.packageName;
				if((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM)==1 || packageName.equals(appName)){
					continue;
				}else if(hasRequestedPermission(packageName,permission)){
					Log.i(TAG,"DASF (killprocesses) Killing background process "+packageName+" : "+permission);
					//android.os.Process.killProcess();killBackgroundProcesses(packageName)

	    	   		am.forceStopPackage(packageName);
				}//elseif
			}//for
		}catch(Exception e){
			Log.i(TAG, "DASF Could not get activity manager or package manager "+e.getLocalizedMessage());
		}finally{
			Binder.restoreCallingIdentity(ident);
		}
	}//killProcesses


	private byte[] decrypt(byte[] key, byte[] ciphertext) throws Exception{
		if(ciphertext == null || key == null) return null;
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		return cipher.doFinal(ciphertext);
	}//decrypt

	private String[] getRestrictions(String policy){
		int startIndex = policy.indexOf('['), endIndex = policy.indexOf(']');
		if(startIndex < 0 || endIndex < 0 || startIndex+1 >= endIndex)
			return null;
		return policy.substring(startIndex+1, endIndex).split(";");
	}//getRestrictions
	
	private String[] getUnrestrictions(String policy){
		int startIndex = policy.indexOf('{'), endIndex = policy.indexOf('}');
		if(startIndex < 0 || endIndex < 0 || startIndex+1 >= endIndex)
			return null;
		return policy.substring(startIndex+1, endIndex).split(";");
	}//getUnrestrictions


	/*
	 *AppPermission Object
	 */
	private static final class AppPermission{
		private final String appName;
		private final HashSet<String> appPermissions;
    		
		private AppPermission(String appName){
			this.appName = appName;
			this.appPermissions = new HashSet<String>();
		}//constructor
    		
		public String getAppName(){
			return this.appName;
		}//getAppName

		public String[] getPermissions(){
			return this.appPermissions.toArray(new String[0]);
		}//getPermissions

		public boolean addPermission(String permissionName){
			return this.appPermissions.add(permissionName);
		}//addPermission

		public boolean containsPermission(String permissionName){
			return this.appPermissions.contains(permissionName);
		}//containsPermission

	}//AppPermission

	/*
	 *RestrictedAppPermission Object
	 */
	private static final class RestrictedAppPermission{
		private final String restrictorAppName;
		private final boolean setBy;	//server = true, user = false
		private final int restrictorUID;
		private final String permission;

		private RestrictedAppPermission(String restrictorAppName,
				 int restrictorUID, boolean setBy, String permission){
			this.restrictorAppName = restrictorAppName;
			this.restrictorUID = restrictorUID;
			this.setBy = setBy;
			this.permission = permission;
		}//RestrictedAppPermission
    		
		public boolean setByServer(){
			return setBy;
		}//setByServer
    		
		public int getRestrictorUID(){
			return this.restrictorUID;
		}//getRestrictorUID
    		
		public String getPermission(){
			return this.permission;
		}//getPermission

		public String getRestrictorAppName(){
			return this.restrictorAppName;
		}//getRestrictorAppName
	}//RestrictedAppPermission
    	
}//MedicalDeviceInitService
