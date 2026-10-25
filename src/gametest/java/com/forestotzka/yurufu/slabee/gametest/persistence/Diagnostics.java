package com.forestotzka.yurufu.slabee.gametest.persistence;

import java.util.function.Consumer;
import java.util.function.LongSupplier;

/**
 * 壊れた保存データの診断ログ。壊れたワールドを読むとブロックの数だけ出てしまうので、
 * 一定時間ごとの件数に上限を設け、超えた分は件数だけまとめて出す。
 */
public final class Diagnostics {
    private final LongSupplier clockMillis;
    private final int maxPerWindow;
    private final long windowMillis;
    private final Consumer<String> sink;

    private long windowStart;
    private int reportedInWindow = 0;
    private int suppressedInWindow = 0;

    public Diagnostics(LongSupplier clockMillis, int maxPerWindow, long windowMillis, Consumer<String> sink) {
        this.clockMillis = clockMillis;
        this.maxPerWindow = maxPerWindow;
        this.windowMillis = windowMillis;
        this.sink = sink;
        this.windowStart = clockMillis.getAsLong();
    }

    /** 何も出さない（テストで診断ログを見ないとき） */
    public static Diagnostics silent() {
        return new Diagnostics(() -> 0L, 0, Long.MAX_VALUE, message -> {});
    }

    public synchronized void report(ComponentStateCodec.Issue issue, String detail) {
        long now = clockMillis.getAsLong();
        if (now - windowStart >= windowMillis) {
            flushSuppressed();
            windowStart = now;
            reportedInWindow = 0;
        }
        if (reportedInWindow < maxPerWindow) {
            reportedInWindow++;
            sink.accept("[Slabee] saved half data: " + issue + " " + detail);
        } else {
            suppressedInWindow++;
        }
    }

    /** まとめて出していない件数を出す。区間が変わったときにも呼ばれる */
    public synchronized void flushSuppressed() {
        if (suppressedInWindow > 0) {
            sink.accept("[Slabee] saved half data: " + suppressedInWindow + " more problems suppressed");
            suppressedInWindow = 0;
        }
    }
}
