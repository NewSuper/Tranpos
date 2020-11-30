package com.newsuper.t.consumer.bean;


public class VersionBean extends BaseBean {
    public VersionData data;

    public class VersionData {
        public String version;//	string	app最低兼容的版本号
        public String newest_version;//	string	当前的最新版本
        public String tips;//	string	更新提示
        public String url;//string	新版本下载的URL路径
    }
}
