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
            # top_slabに関するエントリ
            multipart.append({
                "when": { "top_slab": f"{mod}__{slab}" },
                "apply": { "model": f"{mod}:block/{slab}_top" }
            })
            # bottom_slabに関するエントリ
            multipart.append({
                "when": { "bottom_slab": f"{mod}__{slab}" },
                "apply": { "model": f"{mod}:block/{slab}" }
            })

    return {"multipart": multipart}

# 各スラブアイテムのテンプレートを生成する関数
def generate_vertical_slab_json(slab_name, mod_name, other_slabs):
    data = {
        "multipart": [
            {
                "when": { "facing": "south" },
                "apply": { "model": f"{mod_name}:block/{slab_name}", "uvlock": True, "y": 270 }
            },
            {
                "when": { "facing": "east" },
                "apply": { "model": f"{mod_name}:block/{slab_name}", "uvlock": True, "y": 180 }
            },
            {
                "when": { "facing": "north" },
                "apply": { "model": f"{mod_name}:block/{slab_name}", "uvlock": True, "y": 90 }
            },
            {
                "when": { "facing": "west" },
                "apply": { "model": f"{mod_name}:block/{slab_name}", "uvlock": True }
            }
        ]
    }

    # 他のスラブに対するエントリを追加
    for namespace, slab in other_slabs:
        data["multipart"].append({
            "when": { "is_double": True, "second_slab": f"{namespace}__{slab}", "facing": "north" },
            "apply": { "model": f"{namespace}:block/{slab}", "uvlock": True, "y": 270 }
        })
        data["multipart"].append({
            "when": { "is_double": True, "second_slab": f"{namespace}__{slab}", "facing": "west" },
            "apply": { "model": f"{namespace}:block/{slab}", "uvlock": True, "y": 180 }
        })
        data["multipart"].append({
            "when": { "is_double": True, "second_slab": f"{namespace}__{slab}", "facing": "south" },
            "apply": { "model": f"{namespace}:block/{slab}", "uvlock": True, "y": 90 }
        })
        data["multipart"].append({
            "when": { "is_double": True, "second_slab": f"{namespace}__{slab}", "facing": "east" },
            "apply": { "model": f"{namespace}:block/{slab}", "uvlock": True }
        })

    return data

# JSONフォーマッタ関数
def format_json(data):
    json_str = json.dumps(data, indent=4, ensure_ascii=False)
    # "when": {...}, "apply": {...} を1行にまとめる
    json_str = json_str.replace('"when": {\n                ', '"when": { ').replace('"apply": {\n                ', '"apply": { ').replace(',\n                ', ', ').replace('\n            }', ' }')
    return json_str

# 出力するファイルを生成する
def generate_blockstates(entries, is_vertical):
    for mod_name, slabs in entries.items():
        folder_path = os.path.join(assets_path, mod_name, 'blockstates')
        os.makedirs(folder_path, exist_ok=True)

        # 同じentries.json内の全てのスラブを他スラブとして利用
        other_slabs = [(other_mod, slab) for other_mod, slabs_list in entries.items() for slab in slabs_list if other_mod != mod_name or slab != slabs]

        for slab in slabs:
            if is_vertical == True:
                json_data = generate_vertical_slab_json(slab, mod_name, other_slabs)
            else:
                json_data = generate_slab_json(slab, mod_name, other_slabs)

            # JSONフォーマット
            formatted_json = format_json(json_data)

            # JSONファイルとして出力
            file_path = os.path.join(folder_path, f"{slab}.json")
            with open(file_path, 'w', encoding='utf-8') as json_file:
                json_file.write(formatted_json)

            print(f"Generated: {file_path}")

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
                        "model": f"minecraft:block/{slab.replace('_slab','')}" # ここは仮
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
enum_second_slab_path = os.path.join(sloves_path, 'block', 'enums', 'SecondSlab.java')

# 新しい内容を生成する関数
def generate_new_content(entries, is_vertical):
    lines = []
    if is_vertical == False:
        lines.append('    NONE("minecraft__air"),\n')
    for mod, slabs in entries.items():
        for slab in slabs:
            formatted_slab = slab.upper()
            line = f'    {mod.upper()}__{formatted_slab}("{mod.lower()}__{slab}"),\n'
            lines.append(line)
    if lines and is_vertical:
        lines[-1] = lines[-1].rstrip(',\n') + ';\n'  # 最後の行のカンマをセミコロンに変える
    return lines

def generate_enums_second_slab(entries, is_vertical):
    # javaファイルの内容を読み込む
    with open(enum_second_slab_path, 'r', encoding='utf-8') as java_file:
        java_content = java_file.readlines()

    if is_vertical:
        start_marker = '/* VERTICAL SLABS START MARKER */'
        end_marker = '/* VERTICAL SLABS END MARKER */'
    else:
        start_marker = '/* SLABS START MARKER */'
        end_marker = '/* SLABS END MARKER */'

    inside_section = False
    new_java_content = []
    for line in java_content:
        if start_marker in line:
            inside_section = True
            new_java_content.append(line)
            new_java_content.extend(generate_new_content(entries, is_vertical))  # 新しい内容を挿入
        elif end_marker in line:
            inside_section = False
            new_java_content.append(line)
        elif not inside_section:
            new_java_content.append(line)

    # 変更を反映してファイルに書き込む
    with open(enum_second_slab_path, 'w', encoding='utf-8') as java_file:
        java_file.writelines(new_java_content)
        print(f"Updated: {enum_second_slab_path}")

###################
#   models/item   #
###################

def generate_models_item(entries):
    for mod_name, slabs in entries.items():
        for slab in slabs:
            folder_path = os.path.join(assets_path, mod_name, 'models', 'item')
            os.makedirs(folder_path, exist_ok=True)

            json_data = {
                "parent": f"{mod_name}:block/{slab}"
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
#generate_blockstates(vertical_slabs, True)
#generate_enums_second_slab(slabs, False)
#generate_enums_second_slab(vertical_slabs, True)
#generate_models_item(slabs_models_item)
