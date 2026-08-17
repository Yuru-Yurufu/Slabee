# Slabee beta.4 所有者向け仕様確認ガイド

状態: Stage 1所有者判断・技術Decision反映済み / 対象: Minecraft 1.21.1 / Fabric / Java 21

## この文書の権限と用途

この文書は英語設計文書の全文翻訳ではなく、所有者がbeta.4の製品仕様を承認するためのガイドです。

- 所有者が回答・承認した製品仕様と、未決定事項の一覧はこの文書を正とします。
- [`virtual-components.md`](../design/virtual-components.md)は技術設計、[`beta4.md`](beta4.md)は実装・テスト追跡の正です。
- 英語文書の具体的記述が、この文書の「未決定」を決定済みに変えることはありません。
- 矛盾を見つけた場合は勝手に解決せず、この文書へ差異を記録します。
- 技術検証で新しい製品判断が必要になった場合は、実装前にこの一覧へ追加します。

## 情報区分

- **【確認済み事実】** 基準commit `0db615b2`のコードまたはMinecraft 1.21.1クラスで確認済み。
- **【所有者決定】** 所有者がbeta.4の製品仕様として承認済み。
- **【採用予定案】** beta.4へ入れる方針だが、実装・検証前の設計。
- **【技術Decision】** 所有者が比較結果を確認し、spikeまたは基盤PRで検証する実装方式として承認済み。
- **【Minecraft制約・近似】** 1セル制約により完全再現できず、明示的な近似が必要。
- **【技術検証待ち】** Codex側で比較・実験してから方式を絞る事項。
- **【所有者判断待ち】** この文書で回答されるまで実装方式またはスコープを確定しない事項。

## 実装readiness状態

- `Blocked by design decision`: 未回答のOwner DecisionまたはTechnical Decisionがあります。
- `Pending feasibility spike`: Decision済みですが、独立spikeの成功が本実装のgateです。
- `Pending dependency`: Decision済みですが、先行PR、共通基盤、または別Stageの完了待ちです。
- `Not started`: Decisionと全必須依存関係が完了し、今すぐ着手できますが未着手です。
- `In progress`: 作業中です。
- `Complete`: 実装と必須検証が完了しています。
- `Deferred`: beta.4対象外です。

現行コードに部分実装があるか、追加検証だけが必要かはroadmapの`Current implementation`列へ記録し、readiness状態とは混同しません。通常のPR順序や共通基盤待ちは`Blocked by design decision`ではなく`Pending dependency`です。

## 中核理念と方向

**【所有者決定】** DoubleSlab／DoubleVerticalSlabは実装上1セルですが、利用者からは独立した2枚のSlabとして振る舞うことを目標とします。状態変化、消失、移動、接触効果は可能な限り対象Componentだけへ適用します。

| コンテナ | 軸 | positive | negative | 残存時の単体形状 |
| --- | --- | --- | --- | --- |
| DoubleSlab | Y | 上 | 下 | positive=`TOP`、negative=`BOTTOM` |
| DoubleVerticalSlab | X | 東 | 西 | positive=`EAST`、negative=`WEST` |
| DoubleVerticalSlab | Z | 南 | 北 | positive=`SOUTH`、negative=`NORTH` |

positive／negativeは保存スロット名であり、視点・設置順・素材で入れ替えません。

参照: [`Purpose`](../design/virtual-components.md#purpose)、[`Canonical slot mapping`](../design/virtual-components.md#canonical-slot-mapping)

## 状態の所有者と正データ

**【技術Decision】** 状態を次のように分離します。

- スロットから導出: 水平Slabの`TYPE`、VerticalSlabの`FACING`。
- コンテナ所有・永続化: DoubleVerticalのX/Z `AXIS`。片側の状態ではありません。
- 周囲から再計算: `SNOWY`、葉の`DISTANCE`等。履歴として保存しません。
- Componentごとに保存: 葉の`PERSISTENT`、巨大キノコの6方向面状態等。

**【技術Decision】** beta.4読込後は、外側BlockStateの`AXIS`をコンテナ軸の正とします。Component IDと保存対象propertyはBlock Entity NBTを正とし、`TYPE`、`FACING`、描画・光・当たり判定の要約は再構築します。

旧NBTでは有効なBlock Entityの`axis`を移行入力として外側`AXIS`を初期化・修復します。欠損・不正なら既存BlockState、さらに取得不能ならXを安全な既定値とします。beta.4形式でBlockStateと重複NBTが矛盾した場合はBlockStateを維持し、診断後の保存で重複値を修正します。

**【技術Decision】** バージョン付きschemaと、検査済みの`NbtHelper.fromBlockState`／`NbtHelper.toBlockState`を使用します。既知`BlockTriple`と形状を検証したうえでComponent固有propertyだけを保存し、`TYPE`／形状用`FACING`はslotから復元、`SNOWY`／葉の`DISTANCE`は配置後に再計算、DoubleVerticalの`AXIS`はcontainerへ1回だけ保存します。旧Block IDだけのNBTを読むlegacy readerを維持します。

propertyだけが不正ならそのBlockのdefault値を使います。Block IDが不正、削除済み、または非対応ならそのComponentを失われたものとして扱い、正常な相方だけをSingle化します。両方復元不能ならairとし、診断ログはrate-limitします。最初のPRは永続化spikeとテストだけに限定し、ゲーム内の新機能を変更しません。

参照: [`State ownership`](../design/virtual-components.md#state-ownership)、[`Runtime and migration sources of truth`](../design/virtual-components.md#runtime-and-migration-sources-of-truth)

## Component操作の不変条件

1. Singleの`ComponentRef`は明示的な`SINGLE` locationを持ち、`POSITIVE`／`NEGATIVE`はDoubleだけに使います。
2. 読み取りはworldやBlock Entityを変更しません。
3. 置換・除去対象でない相方の種類とComponent固有状態を維持します。
4. コンテナ所有の軸とスロット方向を維持します。
5. 片側除去後は残存側を正しい単体形状へ変換し、孤立NBTを残しません。
6. Singleには相方がないため、通常除去はair、waterlogged除去は保持FluidStateの復元とし、DoubleコンテナやBlock Entityを残しません。
7. `MutationResult`は残存Singleへの変換、セル消失、流体復元、変更なしを区別し、原因はsurvival、creative、自然消失、燃焼、落下等を区別します。
8. 失敗時は両側を変更しません。同時変更は変更前スナップショットから決定します。
9. world書込、dirty、listener、neighbor通知はトランザクションにつき1回です。
10. loot、音、particle、game event等は原因に従い、影響を受けたComponentごとに正確に処理します。

参照: [`Component operations and invariants`](../design/virtual-components.md#component-operations-and-invariants)

## 所有者が決定した製品仕様

### 原木のAXISと剥皮

**【所有者決定】** 原木系Slabの`AXIS`対応はbeta.4対象外です。現在の固定された木目方向を意図的な仕様として維持します。

- `AXIS`対応には切断面用アセット、新BlockState、model variantが必要です。
- 必要なアセットを用意できる将来の候補として記録します。
- 斧による剥皮はbeta.4対象です。固定方向のまま、選択Componentだけを対応するstripped系Slabへ置換します。

### 巨大キノコの面表示

**【所有者決定】** 赤色／茶色のキノコブロックとキノコの柄について、Minecraft 1.21.1の6方向面状態、外側・内側・柄テクスチャ、生成・配置・隣接・破壊時挙動をbeta.4対象にします。

- 水平、縦、両DoubleでComponentごとの状態を保存します。
- 相方除去で新しく露出する面、保存再読込、単体化、モデル表示をテストします。
- 実装前に、バニラテクスチャと現行UV切り出しだけで対応可能か確認します。
- 足りる場合は既存テクスチャを再利用します。
- 新規アセットが必要なら仮テクスチャを作らず、所有者へ報告します。
- asset確認は開始条件であり完了条件ではありません。BlockState、Component保存、model／UV、全形状、相方除去後の露出面、保存再読込、client同期、描画の実装と試験がすべて成功して初めて`Complete`です。

### Soul Fire

**【所有者決定】** Soul Fireをbeta.4対象にし、次の優先規則を採用します。

- 水平単体は、露出した上面がSoul Sand／Soul Soil系なら上の火をSoul Fireにします。
- 水平Doubleは、外部上面を構成するpositive上側Componentだけで判定します。
- VerticalとDoubleVerticalは、火が接触するComponentで判定します。
- 同じ火セルが通常ComponentとSoul系Componentの両方へ接する場合はSoul Fireを優先します。
- 相方を変更せず、バニラの着火・消火条件を可能な限り維持します。

### 縦Doubleの混在Bubble

**【所有者決定】** 第一目標は、Soul Sand側で上昇、Magma側で下降となるComponentごとの物理挙動と、左右別の泡表示です。

- 第一目標ではEntityごとに、そのAABBの水平投影と各Component半面が重なる面積割合を重みにして、接触するComponentの力を合成します。中心座標の二択にはしません。
- 各Component半面に対応する泡を表示し、物理方向、泡の方向、泡の有無を一致させます。
- spikeが未完成、または一部テストが失敗しただけではfallbackへ移行しません。Minecraft 1.21.1の表現制約、互換性、安定性、またはB4性能基準により第一目標を安全に実装できないと根拠付きで確認された場合だけ、fallbackを候補にします。
- spike結果には、失敗した条件、試した方式、性能値、残る制約を記録します。fallbackの採用前に日本語で所有者へ報告し、明示的な承認を得ます。Codexが自動的にfallbackを採用しません。
- 代替案ではEntity AABBを重みに使いません。セル全体の合成力を`Σ(Componentの露出上面積割合 × 対応するバニラBubbleの力)`で固定し、セル内の全Entityへ同じ結果を適用します。Entityの位置や数でfallback方向を変えません。
- fallbackの固定重みは、水平Slabの露出上面が1.0、縦Slabが0.5、DoubleVerticalの各Componentが0.5ずつです。水平Doubleは水と接する上側Componentだけ1.0で下側は0です。bubble sourceでないComponentの寄与は0です。
- 同種の縦Componentが2枚なら0.5＋0.5で通常ブロック相当、Soul＋Magmaなら各0.5の上昇力／下降力を合成します。fallbackでも物理方向、泡の方向、泡の有無を一致させます。
- ゼロまたは極小とみなす閾値、その場合の粒子の有無、バニラの非対称な速度変化・速度上限へ合成力を適用する正確な順序はfeasibility spikeで確定します。
- fallback自身のテストに失敗した場合は、別の近似へ勝手に進みません。実装を止めて所有者へ再報告します。

**第一目標の合格条件:** EntityごとのAABB重なり割合から期待されるComponent別合成力が、Player／Mob／Item／Boat、両軸、各slot、中央境界、複数Entity、client/serverで一致し、左右別の泡について物理方向・泡の方向・泡の有無が一致し、安定性・互換性試験とB4性能基準を満たすことです。

**fallbackの合格条件:** 所有者がfallbackを明示承認した後、固定露出上面積だけから得る1つの合成ベクトルがEntityの位置・数・種類に依存せずセル内の全Entityへ同じように適用され、確定した閾値と速度上限適用順を含めて物理方向・泡の方向・泡の有無が一致し、安定性・互換性試験とB4性能基準を満たすことです。

### Componentごとの燃焼

**【所有者決定】** 可燃性と燃焼結果はComponentごとに独立させます。

- 可燃Componentと不燃Componentの組み合わせでは可燃側だけが消え、相方は正しいSingleとして残ります。
- 同じ外部面のFireが両Componentへ接触する場合も、変更前スナップショットからそれぞれ独立に燃焼抽選します。評価順によって他方の結果が変化してはなりません。
- 片側だけが燃えた場合、空いた半セルへ専用Fireを置かず、同じセル内にFireを生成しません。
- 両Componentが燃えてセル全体が空いた場合だけ、バニラの設置・生存・雨・流体条件に従って通常FireまたはSoul Fireを配置し、条件不成立ならairにします。
- Soul Fireの種類判定は接触Component規則を使い、同じFireセルが通常ComponentとSoul系Componentの両方へ接触する場合はSoul Fireを優先します。

### 氷の融解

**【所有者決定】** 通常Ice Componentの融解結果を次に固定します。Packed IceとBlue Iceは対象外です。

- 片側Iceが融け、相方がwaterlogged可能なら、相方をSingle化して`WATERLOGGED=true`にします。
- 相方がwaterlogged不可なら相方を残し、水を生成しません。
- セル全体のIceが融ける場合、通常環境では水源、ultrawarm環境ではairにします。
- 隣接セルへ水を押し出す独自処理は行いません。
- Horizontal／Vertical、全slot／axis、両側同時融解、保存再読込、neighbor fluid tickを同じセル単位規則で検証します。

### 葉のloot parity

**【所有者決定】** 葉lootはbeta.4必須で、距離計算・自然消滅の実装可否とは別項目です。ハサミ／Silk Touchは対象Componentに対応する葉Slabを1個落とします。通常破壊／自然消滅は元の葉の苗木・棒・リンゴ等とFortune条件を維持し、1 Componentはバニラ1ブロックの半分、同種2 Componentの合計は1ブロック相当の期待値を目標にします。Mixed Doubleは対象側だけ評価し、creative、Fire等のno-loot原因は0個です。

**【技術Decision】** 各Componentで元のバニラ葉lootをFortuneを含む元のloot contextで1回評価します。生成された通常lootの各`ItemStack`を個別に、互いに独立した50%抽選で保持または破棄します。loot結果全体を1回の50%抽選へ掛けず、保持した`ItemStack`内の`count`も変更しません。Shears／Silk Touchは確定dropなのでthinningせず、no-loot原因は0個です。2枚合計の期待値をバニラ葉1個と一致させ、分布が完全一致しないことは許容します。抽選は固定seedで再現可能にし、通常CIと正式統計taskを下記のとおり分離します。

正式統計検証では固定seedを3本使用し、各seed・各確率条件につき`n = 1,000,000`の対応あり試行を行います。対象Itemについて、試行`i`のバニラ葉1個の個数を`V_i`、同種のSlabee葉Component 2枚の合計個数を`S_i`、差を`D_i = S_i - V_i`とします。`D_i`の標本平均を`mean_D`、標本標準偏差を`s_D`、差の標準誤差を`SE_D = s_D / sqrt(n)`とし、`abs(mean_D) <= 5 * SE_D`を合格条件とします。vanilla側とSlabee側を独立標本として標本分散を単純加算しません。`s_D = 0`なら`mean_D = 0`の場合だけ合格です。全固定seed・全確率条件・全対象Itemで合格を要求します。これは期待値一致だけの検査で、分布の完全一致は要求しません。

Shears、Silk Touch、Creative、Fire等の決定的結果は統計検査へ混ぜず完全一致でassertします。正式統計taskはStage 12完了時に実行し、同じtaskをStage 14のbeta.4リリース前に再実行します。通常CIには入れず、短い決定的単体テストとfixtureだけを入れます。固定seedの値、定数名、試行数の実装表現、対応あり標本の標準誤差式は技術設計の[`Leaf loot`](../design/virtual-components.md#leaf-loot)を唯一の正本とします。

### Pumpkin carving

**【所有者決定】** 通常のPumpkin Slabは維持しますが、Pumpkin carving、Carved Pumpkinの水平／縦Slab、Double内の片側切り抜きはbeta.4のRequiredと通常Plannedから外します。

全Requiredと通常Plannedが完成し、release前に十分な余裕がある場合だけ追加候補として再検討します。ここでいうrelease gateは既存Stage 14のrelease完了条件であり、別のgateや作業単位を追加しません。完了後も自動的に対象へ昇格せず、再検討には新しい所有者承認が必要です。現時点では実装や永続化設計の前提条件にしません。

### 追加parity項目の分類

**【所有者決定】** 追加監査項目を次に分類します。

- Required: Mangrove Rootsのwaterlogging、明るさ・透過・摩擦等の静的設定回帰。
- Planned: クワ・水入り瓶・泥から粘土等の土系変換、干草の落下ダメージ軽減、特殊loot・経験値・Piglin反応、装飾用の固有粒子。
- Deferred: 非原木Pillarの`AXIS`、羊毛による振動遮断。
- Deferred／release-gate完了後の追加候補: Pumpkin carving。

### Sculk Catalyst

**【確認済み事実】** 現行マッピングに`SCULK` Slabはありますが、`SCULK_CATALYST`のSlabeeマッピングやCatalyst Block Entityはありません。

**【所有者決定】** Catalystの伝播・charge・bloom・Component変換の完全対応はbeta.4対象外です。

beta.4では、現行Sculk Slabをvanilla Catalyst周辺で配置・破壊・保存再読込し、Mob死亡時にクラッシュ、Block Entity消失、経験値複製、Component消失、ワールド破損が起きないことだけを必須確認します。

### ピストン

**【所有者決定】** beta.4では片側Component移動を実装せず将来候補とし、現行のセル単位挙動を維持・検証します。Single、DoubleSlab、DoubleVerticalSlabの実際の`PistonBehavior`とBlock Entity処理をMinecraft 1.21.1コードとGameTestで確認し、推測で固定しません。消失・複製・BE/NBT破損・同期不一致があれば、安全な移動拒否を許容します。

## 承認済み技術Decisionと最初のPR範囲

### B1. 落下Componentの状態運搬

**【技術Decision】** vanilla `FallingBlockEntity`へSlabee専用のnamespaced payloadを追加します。専用EntityとFabric Data Attachmentは現時点では採用しません。readinessは`Pending feasibility spike`です。

payloadはschema version、component BlockState、container kind、slot、vertical axisを保存します。他Modブロックは明示的なadapter登録がある場合だけ許可します。最初は独立spikeとし、落下中の保存・再読込、元セルの相方保持、着地時のSingle配置／再結合、重複・消失防止、Concrete Powder硬化、spawn失敗時rollback、チャンク境界を検証します。spikeが成功するまで本実装へ進みません。

### B2. BlockState永続化

**【技術Decision】** 上記「状態の所有者と正データ」のversioned schema、検査済み`NbtHelper`経路、property所有権、legacy reader、Component単位fallbackを採用します。Decisionは回答済みで、readinessは`Pending feasibility spike`です。最初のPRはcodec／registry検査のspike、旧形式・新形式fixture、不正データ、round trip、Single化結果のテストに限定し、ゲーム内の新機能は変更しません。全面的な保存・同期・移行はspikeレビュー後のStage 6で別に実装します。

### B3. 葉loot

**【技術Decision】** 上記「葉のloot parity」の`ItemStack`単位50% thinning方式を採用します。Decisionは回答済みでB3固有のfeasibility gateはありませんが、共通Component／loot基盤の先行PR待ちなのでreadinessは`Pending dependency`です。各stackを独立抽選し、結果全体の一括抽選とstack内`count`の縮小を禁止します。最初のleaf-loot PRは、共通loot変換、Shears／Silk Touch／no-lootの完全一致assert、Fortuneを渡す短い決定的単体テストとfixture、正式統計検証を別taskとして実行できる仕組みまでを範囲とします。全条件の正式検証を毎回のCIへ入れません。

### B4. 性能baseline

**【技術Decision】** 0／1,000／10,000 Double、idle、random tick、scheduled／neighbor burst、falling、fire、sponge、bubble、client rendering、save／reloadを固定fixtureで測定します。vanilla control、Single Slab、イベント1,000回あたりの処理時間も併記します。

暫定調査閾値は次です。pilot測定後に根拠とともに版管理し、承認前はrelease合否の確定値として扱いません。各比較で同じ時間metricの`baseline_ms`と`candidate_ms`を使い、`delta_ms = candidate_ms - baseline_ms`とします。

- `idle_threshold_ms = max(0.05 * baseline_ms, 3 * MAD_ms, 0.5 ms)`。
- `burst_threshold_ms = max(0.10 * baseline_ms, 3 * MAD_ms, 2 ms)`。
- `regression = (delta_ms > corresponding_threshold_ms)`とします。
- server MSPTとclient frame timeは別々のbaseline、MAD、delta、thresholdで計算します。clientの合否はFPSではなくframe timeのmsで判定し、FPSは参考表示だけにできます。
- allocation等の時間以外のmetricは、割合項とMADを同じmetric単位へ変換して`max(rate * baseline_metric, 3 * MAD_metric)`を使い、`0.5 ms`／`2 ms`の絶対下限を使いません。無次元の5%／10%とmsを直接比較しません。
- server p99が継続して50 msを超えた場合は必ず調査します。

通常PRは影響範囲だけの短縮測定、Stage完了時とbeta.4公開前は固定環境で完全測定を行います。GitHub Actions共有runnerは機能テストには使えても、性能の合否判定には使いません。最初のbaseline PRはfixture、採取形式、warm-up、測定時間、反復、JFR、比較レポートとpilotに限定し、機能最適化や新機能を同梱しません。

参照: [`Movement`](../design/virtual-components.md#movement)、[`Logs and huge mushrooms`](../design/virtual-components.md#logs-and-huge-mushrooms)、[`Beta.4 scope classification`](beta4.md#beta4-scope-classification)

## Gateと既存作業単位の対応

新しい独立PRや機能範囲は追加しません。正本の対応表はroadmapの[`Named gate traceability`](beta4.md#named-gate-traceability)です。

| 維持するgate | 既存作業単位 | 成果物／合格証拠 |
| --- | --- | --- |
| B2永続化feasibility | Stage 1のB2 persistence spike PR | versioned schema、検査済み`NbtHelper`、registry/property filter、新旧・不正fixture、fallback、round tripのレビュー済み結果 |
| B4 baseline pilot | Stage 1のB4 baseline PRとpilot | 版管理された測定手順、固定fixture／設定、raw／JFR出力、比較report、pilot後閾値 |
| A1 Bubble feasibility | Stage 9のbubble feasibility spike task | 試行方式、失敗条件、性能値、残る制約、第一目標の合格証拠を含むspike report。fallback時は同じreportを使う日本語報告と所有者の明示承認も必要 |
| 巨大キノコasset／UV feasibility | Stage 11の手順1〜2 | 既存texture／UVで足りることを示すreview記録、または不足点を特定した所有者向けreport。独立PRではない |
| B3葉loot正式証拠 | Stage 12完了時の正式統計taskと、Stage 14での同一taskのrelease前再実行 | 全固定seed・確率条件・対象Itemの対応あり差の統計reportと決定的条件の完全一致report。2回目も同じtask／定数を使う |
| B1 falling payload feasibility | Stage 13冒頭のspike | 落下中save/reload、相方保持、着地／再結合、無消失・無複製、Concrete Powder、spawn rollback、chunk境界のreview済みartifact |

Soul Fire／同時燃焼はStage 10、氷融解はStage 9、追加Planned項目はStage 14の既存作業です。これらに別の独立gateを設けません。Stageの完了条件はそのStageが作る証拠であり、別名のgateとして重複させません。

## Minecraft制約と近似方針

| 制約 | 現在の方針 |
| --- | --- |
| BlockState、Block Entity、tickは位置単位 | hit位置、Entity位置、接触投影、イベント元からComponentを復元し、同時変更は1トランザクション化 |
| FluidStateはセルに1つ | 接触は片側判定、水の生成・除去はセル単位。Double化で排出した水は復元しない |
| 泡の`DRAG`はセルに1つ | 第一目標はEntity別AABB重みと左右別表示。fallbackは根拠付き報告と所有者の明示承認後だけ候補とし、Entity非依存の固定露出上面積重みでセル全体へ同じ力を適用。いずれも物理方向・泡の方向・泡の有無を一致させる |
| 火はセルに1つ | 燃焼はComponentごとに独立。片側消失時はFireなし、両側消失時だけバニラ条件で通常Fire／Soul Fireまたはair |
| FallingBlockEntityは通常1 BlockState | vanilla Entityへversioned namespaced payloadを追加。専用Entity／Data Attachmentは採用せず、spike成功まで本実装を禁止 |
| スポンジの水除去はセル単位 | 両側を変更前スナップショットから対称評価し、対象水集合の和を各セル1回だけ除去 |
| コンクリート粉末の水判定は方向依存 | 下面だけでは硬化せず、現在セルの水または非DOWN方向の有効接触だけを対象にし、バニラのside-solid判定を維持 |
| ピストンはセル単位 | beta.4は片側移動を行わず、現行結果を検証。危険な場合はコンテナ移動を拒否 |

両スポンジが変更前状態で吸水条件を満たす場合、到達水が重複しても両方を濡れ状態にします。各Componentは個別のバニラ上限で探索し、重複水ブロックはworldから1回だけ除去します。

参照: [`Fluids and waterlogging`](../design/virtual-components.md#fluids-and-waterlogging)、[`Sponge transactions`](../design/virtual-components.md#sponge-transactions)、[`One-cell constraints and approximation policy`](../design/virtual-components.md#one-cell-constraints-and-approximation-policy)

## beta.4のスコープ分類

### beta.4必須

- GameTest、ComponentRef、片側除去・単体化、片側置換、状態保存、旧NBT移行、性能baseline。
- 草・雪・道、スポンジ・サンゴ、通常燃焼の基盤、コンクリート硬化、銅、固定方向の原木剥皮、葉のlifecycleとloot parity。Rooted Dirtのシャベル処理はDirt Path化・Hanging Rootsなしです。鍬によるDirt化とHanging Rootsドロップは別のPlanned土系変換として追跡します。
- 氷の滑り、マグマダメージ、ソウルサンド接触、Soul Speed、泥、菌糸・ポドゾル、ナイリウム、苔・根付いた土。
- Mangrove Rootsのwaterlogging、明るさ・透過・摩擦等の静的設定回帰。
- Sculk Catalyst周辺の安全性確認だけ。

### beta.4で実装予定

- 巨大キノコの面表示。asset／UV gateは既存Stage 11の手順1〜2で完了させます。
- Soul Fire。独立gateはなく、既存Stage 10で実装・検証します。
- 同時燃焼、片側落下、氷融解水、縦の混在泡。落下だけがB1 spike、泡だけがA1 feasibility spikeの成功後に本実装します。同時燃焼はStage 10、氷融解水はStage 9に統合され、別名のgateを設けません。
- クワ・水入り瓶・泥から粘土等の土系変換、干草の落下ダメージ軽減、特殊loot・経験値・Piglin反応、装飾用の固有粒子。独立feasibility gateはなく、共通依存完了後に既存Stage 14の最初の作業項目で完了させます。

### beta.4対象外・将来候補

- 原木系の`AXIS`と可変木目モデル。
- 非原木Pillarの`AXIS`、羊毛による振動遮断。
- Pumpkin carving。全Required／通常Planned完了後に余裕がある場合だけ、所有者承認を経て追加候補として再検討します。
- Sculk Catalystの伝播・charge・bloom完全対応。
- ピストンによる片側Component移動。
- Minecraft別バージョン、NeoForge、新形状、半セル流体、大規模BE／描画再設計、大量の新規対応ブロック。

基準commitの初期棚卸しは「core 31行 + additional 10行」でした。葉lootをlifecycleから独立し、分類の異なる複合監査行を分割した現在の追跡表はcore 32行 + additional 12行です。どちらも固定目標数や全行一律必須を意味しません。

実装計画は14段階です。各段階はレビュー可能な大きさに応じて複数の小さなPRへ分割できます。

参照: [`Core behavior tracking checklist`](beta4.md#core-behavior-tracking-checklist)、[`Pull request roadmap`](beta4.md#pull-request-roadmap)、[`Out of scope for beta.4`](beta4.md#out-of-scope-for-beta4)

## リリース条件

- 「beta.4必須」と「beta.4で実装予定」がすべて`Complete`。
- Deferred項目が将来候補として正確に記載され、通常の実装前提になっていない。
- 採用した近似ごとに自動テストと手動テストがある。
- 旧NBT移行、両Double種・両軸の結合・置換・除去・単体化・保存再読込・同期が成功する。
- Single除去のair／FluidState復元、原因別効果、`MutationResult`、残留Block Entityなしを確認する。
- 巨大キノコはasset確認だけでなく、実装、全形状、新露出面、保存、同期、描画の全試験が成功する。
- Stage 1の永続化spike PRが承認され、Stage 6で新旧NBT、fallback、initial chunk data、同期、dirty/listener、Single化、Double維持、chunk再読込を完了する。
- ピストンのセル単位動作にComponent／BE消失、複製、NBT破損、client/server不一致がなく、危険な経路は安全に拒否される。
- `ItemStack`単位の独立50% thinningによるleaf-loot Decisionを実装し、結果全体の一括抽選とstack内`count`変更がないことを確認する。Shears／Silk Touch／Creative／Fire等は完全一致assert、確率条件は技術設計の正本に定めた3固定seed・各`n = 1,000,000`の対応あり差`D_i`と`abs(mean_D) <= 5 * SE_D`基準（`s_D = 0`規則を含む）を全対象Itemで満たす。同じ正式taskをStage 12完了時とStage 14のbeta.4リリース前に実行し、lifecycle完了とは別に判定する。
- bubbleは第一目標とfallbackの合格条件を別に評価する。第一目標が未完成または一部失敗しただけでfallbackへ移行せず、表現制約・互換性・安定性・B4性能基準の根拠を日本語で報告して所有者の明示承認を得る。fallbackも不合格なら別の近似へ自動移行しない。
- 全Doubleへの無条件tickがなく、既存tickerも削除または実測で正当化される。
- baseline PRでpilot・版管理した測定手順とmetric単位が一致する最終閾値を、固定環境のリリース候補測定が満たす。server MSPTとclient frame timeを別々に判定し、GitHub Actions共有runnerを性能合否へ使用しない。
- クライアント、専用サーバー、CIが成功し、所有者が公開を承認する。

参照: [`Performance baseline before feature code`](beta4.md#performance-baseline-before-feature-code)、[`Release gates`](beta4.md#release-gates)

## 所有者判断と技術Decisionの状態

Stage 1で列挙したA1〜A5の製品仕様とB1〜B4の技術方式には、すべて回答が記録されています。旧Q番号は履歴上の識別子として残しますが、未決定候補ではありません。

- Q1／A1: 第一目標はEntity別AABB面積加重、fallbackは固定露出上面積加重。物理と表示を一致。`Pending feasibility spike`。
- Q2／A2: Component独立燃焼、片側消失時Fireなし、両側消失時だけバニラ条件でFire／Soul Fireまたはair。
- Q3／B1: vanilla `FallingBlockEntity`のSlabee namespaced payload。独立spike待ち。
- Q6／A3: waterlog可能な相方はwaterlogged化、不可なら水抑制、セル全消失時はdimension規則。
- Q7／A4: Pumpkin carvingはDeferredで、release-gate後の再検討候補。
- Q8／A5: Required／Planned／Deferredを項目別に確定。
- B2: versioned filtered BlockState NBT。Decision回答済み、`Pending feasibility spike`。
- B3: vanilla leaf loot評価後の`ItemStack`別独立50% thinning。Decision回答済み、共通Component／loot基盤待ちの`Pending dependency`。
- B4: 固定環境baselineと暫定閾値。Decision回答済みで、最終閾値を版管理するbaseline pilot待ちの`Pending feasibility spike`。

現時点で`Blocked by design decision`に当たるStage 1項目はありません。B1／B2のspike、A1のfeasibility spike、Stage 11の巨大キノコasset／UV task、B4のpilotは`Pending feasibility spike`、B3とroadmapの後続機能は必要な共通基盤・先行Stageに応じて`Pending dependency`です。B3正式統計はStage 12とStage 14で同じtaskを実行するrelease証拠であり、別のfeasibility gateではありません。A1のfallbackは根拠付きの日本語報告と所有者の明示承認なしに採用せず、fallback自身が不合格なら別の近似へ勝手に進みません。
