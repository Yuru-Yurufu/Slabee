package com.forestotzka.yurufu.slabee.block.persistence;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComponentStateDiagnosticSinkTest {
    @Test
    void repeatedDiagnosticsAreEmittedOnceAndDistinctStorageIsBounded() {
        List<ComponentStateDiagnosticSink.Diagnostic> emitted = new ArrayList<>();
        ComponentStateDiagnosticSink sink = ComponentStateDiagnosticSink.rateLimited(emitted::add, 2);

        ComponentStateDiagnosticSink.Diagnostic first = ComponentStateDiagnosticSink.Diagnostic.component(
                ComponentStateDiagnosticSink.Code.UNKNOWN_BLOCK,
                ComponentSlot.POSITIVE,
                "missing:first"
        );
        ComponentStateDiagnosticSink.Diagnostic second = ComponentStateDiagnosticSink.Diagnostic.component(
                ComponentStateDiagnosticSink.Code.INVALID_IDENTIFIER,
                ComponentSlot.NEGATIVE,
                "bad id"
        );
        ComponentStateDiagnosticSink.Diagnostic third = ComponentStateDiagnosticSink.Diagnostic.component(
                ComponentStateDiagnosticSink.Code.UNSUPPORTED_BLOCK,
                ComponentSlot.POSITIVE,
                "minecraft:dirt"
        );

        sink.report(first);
        sink.report(first);
        sink.report(second);
        sink.report(third);

        assertEquals(List.of(first, second), emitted);
    }
}
