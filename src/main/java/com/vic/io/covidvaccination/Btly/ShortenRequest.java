package com.vic.io.covidvaccination.Btly;

class ShortenRequest{
    private String domain = "bit.ly";
    private String long_url;

    public ShortenRequest(String long_url){
        this.long_url = long_url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLong_url() {
        return long_url;
    }

    public void setLong_url(String long_url) {
        this.long_url = long_url;
    }
}


