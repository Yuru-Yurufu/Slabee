# Slabee beta.4 所有者向け仕様確認ガイド

状態: 所有者決定を一部反映・残件回答待ち / 対象: Minecraft 1.21.1 / Fabric / Java 21

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
- **【Minecraft制約・近似】** 1セル制約により完全再現できず、明示的な近似が必要。
- **【技術検証待ち】** Codex側で比較・実験してから方式を絞る事項。
- **【所有者判断待ち】** この文書で回答されるまで実装方式またはスコープを確定しない事項。

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

**【採用予定案】** 状態を次のように分離します。

- スロットから導出: 水平Slabの`TYPE`、VerticalSlabの`FACING`。
- コンテナ所有・永続化: DoubleVerticalのX/Z `AXIS`。片側の状態ではありません。
- 周囲から再計算: `SNOWY`等。履歴として保存しません。
- Componentごとに保存: 葉の`DISTANCE`／`PERSISTENT`、巨大キノコの6方向面状態等。

**【採用予定案】** beta.4読込後は、外側BlockStateの`AXIS`をコンテナ軸の正とします。Component IDと保存対象propertyはBlock Entity NBTを正とし、`TYPE`、`FACING`、描画・光・当たり判定の要約は再構築します。

旧NBTでは有効なBlock Entityの`axis`を移行入力として外側`AXIS`を初期化・修復します。欠損・不正なら既存BlockState、さらに取得不能ならXを安全な既定値とします。beta.4形式でBlockStateと重複NBTが矛盾した場合はBlockStateを維持し、診断後の保存で重複値を修正します。

**【所有者決定】** Component状態はMinecraft 1.21.1標準のBlockState NBT方式を第一候補とし、既知`BlockTriple`と形状を検証したうえでComponent固有propertyだけを保存します。`TYPE`／`FACING`／`SNOWY`は正本にせず、コンテナ`AXIS`は明示保存します。旧Block IDを読み、不明block・property・値は相方を失わない安全なfallbackにします。

**【技術検証待ち】** Stage 1の小規模spikeで正確なAPI、registry lookup、失敗時挙動、round tripを確認し、Decision Recordを確定します。依存実装は承認後に開始し、全面実装と同期・移行試験はStage 6で完了させます。

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

### 葉のloot parity

**【所有者決定】** 葉lootはbeta.4必須で、距離計算・自然消滅の実装可否とは別項目です。ハサミ／Silk Touchは対象Componentに対応する葉Slabを1個落とします。通常破壊／自然消滅は元の葉の苗木・棒・リンゴ等とFortune条件を維持し、1 Componentはバニラ1ブロックの半分、同種2 Componentの合計は1ブロック相当の期待値を目標にします。Mixed Doubleは対象側だけ評価し、creative等のno-loot原因は0個です。**【技術検証待ち】** Fortune確率、丸め、2枚の独立抽選／一括評価はleaf-loot Decision Recordと統計テストで確定します。scopeはRequired、readinessだけが`Blocked by leaf-loot Decision Record`です。

### Sculk Catalyst

**【確認済み事実】** 現行マッピングに`SCULK` Slabはありますが、`SCULK_CATALYST`のSlabeeマッピングやCatalyst Block Entityはありません。

**【所有者決定】** Catalystの伝播・charge・bloom・Component変換の完全対応はbeta.4対象外です。

beta.4では、現行Sculk Slabをvanilla Catalyst周辺で配置・破壊・保存再読込し、Mob死亡時にクラッシュ、Block Entity消失、経験値複製、Component消失、ワールド破損が起きないことだけを必須確認します。

### ピストン

**【所有者決定】** beta.4では片側Component移動を実装せず将来候補とし、現行のセル単位挙動を維持・検証します。Single、DoubleSlab、DoubleVerticalSlabの実際の`PistonBehavior`とBlock Entity処理をMinecraft 1.21.1コードとGameTestで確認し、推測で固定しません。消失・複製・BE/NBT破損・同期不一致があれば、安全な移動拒否を許容します。

参照: [`Movement`](../design/virtual-components.md#movement)、[`Logs and huge mushrooms`](../design/virtual-components.md#logs-and-huge-mushrooms)、[`Beta.4 scope classification`](beta4.md#beta4-scope-classification)

## Minecraft制約と近似方針

| 制約 | 現在の方針 |
| --- | --- |
| BlockState、Block Entity、tickは位置単位 | hit位置、Entity位置、接触投影、イベント元からComponentを復元し、同時変更は1トランザクション化 |
| FluidStateはセルに1つ | 接触は片側判定、水の生成・除去はセル単位。Double化で排出した水は復元しない |
| 泡の`DRAG`はセルに1つ | 見た目とEntityへの力を分離する候補。中央境界と見た目は未決定 |
| 火はセルに1つ | 各Componentの燃焼可否は独立判定。両側同時消失後のセル状態は未決定 |
| FallingBlockEntityは通常1 BlockState | Component状態を運ぶ方式は未決定。形状・相方・保存状態を失う方式は不可 |
| スポンジの水除去はセル単位 | 両側を変更前スナップショットから対称評価し、対象水集合の和を各セル1回だけ除去 |
| コンクリート粉末の水判定は方向依存 | 下面だけでは硬化せず、現在セルの水または非DOWN方向の有効接触だけを対象にし、バニラのside-solid判定を維持 |
| ピストンはセル単位 | beta.4は片側移動を行わず、現行結果を検証。危険な場合はコンテナ移動を拒否 |

両スポンジが変更前状態で吸水条件を満たす場合、到達水が重複しても両方を濡れ状態にします。各Componentは個別のバニラ上限で探索し、重複水ブロックはworldから1回だけ除去します。

参照: [`Fluids and waterlogging`](../design/virtual-components.md#fluids-and-waterlogging)、[`Sponge transactions`](../design/virtual-components.md#sponge-transactions)、[`One-cell constraints and approximation policy`](../design/virtual-components.md#one-cell-constraints-and-approximation-policy)

## beta.4のスコープ分類

### beta.4必須

- GameTest、ComponentRef、片側除去・単体化、片側置換、状態保存、旧NBT移行、性能baseline。
- 草・雪・道、スポンジ・サンゴ、通常燃焼の基盤、コンクリート硬化、銅、固定方向の原木剥皮、葉のlifecycleとloot parity。Rooted Dirtのシャベル処理はDirt Path化・Hanging Rootsなしで、鍬によるDirt化とHanging Rootsドロップは別の未決定項目です。
- 氷の滑り、マグマダメージ、ソウルサンド接触、Soul Speed、泥、菌糸・ポドゾル、ナイリウム、苔・根付いた土。
- Sculk Catalyst周辺の安全性確認だけ。

### beta.4で実装予定だが、独立した設計・アセット確認が必要

- 巨大キノコの面表示。
- Soul Fire。
- 同時燃焼、片側落下、氷融解水、縦の混在泡。未決定回答後に実装します。

### beta.4対象外・将来候補

- 原木系の`AXIS`と可変木目モデル。
- Sculk Catalystの伝播・charge・bloom完全対応。
- ピストンによる片側Component移動。
- Minecraft別バージョン、NeoForge、新形状、半セル流体、大規模BE／描画再設計、大量の新規対応ブロック。

### スコープ未決定

- カボチャの切り抜き。
- 土壌追加変換、マングローブの根のwaterlogging、原木以外の柱方向、干草の落下軽減、羊毛の振動遮断、葉以外の特殊loot／ピグリン反応、静的な光・粒子・素材parity。

基準commitの初期棚卸しは「core 31行 + additional 10行」でした。葉lootをlifecycleから独立させた現在はcore 32行 + additional 10行です。どちらも固定目標数や全行一律必須を意味しません。

実装計画は14段階です。各段階はレビュー可能な大きさに応じて複数の小さなPRへ分割できます。

参照: [`Core behavior tracking checklist`](beta4.md#core-behavior-tracking-checklist)、[`Pull request roadmap`](beta4.md#pull-request-roadmap)、[`Out of scope for beta.4`](beta4.md#out-of-scope-for-beta4)

## リリース条件

- 「beta.4必須」と「beta.4で実装予定」がすべて`Complete`。
- スコープ未決定項目に所有者回答があり、延期項目が将来候補として正確に記載されている。
- 採用した近似ごとに自動テストと手動テストがある。
- 旧NBT移行、両Double種・両軸の結合・置換・除去・単体化・保存再読込・同期が成功する。
- Single除去のair／FluidState復元、原因別効果、`MutationResult`、残留Block Entityなしを確認する。
- 巨大キノコはasset確認だけでなく、実装、全形状、新露出面、保存、同期、描画の全試験が成功する。
- Stage 1で永続化Decision Recordを承認し、Stage 6で新旧NBT、fallback、initial chunk data、同期、dirty/listener、Single化、Double維持、chunk再読込を完了する。
- ピストンのセル単位動作にComponent／BE消失、複製、NBT破損、client/server不一致がなく、危険な経路は安全に拒否される。
- leaf-loot Decision Recordを承認し、ハサミ／Silk Touch／no-lootの確定結果と、Fortune・通常破壊・自然消滅の期待値試験が全形状・同種2枚・Mixedで成功する。lifecycle完了とは別に判定する。
- 全Doubleへの無条件tickがなく、既存tickerも削除または実測で正当化される。
- baseline PRで確定・版管理した測定手順と明示的な閾値を、リリース候補が満たす。
- クライアント、専用サーバー、CIが成功し、所有者が公開を承認する。

参照: [`Performance baseline before feature code`](beta4.md#performance-baseline-before-feature-code)、[`Release gates`](beta4.md#release-gates)

## 所有者判断の正規一覧

この節だけが未決定の製品仕様一覧です。英語文書に候補案があっても、ここで未決定なら実装仕様ではありません。旧Q4（ピストン）と旧Q5（永続化方針）は上記の所有者決定へ移したため欠番です。

### Q1. 縦の混在泡で、中央境界と見た目をどう扱うか

- 案A（推奨）: ちょうど0.5はnegative側。混在列の見た目`DRAG`はSoul優先。既存判定との差が小さい。難度: 中、性能: 小。
- 案B: ちょうど0.5はpositive側。見た目はMagma優先。体感差は境界と見た目だけ。難度: 中、性能: 小。
- 案C: Entityの占有面積／直前位置で中央を選び、見た目は別の決定規則にする。自然になり得るが揺れ対策が必要。難度: 高、性能: 小。

**所有者の回答: 未決定**

### Q2. 両Componentが同時に燃え尽きた後を火にするか空気にするか

- 案A（推奨）: 両側除去後にバニラの火の生存条件を1回評価し、成立時だけ火。難度: 中、性能: 小。
- 案B: 常に空気とし、隣接火の後続更新へ任せる。難度: 低、性能: 最小。
- 案C: 常に火。単純だがバニラで火が生存できない状況との差が増える。難度: 低、性能: 最小。
- どの案でも両側の成功判定は変更前スナップショットから独立に行います。random評価順も回答時に固定します。

**所有者の回答: 未決定**

### Q3. 落下Componentの状態を何で運ぶか

- 案A（推奨）: バニラFallingBlockEntityへ最小限のnamespaced payload。再利用しやすいがAPI可否の比較検証が必要。難度: 中、性能: 小。
- 案B: 専用の落下Component Entity。制御しやすいが登録・同期・描画・保存が広い。難度: 高、性能: 小〜中。
- 案C: BlockStateだけ。固有状態や再結合情報を失うため、中核理念を満たさず採用非推奨。難度: 低、性能: 最小。

**所有者の回答: 未決定**

### Q6. 片側の氷が融けたとき、セルの水をどうするか

- 案A（推奨候補）: 残存単体がwaterlog可能ならwaterloggedにし、不可能ならバニラdimension規則に従う。難度: 中、ゲーム上は水が即時に残る。
- 案B: 両Componentがなくなるまで水を生成しない。難度: 低〜中、相方がある間は水が消える近似。
- 案C: 別の決定的セル単位規則。提案時に水流・dimension・相方への影響を比較する。難度・性能: 案次第。

**所有者の回答: 未決定**

### Q7. カボチャの切り抜きをbeta.4へ含めるか

- 案A: carved pumpkinのSlab／VerticalSlab、向き、種drop、鋏耐久までbeta.4へ含める。新state/model確認が必要。難度: 中〜高、性能: 小。
- 案B（推奨）: beta.4では延期し、未対応をrelease notesへ記載。中核基盤への影響を抑える。難度: 低、性能: なし。

**所有者の回答: 未決定**

### Q8. 追加parity監査項目をbeta.4へ含めるか

対象は、土壌追加変換、マングローブの根waterlogging、原木以外の柱方向、干草の落下軽減、羊毛の振動遮断、葉以外の特殊loot／ピグリン反応、静的な光・粒子・素材parityです。葉lootはQ8の対象ではなくbeta.4必須です。

- 案A: 各項目をbeta.4必須として実装・検証する。
- 案B: 個別に「beta.4必須／確認のみ／将来候補」を回答する（推奨）。
- 案C: すべて将来へ延期し、既知の差としてrelease notesへ記載する。

**所有者の回答: 未決定（項目別回答可）**

## Codex側で比較検証する技術選択

次は製品仕様を勝手に決めず、候補の実現性・回帰・性能を比較して所有者判断へ材料を出します。

- バニラ1.21.1巨大キノコの6方向state更新と、現行UV／modelでの外側・内側・柄・新露出面の表現可否。
- Soul Fireの接触Component特定とSoul優先を、最小のMixin／event変更で実現できる場所。
- FallingBlockEntityへの安全なpayload拡張と専用Entityの保存・同期・描画コスト比較。
- 泡の見た目`DRAG`とEntity forceを分離する方法、境界での安定性。
- 氷融解後のFluidState候補を、dimension・waterlogging・neighbor update別に比較。
- Minecraft 1.21.1標準BlockState NBT APIの正確な利用法、registry検証、property filter、fallback、旧world round trip。
- 現行全mappingの`PistonBehavior`とBE移動拒否を検証し、将来の片側移動方式はbeta.4と分離して調査。
- baseline PRで再現可能な測定手順、統計手法、シナリオ別の閾値案。
