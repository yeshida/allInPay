package com.centerm.allinpay.launcher.net;

/**
 * Created by linwanliang on 2016/2/29.
 * 接口访问地址实体类。
 */
public class Address {


    private String ip;
    private String port;
    private String suffix = "";
    private boolean https;

    /**
     * 构造函数。
     *
     * @param ip    IP地址，格式如xxx.xxx.xxx.xxx
     * @param port  端口号，格式如xxxx
     * @param https 是否使用https
     */
    public Address(String ip, String port, boolean https) {
        this.ip = ip;
        this.port = port;
        this.https = https;
    }

    public Address(String fullUrl) {
        if (fullUrl.startsWith("https://")) {
            this.https = true;
            fullUrl.replace("https://", "");
        } else {
            fullUrl.replace("http://", "");
        }
        String[] parts = fullUrl.split(":");
        if (parts.length == 2) {
            this.ip = parts[0];
            this.port = parts[1];
        } else {
            this.ip = fullUrl;
            this.port = "";
        }
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isHttps() {
        return https;
    }

    public void setHttps(boolean https) {
        this.https = https;
    }


    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String generateUrl() {
        StringBuilder sBuilder = new StringBuilder(getAddress());
        sBuilder.append(suffix);
        return sBuilder.toString();
    }

    public String getAddress() {
        StringBuilder sBuilder = new StringBuilder();
        if (isHttps()) {
            sBuilder.append("https://");
        } else {
            sBuilder.append("http://");
        }
        sBuilder.append(ip).append(":");
        sBuilder.append(port).append("/");
        return sBuilder.toString();
    }

}
