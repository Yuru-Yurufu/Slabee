package com.forestotzka.yurufu.slabee.block.persistence;

import org.slf4j.Logger;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@FunctionalInterface
public interface ComponentStateDiagnosticSink {
    ComponentStateDiagnosticSink NONE = diagnostic -> {
    };

    void report(Diagnostic diagnostic);

    static ComponentStateDiagnosticSink rateLimited(
            ComponentStateDiagnosticSink delegate,
            int maximumDistinctDiagnostics
    ) {
        return new RateLimited(delegate, maximumDistinctDiagnostics);
    }

    static ComponentStateDiagnosticSink rateLimitedLogger(Logger logger, int maximumDistinctDiagnostics) {
        Objects.requireNonNull(logger, "logger");
        return rateLimited(
                diagnostic -> logger.warn(
                        "Component-state persistence {}{}: {}",
                        diagnostic.code(),
                        diagnostic.slot().map(slot -> " [" + slot.serializedName() + "]").orElse(""),
                        diagnostic.detail()
                ),
                maximumDistinctDiagnostics
        );
    }

    enum Code {
        INVALID_IDENTIFIER,
        UNKNOWN_BLOCK,
        UNSUPPORTED_BLOCK,
        MALFORMED_COMPONENT,
        UNKNOWN_SCHEMA,
        CONTAINER_KIND_MISMATCH,
        INVALID_AXIS,
        UNKNOWN_PROPERTY,
        INVALID_PROPERTY_VALUE,
        ENCODE_UNSUPPORTED_BLOCK
    }

    record Diagnostic(Code code, Optional<ComponentSlot> slot, String detail) {
        public Diagnostic {
            Objects.requireNonNull(code, "code");
            slot = Objects.requireNonNull(slot, "slot");
            detail = Objects.requireNonNull(detail, "detail");
        }

        public static Diagnostic component(Code code, ComponentSlot slot, String detail) {
            return new Diagnostic(code, Optional.of(slot), detail);
        }

        public static Diagnostic container(Code code, String detail) {
            return new Diagnostic(code, Optional.empty(), detail);
        }
    }

    final class RateLimited implements ComponentStateDiagnosticSink {
        private final ComponentStateDiagnosticSink delegate;
        private final int maximumDistinctDiagnostics;
        private final Set<Diagnostic> emitted = new LinkedHashSet<>();

        private RateLimited(ComponentStateDiagnosticSink delegate, int maximumDistinctDiagnostics) {
            this.delegate = Objects.requireNonNull(delegate, "delegate");
            if (maximumDistinctDiagnostics < 1) {
                throw new IllegalArgumentException("maximumDistinctDiagnostics must be positive");
            }
            this.maximumDistinctDiagnostics = maximumDistinctDiagnostics;
        }

        @Override
        public synchronized void report(Diagnostic diagnostic) {
            if (emitted.contains(diagnostic) || emitted.size() >= maximumDistinctDiagnostics) {
                return;
            }
            emitted.add(diagnostic);
            delegate.report(diagnostic);
        }
    }
}
