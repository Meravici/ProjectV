package b4a.example;

import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	private static final boolean fullScreen = false;
	private static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                anywheresoftware.b4a.keywords.Common.Log("Killing previous instance (main).");
				p.finish();
			}
		}
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
		BA.handler.postDelayed(new WaitForLayout(), 5);

	}
	private static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.main");
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        initializeProcessGlobals();		
        initializeGlobals();
        
        anywheresoftware.b4a.keywords.Common.Log("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (mostCurrent == null || mostCurrent != this)
			return;
        processBA.setActivityPaused(false);
        anywheresoftware.b4a.keywords.Common.Log("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
		return true;
	}
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true)
				return true;
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
		this.setIntent(intent);
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        anywheresoftware.b4a.keywords.Common.Log("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            anywheresoftware.b4a.keywords.Common.Log("** Activity (main) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.http.HttpClientWrapper.HttpUriRequestWrapper _httpreq = null;
public static anywheresoftware.b4a.http.HttpClientWrapper _client = null;
public static String _server = "";
public static String  _activity_create(boolean _firsttime) throws Exception{
		Debug.PushSubsStack("Activity_Create (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 29;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(268435456);
 BA.debugLineNum = 31;BA.debugLine="Activity.LoadLayout(\"Layout1\")";
Debug.ShouldStop(1073741824);
mostCurrent._activity.LoadLayout("Layout1",mostCurrent.activityBA);
 BA.debugLineNum = 32;BA.debugLine="client.Initialize(\"client\")";
Debug.ShouldStop(-2147483648);
_client.Initialize("client");
 BA.debugLineNum = 33;BA.debugLine="End Sub";
Debug.ShouldStop(1);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _activity_pause(boolean _userclosed) throws Exception{
		Debug.PushSubsStack("Activity_Pause (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 62;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(536870912);
 BA.debugLineNum = 64;BA.debugLine="End Sub";
Debug.ShouldStop(-2147483648);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _activity_resume() throws Exception{
		Debug.PushSubsStack("Activity_Resume (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 58;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(33554432);
 BA.debugLineNum = 60;BA.debugLine="End Sub";
Debug.ShouldStop(134217728);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _client_responsesuccess(anywheresoftware.b4a.http.HttpClientWrapper.HttpResponeWrapper _response,int _taskid) throws Exception{
		Debug.PushSubsStack("client_ResponseSuccess (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
String _resp = "";
anywheresoftware.b4a.phone.Phone.PhoneSms _phn = null;
String[] _messages = null;
anywheresoftware.b4a.objects.collections.List[] _mp = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.JSONParser _jsn = null;
Debug.locals.put("Response", _response);
Debug.locals.put("TaskId", _taskid);
 BA.debugLineNum = 38;BA.debugLine="Sub client_ResponseSuccess (Response As HttpResponse, TaskId As Int)";
Debug.ShouldStop(32);
 BA.debugLineNum = 39;BA.debugLine="If(TaskId = 1 ) Then";
Debug.ShouldStop(64);
if ((_taskid==1)) { 
 BA.debugLineNum = 40;BA.debugLine="Dim resp As String=Response.GetString(\"UTF8\")";
Debug.ShouldStop(128);
_resp = _response.GetString("UTF8");Debug.locals.put("resp", _resp);Debug.locals.put("resp", _resp);
 BA.debugLineNum = 41;BA.debugLine="Dim phn As PhoneSms";
Debug.ShouldStop(256);
_phn = new anywheresoftware.b4a.phone.Phone.PhoneSms();Debug.locals.put("phn", _phn);
 BA.debugLineNum = 42;BA.debugLine="Dim messages() As String";
Debug.ShouldStop(512);
_messages = new String[(int)(0)];
java.util.Arrays.fill(_messages,"");Debug.locals.put("messages", _messages);
 BA.debugLineNum = 43;BA.debugLine="Dim mp() As List";
Debug.ShouldStop(1024);
_mp = new anywheresoftware.b4a.objects.collections.List[(int)(0)];
{
int d0 = _mp.length;
for (int i0 = 0;i0 < d0;i0++) {
_mp[i0] = new anywheresoftware.b4a.objects.collections.List();
}
}
;Debug.locals.put("mp", _mp);
 BA.debugLineNum = 44;BA.debugLine="Dim i As Int";
Debug.ShouldStop(2048);
_i = 0;Debug.locals.put("i", _i);
 BA.debugLineNum = 45;BA.debugLine="Dim jsn As JSONParser";
Debug.ShouldStop(4096);
_jsn = new anywheresoftware.b4a.objects.collections.JSONParser();Debug.locals.put("jsn", _jsn);
 BA.debugLineNum = 47;BA.debugLine="jsn.Initialize(resp)";
Debug.ShouldStop(16384);
_jsn.Initialize(_resp);
 BA.debugLineNum = 48;BA.debugLine="mp=jsn.NextValue";
Debug.ShouldStop(32768);
_mp = (anywheresoftware.b4a.objects.collections.List[])(_jsn.NextValue());Debug.locals.put("mp", _mp);
 BA.debugLineNum = 50;BA.debugLine="For i=0 To mp.Length";
Debug.ShouldStop(131072);
{
final double step25 = 1;
final double limit25 = _mp.length;
for (_i = (int)(0); (step25 > 0 && _i <= limit25) || (step25 < 0 && _i >= limit25); _i += step25) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 51;BA.debugLine="phn.Send(mp(i).Get(\"number\"),mp(i).Get(\"text\"))";
Debug.ShouldStop(262144);
_phn.Send(String.valueOf(_mp[_i].Get((int)(Double.parseDouble("number")))),String.valueOf(_mp[_i].Get((int)(Double.parseDouble("text")))));
 BA.debugLineNum = 52;BA.debugLine="httpreq.InitializeGet(server+\"?smsID=\"+mp(i).Get(\"ID\"))";
Debug.ShouldStop(524288);
_httpreq.InitializeGet(BA.NumberToString((double)(Double.parseDouble(_server))+(double)(Double.parseDouble("?smsID="))+(double)(BA.ObjectToNumber(_mp[_i].Get((int)(Double.parseDouble("ID")))))));
 BA.debugLineNum = 53;BA.debugLine="client.Execute(httpreq,2)";
Debug.ShouldStop(1048576);
_client.Execute(processBA,_httpreq,(int)(2));
 }
}Debug.locals.put("i", _i);
;
 };
 BA.debugLineNum = 56;BA.debugLine="End Sub";
Debug.ShouldStop(8388608);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}

public static void initializeProcessGlobals() {
    if (mostCurrent != null && mostCurrent.activityBA != null) {
Debug.StartDebugging(mostCurrent.activityBA, 46722, new int[] {3}, "6ae48ae1-90e7-470f-89ad-778e7f9d8f77");}

    if (processGlobalsRun == false) {
	    processGlobalsRun = true;
		try {
		        main._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}

public static void killProgram() {
    
            if (main.previousOne != null) {
				Activity a = main.previousOne.get();
				if (a != null)
					a.finish();
			}

}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 23;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 18;BA.debugLine="Dim httpreq As HttpRequest";
_httpreq = new anywheresoftware.b4a.http.HttpClientWrapper.HttpUriRequestWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim client As HttpClient";
_client = new anywheresoftware.b4a.http.HttpClientWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim server As String";
_server = "";
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public static String  _pull_from_server() throws Exception{
		Debug.PushSubsStack("pull_from_server (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 34;BA.debugLine="Sub pull_from_server()";
Debug.ShouldStop(2);
 BA.debugLineNum = 35;BA.debugLine="httpreq.InitializeGet(server+\"?getSMS=1\")";
Debug.ShouldStop(4);
_httpreq.InitializeGet(BA.NumberToString((double)(Double.parseDouble(_server))+(double)(Double.parseDouble("?getSMS=1"))));
 BA.debugLineNum = 36;BA.debugLine="client.Execute(httpreq,1)";
Debug.ShouldStop(8);
_client.Execute(processBA,_httpreq,(int)(1));
 BA.debugLineNum = 37;BA.debugLine="End Sub";
Debug.ShouldStop(16);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
  public Object[] GetGlobals() {
		return new Object[] {"Activity",_activity,"httpreq",_httpreq,"client",_client,"server",_server};
}
}
