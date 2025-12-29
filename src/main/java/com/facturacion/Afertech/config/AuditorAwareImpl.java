package com.facturacion.Afertech.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return Optional.empty();
        }

        if (!authentication.isAuthenticated()) {
            return Optional.empty();
        }

        if ("anonymousUser".equals(authentication.getPrincipal())) {
            return Optional.empty();
        }

        return Optional.of(authentication.getName()); // email
    }
}
