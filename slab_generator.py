import os
import json
import re

# 実行場所を固定
os.chdir(os.path.dirname(os.path.abspath(__file__)))

# jsonからデータを読み込む
with open('slab_entries.json', 'r') as f:
    slab_entries = json.load(f)
with open('vertical_slab_entries.json', 'r') as f:
    vertical_slab_entries = json.load(f)
with open('slab_entries_models_item.json', 'r') as f:
    slab_model_item_entries = json.load(f)

###################
#   blockstates   #
###################

assets_path = os.path.join('src', 'main', 'resources', 'assets')

# 各スラブアイテムのテンプレートを生成する関数
def generate_slab_json(entries):
    multipart = []

    for mod, slabs in entries.items():
        for slab in slabs:
            multipart.append({
                "when": { "top_slab": f"{mod}__{slab}" },
                "apply": { "model": f"{mod}:block/{slab.replace('waxed_','')}_top" }
            })
            multipart.append({
                "when": { "bottom_slab": f"{mod}__{slab}" },
                "apply": { "model": f"{mod}:block/{slab.replace('waxed_','')}" }
            })

    return {"multipart": multipart}

# 各スラブアイテムのテンプレートを生成する関数
def generate_vertical_slab_json(entries):
    multipart = []

    for mod, slabs in entries.items():
        for slab in slabs:
            if slab == "smooth_stone_vertical_slab":
                multipart.append({
                    "when": { "AND": [{"axis": "z"}, {"positive_slab": f"{mod}__{slab}"}] },
                    "apply": { "model": f"{mod}:block/{slab}_positive", "uvlock": False, "y": 90 }
                })
                multipart.append({
                    "when": { "AND": [{"axis": "x"}, {"positive_slab": f"{mod}__{slab}"}] },
                    "apply": { "model": f"{mod}:block/{slab}_positive", "uvlock": False }
                })
                multipart.append({
                    "when": { "AND": [{"axis": "z"}, {"negative_slab": f"{mod}__{slab}"}] },
                    "apply": { "model": f"{mod}:block/{slab}_negative", "uvlock": False, "y": 90 }
                })
                multipart.append({
                    "when": { "AND": [{"axis": "x"}, {"negative_slab": f"{mod}__{slab}"}] },
                    "apply": { "model": f"{mod}:block/{slab}_negative", "uvlock": False }
                })
            else:
                multipart.append({
                    "when": { "AND": [{"axis": "z"}, {"positive_slab": f"{mod}__{slab}"}] },
                    "apply": { "model": f"{mod}:block/{slab.replace('waxed_','')}", "uvlock": True, "y": 270 }
                })
                multipart.append({
                    "when": { "AND": [{"axis": "x"}, {"positive_slab": f"{mod}__{slab}"}] },
                    "apply": { "model": f"{mod}:block/{slab.replace('waxed_','')}", "uvlock": True, "y": 180 }
                })
                multipart.append({
                    "when": { "AND": [{"axis": "z"}, {"negative_slab": f"{mod}__{slab}"}] },
                    "apply": { "model": f"{mod}:block/{slab.replace('waxed_','')}", "uvlock": True, "y": 90}
                })
                multipart.append({
                    "when": { "AND": [{"axis": "x"}, {"negative_slab": f"{mod}__{slab}"}] },
                    "apply": { "model": f"{mod}:block/{slab.replace('waxed_','')}", "uvlock": True}
                })

    return {"multipart": multipart}

# JSONフォーマッタ関数
def format_json(data):
    json_str = json.dumps(data, indent=4, ensure_ascii=False)
    # "when": {...}, "apply": {...} を1行にまとめる
    json_str = json_str.replace('"when": {\n                ', '"when": { ').replace('"apply": {\n                ', '"apply": { ').replace(',\n                ', ', ').replace('\n            }', ' }').replace('[\n                    {\n                        "axis"', '[{"axis"').replace('"\n                    }\n                ]','"}]').replace('"\n                    },     {\n                        "','"}, {"')
    return json_str

def generate_slab_blockstates():

    # フォルダを作る
    folder_path = os.path.join(assets_path, "sloves", 'blockstates')
    os.makedirs(folder_path, exist_ok=True)

    # JSONをフォーマット
    json_data = generate_slab_json(slab_entries)
    formatted_json = format_json(json_data)

    # JSONファイルとして出力
    file_path = os.path.join(folder_path, "double_slab_block.json")
    with open(file_path, 'w', encoding='utf-8') as json_file:
        json_file.write(formatted_json)

    print(f"Generated: {file_path}")

def generate_vertical_slab_blockstates():

    # フォルダを作る
    folder_path = os.path.join(assets_path, "sloves", 'blockstates')
    os.makedirs(folder_path, exist_ok=True)

    # JSONをフォーマット
    json_data = generate_vertical_slab_json(vertical_slab_entries)
    formatted_json = format_json(json_data)

    # JSONファイルとして出力
    file_path = os.path.join(folder_path, "double_vertical_slab_block.json")
    with open(file_path, 'w', encoding='utf-8') as json_file:
        json_file.write(formatted_json)

    print(f"Generated: {file_path}")

    # 個別のjsonも作っちゃおう
    for mod, slabs in vertical_slab_entries.items():
        for slab in slabs:
            if slab == "smooth_stone_vertical_slab":
                data = {
                    "multipart": [
                        {
                            "when": { "facing": "south" },
                            "apply": { "model": f"{mod}:block/{slab}_positive", "uvlock": False, "y": 90 }
                        },
                        {
                            "when": { "facing": "east" },
                            "apply": { "model": f"{mod}:block/{slab}_positive", "uvlock": False }
                        },
                        {
                            "when": { "facing": "north" },
                            "apply": { "model": f"{mod}:block/{slab}_negative", "uvlock": False, "y": 90 }
                        },
                        {
                            "when": { "facing": "west" },
                            "apply": { "model": f"{mod}:block/{slab}_negative", "uvlock": False }
                        }
                    ]
                }
            else:
                data = {
                    "multipart": [
                        {
                            "when": { "facing": "south" },
                            "apply": { "model": f"{mod}:block/{slab.replace('waxed_','')}", "uvlock": True, "y": 270 }
                        },
                        {
                            "when": { "facing": "east" },
                            "apply": { "model": f"{mod}:block/{slab.replace('waxed_','')}", "uvlock": True, "y": 180 }
                        },
                        {
                            "when": { "facing": "north" },
                            "apply": { "model": f"{mod}:block/{slab.replace('waxed_','')}", "uvlock": True, "y": 90 }
                        },
                        {
                            "when": { "facing": "west" },
                            "apply": { "model": f"{mod}:block/{slab.replace('waxed_','')}", "uvlock": True }
                        }
                    ]
                }

            # フォーマット
            formatted_json = format_json(data)

            # JSONファイルとして出力
            file_path = os.path.join(folder_path, f"{slab}.json")
            with open(file_path, 'w', encoding='utf-8') as json_file:
                json_file.write(formatted_json)

            print(f"Generated: {file_path}")

####################################
#   blockstates (original slabs)   #
####################################

def generate_mod_slab_blockstates():
    for mod, slabs in slab_entries.items():
        if mod == "minecraft":
            continue

        # フォルダを作る
        folder_path = os.path.join(assets_path, mod, 'blockstates')
        os.makedirs(folder_path, exist_ok=True)

        for slab in slabs:
            json_data = {
                "variants": {
                    "type=bottom": {
                        "model": f"{mod}:block/{slab}"
                    },
                    "type=double": {
                        "model": f"minecraft:block/{slab.replace('_slab','_block' if 'bamboo' in slab or 'grass' in slab or 'amethyst' in slab else '').replace('_brick','_bricks')}" # ここは仮
                    },
                    "type=top": {
                        "model": f"{mod}:block/{slab}_top"
                    }
                }
            }

            # 出力
            file_path = os.path.join(folder_path, f"{slab}.json")
            with open(file_path, 'w', encoding='utf-8') as json_file:
                json.dump(json_data, json_file, indent=4)

            print(f"Generated: {file_path}")

#############
#   enums   #
#############

sloves_path = os.path.join('src', 'main', 'java', 'com', 'forestotzka', 'yurufu', 'sloves')
enums_path = os.path.join(sloves_path, 'block', 'enums')
enums_slab_type_path = os.path.join(enums_path, 'CustomSlabType.java')
enums_vertical_slab_type_path = os.path.join(enums_path, 'CustomVerticalSlabType.java')

# 新しい内容を生成する関数
def generate_new_content(is_vertical):
    lines = ['    NONE("minecraft__air"),\n']
    if is_vertical:
        entries = vertical_slab_entries
    else:
        entries = slab_entries
    for mod, slabs in entries.items():
        for slab in slabs:
            formatted_slab = slab.upper()
            line = f'    {mod.upper()}__{formatted_slab}("{mod.lower()}__{slab}"),\n'
            lines.append(line)
    if lines:
        lines[-1] = lines[-1].rstrip(',\n') + ';\n'  # 最後の行のカンマをセミコロンに変える
    return lines

def generate_enums_slab_type(is_vertical):
    if is_vertical:
        file_path = enums_vertical_slab_type_path
    else:
        file_path = enums_slab_type_path
    # javaファイルの内容を読み込む
    with open(file_path, 'r', encoding='utf-8') as java_file:
        java_content = java_file.readlines()

    start_marker = '/* START MARKER */'
    end_marker = '/* END MARKER */'

    inside_section = False
    new_java_content = []
    for line in java_content:
        if start_marker in line:
            inside_section = True
            new_java_content.append(line)
            new_java_content.extend(generate_new_content(is_vertical))
        elif end_marker in line:
            inside_section = False
            new_java_content.append(line)
        elif not inside_section:
            new_java_content.append(line)

    # 変更を反映してファイルに書き込む
    with open(file_path, 'w', encoding='utf-8') as java_file:
        java_file.writelines(new_java_content)
        print(f"Updated: {file_path}")

####################
#   models/block   #
####################

def generate_models_block():
    for mod, slabs in slab_entries.items():
        if mod == "minecraft":
            continue
        for slab in slabs:
            if "waxed_" in slab or slab == "grass_slab":
                continue
            folder_path = os.path.join(assets_path, mod, 'models', 'block')
            os.makedirs(folder_path, exist_ok=True)

            block = slab.replace('_slab', '').replace('brick', 'bricks').replace('_tile', '_tiles')
            if block in ("oak", "spruce", "birch", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "crimson", "warped"):
                block = block + "_planks"
            if block in ("purpur", "quartz", "amethyst") or "bamboo" in block:
                block = block + "_block"
            if block in ("smooth_sandstone", "smooth_red_sandstone"):
                block = block.replace("smooth_", "") + "_top"
            if block == "smooth_quartz":
                block = "quartz_block_bottom"
            if block == "petrified_oak":
                block = "oak_planks"

            json_data = {
                "parent": f"minecraft:block/slab",
                "textures": {
                    "bottom": f"minecraft:block/{block.replace('wood', 'log').replace('hyphae','stem')}{'_top' if (block in ('quartz_block', 'basalt', 'polished_basalt') or block.endswith('_log') or block.endswith('_stem') or 'bamboo' in block) else ''}{'_bottom' if block == 'reinforced_deepslate' else ''}",
                    "side": f"minecraft:block/{block.replace('wood', 'log').replace('hyphae','stem')}{'_side' if (block in ('quartz_block', 'basalt', 'polished_basalt') or block == 'reinforced_deepslate') else ''}",
                    "top": f"minecraft:block/{block.replace('wood', 'log').replace('hyphae','stem')}{'_top' if (block in ('quartz_block', 'basalt', 'polished_basalt', 'reinforced_deepslate') or block.endswith('_log') or block.endswith('_stem') or 'bamboo' in block) else ''}"
                }
            }
            json_data_top = {
                "parent": f"minecraft:block/slab_top",
                "textures": {
                    "bottom": f"minecraft:block/{block.replace('wood', 'log').replace('hyphae','stem')}{'_top' if (block in ('quartz_block', 'basalt', 'polished_basalt') or block.endswith('_log') or block.endswith('_stem') or 'bamboo' in block) else ''}{'_bottom' if block == 'reinforced_deepslate' else ''}",
                    "side": f"minecraft:block/{block.replace('wood', 'log').replace('hyphae','stem')}{'_side' if (block in ('quartz_block', 'basalt', 'polished_basalt') or block == 'reinforced_deepslate') else ''}",
                    "top": f"minecraft:block/{block.replace('wood', 'log').replace('hyphae','stem')}{'_top' if (block in ('quartz_block', 'basalt', 'polished_basalt', 'reinforced_deepslate') or block.endswith('_log') or block.endswith('_stem') or 'bamboo' in block) else ''}"
                }
            }

            # JSONファイルとして出力
            file_path = os.path.join(folder_path, f"{slab}.json")
            with open(file_path, 'w', encoding='utf-8') as json_file:
                json.dump(json_data, json_file, ensure_ascii=False, indent=4)

            print(f"Generated: {file_path}")

            file_path = os.path.join(folder_path, f"{slab}_top.json")
            with open(file_path, 'w', encoding='utf-8') as json_file:
                json.dump(json_data_top, json_file, ensure_ascii=False, indent=4)

            print(f"Generated: {file_path}")

def generate_vertical_models_block():
    for mod, slabs in vertical_slab_entries.items():
        for slab in slabs:
            if "waxed_" in slab:
                continue
            folder_path = os.path.join(assets_path, mod, 'models', 'block')
            os.makedirs(folder_path, exist_ok=True)

            block = slab.replace('_vertical_slab', '').replace('brick', 'bricks').replace('_tile', '_tiles')
            if block in ("oak", "spruce", "birch", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo", "crimson", "warped"):
                block = block + "_planks"
            if block in ("purpur", "quartz", "amethyst"):
                block = block + "_block"
            if block in ("smooth_sandstone", "smooth_red_sandstone"):
                block = block.replace("smooth_", "") + "_top"
            if block == "smooth_quartz":
                block = "quartz_block_bottom"
            if block == "petrified_oak":
                block = "oak_planks"

            if block == "smooth_stone":
                json_data_positive = {
                    "parent": f"sloves:block/vertical_slab_positive",
                    "textures": {
                        "side": f"sloves:block/{block}_vertical_slab_side",
                        "east": f"minecraft:block/{block}",
                        "west": f"minecraft:block/{block}"
                    }
                }
                json_data_negative = {
                    "parent": f"sloves:block/vertical_slab_negative",
                    "textures": {
                        "side": f"sloves:block/{block}_vertical_slab_side",
                        "east": f"minecraft:block/{block}",
                        "west": f"minecraft:block/{block}"
                    }
                }

                # JSONファイルとして出力
                file_path = os.path.join(folder_path, f"{slab}_positive.json")
                with open(file_path, 'w', encoding='utf-8') as json_file:
                    json.dump(json_data_positive, json_file, ensure_ascii=False, indent=4)

                print(f"Generated: {file_path}")

                file_path = os.path.join(folder_path, f"{slab}_negative.json")
                with open(file_path, 'w', encoding='utf-8') as json_file:
                    json.dump(json_data_negative, json_file, ensure_ascii=False, indent=4)

                print(f"Generated: {file_path}")

            else:
                json_data = {
                    "parent": f"sloves:block/vertical_slab",
                    "textures": {
                        "side": f"minecraft:block/{block}{'_side' if block == 'quartz_block' else ''}",
                        "east": f"minecraft:block/{block}{'_top' if block == 'quartz_block' else ''}",
                        "west": f"minecraft:block/{block}{'_top' if block == 'quartz_block' else ''}"
                    }
                }

                # JSONファイルとして出力
                file_path = os.path.join(folder_path, f"{slab}.json")
                with open(file_path, 'w', encoding='utf-8') as json_file:
                    json.dump(json_data, json_file, ensure_ascii=False, indent=4)

                print(f"Generated: {file_path}")


###################
#   models/item   #
###################

def generate_models_item(entries):
    for mod_name, slabs in entries.items():
        for slab in slabs:
            folder_path = os.path.join(assets_path, mod_name, 'models', 'item')
            os.makedirs(folder_path, exist_ok=True)

            if slab == "smooth_stone_vertical_slab":
                json_data = {
                    "parent": f"{mod_name}:block/{slab}_negative"
                }
            else:
                json_data = {
                    "parent": f"{mod_name}:block/{slab.replace('waxed_','')}"
                }

            # JSONファイルとして出力
            file_path = os.path.join(folder_path, f"{slab}.json")
            with open(file_path, 'w', encoding='utf-8') as json_file:
                json.dump(json_data, json_file, ensure_ascii=False, indent=4)

            print(f"Generated: {file_path}")


###########
#   RUN   #
###########

generate_slab_blockstates()
generate_mod_slab_blockstates()
generate_vertical_slab_blockstates()
generate_models_block()
generate_vertical_models_block()
generate_enums_slab_type(True)
generate_enums_slab_type(False)
generate_models_item(slab_model_item_entries)
