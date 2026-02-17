# Elytra Time

[English] | [日本語](#日本語)

A Fabric MOD that calculates and displays the remaining flight time of your Elytra, taking into account its durability and Unbreaking enchantment level.

## Features
- **Tooltip Display**: See the remaining flight time directly on the Elytra item tooltip.
- **HUD Display**: A customizable HUD showing your flight time while gliding.
    - **Visual Alerts**: The HUD blinks when the remaining time falls below the warning threshold.
    - **Flexible Alignment**: Supports Left, Center, and Right alignment.
- **Low Durability Warnings**: Get alerted when your Elytra is about to break.
    - Customizable threshold (in seconds).
    - Multiple alert types: Chat, Action Bar, or Title screen.
- **Keybindings**: 
    - Press **F4** (default) to report the current flight time in chat.
    - Press **O** (default) to open the configuration menu.
- **Highly Customizable**:
    - **Time Formatting**: Support for various formats (e.g., `1m 05s` or `65s`), including zero-padding and total seconds only mode.
    - **Dynamic Colors**: Adjust colors for safe, warning, and critical durability levels based on percentage thresholds.
    - **HUD Positioning**: Precise control over X/Y position and scale.

## Requirements
- Fabric Loader
- Fabric API
- Fabric Language Kotlin
- Cloth Config API
- Mod Menu (Recommended for accessing settings)

## Credits
- Original Author: **Tomddev**
- Current Maintainer: **inorganic**

## License
Elytra Time is licensed under **GNU LGPL v3**.

---

<a name="日本語"></a>
# Elytra Time

エリトラの耐久値と「耐久力」エンチャントのレベルに基づき、残り飛行可能時間を計算して表示するMODです。

## 主な機能
- **ツールチップ表示**: エリトラのツールチップに直接残り時間を表示します。
- **HUD表示**: 飛行中に残り時間を画面上に表示します。
- **視覚的な警告**: 残り時間が警告しきい値を下回ると、HUDが点滅して知らせます。
- **配置設定**: 左揃え、中央揃え、右揃えに対応しています。
- **耐久度低下の警告**: エリトラの耐久度が少なくなった際に通知します。
    - 警告を表示するしきい値（秒数）をカスタマイズ可能。
    - 通知方法：チャット、アクションバー、またはタイトル表示。
- **キー割り当て**: 
    - **F4キー**（デフォルト）: チャット欄に現在の飛行可能時間を報告します。
    - **Oキー**（デフォルト）: 設定画面を開きます。
- **高度なカスタマイズ**:
    - **時間の表示形式**: `1分 05秒` や `65秒` など、柔軟な表示形式に対応。ゼロ埋めや秒数のみの表示も可能です。
    - **動的な文字色**: 耐久度の割合（%）に応じて、安全・警告・危険の状態ごとに色を設定できます。
    - **HUDの位置とサイズ**: 座標（X, Y）やスケールを細かく調整可能です。

## 必要要件
- Fabric Loader
- Fabric API
- Fabric Language Kotlin
- Cloth Config API
- Mod Menu (設定画面へのアクセスに推奨)

## クレジット
- 元の作者: **Tomddev**
- 現在の開発者: **inorganic**

## ライセンス
このMODは **GNU LGPL v3** ライセンスの下で公開されています。
