package com.ares.security.common;

import org.springframework.security.core.GrantedAuthority;

/**
 * @description:
 * @author: Young
 * @date: 2020/10/19
 * @see: com.ares.security.security GrantedAuthorityImpl.java
 **/
public class GrantedAuthorityImpl implements GrantedAuthority {
    private static final long serialVersionUID = 1L;

    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
