package com.licrafter.basenet.cookie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by lijx on 2017/7/23.
 */

public class MemoryCookieJar implements CookieJar {
    private ConcurrentHashMap<CookieKey, Cookie> cookieStore;

    public MemoryCookieJar() {
        cookieStore = new ConcurrentHashMap<>();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        for (Cookie value : cookies) {
            CookieKey key = new CookieKey(value);
            cookieStore.put(key, value);
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> validCookies = new ArrayList<>();

        for (Iterator<Map.Entry<CookieKey, Cookie>> it = cookieStore.entrySet().iterator(); it.hasNext(); ) {
            Cookie currentCookie = it.next().getValue();
            if (currentCookie.expiresAt() < System.currentTimeMillis()) {
                it.remove();
            } else if (currentCookie.matches(url)) {
                validCookies.add(currentCookie);
            }
        }

        return validCookies;
    }

    /**
     * remove all exists cookies
     */
    public void clearAll() {
        cookieStore.clear();
    }


    /**
     * This class decorates a Cookie to re-implements equals() and hashcode() methods in order to identify
     * the cookie by the following attributes: name, domain, path, secure & hostOnly.<p>
     * <p>
     * This new behaviour will be useful in determining when an already existing cookie in session must be overwritten.
     */
    private static class CookieKey {
        private Cookie cookie;

        CookieKey(Cookie cookie) {
            this.cookie = cookie;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof CookieKey)) return false;
            CookieKey that = (CookieKey) other;
            return that.cookie.name().equals(this.cookie.name())
                    && that.cookie.domain().equals(this.cookie.domain())
                    && that.cookie.path().equals(this.cookie.path())
                    && that.cookie.secure() == this.cookie.secure()
                    && that.cookie.hostOnly() == this.cookie.hostOnly();
        }

        @Override
        public int hashCode() {
            int hash = 17;
            hash = 31 * hash + cookie.name().hashCode();
            hash = 31 * hash + cookie.domain().hashCode();
            hash = 31 * hash + cookie.path().hashCode();
            hash = 31 * hash + (cookie.secure() ? 0 : 1);
            hash = 31 * hash + (cookie.hostOnly() ? 0 : 1);
            return hash;
        }
    }
}
