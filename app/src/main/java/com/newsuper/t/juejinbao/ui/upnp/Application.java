
package com.newsuper.t.juejinbao.ui.upnp;

import com.newsuper.t.juejinbao.base.JJBApplication;
import com.newsuper.t.juejinbao.ui.upnp.dms.JettyResourceServer;

import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Application extends JJBApplication {
    private static Application sBeyondApplication = null;
    private ExecutorService mThreadPool = Executors.newCachedThreadPool();
    private JettyResourceServer mJettyResourceServer;

    private static InetAddress inetAddress;

    private static String hostAddress;

    private static String hostName;

    @Override
    public void onCreate() {
        super.onCreate();

        sBeyondApplication = this;

        mJettyResourceServer = new JettyResourceServer();
        mThreadPool.execute(mJettyResourceServer);
    }

    public static void setLocalIpAddress(InetAddress inetAddr) {
        inetAddress = inetAddr;

    }

    public static InetAddress getLocalIpAddress() {
        return inetAddress;
    }

    public static String getHostAddress() {
        return hostAddress;
    }

    public static void setHostAddress(String hostAddress) {
        Application.hostAddress = hostAddress;
    }

    public static String getHostName() {
        return hostName;
    }

    public static void setHostName(String hostName) {
        Application.hostName = hostName;
    }

    synchronized public static Application getApplication() {
        return sBeyondApplication;
    }

    synchronized public void stopServer() {
        mJettyResourceServer.stopIfRunning();
    }
}
