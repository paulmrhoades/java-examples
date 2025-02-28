package com.keepitsimples.examples.authorization.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Paul Rhoades on 18/02/2025.
 */
public class CustomUser implements UserDetails, CredentialsContainer {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private static final Log logger = LogFactory.getLog(CustomUser.class);

    private String password;

    private final String username;

    private final Set<GrantedAuthority> authorities;

    private final boolean accountNonExpired;

    private final boolean accountNonLocked;

    private final boolean credentialsNonExpired;

    private final boolean enabled;

    /**
     * Calls the more complex constructor with all boolean arguments set to {@code true}.
     */
    public CustomUser(String username, String password, Set<? extends GrantedAuthority> authorities) {
        this(username, password, true, true, true, true, authorities);
    }

    /**
     * Construct the <code>User</code> with the details required by
     * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}.
     * @param username the username presented to the
     * <code>DaoAuthenticationProvider</code>
     * @param password the password that should be presented to the
     * <code>DaoAuthenticationProvider</code>
     * @param enabled set to <code>true</code> if the user is enabled
     * @param accountNonExpired set to <code>true</code> if the account has not expired
     * @param credentialsNonExpired set to <code>true</code> if the credentials have not
     * expired
     * @param accountNonLocked set to <code>true</code> if the account is not locked
     * @param authorities the authorities that should be granted to the caller if they
     * presented the correct username and password and the user is enabled. Not null.
     * @throws IllegalArgumentException if a <code>null</code> value was passed either as
     * a parameter or as an element in the <code>GrantedAuthority</code> collection
     */
    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired,
                boolean credentialsNonExpired, boolean accountNonLocked,
                Set<? extends GrantedAuthority> authorities) {
        Assert.isTrue(username != null && !"".equals(username) && password != null,
            "Cannot pass null or empty values to constructor");
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = Collections.unmodifiableSet(authorities);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }



    /**
     * Returns the hashcode of the {@code username}.
     */
    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    /**
     * Returns {@code true} if the supplied object is a {@code User} instance with the
     * same {@code username} value.
     * <p>
     * In other words, the objects are equal if they have the same username, representing
     * the same principal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CustomUser) {
            return this.username.equals(((CustomUser) obj).username);
        }
        return false;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" [");
        sb.append("Username=").append(this.username).append(", ");
        sb.append("Password=[PROTECTED], ");
        sb.append("Enabled=").append(this.enabled).append(", ");
        sb.append("AccountNonExpired=").append(this.accountNonExpired).append(", ");
        sb.append("credentialsNonExpired=").append(this.credentialsNonExpired).append(", ");
        sb.append("AccountNonLocked=").append(this.accountNonLocked).append(", ");
        sb.append("Granted Authorities=").append(this.authorities).append("]");
        return sb.toString();
    }

    /**
     * Creates a UserBuilder
     * @return the UserBuilder
     */
    public static UserBuilder forUsername(String for_username) {
        return new UserBuilder(for_username);
    }

    public static UserBuilder withUserDetails(UserDetails userDetails) {
        // @formatter:off
        return forUsername(userDetails.getUsername())
            .withEncodedPassword(userDetails.getPassword())
            .accountExpired(!userDetails.isAccountNonExpired())
            .accountLocked(!userDetails.isAccountNonLocked())
            .withAuthorities(userDetails.getAuthorities())
            .credentialsExpired(!userDetails.isCredentialsNonExpired())
            .disabled(!userDetails.isEnabled());
        // @formatter:on
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {

        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        @Override
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to
            // the set. If the authority is null, it is a custom authority and should
            // precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }
            if (g1.getAuthority() == null) {
                return 1;
            }
            return g1.getAuthority().compareTo(g2.getAuthority());
        }

    }

    /**
     * Builds the user to be added. At minimum the username, password, and authorities
     * should provided. The remaining attributes have reasonable defaults.
     */
    public static final class UserBuilder {

        private String username;

        private String encodedPassword;

        private Set<GrantedAuthority> authorities = new HashSet<>();

        private boolean accountExpired = false;

        private boolean accountLocked = false;

        private boolean credentialsExpired = false;

        private boolean disabled = false;


        /**
         * Creates a new instance
         */
        private UserBuilder(String username) {
            Assert.notNull(username, "username cannot be null");
            this.username = username;
        }

        /**
         * Populates the password. This attribute is required.
         * @param password the password. Cannot be null.
         * @return the {@link UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public UserBuilder withEncodedPassword(String password) {
            Assert.notNull(password, "password cannot be null");
            this.encodedPassword = password;
            return this;
        }

        /**
         * Populates the roles. This method is a shortcut for calling
         * {@link #withAuthorities(String...)}, but automatically prefixes each entry with
         * "ROLE_". This means the following:
         *
         * <code>
         *     builder.roles("USER","ADMIN");
         * </code>
         *
         * is equivalent to
         *
         * <code>
         *     builder.authorities("ROLE_USER","ROLE_ADMIN");
         * </code>
         *
         * <p>
         * This attribute is required, but can also be populated with
         * {@link #withAuthorities(String...)}.
         * </p>
         * @param roles the roles for this user (i.e. USER, ADMIN, etc). Cannot be null,
         * contain null values or start with "ROLE_"
         * @return the {@link UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public UserBuilder withRoles(String... roles) {
            List<GrantedAuthority> authorities = new ArrayList<>(roles.length);
            for (String role : roles) {
                Assert.isTrue(!role.startsWith("ROLE_"),
                    () -> role + " cannot start with ROLE_ (it is automatically added)");
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            this.authorities.addAll(authorities);
            return this;
        }

        /**
         * Populates the authorities. This attribute is required.
         * @param authorities the authorities for this user. Cannot be null, or contain
         * null values
         * @return the {@link UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         * @see #withRoles(String...)
         */
        public UserBuilder withAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities.addAll(authorities);
            return this;
        }
        public UserBuilder withAuthorities(GrantedAuthority... authorities) {
            this.authorities.addAll(Arrays.asList(authorities));
            return this;
        }
        public UserBuilder withAuthorities(String... authorities) {
            this.authorities.addAll(AuthorityUtils.createAuthorityList(authorities));
            return this;
        }

        /**
         * Defines if the account is expired or not. Default is false.
         * @param accountExpired true if the account is expired, false otherwise
         * @return the {@link UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public UserBuilder accountExpired(boolean accountExpired) {
            this.accountExpired = accountExpired;
            return this;
        }

        /**
         * Defines if the account is locked or not. Default is false.
         * @param accountLocked true if the account is locked, false otherwise
         * @return the {@link UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public UserBuilder accountLocked(boolean accountLocked) {
            this.accountLocked = accountLocked;
            return this;
        }

        /**
         * Defines if the credentials are expired or not. Default is false.
         * @param credentialsExpired true if the credentials are expired, false otherwise
         * @return the {@link UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public UserBuilder credentialsExpired(boolean credentialsExpired) {
            this.credentialsExpired = credentialsExpired;
            return this;
        }

        /**
         * Defines if the account is disabled or not. Default is false.
         * @param disabled true if the account is disabled, false otherwise
         * @return the {@link UserBuilder} for method chaining (i.e. to populate
         * additional attributes for this user)
         */
        public UserBuilder disabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public UserDetails build() {
            return new CustomUser(this.username, encodedPassword, !this.disabled, !this.accountExpired,
                !this.credentialsExpired, !this.accountLocked, this.authorities);
        }
    }
}
