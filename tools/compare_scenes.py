#!/usr/bin/env python3
"""
2回分の ./gradlew runScene の結果を見比べる（開発用）。

使い方:
    python3 tools/compare_scenes.py <変更前の slabee-scene フォルダ> <変更後の slabee-scene フォルダ> [出力フォルダ]

- 同じ名前のスクリーンショットどうしをピクセル単位で比べ、違いの大きい順に表示する。
- 違いのあった画像は、出力フォルダに「変更前 | 変更後 | 違いを赤で強調」の3枚並びで保存する。
- results.json の重さの測定結果も並べて表示する。

必要なもの: Pillow, numpy
"""
import json
import sys
from pathlib import Path

import numpy as np
from PIL import Image

# 1ピクセルの色の差（RGBの最大差）がこれ以下なら「同じ」とみなす
PIXEL_TOLERANCE = 8
# 違うピクセルの割合（%）がこれを超えた画像を「違いあり」として報告する
IMAGE_THRESHOLD_PERCENT = 0.05


def load(path: Path) -> np.ndarray:
    return np.asarray(Image.open(path).convert("RGB"), dtype=np.int16)


def compare(before: Path, after: Path):
    a = load(before)
    b = load(after)
    if a.shape != b.shape:
        return None, None
    diff = np.abs(a - b).max(axis=2)
    mask = diff > PIXEL_TOLERANCE
    return float(mask.mean() * 100.0), mask


def save_side_by_side(before: Path, after: Path, mask: np.ndarray, out: Path):
    a = Image.open(before).convert("RGB")
    b = Image.open(after).convert("RGB")
    highlight = np.asarray(b, dtype=np.uint8).copy() // 3
    highlight[mask] = [255, 0, 0]
    h = Image.fromarray(highlight)
    w, height = a.size
    canvas = Image.new("RGB", (w * 3, height))
    canvas.paste(a, (0, 0))
    canvas.paste(b, (w, 0))
    canvas.paste(h, (w * 2, 0))
    canvas.save(out)


def main():
    if len(sys.argv) < 3:
        print(__doc__)
        sys.exit(2)
    before_dir = Path(sys.argv[1])
    after_dir = Path(sys.argv[2])
    out_dir = Path(sys.argv[3]) if len(sys.argv) > 3 else Path("build/scene-compare")
    out_dir.mkdir(parents=True, exist_ok=True)

    before_shots = {p.name: p for p in (before_dir / "screenshots").glob("*.png")}
    after_shots = {p.name: p for p in (after_dir / "screenshots").glob("*.png")}

    rows = []
    for name in sorted(set(before_shots) | set(after_shots)):
        if name not in before_shots or name not in after_shots:
            rows.append((name, None, "片方にしかない"))
            continue
        percent, mask = compare(before_shots[name], after_shots[name])
        if percent is None:
            rows.append((name, None, "画像サイズが違う"))
            continue
        if percent > IMAGE_THRESHOLD_PERCENT:
            save_side_by_side(before_shots[name], after_shots[name], mask, out_dir / name)
        rows.append((name, percent, ""))

    changed = [r for r in rows if r[1] is None or r[1] > IMAGE_THRESHOLD_PERCENT]
    print(f"比較した画像: {len(rows)} 枚 / 違いあり: {len(changed)} 枚 "
          f"(ピクセル許容差 {PIXEL_TOLERANCE}, 画像のしきい値 {IMAGE_THRESHOLD_PERCENT}%)")
    for name, percent, note in sorted(rows, key=lambda r: -(r[1] if r[1] is not None else 1e9)):
        if percent is None:
            print(f"  !! {name}: {note}")
        elif percent > IMAGE_THRESHOLD_PERCENT:
            print(f"  差 {percent:7.3f}%  {name}")
    if changed:
        print(f"違いのあった画像の3枚並び（変更前|変更後|違い）: {out_dir}")

    def perf(path: Path):
        f = path / "results.json"
        if not f.exists():
            return {}
        return {p["name"]: p for p in json.loads(f.read_text(encoding="utf-8")).get("perf", [])}

    pb, pa = perf(before_dir), perf(after_dir)
    if pb or pa:
        print("\n重さ（フレーム平均 ms / サーバーtick平均 ms）")
        for name in sorted(set(pb) | set(pa)):
            b, a = pb.get(name, {}), pa.get(name, {})
            print(f"  {name:15s} 変更前 {b.get('frameMsAvg', '-'):>8} / {b.get('serverTickMsAvg', '-'):>6}"
                  f"   変更後 {a.get('frameMsAvg', '-'):>8} / {a.get('serverTickMsAvg', '-'):>6}")


if __name__ == "__main__":
    main()
